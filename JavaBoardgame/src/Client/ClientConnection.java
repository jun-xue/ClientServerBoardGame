package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Server.ServerObject;

public class ClientConnection extends Thread 
{
	Socket s;
	String u;
	ObjectInputStream din2;
	ObjectOutputStream dout2;
	boolean shouldRun = true;

	JFrame frame = new JFrame("Client");
	
	JTextArea message = new JTextArea(8, 120);
	JTextField data = new JTextField(40);

	public ClientConnection(Socket socket, Client client, String username, String password)
	{
		s = socket;
		u = username;
		try 
		{
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
		
			dout2 = new ObjectOutputStream(os);
			din2 = new ObjectInputStream(is);
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
        frame.getContentPane().add(data, "South");
        frame.getContentPane().add(message, "North");
        
        frame.setTitle("Challenger Client");
        frame.setSize(800, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.pack();
	    frame.setVisible(true);
	    
        data.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	ServerObject outMessege = new ServerObject("MESSAGE", username, data.getText());
                sendPacketToServer(outMessege);
                data.setText("");
            }
        });
	}

	public void sendPacketToServer(ServerObject packet)
	{
		try 
		{
			dout2.writeObject(packet);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			close();
		}
	}

	public void run()
	{
		while(shouldRun)
		{
			try 
			{
				ServerObject incoming = (ServerObject) din2.readObject();
				
				System.out.println(incoming.getHeader());
				System.out.println(incoming.getPayload());
				
				if (incoming.getHeader().equals("MESSAGE"))
				{
					System.out.println("Here?");
					message.append(incoming.getSender() + " > " + incoming.getPayload() + "\n");
				}	
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				close();
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
		}		
	}
	
	public void close()
	{
		try 
		{
			din2.close();
			dout2.close();
			s.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
