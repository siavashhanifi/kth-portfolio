package hangmanServer.model;

import java.io.IOException;

import common.GamestatusDTO;

/**
 * @author Siavash
 *A class that handles one game session; keeps track of total score and the creation of new rounds.
 */
public class GameHandler {

	private int totalScore;
	private Round round = null;
	private GamestatusDTO gamestatus = null;
	
	/**
	 * Reveals the correctWord for the current round.
	 */
	private void revealCorrectWord() {
		String correctWord = this.round.getCorrectWord();
		this.gamestatus.setCorrectWord(correctWord);
	}
	
	/**
	 * Creates a gamehandler and starts a new round
	 * @throws IOException
	 */
	public GameHandler() throws IOException{
		this.totalScore = 0;
		this.gamestatus = new GamestatusDTO();
		this.startNewRound();
	}
	
	/**
	 * Starts a new round.
	 * @throws IOException
	 */
	public void startNewRound() throws IOException{
		this.round = new Round();
		this.gamestatus.updateRoundVars(this.round.getSoFarGuessedWord(),this.round.failAttemptsLeft(),this.totalScore);
	}
	
	/**
	 * Handles an attempts to guess a word.
	 * if it implies that the word has been guessed or that all fail attempts are consumed, a new round is started.
	 * @param received
	 * @throws IOException
	 */
	public void guessWord(String received) throws IOException {
		this.round.matchWord(received);
		if(this.round.failsConsumed()) {
			this.totalScore--;
			this.revealCorrectWord();
			this.gamestatus.setRoundStatus(-1);
			this.startNewRound();
		}
		if(this.round.wordGuessed()) {
			this.totalScore++;
			this.gamestatus.setRoundStatus(1);
			this.startNewRound();
		}
		else {
			this.gamestatus.updateRoundVars(this.round.getSoFarGuessedWord(), this.round.failAttemptsLeft(), this.totalScore);
		}
	}

	/**
	 * Handles an attempts to guess a word.
	 * if it implies that the word has been guessed or that all fail attempts are consumed, a new round is started.
	 * @param received
	 * @throws IOException
	 */
	public void guessLetter(char received) throws IOException {
		this.round.matchLetter(received);
		if(this.round.failsConsumed()) {
			this.totalScore--;
			this.revealCorrectWord();
			this.gamestatus.setRoundStatus(-1);
			this.startNewRound();
		}
		else if(this.round.wordGuessed()) {
			this.totalScore++;
			this.gamestatus.setRoundStatus(1);
			this.startNewRound();
		}
		else {
			this.gamestatus.updateRoundVars(this.round.getSoFarGuessedWord(), this.round.failAttemptsLeft(), this.totalScore);
		}
	}
	
	/**
	 * Returns the current game status
	 * @return the game status
	 */
	public GamestatusDTO getGameStatus() {
		return this.gamestatus;
	}

}
