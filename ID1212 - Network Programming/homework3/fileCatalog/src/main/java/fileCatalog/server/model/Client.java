package fileCatalog.server.model;

import fileCatalog.common.rmi.ClientInterface;

public class Client {
	private String username;
	private ClientInterface clientNotifier;

	public Client(String username, ClientInterface clientNotifier) {
		this.username = username;
		this.clientNotifier = clientNotifier;
	}
	
	public String getUsername() {
		return this.username;
	}
	public ClientInterface getClientNotifier() {
		return this.clientNotifier;
	}
}
