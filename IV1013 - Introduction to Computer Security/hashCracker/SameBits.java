public class SameBits {
	static String hashOne;
	static String hashTwo;

	public static void main(String[] args) {
		parseArgs(args);
		int similarBits = compareBits(hashOne, hashTwo);
		System.out.println("Similar bits: " + similarBits);
	}

	private static int compareBits(String hashOneBinary2, String hashTwoBinary2) {
		int similarBits = 0;
		char[] hashOneBinaryCharArr = hashOneBinary2.toCharArray();
		char[] hashTwoBinaryCharArr = hashTwoBinary2.toCharArray();
		int i = 0;
		for(char c : hashOneBinaryCharArr)
			if(hashTwoBinaryCharArr[i++] == c && c != '\0')
				similarBits++;
		return similarBits;
	}

	private static void parseArgs(String[] args) {
		String hashOneParam = args[0];
		String hashTwoParam = args[1];
		hashOne = hex_to_binary(hashOneParam);
		hashTwo = hex_to_binary(hashTwoParam);
	}

	/*Taken from user3184391's answer at https://stackoverflow.com/questions/8640803/convert-hex-string-to-binary-string*/
	public static String zero_pad_bin_char(String bin_char){
		int len = bin_char.length();
		if(len == 8) return bin_char;
		String zero_pad = "0";
		for(int i=1;i<8-len;i++) zero_pad = zero_pad + "0"; 
		return zero_pad + bin_char;
	}

	/*Taken from user3184391's answer at https://stackoverflow.com/questions/8640803/convert-hex-string-to-binary-string*/
	public static String hex_to_binary(String hex) {
		String hex_char,bin_char,binary;
		binary = "";
		int len = hex.length()/2;
		for(int i=0;i<len;i++){
			hex_char = hex.substring(2*i,2*i+2);
			int conv_int = Integer.parseInt(hex_char,16);
			bin_char = Integer.toBinaryString(conv_int);
			bin_char = zero_pad_bin_char(bin_char);
			if(i==0) binary = bin_char; 
			else binary = binary+bin_char;
		}
		return binary;
	}

}
