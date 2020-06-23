package net;

import java.io.*; 
import java.net.*; 
import integration.GamestatusDTO;
 
/**
 * @author Siavash
 * A class that handles the sending of responses to the client.
 */
public class ResponseSender { 

	final ObjectOutputStream oos;
	final Socket s;
	
	/**
	 * Creates a response sender associated with a specified socket
	 * @param socket the socket
	 * @throws IOException
	 */
	public ResponseSender(Socket socket) throws IOException { 
		this.s = socket; 
		this.oos = new ObjectOutputStream(this.s.getOutputStream());
	} 

	/**
	 * Sends a response to the client.
	 * 
	 * @param toreturn
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void sendResponse(GamestatusDTO toreturn) throws IOException, InterruptedException {
		oos.reset();
		oos.writeObject(toreturn);
	}

} 
