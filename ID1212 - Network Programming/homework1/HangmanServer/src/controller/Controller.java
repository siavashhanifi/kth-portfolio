package controller;


import java.io.IOException;
import integration.GamestatusDTO;
import model.GameHandler;

/**
 * @author Siavash
 *
 */
public class Controller{
	private GameHandler gameHandler = null;
	
	
	/**
	 * @throws IOException
	 */
	public Controller() throws IOException {
			this.gameHandler = new GameHandler();
	}
	
	
	/**
	 * @param received
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public GamestatusDTO guessLetter(char received) throws IOException, InterruptedException{
		this.gameHandler.guessLetter(received);
		return this.gameHandler.getGameStatus();
	}

	/**
	 * @param received
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public GamestatusDTO guessWord(String received) throws IOException, InterruptedException {
		this.gameHandler.guessWord(received);
		return this.gameHandler.getGameStatus();
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public GamestatusDTO startGame() throws IOException, InterruptedException{
		return this.gameHandler.getGameStatus();
	}
	

	
}
