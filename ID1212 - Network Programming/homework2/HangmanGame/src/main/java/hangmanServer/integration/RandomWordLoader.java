package hangmanServer.integration;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Random;

import com.google.common.io.Resources;


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
		URL filePath = Resources.getResource("words.txt");
		RandomAccessFile file = new RandomAccessFile(filePath.getFile(),"r");
		int nrOfLines = (int) file.length();
		Random random = new Random();
		int randomLine = random.nextInt(nrOfLines);
		file.seek(randomLine);
		file.readLine();
		this.word = file.readLine();
		System.out.println(word);
		file.close();
	}

	/**
	 * @return
	 */
	public String getWord() {
		return this.word;
	}

}
