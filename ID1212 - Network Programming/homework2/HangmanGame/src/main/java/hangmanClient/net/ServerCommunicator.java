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

}