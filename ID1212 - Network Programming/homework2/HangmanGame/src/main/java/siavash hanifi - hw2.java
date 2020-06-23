package hangmanServer.startup;

import hangmanServer.net.ClientHandler;

public class Main {
	public static void main(String[] args)  {
		try {
			ClientHandler clientHandler = new ClientHandler("localhost", 5008);
			clientHandler.handleClients();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
package hangmanServer.NioUtil;

import java.nio.channels.SelectionKey;

public class NioUtil {
	
	public static void setKeyNextOperationToRead(SelectionKey key) {
		key.interestOps(SelectionKey.OP_READ);
	}
	
	public static void setKeyNextOperationToWrite(SelectionKey key) {
		key.interestOps(SelectionKey.OP_WRITE);
	}
}
package hangmanServer.net;

import java.io.IOException;
import hangmanServer.controller.Controller;

public class ClientData {
	final Controller controller;
	private byte[] nextDataToSend;
	
	ClientData() throws IOException{
		this.controller = new Controller();
	}

	public byte[] getNextDataToSend() {
		return nextDataToSend;
	}

	public void setNextDataToSend(byte[] nextDataToSend) {
		this.nextDataToSend = nextDataToSend;
	}

	public Controller getController() {
		return this.controller;
	}
	
	
}
package hangmanServer.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Siavash
 *Handles the client-server communication for all clients
 */
public class ClientHandler{

	private ServerSocketChannel serverSocketChannel;
	private Selector selector;
	private Map<SocketChannel, ClientData> dataTracking = new HashMap<>();
	private ResponseHandler responseHandler;
	
	/**
	 * Creates a ClientHandler
	 * @param socket
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public ClientHandler(String ip, int port) throws IOException, InterruptedException {
		this.serverSocketChannel = ServerSocketChannel.open();
		this.serverSocketChannel.bind(new InetSocketAddress(ip, port));
		this.serverSocketChannel.configureBlocking(false);
		this.selector = Selector.open();
		this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
		this.responseHandler = new ResponseHandler();
	}

	/**
	 * Handles the clients
	 * @param ByteBuffer 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void handleClients() throws IOException, InterruptedException {
		while(true) {
			Set<SelectionKey> selectedKeys = this.getSelectedKeys();
			this.handleKeys(selectedKeys);
		}
	}
	
	private Set<SelectionKey> getSelectedKeys() throws IOException{
		this.selector.selectNow();
		return this.selector.selectedKeys();
	}

	private void handleKeys(Set<SelectionKey> selectedKeys) throws IOException {
		Iterator<SelectionKey> iterator = selectedKeys.iterator();
		while(iterator.hasNext()) {
			SelectionKey key = iterator.next();
			if(!key.isValid()) {
				continue;
			}
			if (key.isAcceptable()) {
				this.registerClient(selector, serverSocketChannel);
			}
			if(key.isWritable()) {
				this.responseHandler.handleResponse(key, this.dataTracking);
			}
			if (key.isReadable()) {
				ByteBuffer request = ByteBuffer.allocate(256);
				((SocketChannel) key.channel()).read(request);
				Thread t = new RequestHandler(key, this.dataTracking, request);
				t.start();
			}
			iterator.remove();
		}
	}

	private void registerClient(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
		dataTracking.put(socketChannel, new ClientData());
	}

}

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
package hangmanServer.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import hangmanServer.NioUtil.NioUtil;

public class ResponseHandler {
	
	void handleResponse(SelectionKey key, Map<SocketChannel, ClientData> dataTracking) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		byte[] dataToSend = this.getResponseToSend(socketChannel, dataTracking);
		this.writeToSocketChannel(socketChannel, dataToSend);
		NioUtil.setKeyNextOperationToRead(key);
	}
	
	private byte[] getResponseToSend(SocketChannel socketChannel, Map<SocketChannel, ClientData> dataTracking) {
		ClientData clientData = dataTracking.get(socketChannel);
		return clientData.getNextDataToSend();
	}
	
	private void writeToSocketChannel(SocketChannel socketChannel, byte[] data) throws IOException {
		socketChannel.write(ByteBuffer.wrap(data));
	}
}
package hangmanServer.model;

import java.io.IOException;

import common.GamestatusDTO;

/**
 * @author Siavash
 *A class that handles one game session; keeps track of total score and the creation of new rounds.
 */
public class GameHandler {

	private int totalScore;
	private Round round = null;
	private GamestatusDTO gamestatus = null;
	
	/**
	 * Reveals the correctWord for the current round.
	 */
	private void revealCorrectWord() {
		String correctWord = this.round.getCorrectWord();
		this.gamestatus.setCorrectWord(correctWord);
	}
	
	/**
	 * Creates a gamehandler and starts a new round
	 * @throws IOException
	 */
	public GameHandler() throws IOException{
		this.totalScore = 0;
		this.gamestatus = new GamestatusDTO();
		this.startNewRound();
	}
	
	/**
	 * Starts a new round.
	 * @throws IOException
	 */
	public void startNewRound() throws IOException{
		this.round = new Round();
		this.gamestatus.updateRoundVars(this.round.getSoFarGuessedWord(),this.round.failAttemptsLeft(),this.totalScore);
	}
	
	/**
	 * Handles an attempts to guess a word.
	 * if it implies that the word has been guessed or that all fail attempts are consumed, a new round is started.
	 * @param received
	 * @throws IOException
	 */
	public void guessWord(String received) throws IOException {
		this.round.matchWord(received);
		if(this.round.failsConsumed()) {
			this.totalScore--;
			this.revealCorrectWord();
			this.gamestatus.setRoundStatus(-1);
			this.startNewRound();
		}
		if(this.round.wordGuessed()) {
			this.totalScore++;
			this.gamestatus.setRoundStatus(1);
			this.startNewRound();
		}
		else {
			this.gamestatus.updateRoundVars(this.round.getSoFarGuessedWord(), this.round.failAttemptsLeft(), this.totalScore);
		}
	}

	/**
	 * Handles an attempts to guess a word.
	 * if it implies that the word has been guessed or that all fail attempts are consumed, a new round is started.
	 * @param received
	 * @throws IOException
	 */
	public void guessLetter(char received) throws IOException {
		this.round.matchLetter(received);
		if(this.round.failsConsumed()) {
			this.totalScore--;
			this.revealCorrectWord();
			this.gamestatus.setRoundStatus(-1);
			this.startNewRound();
		}
		else if(this.round.wordGuessed()) {
			this.totalScore++;
			this.gamestatus.setRoundStatus(1);
			this.startNewRound();
		}
		else {
			this.gamestatus.updateRoundVars(this.round.getSoFarGuessedWord(), this.round.failAttemptsLeft(), this.totalScore);
		}
	}
	
	/**
	 * Returns the current game status
	 * @return the game status
	 */
	public GamestatusDTO getGameStatus() {
		return this.gamestatus;
	}

}
package hangmanServer.model;

import java.io.IOException;

import hangmanServer.integration.RandomWordLoader;


/**
 * @author Siavash
 *
 */
class Round {
	/**
	 * The correct word as a string
	 */
	private String wordStr = null;
	private int allowedFailsLeft = 0;
	/**
	 * The correct word as a char array
	 */
	private char[] wordCharArr = null;
	/**
	 * An char array with the so far guessed letters and unguessed letters as '0's 
	 */ 
	private char[] soFarGuessedWord = null;
	
	/**
	 * Creates a new round with a new word
	 * @throws IOException
	 */
	Round() throws IOException  {
		RandomWordLoader randomWordLoader = new RandomWordLoader(); 
		this.wordStr = randomWordLoader.getWord();
		this.wordCharArr = wordStr.toCharArray();
		this.allowedFailsLeft = wordCharArr.length;
		this.soFarGuessedWord = new char[wordCharArr.length];
		for(int i = 0; i < wordCharArr.length;i++)
			soFarGuessedWord[i] = '0';
	}
	
	/**
	 * Matches a letter to the correct word and updates the soFarGuessedWord
	 * @param received
	 */
	void matchLetter(char received) {
			int i=0;
			boolean failedAttempt = true;
			for(char c : wordCharArr) {
				if(c == received) {
					this.soFarGuessedWord[i] = c;
					failedAttempt = false;
				}
				i++;
			}
			if(failedAttempt)
				this.allowedFailsLeft--;
	}
	
	/**
	 * Matches a word to the current correct word
	 * @param received
	 */
	void matchWord(String received) {
			if(this.wordStr.compareTo(received) == 0) {
				this.soFarGuessedWord = this.wordCharArr;
			}
			else
				this.allowedFailsLeft--;
	}
	
	/**
	 * Returns true if all the fails are consumed
	 * @return
	 */
	public boolean failsConsumed() {
		if(allowedFailsLeft == 0)
			return true;
		else return false;
	}
	
	/**
	 * Returns true if the current correct word has been guessed.
	 * @return
	 */
	boolean wordGuessed() {
		String guessedWord = new String(soFarGuessedWord);
		if(guessedWord.compareTo(this.wordStr) == 0)
			return true;
		else
			return false;
	}

	/**
	 *Return
	 * @return
	 */
	public int failAttemptsLeft() {
		return this.allowedFailsLeft;
	}
	
	/**
	 * @return
	 */
	public String getCorrectWord() {
		return this.wordStr;
	}
	
	/**
	 * @return
	 */
	public char[] getSoFarGuessedWord(){
		return this.soFarGuessedWord;
	}


}
package hangmanServer.integration;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Random;

import com.google.common.io.Resources;


/**
 * @author Siavash
 *
 */
public class RandomWordLoader {
	private String word = null;
	
	/**
	 * @throws IOException
	 */
	public RandomWordLoader() throws IOException {
		URL filePath = Resources.getResource("words.txt");
		RandomAccessFile file = new RandomAccessFile(filePath.getFile(),"r");
		int nrOfLines = (int) file.length();
		Random random = new Random();
		int randomLine = random.nextInt(nrOfLines);
		file.seek(randomLine);
		file.readLine();
		this.word = file.readLine();
		System.out.println(word);
		file.close();
	}

	/**
	 * @return
	 */
	public String getWord() {
		return this.word;
	}

}
package hangmanServer.controller;


import java.io.IOException;

import common.GamestatusDTO;
import hangmanServer.model.GameHandler;

/**
 * @author Siavash
 *
 */
public class Controller{
	private GameHandler gameHandler = null;
	
	
	/**
	 * @throws IOException
	 */
	public Controller() throws IOException {
			
	}
	
	
	/**
	 * @param received
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public GamestatusDTO guessLetter(char received) throws IOException, InterruptedException{
		this.gameHandler.guessLetter(received);
		return this.gameHandler.getGameStatus();
	}

	/**
	 * @param received
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public GamestatusDTO guessWord(String received) throws IOException, InterruptedException {
		this.gameHandler.guessWord(received);
		return this.gameHandler.getGameStatus();
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public GamestatusDTO startGame() throws IOException, InterruptedException{
		this.gameHandler = new GameHandler();
		return this.gameHandler.getGameStatus();
	}
	

	
}
package hangmanClient.view;

import java.io.IOException;
import java.util.Scanner;

import hangmanClient.controller.Controller;

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
				this.handleExitRequest(request);
			else
				this.delegateServerRequest(request);;
		}
	}
	
	/**
	 * Handles requests that do not have to be sent forward to the server.
	 * @param request the request
	 * @throws IOException
	 */
	private void handleExitRequest(String request) throws IOException {
		switch(request) {
		case "Exit":
			this.delegateServerRequest(request);
			this.programRunning = false;
			//this.controller.shutdown();
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
package hangmanClient.view;

import common.GamestatusDTO;

/**
 * @author Siavash
 *
 */
public class OutputHandler implements ServerResponseObserver {
	private String lostRoundMessage = "You lost the round";
	private String roundWonMessage = "Word Guessed!";
	
	/**
	 * 
	 */
	public OutputHandler(){
		this.presentStartupMessage();
	}
	
	/**
	 * 
	 */
	private void presentStartupMessage() {
		System.out.println("To start game write \"START\"");
	}
	
	/**
	 * @param gamestatus
	 */
	protected void presentGameStatus(GamestatusDTO gamestatus) {
		String soFarGuessedWord = new String(gamestatus.getSoFarGuessedWord());
		
		System.out.println("TotalScore: " + gamestatus.getTotalScore());
		if(gamestatus.getRoundStatus() == -1) {
			System.out.println("Message: " + lostRoundMessage);
			System.out.println("The correct word was: " +gamestatus.getCorrectWord());
		}
		else if(gamestatus.getRoundStatus() == 1)
			System.out.println("Message: " + roundWonMessage);
		//System.out.println("Current round:");
		System.out.println("Fail attempts left: " + gamestatus.getAllowedFailsLeft());
		System.out.println("Word: " + soFarGuessedWord);
		System.out.println("Guess letter or full word: ");
	}

	/**
	 * 
	 */
	protected void presentExitMessage() {
		System.out.println("Program shut down.");
	}

	@Override
	public void newServerResponse(String response) {
		// TODO Auto-generated method stub
		
	}
	
}
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
package hangmanClient.startup;

import java.io.IOException;
import hangmanClient.controller.Controller;
import hangmanClient.net.ServerCommunicator;
import hangmanClient.view.InputHandler;
import hangmanClient.view.OutputHandler;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try {
			ServerCommunicator serverCommunicator = new ServerCommunicator("localhost",5008);
			Controller controller = new Controller(serverCommunicator);
			OutputHandler outputHandler = new OutputHandler();
			new InputHandler(outputHandler, controller);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
package hangmanClient.net;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import common.GamestatusDTO;

/**
 * @author Siavash
 * Handles client-server communication
 */
public class ServerCommunicator {

	private SocketChannel socketChannel;
	private ByteBuffer readBuffer;
	
	/**
	 * Creates a ServerCommunicator 
	 * @param ip the server ip
	 * @param port the server port
	 * @throws IOException
	 */
	public ServerCommunicator(String ip, int port) throws IOException {
		this.socketChannel = SocketChannel.open(new InetSocketAddress(ip, port));
		this.readBuffer = ByteBuffer.allocate(4096);
	}

	/**
	 * Sends a client-request to the server
	 * @param msg the request
	 * @throws IOException
	 */
	public void sendRequest(String msg) throws IOException {
		this.socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
	}

	/**
	 * Gets the latest response from the server
	 * @return the gamestatus
	 * @throws IOException
	 */
	public GamestatusDTO getResponse() throws IOException {
		this.loadResponseIntoBuffer(this.readBuffer);
		GamestatusDTO gamestatus = this.deserializeDTOFromBuffer(this.readBuffer);
		this.readBuffer.clear();
		return gamestatus;
	}
	
	private void loadResponseIntoBuffer(ByteBuffer byteBuffer) throws IOException {
		this.socketChannel.read(byteBuffer);
	}
	
	private GamestatusDTO deserializeDTOFromBuffer(ByteBuffer byteBuffer) {
		GamestatusDTO gamestatus = (GamestatusDTO)SerializationUtils.deserialize(byteBuffer.array());
		return gamestatus;
	}

	public void closeResources() throws IOException {
		this.socketChannel.close();
	}

}package hangmanClient.controller;

import java.io.IOException;

import common.GamestatusDTO;
import hangmanClient.net.ServerCommunicator;


/**
 * @author Siavash
 * A class that delegates user request to the appropriate task in the net-layer.
 */
public class Controller {
	private ServerCommunicator serverCommunicator;
	
	/**
	 * @param serverCommunicator
	 */
	public Controller(ServerCommunicator serverCommunicator) {
		this.serverCommunicator = serverCommunicator;
	}
	
	/**
	 * Delegates the clients attempt to guess a word to the clientNetworkHandler and returns an object with the next game state.
	 * @param wordOrLetter
	 * @return the game status after the word-guessing attempt
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public GamestatusDTO guessWord(String wordOrLetter) throws IOException, ClassNotFoundException{
		this.serverCommunicator.sendRequest(wordOrLetter);
		return this.serverCommunicator.getResponse();
	}

	/**
	 * Closes resources
	 * @throws IOException
	 */
	public void shutdown() throws IOException {
		this.serverCommunicator.closeResources();
	}

	public GamestatusDTO startGame() {
		// TODO Auto-generated method stub
		return null;
	}
}
package common;

import java.io.Serializable;

/**
 * @author Siavash
 *
 */
public class GamestatusDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private char[] soFarGuessedWord = null;
	private String correctWord = "cheater!";
	private int allowedFailsLeft = 0;
	private int totalScore = 0;
	private int roundStatus = 0;
	 
	/**
	 * @param soFarGuessedWord
	 * @param allowedFailsLeft
	 * @param totalScore
	 */
	public void updateRoundVars(char[]soFarGuessedWord, int allowedFailsLeft, int totalScore) {
		this.soFarGuessedWord = soFarGuessedWord;
		this.allowedFailsLeft = allowedFailsLeft;
		this.totalScore = totalScore;
	}
	
	/**
	 * @param correctWord
	 */
	public void setCorrectWord(String correctWord) {
		this.correctWord = correctWord;
	}
	
	/**
	 * @param statusCode
	 */
	public void setRoundStatus(int statusCode) {
		this.roundStatus = statusCode;
	}
	
	/**
	 * @return
	 */
	public int getRoundStatus() {
		return this.roundStatus;
	}
	
	/**
	 * @return
	 */
	public String getCorrectWord() {
		return this.correctWord;
	}
	
	/**
	 * @return
	 */
	public char[] getSoFarGuessedWord() {
		return this.soFarGuessedWord;
	}
	
	/**
	 * @return
	 */
	public int getAllowedFailsLeft() {
		return this.allowedFailsLeft;
	}
	
	/**
	 * @return
	 */
	public int getTotalScore() {
		return this.totalScore;
	}
}
