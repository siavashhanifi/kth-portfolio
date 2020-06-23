package controller;

import java.io.IOException;

import integration.GamestatusDTO;
import net.ClientNetworkHandler;


/**
 * @author Siavash
 * A class that delegates user request to the appropriate task in the net-layer.
 */
public class Controller {
	private ClientNetworkHandler clientNetworkHandler;
	
	/**
	 * @param clientNetworkHandler
	 */
	public Controller(ClientNetworkHandler clientNetworkHandler) {
		this.clientNetworkHandler = clientNetworkHandler;
	}
	
	/**
	 * Delegates the clients attempt to guess a word to the clientNetworkHandler and returns an object with the next game state.
	 * @param wordOrLetter
	 * @return the game status after the word-guessing attempt
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public GamestatusDTO guessWord(String wordOrLetter) throws IOException, ClassNotFoundException{
		this.clientNetworkHandler.sendRequest(wordOrLetter);
		return this.clientNetworkHandler.getResponse();
	}

	/**
	 * Closes resources
	 * @throws IOException
	 */
	public void shutdown() throws IOException {
		this.clientNetworkHandler.closeResources();
	}
}
