package fileCatalog.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import fileCatalog.common.integration.FileMeta;

public interface ServerInterface extends Remote {
	public boolean register(String username, String password) throws RemoteException, ClassNotFoundException, SQLException;
	public int login(String username, String password, ClientInterface clientNotifier) throws RemoteException, SQLException;
	public void logout(int sessionId) throws RemoteException;
	public void storeFile(int sessionId, FileMeta file) throws RemoteException, SQLException;
	public FileMeta retrieveFile(int sessionId, String filename) throws RemoteException, SQLException;
	public void removeFile(int sessionId, String filename) throws RemoteException, SQLException;
	public FileMeta[] getAllFileMetaData() throws RemoteException, SQLException;
}
