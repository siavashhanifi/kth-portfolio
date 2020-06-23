package view;

import integration.GamestatusDTO;

/**
 * @author Siavash
 *
 */
public class OutputHandler{
	private String lostRoundMessage = "You lost the round";
	private String roundWonMessage = "Word Guessed!";
	
	/**
	 * 
	 */
	public OutputHandler(){
		this.presentStartupMessage();
	}
	
	/**
	 * 
	 */
	private void presentStartupMessage() {
		System.out.println("To start game write \"START\"");
	}
	
	/**
	 * @param gamestatus
	 */
	protected void presentGameStatus(GamestatusDTO gamestatus) {
		String soFarGuessedWord = new String(gamestatus.getSoFarGuessedWord());
		
		System.out.println("TotalScore: " + gamestatus.getTotalScore());
		if(gamestatus.getRoundStatus() == -1) {
			System.out.println("Message: " + lostRoundMessage);
			System.out.println("The correct word was: " +gamestatus.getCorrectWord());
		}
		else if(gamestatus.getRoundStatus() == 1)
			System.out.println("Message: " + roundWonMessage);
		//System.out.println("Current round:");
		System.out.println("Fail attempts left: " + gamestatus.getAllowedFailsLeft());
		System.out.println("Word: " + soFarGuessedWord);
		System.out.println("Guess letter or full word: ");
	}

	/**
	 * 
	 */
	protected void presentExitMessage() {
		System.out.println("Program shut down.");
	}
	
}
