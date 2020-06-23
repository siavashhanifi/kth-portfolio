import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import org.jscience.mathematics.vector.*;
import org.jscience.mathematics.number.*;

public class HillDecipher {
	private static int radix;
	private static int blocksize;
	private static String keyFile;
	private static String plainTextFileName;
	private static String cipherFileName;

	public static void main(String[] args) throws IOException {
		try {
		parseLaunchArguments(args);
		}catch(Exception e) {
			System.out.println("Invalid parameters: parameters must be specified in the format below.");
			System.out.println("HillDecipher <radix> <blocksize> <keyfile> <plainfile> <cipherfile>");
			System.exit(1);
		}
		try {
		Matrix<Rational> decryptionKey = calculateModInverseKeyFromFile(new File(keyFile), radix);
		}catch(Exception e) {
			System.out.println("Key not invertible.");
			System.exit(1);
		}
		Matrix<Rational> decryptionKey = calculateModInverseKeyFromFile(new File(keyFile), radix);
		int[] paddedIntSequence = loadIntSequenceFromFile(cipherFileName);
		Vector<Rational>[] vectors = divideIntSeqIntoVectors(paddedIntSequence, blocksize);
		int[] decryptedPaddedIntSequence = decryptIntSequence(vectors, decryptionKey, radix);
		int[] finalIntSequence = removePadding(decryptedPaddedIntSequence, blocksize);
		String decryptedIntSeqStr = convertIntArrToStr(finalIntSequence);
		System.out.println(decryptedIntSeqStr);
		writeIntArrToFile(plainTextFileName, decryptedIntSeqStr);
	}
	
	
	private static int[] removePadding(int[] paddedIntSequence, int blocksize) {
		
		int prev = paddedIntSequence[paddedIntSequence.length - 1];
		int next;
		for(int i = paddedIntSequence.length - 2; i > paddedIntSequence.length - 1 - blocksize; i--) {
			next = paddedIntSequence[i];
			if(next != prev)
				return paddedIntSequence;
		}


		int paddingNumber = (int)prev;
		int[] intSequence = new int[paddedIntSequence.length - blocksize - paddingNumber];
		int i = 0;
		for(int c : paddedIntSequence) {
			if(i == paddedIntSequence .length - blocksize - paddingNumber)
				break;
			else
				intSequence[i++] = c;
		}
		return intSequence ;

	}

	private static Matrix<Rational> calculateModInverseKeyFromFile(File file, int mod) throws IOException {
		int nrOfRows = countRows(file);
		int nrOfColumns = countColumns(file);
		ModMatrix modMatrix = new ModMatrix(nrOfRows, nrOfColumns);
		modMatrix.setMod(new BigInteger(String.valueOf(mod))); 
		loadDataIntoMatrix(modMatrix, file);
		modMatrix = modMatrix.inverse(modMatrix);
		Matrix<Rational> inversekey = modMatrixToJscienceMatrix(modMatrix);
		return inversekey;
	}

	private static int[] decryptIntSequence(Vector<Rational>[] vectors, Matrix<Rational> decipherKey,int radix) {
		int[] encryptedIntSeq = new int[vectors.length * vectors[0].getDimension()];
		int i = 0;
		for(Vector v : vectors) {
			Vector cipherVector = decipherKey.times(v);
			for(int j = 0; j < cipherVector.getDimension(); j++) {
				int value = ((Rational)cipherVector.get(j)).intValue();
				encryptedIntSeq[i++] = value % radix;
			}
		}
		return encryptedIntSeq;
	}
	
	
	private static int[] padIntSequence(int[] intSequence) {
		int noChars = intSequence.length;
		int noCharsModBlocksize = noChars % blocksize;
		int blocks = noChars / blocksize;

		if(noCharsModBlocksize == 0)
			return intSequence;
		else if(blocks * blocksize < noChars) {
			int paddingNeeded = (blocksize - noCharsModBlocksize); 
			int[] paddedIntSequence = new int[noChars + paddingNeeded + blocksize];
			int i = 0;
			int j;
			for(int value : intSequence) 
				paddedIntSequence[i++] = value;
			for(j = i; j < noChars + paddingNeeded + blocksize; j++)
				paddedIntSequence[j] = paddingNeeded;
			return paddedIntSequence;
		}

		else {
			int paddingNeeded = (blocksize - noCharsModBlocksize); 
			int[] paddedIntSequence = new int[blocks * blocksize + blocksize];
			int i = 0;
			for(int value : intSequence)
				paddedIntSequence[i++] = value;
			for(int j = i; j < paddingNeeded + blocksize; j++)
				paddedIntSequence[j] = paddingNeeded;
			return paddedIntSequence;
		}
	}

	private static String convertIntArrToStr(int[] encryptedIntSeq) {
		StringBuilder sb = new StringBuilder("");
		int i = 0;
		for(int value: encryptedIntSeq) {
			sb.append(value);
			sb.append(" ");
		}
		return sb.toString();
	}

	private static Vector<Rational>[] divideIntSeqIntoVectors(int[] intSequence, int blocksize2) {

		Rational[][] intVector = new Rational[intSequence.length/blocksize][blocksize];

		int i = 0;
		int j = 0;

		for(int value : intSequence) {
			if(j == blocksize) {//block filled
				j = 0;
				i++; //go to next block
			}
			intVector[i][j++] = Rational.valueOf(String.valueOf(value));

		}

		Vector<Rational> vectors[] = new Vector[intSequence.length/blocksize];
		i = 0;
		for(Rational[] r: intVector) {
			vectors[i++] = DenseVector.valueOf(r);
		}

		return vectors;
	}


	private static int[] loadIntSequenceFromFile(String encodedPlainTextFileName2) throws IOException {
		String [] values = new String(Files.readAllBytes(Paths.get(encodedPlainTextFileName2))).trim().split(" ");

		int[] intSequence = new int[values.length];
		int i = 0;
		for(String s: values) {
			intSequence[i++] = Integer.parseInt(s);
		}
		return intSequence;
	}

	private static int paddingInfo(int[] intSequence, int blocksize) {
		int noChars = intSequence.length;
		int noCharsModBlocksize = noChars % blocksize;
		if(noCharsModBlocksize == 0)
			return 0;
		else {
			int paddingNumber = (blocksize - noCharsModBlocksize); 
			return paddingNumber;
		}
	}

	private static int[] encryptIntSequence(Vector<Rational>[] vectors, Matrix<Rational> decipherKey,int radix, int paddingNumber) {
		int[] encryptedIntSeq = new int[vectors.length * vectors[0].getDimension()];
		int i = 0;
		for(Vector v : vectors) {
			Vector cipherVector = decipherKey.times(v);
			for(int j = 0; j < cipherVector.getDimension(); j++) {
				int value = ((Rational)cipherVector.get(j)).intValue();
				encryptedIntSeq[i++] = value % radix;
			}
		}
		return encryptedIntSeq;
	}


	private static Matrix<Rational> loadKeyFromFile(File file, int mod) throws IOException {
		int nrOfRows = countRows(file);
		int nrOfColumns = countColumns(file);
		ModMatrix modMatrix = new ModMatrix(nrOfRows, nrOfColumns);
		modMatrix.setMod(new BigInteger(String.valueOf(mod))); 
		loadDataIntoMatrix(modMatrix, file);
		Matrix<Rational> inversekey = modMatrixToJscienceMatrix(modMatrix);
		return inversekey;
	}


	private static Matrix<Rational> modMatrixToJscienceMatrix(ModMatrix modMatrix) {
		BigInteger[][] modMatrixData = modMatrix.getData();
		Rational[][] modMatrixDataJscience = new Rational[modMatrix.getNrows()][modMatrix.getNcols()];
		for(int i = 0; i < modMatrix.getNrows(); i++) {
			for(int j=0; j < modMatrix.getNcols(); j++) {
				modMatrixDataJscience[i][j] = Rational.valueOf(modMatrixData[i][j].toString());
			}
		}
		return DenseMatrix.valueOf(modMatrixDataJscience);
	}

	private static void loadDataIntoMatrix(ModMatrix modMatrix, File file) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		for(int i = 0; i<modMatrix.getNrows(); i++) {
			for(int j=0; j<modMatrix.getNcols(); j++) {
				BigInteger value = new BigInteger(scanner.next());
				modMatrix.setValueAt(i, j, value);
			}
		}
	}


	private static int countColumns(File file) throws IOException {
		int columns = 0; 
		Scanner scanner = new Scanner(file);
		String line = scanner.nextLine();
		String[] rowElements = line.split(" ");
		columns = rowElements.length;
		scanner.close();
		return columns;
	}

	private static int countRows(File file) throws FileNotFoundException {
		int rows = 0;
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			rows++;
			scanner.nextLine();
		}
		scanner.close();
		return rows;
	}

	private static void writeIntArrToFile(String plainTextFileName, String cipherText) throws IOException {
		FileWriter myWriter = new FileWriter(plainTextFileName);
		myWriter.write(cipherText);
		myWriter.close();
	}

	private static void parseLaunchArguments(String[] args) {
		radix = Integer.parseInt(args[0]);
		blocksize = Integer.parseInt(args[1]);
		keyFile = args[2];
		plainTextFileName = args[3];
		cipherFileName = args[4];
	}

}

