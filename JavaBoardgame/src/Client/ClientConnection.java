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
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Server.Player;
import Server.ServerObject;

public class ClientConnection extends Thread 
{
	Socket s;
	String user;
	String pass;
	
	ObjectInputStream din2;
	ObjectOutputStream dout2;
	boolean shouldRun = true;

	JFrame frame = new JFrame("Client");
	
	JTextArea message = new JTextArea(8, 120);
	JTextField data = new JTextField(40);

	public ClientConnection(Socket socket, Client client) throws ClassNotFoundException, IOException
	{
		s = socket;
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
		
		Object[] options = {"Login", "New Account"};
		int sel = JOptionPane.showOptionDialog(null, "Select an option:\n" +
				"(if the prvious login fails, this will pop up again)", 
				"Challenger Log In", JOptionPane.DEFAULT_OPTION,	
				JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		
		if (sel == 0)
		{
			while(true)
			{
				String u = JOptionPane.showInputDialog(frame, "Enter your username:");
				String p = JOptionPane.showInputDialog(frame, "Enter your password:");
				
				// need to make it crash correctly if improper stuff is typed in
				
				Player temp = new Player(u,p);
	        	ServerObject outMessage = new ServerObject("LOGIN", u, temp);
	        	sendPacketToServer(outMessage);
	        	
				ServerObject incoming = (ServerObject) din2.readObject();
				
				if (incoming.getHeader().equals("VALID"))
				{
					user = u;
					break;
				}
				else
				{
					continue;
				}
	        	
			}
		}
		else if (sel == 1)
		{
			while(true)
			{
				String u = JOptionPane.showInputDialog(frame, "Enter your username:");
				String p = JOptionPane.showInputDialog(frame, "Enter your password:");

				// need to make it crash correctly if improper stuff is typed in
				
				Player temp = new Player(u,p);
	        	ServerObject outMessage = new ServerObject("REGISTER", u, temp);
	        	sendPacketToServer(outMessage);
	        	
				ServerObject incoming = (ServerObject) din2.readObject();
				
				if (incoming.getHeader().equals("VALID"))
				{
					user = u;
					break;
				}
				else
				{
					continue;
				}
			}
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
            	if (data.getText().equals("quit"))
            	{
                	ServerObject outMessage = new ServerObject("LEAVE", user, data.getText());
                	sendPacketToServer(outMessage);
                	close();
                	System.exit(0);
            	}
            	
            	ServerObject outMessege = new ServerObject("MESSAGE", user, data.getText());
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
				
				if (incoming.getHeader().equals("MESSAGE"))
				{
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
