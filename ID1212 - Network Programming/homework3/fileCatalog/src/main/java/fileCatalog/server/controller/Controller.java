package fileCatalog.server.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import fileCatalog.common.integration.ActionDetailsDTO;
import fileCatalog.common.integration.FileMeta;
import fileCatalog.common.rmi.ClientInterface;
import fileCatalog.common.rmi.ServerInterface;
import fileCatalog.server.integration.DbHandler;
import fileCatalog.server.model.ClientHandler;

public class Controller extends UnicastRemoteObject implements ServerInterface{
	private static final long serialVersionUID = 1L;

	private DbHandler dbHandler = null;
	private ClientHandler clientHandler = null;

	public Controller(DbHandler dbHandler) throws RemoteException {
		super();
		this.dbHandler = dbHandler;
		this.clientHandler = new ClientHandler();
	}

	@Override
	public boolean register(String username, String password) throws ClassNotFoundException, SQLException {
		if(this.dbHandler.usernameAvailable(username)) {
			this.dbHandler.registerUser(username, password);
			return true;
		}
		else return false;
	}

	@Override
	public int login(String username, String password, ClientInterface clientNotifier)
			throws RemoteException, SQLException {
		if(this.dbHandler.validCredentials(username, password)) {
			int sessionId = this.clientHandler.newUserLoggedIn(username, clientNotifier);
			this.loggedInUsers();
			return sessionId;
		}
		else {
			this.loggedInUsers();
			return -1;
		}
	}

	@Override
	public void logout(int sessionId) throws RemoteException {
		this.clientHandler.userLoggedOut(sessionId);
		this.loggedInUsers();
	}

	@Override
	public FileMeta[] getAllFileMetaData() throws RemoteException, SQLException {
		return this.dbHandler.getFiles();
	}

	private void loggedInUsers() {
		System.out.println("Currently logged in users:");
		for(String s: clientHandler.getLoggedInUsers())
			System.out.println(s);
	}

	@Override
	public void storeFile(int sessionId, FileMeta file) throws RemoteException, SQLException {
		file.setOwner(clientHandler.getUsernameById(sessionId));
		this.dbHandler.registerFile(file);
	}

	@Override
	public FileMeta retrieveFile(int sessionId, String filename) throws RemoteException, SQLException {
		FileMeta file = dbHandler.getFile(filename);
		if(file != null)
			this.clientHandler.notifyFileOwner(file.getOwner(), new ActionDetailsDTO(clientHandler.getUsernameById(sessionId), "get" ));
		return file;
	}

	@Override
	public void removeFile(int sessionId, String filename) throws RemoteException, SQLException {
		FileMeta file = dbHandler.getFile(filename);
		this.dbHandler.deleteFile(filename);
		this.clientHandler.notifyFileOwner(file.getOwner(), new ActionDetailsDTO(clientHandler.getUsernameById(sessionId), "remove" ));
	}

}
