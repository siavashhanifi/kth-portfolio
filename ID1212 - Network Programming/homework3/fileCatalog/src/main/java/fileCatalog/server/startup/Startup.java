package fileCatalog.server.startup;

import fileCatalog.common.rmi.ServerInterface;
import fileCatalog.server.controller.Controller;
import fileCatalog.server.integration.DbHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;

public class Startup {
	public static void main(String[] args) throws RemoteException, MalformedURLException, ClassNotFoundException, SQLException {
		ServerInterface controller = new Controller(new DbHandler()); 
		LocateRegistry.createRegistry(5008);
		Naming.rebind("rmi://localhost:5008"+ "/rmi",controller); 
	}
}
