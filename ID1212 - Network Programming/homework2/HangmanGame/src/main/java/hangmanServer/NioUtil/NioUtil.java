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
