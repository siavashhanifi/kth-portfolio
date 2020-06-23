import java.net.*;
import java.io.*;

public class HTTPEcho {
	
	private static final String HTTPstatusLine = "HTTP/1.1 200 OK";
	private static final String HTTPemptyLine = "\r\n";
		
	
	private static String readFromClient(Socket connectionSocket) throws IOException{
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		
		StringBuilder builder = new StringBuilder();
		String aux = "";
			while (!(aux = inFromClient.readLine()).isEmpty()) {
				builder.append(aux + "\r\n");
			}
			String HTTPmessageBody = builder.toString();
			return HTTPmessageBody;

	}
	
	private static void sendToClient(Socket connectionSocket, String httpResponse) throws IOException {
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		outToClient.writeBytes(httpResponse); 
		outToClient.flush();
	}
	
	
	public static void main( String[] args) throws IOException{ 
		StringBuilder HTTPResponse = null;
		String HTTPmessageBody; 
		ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
		
		while(true) { 
			HTTPResponse = new StringBuilder(HTTPstatusLine + "\r\n" + HTTPemptyLine);
			Socket connectionSocket = serverSocket.accept();
			HTTPmessageBody = readFromClient(connectionSocket);
			HTTPResponse.append(HTTPmessageBody);
			sendToClient(connectionSocket, HTTPResponse.toString());
			connectionSocket.close();
		} 

	} 

}

