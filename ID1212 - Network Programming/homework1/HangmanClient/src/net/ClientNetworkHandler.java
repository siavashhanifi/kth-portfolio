package net;
// Java implementation for a client 
// Save file as Client.java 

import java.io.*; 
import java.net.*; 

import integration.GamestatusDTO;

// Client class 
/**
 * @author Siavash
 * A class that handles the client-server communication.
 */
public class ClientNetworkHandler {
	
	private Socket socket;
	private ServerResponseGetter serverResponseGetter;
	private RequestSender requestSender;
	
	/**
	 * Creates a ClientNetworkHandler for a socket connection to the specified ip and port.
	 * It uses a RequestSender to send client-requests to the server and uses a ServerResponseGetter to
	 * get responses from server.
	 * @param ip
	 * @param port
	 * @throws IOException
	 */
	public ClientNetworkHandler(InetAddress ip, int port) throws IOException{
		this.socket = new Socket(ip,port); 
		this.serverResponseGetter = new ServerResponseGetter(this.socket);
		this.requestSender = new RequestSender(this.socket);
	}
	
	/**
	 * Closes the resources
	 * @throws IOException
	 */
	public void closeResources() throws IOException{
		this.serverResponseGetter.closeStream();
		this.requestSender.closeStream();
		this.socket.close();
	} 
	
	/**
	 * Sends a request to the server 
	 * @param wordOrLetter
	 * @throws IOException
	 */
	public void sendRequest(String wordOrLetter) throws IOException{
			this.requestSender.sendRequest(wordOrLetter);
	}
	
	/**
	 * Gets the lastest repsonse from the server
	 * @return the response
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public GamestatusDTO getResponse() throws ClassNotFoundException, IOException{
			return this.serverResponseGetter.getResponse();
	}
}  
