package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Server.Player;
import Server.ServerObject;
import UI.GameRoomUI;
import UI.LoadInUI;

public class ClientMain 
{
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private String server;
	private int port;
	private String[] usernameList;
	private String[] roomList;
	private Player account;
	private LoadInUI loadInUI;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		String serverName = JOptionPane.showInputDialog("Enter Server IP address:");
		int portNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter Server port number#:"));
		
		ClientMain client = new ClientMain(serverName, portNumber);
		client.loadInUI.setVisible(true);
		client.run();
	}
	public ClientMain(String server, int port)
	{
		this.server = server;
		this.port = port;
		loadInUI = new LoadInUI();

		//make action listoners 
		loadInUI.text.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try 
				{
					oos.writeObject(new ServerObject("MESSAGE", account.username, loadInUI.text.getText()));
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				loadInUI.text.setText("");
			}
		});
		
		loadInUI.hostGame.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String roomName = JOptionPane.showInputDialog("Enter a Room Name: ");
				String[] options = {"Tic-Tac-Toe", "Chess", "Checkers" };
				int choice = JOptionPane.showOptionDialog(null, "Choose a Game:", "Choose Game",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
				String[] temp = {roomName, Integer.toString(choice)};
				try 
				{
					oos.writeObject(new ServerObject("MAKEROOM", account.username, temp));
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		loadInUI.refreshPlayers.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					oos.writeObject(new ServerObject("REFRESHPLAYERS", account.username, null));
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		loadInUI.refreshGames.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					oos.writeObject(new ServerObject("REFRESHGAMES", account.username, null));
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		loadInUI.games.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent le) {
		    	  if(loadInUI.games.getSelectedIndex() != -1 && !le.getValueIsAdjusting()) 
		    	  {
		    		  try 
		    		  {
		    			  oos.writeObject(new ServerObject("JOINROOM", account.username, loadInUI.games.getSelectedIndex()));
		    		  } 
		    		  catch (IOException e1) 
		    		  {
		    			  e1.printStackTrace();
		    		  }
		    	  }
		      }
		});
	}
	
	public void run() throws IOException, ClassNotFoundException
	{
		// Set up server stuff
		Socket s = new Socket(server, port);
		oos = new ObjectOutputStream(s.getOutputStream());
		ois = new ObjectInputStream(s.getInputStream());
		
		while(true)
		{
			// Handle Commands from the server
			ServerObject packetIn = (ServerObject)ois.readObject();
			
			if(packetIn.getHeader().equals("NAMEREQUEST"))
			{
				while(true)
				{
					String[] options = { "Existing User", "New User" };
					int choice = JOptionPane.showOptionDialog(null, "Please choose an account option", "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					if (choice == 0)
					{
						// Existing user
						String accountName = JOptionPane.showInputDialog("Enter your username: ");
						String accountPass = JOptionPane.showInputDialog("Enter your password: ");
						Player temp = new Player(accountName, accountPass);
						oos.writeObject(new ServerObject("LOGIN", null, temp));
						ServerObject response = (ServerObject)ois.readObject();
						if (response.getHeader().equals("VALID"))
						{
							account = (Player) response.getPayload();
							break;
						}
					}
					else
					{
						// New user
						String accountName = JOptionPane.showInputDialog("Enter your username: ");
						String accountPass = JOptionPane.showInputDialog("Enter your password: ");
						Player temp = new Player(accountName, accountPass);
						oos.writeObject(new ServerObject("REGISTER", null, temp));
						ServerObject response = (ServerObject)ois.readObject();
						if (response.getHeader().equals("VALID"))
						{
							account = (Player) response.getPayload();
							break;
						}
					}
				}	
			}
			else if (packetIn.getHeader().equals("MESSAGE"))
			{
				loadInUI.message.append(packetIn.getSender() + "> " + packetIn.getPayload().toString() + "\n");
			}
			else if (packetIn.getHeader().equals("UPDATEPLAYERS"))
			{
				loadInUI.updatePlayers((String[])packetIn.getPayload());
			}
			else if (packetIn.getHeader().equals("UPDATEROOMS"))
			{
				loadInUI.updateRooms((String[]) packetIn.getPayload());
			}
			else if (packetIn.getHeader().equals("CONNECTTOROOM"))
			{
				connectToRoom(server, (int)packetIn.getPayload());
			}
			loadInUI.setTitle("Challenger Client: " + account.username);
		}
	}
	
	public void connectToRoom(String serverName, int port) throws UnknownHostException, IOException, ClassNotFoundException
	{	
		loadInUI.setVisible(false);
        Socket socket = new Socket(serverName, port);
		ObjectOutputStream oosRoom;
		ObjectInputStream oisRoom;
		GameRoomUI newRoom = new GameRoomUI();
		newRoom.setVisible(true);
		//this will create the new window and display everything
		oosRoom = new ObjectOutputStream(socket.getOutputStream());
		oisRoom = new ObjectInputStream(socket.getInputStream());
		//make the listners and all that shit in here, including sending stuff to the GameRoomServer
		newRoom.text.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try 
				{
					oosRoom.writeObject(new ServerObject("MESSAGE", account.username, newRoom.text.getText()));
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				newRoom.text.setText("");
			}
		});
		newRoom.leave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try 
				{
					oosRoom.writeObject(new ServerObject("QUIT", account.username, null));
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		while(true)
		{
			ServerObject packetIn = (ServerObject)oisRoom.readObject();
			
			if (packetIn.getHeader().equals("MESSAGE"))
			{
				newRoom.message.append(packetIn.getPayload().toString());
			}
			
			if (packetIn.getHeader().equals("FINISHED"))
			{
				break;
			}
		}
		
		loadInUI.setVisible(true);
		newRoom.dispose();
		socket.close();
		oosRoom.close();
		oisRoom.close();
	}
}
