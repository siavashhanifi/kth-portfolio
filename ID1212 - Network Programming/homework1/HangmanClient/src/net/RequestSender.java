package net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Siavash
 *A class that sends requests to the server.
 */
public class RequestSender{
	
	final DataOutputStream dataOutputStream;
	
	/**
	 * Creates a RequestSender and an data output stream for a specified socket.
	 * @param socket
	 * @throws IOException
	 */
	RequestSender(Socket socket) throws IOException{
		this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	
	/**
	 * Sends a message to the server.
	 * @param request the message
	 * @throws IOException
	 */
	public void sendRequest(String request) throws IOException {
		dataOutputStream.writeUTF(request);
	}
	
	/**
	 * @throws IOException
	 */
	public void closeStream() throws IOException{
		this.dataOutputStream.close();
	}

}

