
import java.security.*;

public class NoTrials {
	public static void main(String[] args) {

		String digestAlgorithm = "SHA-256";
		String textEncoding = "UTF-8";
		String inputText = "";
		for(String arg : args)
			inputText = inputText.concat(" "+ arg);
		inputText = inputText.replaceFirst(" ", "");
		
		try {
			MessageDigest md = MessageDigest.getInstance(digestAlgorithm);

			byte[] inputBytes = inputText.getBytes(textEncoding);
			md.update(inputBytes);
			byte[] digest1 = md.digest();

			BruteForce	bf = new BruteForce(' ', 'z', 2000);
			int nrOfTrials  = 0;
			byte[] digest2 = null;
			
			while(true) {
				nrOfTrials++;
				String str = new String(bf.run());
				md.update(str.getBytes());
				digest2 = md.digest();

				if(digestEqual(digest1, digest2)) {
					printDigest(inputText, md.getAlgorithm(), digest1);
					printDigest(str, md.getAlgorithm(), digest2);
					break;
				}

			}
			System.out.println("No trials: " + nrOfTrials);

		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algorithm \"" + digestAlgorithm  + "\" is not available");
		} catch (Exception e) {
			System.out.println("Exception "+e);
		}
	}


	private static boolean digestEqual(byte[] digest1, byte[] digest2) {
		boolean equal = true;
		for (int i=0; i<2; i++) {
			if(digest1[i] != digest2[i]) {
				equal = false;
				break;
			}
		}
		return equal;
	}


	public static void printDigest(String inputText, String algorithm, byte[] digest) {
		System.out.println("Digest for the message \"" + inputText +"\", using " + algorithm + " is:");
		for (int i=0; i<2; i++)
			System.out.format("%02x", digest[i]&0xff);
		System.out.println();
	}
}