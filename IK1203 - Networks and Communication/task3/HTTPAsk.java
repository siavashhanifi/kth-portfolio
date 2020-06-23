import java.net.*;
import java.io.*;

public class HTTPAsk {
	
	//private static final String HTTPstatusLine = "HTTP/1.1 200 OK";
	private static final String HTTPemptyLine = "\r\n";
	
		
	private static String sendMessageToServer(String hostname, int port, String message) throws IOException {
		return TCPClient.askServer(hostname, port, message);
	} 
	
	private static String sendMessageToServer(String hostname, int port) throws IOException {
		return TCPClient.askServer(hostname, port);
	} 
	
	private static String readFromClient(Socket connectionSocket) throws IOException{
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		
		StringBuilder builder = new StringBuilder();
		String aux = "";
			while ((aux = inFromClient.readLine()) != null && !(aux.isEmpty())) {
				builder.append(aux + "\r\n");
			}
			String HTTPGETRequest = builder.toString();
			return HTTPGETRequest;
	}
	
	private static void sendToClient(Socket connectionSocket, String httpResponse) throws IOException {
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		outToClient.writeBytes(httpResponse); 
		outToClient.flush();
	}
	
	
	public static void main( String[] args) throws IOException{ 
		String HTTPGETRequest = null;
		String HTTPResponse = null;
		ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
		
		while(true) { 
			//Recieving the HTTP GET request
			Socket connectionSocket = serverSocket.accept();
			try {
			HTTPGETRequest = readFromClient(connectionSocket);
			}
			catch(IOException socketException){
				connectionSocket.close();
				continue;
			}
			//System.out.println(HTTPGETRequest);
			
			//Constructing the HTTP Response
			HTTPResponse = constructHTTPResponse(HTTPGETRequest);
			//System.out.println(HTTPResponse);
			
			//sending the HTTPResponse to the client/browser
			try {
				sendToClient(connectionSocket, HTTPResponse);
				connectionSocket.close();
				}
			catch(IOException socketException){
				connectionSocket.close();
			}
	
		} 

	}

	private static String constructHTTPResponse(String HTTPGETRequest) throws NumberFormatException, IOException, SocketException {// throws MalformedURLException {
		StringBuilder HTTPResponse = new StringBuilder("");
		try {
			String[] componentsOfRequest = HTTPGETRequest.split("\\s+"); // [0] -> GET [1] -> ResourceURI [2] -> HTTP/1.1 ...
			String[] componentsOfRequestURI = componentsOfRequest[1].split("\\?"); // [0] -> /ask [1] -> hostname=Val1&port=Int
			String[] parameters = componentsOfRequestURI[1].split("&");//[0] -> hostname=Val1 [1] -> port=Int
			
			int hostnameIndex = 0;
			int portIndex = 0;
			int stringIndex = 0;
			
			String[] parameterKeys = new String[3];//[0] -> hostname [1] -> port [2] -> string or null
			int i = 0;
			for(String str : parameters) {
				parameterKeys[i++] = str.split("=")[0];
			}
			
			i=0;
			for(String str : parameterKeys) {
				if(str == null) break;
				if(str.compareTo("hostname") == 0 || str.compareTo("host") == 0) hostnameIndex = i++;
				if(str.compareTo("port") == 0) portIndex = i++;
				if(str.compareTo("string") == 0) stringIndex = i++;	
			}
			
			String[] parameterValues = new String[3];
			i = 0;
			for(String str : parameters) {
				parameterValues[i++] = str.split("=")[1];
			}
			
			String messageFromTCPServer = null;	
			if(parameterKeys[2] == null)
				messageFromTCPServer = sendMessageToServer(parameterValues[hostnameIndex], Integer.parseInt(parameterValues[portIndex]));
			else
				messageFromTCPServer = sendMessageToServer(parameterValues[hostnameIndex], Integer.parseInt(parameterValues[portIndex]), parameterValues[stringIndex]);
			HTTPResponse.append("HTTP/1.1 200 OK\r\n" + HTTPemptyLine + messageFromTCPServer);
			return HTTPResponse.toString();
		}
		//Bad Request
		catch(NullPointerException nPtrEx) {
			HTTPResponse.append("HTTP/1.1 400 Bad Request\r\n" + HTTPemptyLine + "HTTP/1.1 400 Bad Request: Bad parameters");
			return HTTPResponse.toString();
		}
		//Bad Request
		catch(NumberFormatException nFormatEx) {
			HTTPResponse.append("HTTP/1.1 400 Bad Request\r\n" + HTTPemptyLine + "HTTP/1.1 400 Bad Request: Invalid port.");
			return HTTPResponse.toString();
		}
		//Bad Request
		catch(ArrayIndexOutOfBoundsException arrayIndexEx) {
			HTTPResponse.append("HTTP/1.1 400 Bad Request\r\n" + HTTPemptyLine + "HTTP/1.1 400 Bad Request: Bad parameters.");
			return HTTPResponse.toString();
		}
		//Not found
		catch(UnknownHostException nFormatEx) {
			HTTPResponse.append("HTTP/1.1 404 Not Found\r\n" + HTTPemptyLine + "HTTP/1.1 404 Not Found: Hostname could not be resolved.");
			return HTTPResponse.toString();
		}
		//Not found
		catch(IOException socketException) {
			HTTPResponse.append("HTTP/1.1 404 Not Found\r\n" + HTTPemptyLine + "HTTP/1.1 404 Not Found: Connection could not be established with TCP server.");
			return HTTPResponse.toString();
		}
	}
}



