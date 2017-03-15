package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	ServerSocket ss;
	ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
	boolean shouldRun = true;
	
	
	public static void main(String[] args){
		new Server();
		
		
	}
	
	
	public Server(){
		try {
			while(shouldRun){
				//listens on socket 2345
				ss = new ServerSocket(2345);
				//once detected connection it accepts
				Socket s = ss.accept();
				//passes socket into new server conection with reference to itself
				ServerConnection sc = new ServerConnection(s, this);
				//start thread
				sc.start();
				//adds server connection to arraylist
				connections.add(sc);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}










