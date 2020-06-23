import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

import org.jscience.mathematics.vector.*;
import org.jscience.mathematics.number.*;


public class HillKeys {
	private static int radix;
	private static int blocksize;
	private static String keyfile;
	private static Matrix<Rational> generatedKey;
	
	public static void main(String[] args) throws IOException {
		try {
		parseLaunchArguments(args);
		}catch(Exception e) {
			System.out.println("Invalid parameters: parameters must be specified in the format below.");
			System.out.println("HillDecipher <radix> <blocksize> <keyfile> <plainfile> <cipherfile>");
			System.exit(1);
		}

		generateKey(radix, blocksize);
		writeToKeyFile(generatedKey);
	}

	private static void writeToKeyFile(Matrix generatedKey) throws IOException {
		String keyToWrite = formatKey(generatedKey);
		FileWriter myWriter = new FileWriter(keyfile);
		myWriter.write(keyToWrite);
		myWriter.close();
	}

	private static String formatKey(Matrix generatedKey) {
		String jScienceKey = generatedKey.toString();
		String correctFormat = jScienceKey.replace("{", "");
		correctFormat = correctFormat.replace("}", "");
		correctFormat = correctFormat.replace(",", "");
		correctFormat = correctFormat.replace("/1", "");
		return correctFormat;
	}

	private static void generateKey(int radix, int blocksize) {

		//Rational[][] arr = new Rational[blocksize][blocksize];
		ModMatrix modMatrix = new ModMatrix(blocksize, blocksize);

		for(int i = 0; i < blocksize;i++) {
			for(int j = 0; j<blocksize; j++) {
				Random intElement = new Random();
				String number = Integer.toString(intElement.nextInt(radix));
				modMatrix.setValueAt(i, j, new BigInteger(number));
			}
		}
		//generatedKey = DenseMatrix.valueOf(arr);
		try{
			modMatrix.inverse(modMatrix);
			generatedKey = modMatrixToJscienceMatrix(modMatrix);
		}catch(Exception e) {
			generateKey(blocksize,blocksize);
		}
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

	private static void parseLaunchArguments(String[] args) {
		radix = Integer.parseInt(args[0]);
		blocksize = Integer.parseInt(args[1]);
		keyfile = args[2];
	}
	
}

