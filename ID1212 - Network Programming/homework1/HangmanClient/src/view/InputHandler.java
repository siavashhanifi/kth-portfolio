package view;

import java.io.IOException;
import java.util.Scanner;
import controller.Controller;

/**
 * @author Siavash
 * A class that handles the client console input.
 */
public class InputHandler {
	
	private boolean programRunning = true;
	private Scanner scanner = null;
	private OutputHandler outputHandler;
	private Controller controller = null;
	
	/**
	 * Creates an InputHandler 
	 * @param outputHandler a reference to the console output handler
	 * @param controller a reference to the controller
	 * @throws IOException
	 */
	public InputHandler (OutputHandler outputHandler, Controller controller) throws IOException{
		this.programRunning = true;
		this.scanner = new Scanner(System.in);
		this.outputHandler = outputHandler;
		this.controller = controller;
		while(programRunning) {
			this.handleInput();
		}
	}
	
	/**
	 * Gets the next request from the client.
	 * @return the next request
	 */
	private String getNextRequest() {
		return scanner.nextLine();
	}
	
	/**
	 * Handles client input while the client has not exited.
	 * @throws IOException
	 */
	private void handleInput() throws IOException {
		while(programRunning) {
			String request = this.getNextRequest();
			if(isNotServerRequest(request))
				this.handleNonServerRequest(request);
			else
				this.delegateServerRequest(request);;
		}
	}
	
	/**
	 * Handles requests that do not have to be sent forward to the server.
	 * @param request the request
	 * @throws IOException
	 */
	private void handleNonServerRequest(String request) throws IOException {
		switch(request) {
		case "Exit":
			this.programRunning = false;
			this.controller.shutdown();
			this.outputHandler.presentExitMessage();
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * Delegates requests that need to be sent forward to the server to a new thread.
	 * @param request the request
	 */
	private void delegateServerRequest(String request) {
		Thread t = this.createServerRequestThread(request);
		this.startServerRequestThread(t);
	}
	
	
	/**
	 * Starts a thread
	 * @param t the thread
	 */
	private void startServerRequestThread(Thread t) {
		t.start();
	}

	/**
	 * Creates a new thread for a ServerRequestHandler
	 * @param request the request
	 * @return a reference to the new thread
	 */
	private Thread createServerRequestThread(String request) {
		return new ServerRequestHandler(request, this.controller, this.outputHandler);
		
	}

	/**
	 * Checks if it is a server request or not.
	 * @param request the request
	 * @return true if it isn't a server request
	 */
	private boolean isNotServerRequest(String request) {
		switch(request) {
		case "Exit":
			return true;
		default:
			return false;
		}
	}
	
}
