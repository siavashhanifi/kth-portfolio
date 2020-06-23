package net;

import java.io.*; 
import java.net.*;

public class ClientListener { 
	
	/**
	 * 
	 */
	private ServerSocket serverSocket = null;
	
	/**
	 * @param port
	 * @throws IOException
	 */
	public ClientListener(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}
	
	/**
	 * @throws IOException
	 */
	public void handleNewClients() throws IOException {
		Socket client = listenForClient();
		Thread t = this.createNewThread(client);
		this.startThread(t);
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private Socket listenForClient() throws IOException {
		return serverSocket.accept();
	}

	/**
	 * @param s
	 * @return
	 * @throws IOException
	 */
	private ClientHandler createNewThread(Socket s) throws IOException {
		return new ClientHandler(s);
	}
	
	/**
	 * @param t
	 */
	private void startThread(Thread t) {
		t.start();
	}

	/**
	 * @throws IOException
	 */
	public void closeResources() throws IOException {
		this.serverSocket.close();
	} 

	
} 

