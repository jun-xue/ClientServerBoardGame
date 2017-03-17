package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class Server1 
{
	ServerSocket ss;
	ArrayList<ServerConnection1> connections = new ArrayList<ServerConnection1>();
	Hashtable<String, String> challenges = new Hashtable<String, String>();
	String[] gamesOnServer = {"TicTacToe", "Chess", "Checkers"};
	
	boolean shouldRun = true;
	
	public static void main(String[] args)
	{
		Player.getAccounts();
		new Server1();
	}
	
	public Server1()
	{
		try 
		{
			ss = new ServerSocket(42069);
			while(shouldRun)
			{
				Socket s = ss.accept();
				ServerConnection1 sc = new ServerConnection1(s, this);
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










