package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
	
	private static void sendRequestToServer(Socket clientSocket, String request) throws IOException {
		DataOutputStream outStreamToServer = new DataOutputStream(clientSocket.getOutputStream());
		
		outStreamToServer.writeBytes(request + '\n');
		outStreamToServer.flush();
		
	}
	
	private static String readReplyFromServer(Socket clientSocket) throws IOException, SocketTimeoutException{
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		StringBuilder builder = new StringBuilder();
		String aux = "";
		
		try{
			while ((aux = inFromServer.readLine()) != null) {
				builder.append(aux + '\n');
			}
			String reply = builder.toString();
			inFromServer.close();
			return reply;
		}
		catch(SocketTimeoutException socketTimeoutException) {
			String reply = builder.toString();
			inFromServer.close();
			return reply;
		}	
	}
	

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
    	String serverReply = null;
    	Socket clientSocket = new Socket(hostname, port);
    	clientSocket.setSoTimeout(6000);
    	
    	sendRequestToServer(clientSocket, ToServer);
    	serverReply = readReplyFromServer(clientSocket);
    	clientSocket.close();
    	return serverReply;
    }
	
    public static String askServer(String hostname, int port) throws  IOException {
    	String serverReply = null;
    	Socket clientSocket = new Socket(hostname, port);
    	clientSocket.setSoTimeout(6000);
    	
    	serverReply = readReplyFromServer(clientSocket);
    	clientSocket.close();
    	return serverReply;
    }
}
