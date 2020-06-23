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
