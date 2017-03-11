package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
		while (running)
		{
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args)
	{

	}

}
