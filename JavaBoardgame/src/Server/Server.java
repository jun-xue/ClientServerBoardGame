package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server 
{
	ServerSocket ss;
	ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
	boolean shouldRun = true;
	
	public static void main(String[] args)
	{
		new Server();
	}
	
	public Server()
	{
		try 
		{
			ss = new ServerSocket(42069);
			while(shouldRun)
			{
				Socket s = ss.accept();
				ServerConnection sc = new ServerConnection(s, this);
				sc.start();
				connections.add(sc);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}










