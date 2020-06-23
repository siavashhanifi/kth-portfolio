package model;

import java.io.IOException;

import integration.RandomWordLoader;


/**
 * @author Siavash
 *
 */
class Round {
	/**
	 * The correct word as a string
	 */
	private String wordStr = null;
	private int allowedFailsLeft = 0;
	/**
	 * The correct word as a char array
	 */
	private char[] wordCharArr = null;
	/**
	 * An char array with the so far guessed letters and unguessed letters as '0's 
	 */ 
	private char[] soFarGuessedWord = null;
	
	/**
	 * Creates a new round with a new word
	 * @throws IOException
	 */
	Round() throws IOException  {
		RandomWordLoader randomWordLoader = new RandomWordLoader(); 
		this.wordStr = randomWordLoader.getWord();
		this.wordCharArr = wordStr.toCharArray();
		this.allowedFailsLeft = wordCharArr.length;
		this.soFarGuessedWord = new char[wordCharArr.length];
		for(int i = 0; i < wordCharArr.length;i++)
			soFarGuessedWord[i] = '0';
	}
	
	/**
	 * Matches a letter to the correct word and updates the soFarGuessedWord
	 * @param received
	 */
	void matchLetter(char received) {
			int i=0;
			boolean failedAttempt = true;
			for(char c : wordCharArr) {
				if(c == received) {
					this.soFarGuessedWord[i] = c;
					failedAttempt = false;
				}
				i++;
			}
			if(failedAttempt)
				this.allowedFailsLeft--;
	}
	
	/**
	 * Matches a word to the current correct word
	 * @param received
	 */
	void matchWord(String received) {
			if(this.wordStr.compareTo(received) == 0) {
				this.soFarGuessedWord = this.wordCharArr;
			}
			else
				this.allowedFailsLeft--;
	}
	
	/**
	 * Returns true if all the fails are consumed
	 * @return
	 */
	public boolean failsConsumed() {
		if(allowedFailsLeft == 0)
			return true;
		else return false;
	}
	
	/**
	 * Returns true if the current correct word has been guessed.
	 * @return
	 */
	boolean wordGuessed() {
		String guessedWord = new String(soFarGuessedWord);
		if(guessedWord.compareTo(this.wordStr) == 0)
			return true;
		else
			return false;
	}

	/**
	 *Return
	 * @return
	 */
	public int failAttemptsLeft() {
		return this.allowedFailsLeft;
	}
	
	/**
	 * @return
	 */
	public String getCorrectWord() {
		return this.wordStr;
	}
	
	/**
	 * @return
	 */
	public char[] getSoFarGuessedWord(){
		return this.soFarGuessedWord;
	}


}
