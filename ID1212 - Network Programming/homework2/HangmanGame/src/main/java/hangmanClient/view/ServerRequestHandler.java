package hangmanClient.view;


import java.io.IOException;

import common.GamestatusDTO;
import hangmanClient.controller.Controller;


/**
 * @author Siavash
 *
 */
class ServerRequestHandler extends Thread{
	private final Controller controller;
	private final OutputHandler outputHandler;
	private final String request;

	/**
	 * @param request
	 * @param controller
	 * @param outputHandler
	 */
	ServerRequestHandler(String request, Controller controller, OutputHandler outputHandler) {
		this.controller = controller;
		this.request = request;
		this.outputHandler = outputHandler;
	}
	
	/**
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void handleServerRequest() throws ClassNotFoundException, IOException {
		GamestatusDTO gamestatus;
		gamestatus = this.controller.guessWord(this.request);
		outputHandler.presentGameStatus(gamestatus);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			handleServerRequest();
		} catch (Exception e) {
			//Error handling here
		}
	
		
	}
	
}
