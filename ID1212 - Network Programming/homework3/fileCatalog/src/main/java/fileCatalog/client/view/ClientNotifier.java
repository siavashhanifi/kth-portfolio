package fileCatalog.client.view;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import fileCatalog.common.integration.ActionDetailsDTO;
import fileCatalog.common.rmi.ClientInterface;

public class ClientNotifier extends UnicastRemoteObject implements ClientInterface{
	public ClientNotifier() throws RemoteException{
		super();
	}

	@Override
	public void notifyFileAccessed(ActionDetailsDTO actionDetails) throws RemoteException {
		System.out.println("File accessed:");
		System.out.println("Action: " + actionDetails.getAction());
		System.out.println("By user: " + actionDetails.getUser());	
	}
}
