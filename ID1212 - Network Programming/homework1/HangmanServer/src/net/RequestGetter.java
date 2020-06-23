package net;

import java.io.*; 
import java.net.*;

/**
 * @author Siavash
 *
 */
class RequestGetter{
	/**
	 * 
	 */
	final private DataInputStream dis; 
	
	/**
	 * @param socket
	 * @throws IOException
	 */
	RequestGetter(Socket socket) throws IOException { 
		this.dis = new DataInputStream(socket.getInputStream());
	} 
	
	/**
	 * @return
	 * @throws IOException
	 */
	String getClientRequest() throws IOException {
		return this.dis.readUTF(); 
	}

	/**
	 * @throws IOException
	 */
	void closeResources() throws IOException {
		this.dis.close();
	}
	
} 
