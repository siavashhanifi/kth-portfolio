import java.util.Arrays;
/*Code is a slightly modified version of Roland Illig s answer at 
 * https://codereview.stackexchange.com/questions/10338/producing-every-string-of-a-given-length-using-some-range-of-characters
 * */
public class BruteForce {
	
	final int min;
	final int max;
	final int stringLength;

	/**
	 * One more element than <i>stringLength</i>,
	 * to efficiently check for overflow.
	 */
	private final int[] chars;

	public BruteForce(char min, char max, int len) {
		this.min = min;
		this.max = max;
		this.stringLength = len;

		chars = new int[stringLength + 1];
		Arrays.fill(chars, 1, chars.length, min);
	}

	public char[] run() {
		while (chars[0] == 0) {
			increment();
			return charArr();
		}
		return run();
	}

	private void increment() {
		for (int i = chars.length - 1; i >= 0; i--) {
			if (chars[i] < max) {
				chars[i]++;
				return;

			}
			chars[i] = min;
		}
	}

	private char[] charArr() {
		char[] charArr = new char[chars.length];
		for (int i = 1; i < chars.length; i++) {
			charArr[i] =(char) chars[i];
		}
		return charArr;
	}

}