package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.*;

public class GameClient extends JFrame implements ActionListener, Runnable
{
	private static final long serialVersionUID = -8778468847383756441L;
	private Socket connection;
	private Thread thread;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;

	public void run() 
	{
		// TODO Auto-generated method stub
	}
	
	public void connect(String host, int port)
	{
		try
		{
			connection = new Socket(host, port);
			OutputStream os = connection.getOutputStream();
			oos = new ObjectOutputStream(os);
			InputStream is = connection.getInputStream();
			ois = new ObjectInputStream(is);
			this.start();
		}
		catch(IOException e)
		{

		}
	}
	
	
	public void start()
	{
		thread = new Thread(this);
		thread.start();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
