package startup;

import net.ClientListener;

public class Main {
	public static void main(String[] args)  {
		try {
			ClientListener clientListener = new ClientListener(5024);
			while(true) {
				clientListener.handleNewClients();
			}
		}catch(Exception e) {
			//ErrorHandler 
		}
	}

}
