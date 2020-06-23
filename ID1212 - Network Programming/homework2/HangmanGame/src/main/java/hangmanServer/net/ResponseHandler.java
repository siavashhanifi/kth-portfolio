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
