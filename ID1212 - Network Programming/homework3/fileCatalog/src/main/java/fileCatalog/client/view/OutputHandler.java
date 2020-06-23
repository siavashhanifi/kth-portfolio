package fileCatalog.client.view;

import fileCatalog.common.integration.FileMeta;

/**
 * @author Siavash
 *
 */
public class OutputHandler{

	public OutputHandler() {
		this.presentMenu();
	}
	void presentExitMessage() {
		System.out.println("Program shut down.");
	}

	void presentEnterUsernameMsg() {
		System.out.println("Enter username: ");
	}

	void presentEnterPasswordMsg() {
		System.out.println("Enter password: ");
	}


	void presentEnterFilenameMsg() {
		System.out.println("Enter filename:");
	}

	void presentFiles(FileMeta[] allFileMetaData) {
		for(FileMeta fileMeta : allFileMetaData) {
			System.out.println("File name: " + fileMeta.getName());
			System.out.println("File owner: " + fileMeta.getOwner());
			System.out.println("File size: " + fileMeta.getSize()+" byte(s)");
		}

	}

	void presentLoginResultMsg(int sessionId) {
		if(sessionId == -1)
			System.out.println("Login failed, try again.");
		else
			System.out.println("Login successful!");
	}

	void presentLoggedOutMsg() {
		System.out.println("Logged out.");		
	}

	void presentFileStoredMsg(String filename) {
		System.out.println("File with name: " + filename + " stored.");
	}

	void presentRetrievedFileMetaMsg(FileMeta fileMeta) {
		if(fileMeta != null) {
			System.out.println("File recieved: " + fileMeta.getName());
			System.out.println("Owner: " + fileMeta.getOwner());
			System.out.println("Size: " + fileMeta.getSize());
		}
		else
			System.out.println("File not found!");
	}

	void presentRemovedFileMsg(String filename) {
		System.out.println("File with name: " + filename + " removed.");
	}

	void presentWrongCommandMsg() {
		System.out.println("Invalid command, try again.");
	}
	
	
	void presentMenu() {
		System.out.println("COMMANDS:");
		System.out.println("Register");
		System.out.println("Login");
		System.out.println("Logout");
		System.out.println("RetrieveFile");
		System.out.println("StoreFile");
		System.out.println("RemoveFile");
		System.out.println("ShowFiles");
	}
	public void presentRegisterStatusMsg(boolean registerSuccessful) {
		if(registerSuccessful)
			System.out.println("register successful.");
		else
			System.out.println("username unavailable.");
	}

}
