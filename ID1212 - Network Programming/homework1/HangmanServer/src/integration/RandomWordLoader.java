package integration;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;


/**
 * @author Siavash
 *
 */
public class RandomWordLoader {
	private String word = null;
	
	/**
	 * @throws IOException
	 */
	public RandomWordLoader() throws IOException {
		String fileName = "words.txt";
		RandomAccessFile file = new RandomAccessFile(fileName,"r");
		int nrOfLines = (int) file.length();
		Random random = new Random();
		int randomLine = random.nextInt(nrOfLines);
		file.seek(randomLine);
		file.readLine();
		this.word = file.readLine();
		file.close();
	}

	/**
	 * @return
	 */
	public String getWord() {
		return this.word;
	}

}
