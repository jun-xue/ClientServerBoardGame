package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import Server.Player;
import Server.ServerObject;

public class ClientMain 
{
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private String server;
	private int port;
	private String[] usernameList;
	private String[] roomList;
	private Player account;
	//private LoadInUI loadInUI;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		String serverName = JOptionPane.showInputDialog("Enter Server IP the Server is running on");
		int portNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter Server IP the Server is running on"));
		
		ClientMain client = new ClientMain(serverName, portNumber);
		//client.loadInUI.setVisible(true);
		client.run();
	}
	public ClientMain(String server, int port)
	{
		this.server = server;
		this.port = port;
		//loadInUI = new LoadInUI();
		
		//make ui for the screen that opens when you load in
		//make it in a new class
		//make action listoners for the loadInUI stuff here
		
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
				//loadInUI.textfield.append(packetIn.getPayload.toString());
			}
			else if (packetIn.getHeader().equals("CONNECTTOROOM"))
			{
				// the packetIN payload will have the port number for the room to connect to
				connectToRoom(server, (int)packetIn.getPayload());
			}
		}
	}
	
	public void connectToRoom(String serverName, int port) throws UnknownHostException, IOException
	{	
        Socket socket = new Socket(serverName, port);
		ObjectOutputStream oosRoom;
		ObjectInputStream oisRoom;
		//this will create the new window and display everything
		oosRoom = new ObjectOutputStream(socket.getOutputStream());
		oisRoom = new ObjectInputStream(socket.getInputStream());
		//make the listners and all that shit in here, including sending stuff to the GameRoomServer
	}
}
