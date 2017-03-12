package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer extends Thread
{
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private boolean running;
	
	public GameServer(Socket connection)
	{
		socket = connection;
		try
		{
			OutputStream os = connection.getOutputStream();
			oos = new ObjectOutputStream(os);
			InputStream is = connection.getInputStream();
			ois = new ObjectInputStream(is);
			running = socket.isConnected();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		System.out.println("Server Started");
		while (running)
		{
			try 
			{
				ServerObject incoming = (ServerObject)ois.readObject();
				ServerObject outgoing;
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				running = false;
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args)
	{
		ServerSocket server = null;
		try
		{
			server = new ServerSocket(42069);
		}
		catch(IOException ioe)
		{
		}
		while (true)
		{
			try 
			{
				Socket connection = server.accept();
				GameServer thread = new GameServer(connection);
				thread.start();
				System.out.println("Server Started");
				
				
				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

}
