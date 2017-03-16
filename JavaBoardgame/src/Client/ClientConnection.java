package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import Server.Player;
import Server.ServerConnection;
import Server.ServerObject;

public class ClientConnection extends Thread 
{
	Socket s;
	String user;
	String pass;
	
	ArrayList<String> currentConnected = new ArrayList<String>();
	ObjectInputStream din2;
	ObjectOutputStream dout2;
	boolean shouldRun = true;

	JFrame frame = new JFrame("Client");
	
	JMenuBar menuBar = new JMenuBar();
	JMenu challenge = new JMenu("Challenge");;
	JTextArea message = new JTextArea(8, 120);
	JTextField data = new JTextField(40);
	//MAKE A SCROLL WINDOW FOR MESSAGE.

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
				"(if the previous login fails, this will pop up again)", 
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
		menuBar.add(challenge);
		
        frame.getContentPane().add(data, "South");
        frame.getContentPane().add(message, "Center");
        frame.getContentPane().add(menuBar, "North");
        
        frame.setTitle("Challenger Client: " + user);
        message.setEditable(false);

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
            	else if (data.getText().startsWith("/challenge"))
            	{
            		ServerObject outMessage = new ServerObject("CHALLENGE", user, data.getText());
                	sendPacketToServer(outMessage);
            	}
            	else if (data.getText().equals("/accept"))
            	{
            		ServerObject outMessage = new ServerObject("ACCEPT", user, null);
                	sendPacketToServer(outMessage);
            	}
            	else
            	{
	            	ServerObject outMessege = new ServerObject("MESSAGE", user, data.getText());
	                sendPacketToServer(outMessege);
            	}
	            data.setText("");
            }
        });
        challenge.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) 
            {
                ServerObject outMessege = new ServerObject("USERS", user, null);
                sendPacketToServer(outMessege);
                
                for (String user1 : currentConnected)
                {
                	if (user1.equals(user))
                	{
                		continue;
                	}
                	JMenuItem temp = new JMenuItem(user1);
                	challenge.add(temp);
                	temp.addActionListener(new ActionListener()
                	{
                		String c1 = temp.getText();
                		@Override
                		public void actionPerformed(ActionEvent e)
                		{
                			Object[] options = {"Tic Tac Toe", "Chess", "Checkers"};
                			int sel = JOptionPane.showOptionDialog(null, "Select an option:\n", 
                					"Game Choice", JOptionPane.DEFAULT_OPTION,	
                					JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                			
                			System.out.println(sel);
                			System.out.println(c1);
                			System.out.println(user);
                            ServerObject outMessege = new ServerObject("CHALLENGE", user, c1, sel);
                            sendPacketToServer(outMessege);
                		}
                	});
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) 
            {
                challenge.removeAll();
            }

            @Override
            public void menuCanceled(MenuEvent e) {

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
        ServerObject outMessege = new ServerObject("USERS", user, null);
        sendPacketToServer(outMessege);
        
		while(shouldRun)
		{
			try 
			{

                
				ServerObject incoming = (ServerObject) din2.readObject();
				
				if (incoming.getHeader().equals("MESSAGE"))
				{
					message.append(incoming.getSender() + " > " + incoming.getPayload() + "\n");
<<<<<<< HEAD
					
				}	
				else if (incoming.getHeader().equals("SERVER ANNOUNCEMENT"))
				{	
					message.append(incoming.getPayload() + "\n");
=======
				}
				
				if (incoming.getHeader().equals("USERS"))
				{
					currentConnected = (ArrayList<String>)incoming.getPayload();
>>>>>>> origin/master
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
