package fileCatalog.server.model;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import fileCatalog.common.integration.ActionDetailsDTO;
import fileCatalog.common.rmi.ClientInterface;

public class ClientHandler {
	private ConcurrentHashMap<Integer, Client> loggedInUsers;
	private ConcurrentHashMap<String, Integer> loggedInUsers2;
	private Collection<Client> clients;
	private int availableSessionId;

	public ClientHandler() {
		this.loggedInUsers = new ConcurrentHashMap<Integer, Client>();
		this.loggedInUsers2 = new ConcurrentHashMap<String, Integer>();
		this.clients = loggedInUsers.values();
		availableSessionId = 0;
	}

	public int newUserLoggedIn(String username, ClientInterface clientNotifier) {
		this.loggedInUsers.put(availableSessionId, new Client(username, clientNotifier));
		this.loggedInUsers2.put(username, availableSessionId);
		return availableSessionId++;
	}

	public void userLoggedOut(int sessionId) {
		if(loggedInUsers.containsKey(sessionId)) {
			loggedInUsers2.remove(loggedInUsers.get(sessionId).getUsername());
			loggedInUsers.remove(sessionId);
		}
	}

	public String getUsernameById(int sessionId) {
		return this.loggedInUsers.get(sessionId).getUsername();
	}

	public String[] getLoggedInUsers() {
		Iterator<Client> iterator = clients.iterator();
		String[] loggedInUsers = new String[clients.size()];
		int i = 0;
		while(iterator.hasNext()) {
			loggedInUsers[i++] = iterator.next().getUsername();
		}
		return loggedInUsers;
	}
	
	
	public void notifyFileOwner(String owner, ActionDetailsDTO actionDetails) throws RemoteException {
		if(this.loggedInUsers2.containsKey(owner)) {
			Client clientToNotify = this.loggedInUsers.get(loggedInUsers2.get(owner));
			clientToNotify.getClientNotifier().notifyFileAccessed(actionDetails);
		}
	}


}
