package fileCatalog.client.startup;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import fileCatalog.client.view.ClientNotifier;
import fileCatalog.client.view.InputHandler;
import fileCatalog.client.view.OutputHandler;
import fileCatalog.common.rmi.ClientInterface;
import fileCatalog.common.rmi.ServerInterface;

public class Startup {

	public static void main(String[] args) throws Exception {
		ClientNotifier clientNotifier = new ClientNotifier();
		ServerInterface controller = (ServerInterface) Naming.lookup("rmi://localhost:5008"+ "/rmi"); 
		OutputHandler outputHandler = new OutputHandler();
		new InputHandler(outputHandler, controller);
	}

}
