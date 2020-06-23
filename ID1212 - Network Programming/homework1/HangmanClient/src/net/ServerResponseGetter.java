package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import integration.GamestatusDTO;

/**
 * @author Siavash
 * A class that gets the responses from the server.
 */
public class ServerResponseGetter extends Thread {
	
	final ObjectInputStream objectInputStream;
	
	/**
	 * Creates a server response getter and creates a object input stream for a specified socket.
	 * @param socket the socket
	 * @throws IOException
	 */
	ServerResponseGetter(Socket socket) throws IOException{
		objectInputStream = new ObjectInputStream(socket.getInputStream());
	}

	/**
	 * Gets the lastest response from the server.
	 * @return the gamestatus.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public GamestatusDTO getResponse() throws ClassNotFoundException, IOException{
		return (GamestatusDTO) this.objectInputStream.readObject();
	} 
	
	/**
	 * Closes the object input stream.
	 * @throws IOException
	 */
	void closeStream() throws IOException {
		this.objectInputStream.close();
	}
}
