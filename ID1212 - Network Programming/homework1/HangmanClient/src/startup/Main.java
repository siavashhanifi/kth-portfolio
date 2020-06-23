package startup;

import java.io.IOException;
import java.net.InetAddress;
import controller.Controller;
import net.ClientNetworkHandler;
import view.InputHandler;
import view.OutputHandler;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try {
			ClientNetworkHandler clientNetworkHandler = new ClientNetworkHandler(InetAddress.getByName("localhost"),5024);
			Controller controller = new Controller(clientNetworkHandler);
			OutputHandler outputHandler = new OutputHandler();
			new InputHandler(outputHandler, controller);
		}catch(Exception e) {
			
		}
		
	}

}
