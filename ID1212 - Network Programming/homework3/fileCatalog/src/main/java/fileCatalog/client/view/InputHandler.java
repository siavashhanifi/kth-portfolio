package fileCatalog.client.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Scanner;

import com.google.common.io.Resources;

import fileCatalog.client.sessionhandling.Session;
import fileCatalog.common.integration.FileMeta;
import fileCatalog.common.rmi.ClientInterface;
import fileCatalog.common.rmi.ServerInterface;

/**
 * @author Siavash
 * A class that handles the client console input.
 */
public class InputHandler {
	
	private boolean programRunning = true;
	private Scanner scanner = null;
	private OutputHandler outputHandler;
	private ServerInterface controller = null;
	private Session session = null;
	/**
	 * Creates an InputHandler 
	 * @param outputHandler a reference to the console output handler
	 * @param controller a reference to the controller
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public InputHandler (OutputHandler outputHandler, ServerInterface controller) throws IOException, SQLException, ClassNotFoundException{
		this.programRunning = true;
		this.scanner = new Scanner(System.in);
		this.outputHandler = outputHandler;
		this.controller = controller;
		while(programRunning) {
			this.handleInput();
		}
	}
	
	/**
	 * Gets the next request from the client.
	 * @return the next request
	 */
	private String getNextInput() {
		return scanner.nextLine();
	}
	
	/**
	 * Handles client input while the client has not exited.
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void handleInput() throws IOException, SQLException, ClassNotFoundException {
		while(programRunning) {
			String request = this.getNextInput();
			this.handleRequest(request);
		}
	}
	
	/**
	 * Handles requests that do not have to be sent forward to the server.
	 * @param request the request
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void handleRequest(String request) throws IOException, SQLException, ClassNotFoundException {
		String username;
		String password;
		String filename;
		File file;
		FileMeta fileMeta;
		long filesize;
		switch(request) {
		case "Exit":
			this.programRunning = false;
			this.controller.logout(this.session.getSessionId());
			this.outputHandler.presentExitMessage();
			break;
		case "Register":
			this.outputHandler.presentEnterUsernameMsg();
			username = this.getNextInput();
			this.outputHandler.presentEnterPasswordMsg();
			password = this.getNextInput();
			this.handleCredentialsForRegister(username, password);
			break;
		case "Login":
			this.outputHandler.presentEnterUsernameMsg();
			username = this.getNextInput();
			this.outputHandler.presentEnterPasswordMsg();
			password = this.getNextInput();
			this.handleCredentialsForLogin(username, password);
			break;
		case "Logout":
			this.controller.logout(this.session.getSessionId());
			this.outputHandler.presentLoggedOutMsg();
			break;
		case "StoreFile":
			this.outputHandler.presentEnterFilenameMsg();
			filename = this.getNextInput();
			file = new File(
					getClass().getClassLoader().getResource(filename).getFile()
				);
	
			filesize = file.length();
			this.controller.storeFile(this.session.getSessionId(), new FileMeta(filename, filesize));
			this.outputHandler.presentFileStoredMsg(filename);
			break;
		case "RetrieveFile":
			this.outputHandler.presentEnterFilenameMsg();
			filename = this.getNextInput();
			fileMeta = this.controller.retrieveFile(this.session.getSessionId(), filename);
			this.outputHandler.presentRetrievedFileMetaMsg(fileMeta);
			break;
		case "RemoveFile":
			this.outputHandler.presentEnterFilenameMsg();
			filename = this.getNextInput();
			this.controller.removeFile(this.session.getSessionId(), filename);
			this.outputHandler.presentRemovedFileMsg(filename);
			break;
		case "ShowFiles":
			this.outputHandler.presentFiles(this.controller.getAllFileMetaData());
			break;
		default:
			this.outputHandler.presentWrongCommandMsg();
			break;
		}
	}

	private void handleCredentialsForRegister(String username, String password) throws RemoteException, ClassNotFoundException, SQLException {
		boolean registerSuccessful = this.controller.register(username, password);
		this.outputHandler.presentRegisterStatusMsg(registerSuccessful);
	}

	private void handleCredentialsForLogin(String username, String password) throws RemoteException, SQLException {
		int sessionId = this.controller.login(username, password, (ClientInterface) new ClientNotifier());
		this.session = new Session (sessionId);
		this.outputHandler.presentLoginResultMsg(sessionId);
	}
	
}
