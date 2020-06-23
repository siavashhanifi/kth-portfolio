package hangmanClient.controller;

import java.io.IOException;

import common.GamestatusDTO;
import hangmanClient.net.ServerCommunicator;


/**
 * @author Siavash
 * A class that delegates user request to the appropriate task in the net-layer.
 */
public class Controller {
	private ServerCommunicator serverCommunicator;
	
	/**
	 * @param serverCommunicator
	 */
	public Controller(ServerCommunicator serverCommunicator) {
		this.serverCommunicator = serverCommunicator;
	}
	
	/**
	 * Delegates the clients attempt to guess a word to the clientNetworkHandler and returns an object with the next game state.
	 * @param wordOrLetter
	 * @return the game status after the word-guessing attempt
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public GamestatusDTO guessWord(String wordOrLetter) throws IOException, ClassNotFoundException{
		this.serverCommunicator.sendRequest(wordOrLetter);
		return this.serverCommunicator.getResponse();
	}

	/**
	 * Closes resources
	 * @throws IOException
	 */
	public void shutdown() throws IOException {
		this.serverCommunicator.closeResources();
	}

	public GamestatusDTO startGame() {
		// TODO Auto-generated method stub
		return null;
	}
}
