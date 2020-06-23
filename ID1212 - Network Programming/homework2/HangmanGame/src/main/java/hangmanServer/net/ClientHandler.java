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

