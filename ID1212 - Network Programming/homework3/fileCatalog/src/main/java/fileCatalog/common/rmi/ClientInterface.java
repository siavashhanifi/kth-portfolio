package fileCatalog.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import fileCatalog.common.integration.ActionDetailsDTO;

public interface ClientInterface extends Remote {
	public void notifyFileAccessed(ActionDetailsDTO actionDetailsDTO) throws RemoteException;
}
