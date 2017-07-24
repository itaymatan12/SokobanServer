package server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * the clientHandler Interface represents a way to handle the client and to connect between the client and th server
 *
 */

//interface that defines a way to handle the client and to connect between the client and the server 
public interface ClientHandler {
	
	public void aSyncReadInputsAndSend(InputStream in, OutputStream out);


}