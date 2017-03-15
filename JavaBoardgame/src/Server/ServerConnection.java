package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection extends Thread 
{
	Socket socket;
	Server server;
	ObjectInputStream din2;
	ObjectOutputStream dout2;
	boolean shouldRun = true;
	private Player account;
	
	public ServerConnection (Socket socket, Server server)
	{
		super("ServerConnectionThread");
		this.socket = socket;
		this.server = server;
	}
	
	public void sendStringToClient(Object packet)
	{
		try 
		{
			dout2.writeObject(packet);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void sendStringToAllClients(Object packet)
	{
		for (int index = 0; index < server.connections.size(); index++)
		{
			ServerConnection sc = server.connections.get(index);
			sc.sendStringToClient(packet);
		}
	}
	
	public void run()
	{
		try 
		{
			din2 = new ObjectInputStream(socket.getInputStream());
			dout2 = new ObjectOutputStream(socket.getOutputStream());
			
			while(shouldRun)
			{
				ServerObject packetIn = (ServerObject)din2.readObject();
				
				if (packetIn.getHeader().equals("MESSAGE"))
				{
					sendStringToAllClients(packetIn);
				}
				
				else if (packetIn.getHeader().equals("REGISTER"))
				{
					account = (Player)packetIn.getPayload();
					if (Player.checkForAccount(account) == true)
					{
						ServerObject outPacket = new ServerObject("INVALID", null, null);
						sendStringToClient(outPacket);
						System.out.println("Account invalid");
					}
					else
					{
						Player.putNewAccount(account);
						Player.saveAccounts();
						ServerObject outPacket = new ServerObject("VALID", null, null);
						sendStringToClient(outPacket);
						System.out.println("Account added");
					}
				}
				else if (packetIn.getHeader().equals("LOGIN"))
				{
					account = (Player)packetIn.getPayload();
					if (Player.checkPassword(account) == true)
					{
						ServerObject outPacket = new ServerObject("VALID", null, null);
						sendStringToClient(outPacket);
						
					}
					else
					{
						ServerObject outPacket = new ServerObject("INVALID", null, null);
						sendStringToClient(outPacket);
						System.out.println("Account invalid");
					}
				}
				else if (packetIn.getHeader().equals("LEAVE"))
				{
					server.connections.remove(this);
					break;
				}
			}

			din2.close();
			dout2.close();
			socket.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
