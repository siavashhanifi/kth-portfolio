package net;

import java.io.IOException;
import java.net.Socket;

import controller.Controller;
import integration.GamestatusDTO;

/**
 * @author Siavash
 *Handles the client-server communication for a connected client
 */
class ClientHandler extends Thread{

	final Socket socket;
	final RequestGetter requestGetter;
	final ResponseSender responseSender;
	final Controller controller;
	
	/**
	 * Creates a ClientHandler for a specified socket connection.
	 * @param socket
	 * @throws IOException
	 */
	ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.requestGetter = new RequestGetter(socket);
		this.responseSender = new ResponseSender(socket);
		this.controller = new Controller();
	}
	
	
	/**
	 * Handles a client while it is connected.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void handleClient() throws IOException, InterruptedException {
		while(clientIsConnected()) {
			String request = this.getRequest();
			GamestatusDTO torespond = this.parseRequest(request);
			this.sendResponse(torespond);
		}
	}
	
	
	/**
	 * Gets the lastest request from the client.
	 * @return
	 * @throws IOException
	 */
	private String getRequest() throws IOException {
		return this.requestGetter.getClientRequest();
	}
	
	/**
	 * Sends a response to the client
	 * @param gamestatus
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void sendResponse(GamestatusDTO gamestatus) throws IOException, InterruptedException {
		this.responseSender.sendResponse(gamestatus);
		
	}
	
	/**
	 * Parses a string request and delegates it to the correct method in the controller
	 * @param request
	 * @return game status
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private GamestatusDTO parseRequest(String request) throws IOException, InterruptedException{
		if(request.compareTo("START") == 0) {
			 return controller.startGame();
		}
		if(requestIsLetter(request)) {
			return controller.guessLetter(request.toCharArray()[0]);
		}
		else{
			return controller.guessWord(request);
		}
	}
	
	
	/**
	 * Checks if a request is a letter
	 * @param request
	 * @return
	 */
	private boolean requestIsLetter(String request) {
		if(request.length() == 1) 
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if a client is connected
	 * @return
	 */
	private boolean clientIsConnected() {
		return !(this.socket.isClosed());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
			try {
				this.handleClient();
			} catch (Exception e) {
				//error handling here
			}
	}
}

