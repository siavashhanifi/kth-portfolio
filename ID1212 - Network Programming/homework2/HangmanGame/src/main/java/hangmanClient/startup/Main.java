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
