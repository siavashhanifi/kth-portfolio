package hangmanServer.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import org.apache.commons.lang3.SerializationUtils;
import common.GamestatusDTO;
import hangmanServer.NioUtil.NioUtil;
import hangmanServer.controller.Controller;

public class RequestHandler extends Thread{

	private ByteBuffer request;
	private SelectionKey key;
	private Map<SocketChannel, ClientData> dataTracking;

	
	RequestHandler(SelectionKey key, Map<SocketChannel, ClientData> dataTracking, ByteBuffer request) {
		 this.key = key;
		 this.dataTracking = dataTracking;
		 this.request = request;
	}
	
	/**
	 * 	Handles a client request
	 * @param key The key associated with the client
	 * @param dataTracking The hashmap containing data relevant to the client
	 * @throws IOException
	 * @throws InterruptedException
	 */
	void handleClientRequest(SelectionKey key, Map<SocketChannel, ClientData> dataTracking) throws IOException, InterruptedException {
		
		SocketChannel socketChannel = (SocketChannel) key.channel();
		ClientData clientData = dataTracking.get(socketChannel);
		String clientRequest = this.parseRequest(socketChannel);
		GamestatusDTO gamestatus = this.delegateRequest(clientRequest, clientData);
		if(gamestatus == null) {
			key.cancel();
			dataTracking.remove(socketChannel);
			socketChannel.close();
			return;
		}
		clientData.setNextDataToSend(SerializationUtils.serialize(gamestatus));
		dataTracking.put(socketChannel, clientData);
		NioUtil.setKeyNextOperationToWrite(key);
	}
	
	
	private String parseRequest(SocketChannel client) throws IOException {
		String request = new String(this.request.array()).trim();
		return request; 
	}
	

	/**
	 * Parses a string request and delegates it to the correct method in the controller
	 * @param request
	 * @return game status
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private GamestatusDTO delegateRequest(String request, ClientData client) throws IOException, InterruptedException{
		Controller clientController = client.getController();
		if(request.compareTo("START") == 0) {
			return clientController.startGame();
		}
		else if(request.compareTo("Exit") == 0) {
			return null;
		}
		else if(requestIsLetter(request)) {
			return clientController.guessLetter(request.toCharArray()[0]);
		}
		else{
			return clientController.guessWord(request);
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
	
	@Override
	public void run() {
		try {
			this.handleClientRequest(this.key, this.dataTracking);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
