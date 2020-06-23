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
