package integration;

import java.io.Serializable;

/**
 * @author Siavash
 *
 */
public class GamestatusDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private char[] soFarGuessedWord = null;
	private String correctWord = "cheater!";
	private int allowedFailsLeft = 0;
	private int totalScore = 0;
	private int roundStatus = 0;
	 
	/**
	 * @param soFarGuessedWord
	 * @param allowedFailsLeft
	 * @param totalScore
	 */
	public void updateRoundVars(char[]soFarGuessedWord, int allowedFailsLeft, int totalScore) {
		this.soFarGuessedWord = soFarGuessedWord;
		this.allowedFailsLeft = allowedFailsLeft;
		this.totalScore = totalScore;
	}
	
	/**
	 * @param correctWord
	 */
	public void setCorrectWord(String correctWord) {
		this.correctWord = correctWord;
	}
	
	/**
	 * @param statusCode
	 */
	public void setRoundStatus(int statusCode) {
		this.roundStatus = statusCode;
	}
	
	/**
	 * @return
	 */
	public int getRoundStatus() {
		return this.roundStatus;
	}
	
	/**
	 * @return
	 */
	public String getCorrectWord() {
		return this.correctWord;
	}
	
	/**
	 * @return
	 */
	public char[] getSoFarGuessedWord() {
		return this.soFarGuessedWord;
	}
	
	/**
	 * @return
	 */
	public int getAllowedFailsLeft() {
		return this.allowedFailsLeft;
	}
	
	/**
	 * @return
	 */
	public int getTotalScore() {
		return this.totalScore;
	}
}
