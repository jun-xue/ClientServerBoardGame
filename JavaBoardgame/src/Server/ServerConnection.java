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
	public Player account;
	
	public ServerConnection (Socket socket, Server server)
	{
		super("ServerConnectionThread");
		this.socket = socket;
		this.server = server;
	}
	
	public void sendPacketToClient(Object packet)
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
	
	public void sendPacketToAllClients(Object packet)
	{
		for (int index = 0; index < server.connections.size(); index++)
		{
			ServerConnection sc = server.connections.get(index);
			sc.sendPacketToClient(packet);
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
				
				//////////////////////////////////////////
				//////////      MESSAGES     /////////////
				//////////////////////////////////////////
				
				if (packetIn.getHeader().equals("MESSAGE"))
				{
					sendPacketToAllClients(packetIn);
				}
				
				//////////////////////////////////////////
				//////////  ACCOUNT SETTINGS /////////////
				//////////////////////////////////////////
				
				else if (packetIn.getHeader().equals("REGISTER"))
				{
					account = (Player)packetIn.getPayload();
					if (Player.checkForAccount(account) == true)
					{
						ServerObject outPacket = new ServerObject("INVALID", null, null);
						sendPacketToClient(outPacket);
						System.out.println("Account invalid");
					}
					else
					{
						Player.putNewAccount(account);
						Player.saveAccounts();
						ServerObject outPacket = new ServerObject("VALID", null, null);
						sendPacketToClient(outPacket);
						System.out.println("Account added");
					}
				}
				else if (packetIn.getHeader().equals("LOGIN"))
				{
					account = (Player)packetIn.getPayload();
					if (Player.checkPassword(account) == true)
					{
						ServerObject outPacket = new ServerObject("VALID", null, null);
						sendPacketToClient(outPacket);
						
					}
					else
					{
						ServerObject outPacket = new ServerObject("INVALID", null, null);
						sendPacketToClient(outPacket);
					}
				}
				//////////////////////////////////////////
				//////////       USERS       /////////////
				//////////////////////////////////////////
				
				else if (packetIn.getHeader().equals("USERS"))
				{
					ArrayList<String> users = new ArrayList<String>();
					for (ServerConnection sc : server.connections)
					{
						users.add(sc.account.username);
					}

					ServerObject outPacket = new ServerObject("USERS", null, users);
					sendPacketToClient(outPacket);
				}
				
				//////////////////////////////////////////
				//////////       LEAVING     /////////////
				//////////////////////////////////////////
				
				else if (packetIn.getHeader().equals("LEAVE"))
				{
					server.connections.remove(this);
					break;
				}
				
				//////////////////////////////////////////
				///////////   CHALLENGING    /////////////
				//////////////////////////////////////////
				
				
				else if (packetIn.getHeader().equals("CHALLENGE"))
				{
					int temp = ((int) packetIn.getPayload());
					ServerConnection challenged = null;
					ServerConnection challenger = null;
					
					for (ServerConnection sc : server.connections)
					{
						if (sc.account.username.equals(packetIn.getReceiver()))
						{
							challenged = sc;
						}
						
						if (sc.account.username.equals(packetIn.getSender()))
						{
							challenger = sc;
						}
					}
					if (server.challenges.containsKey(challenger.account.username))
					{
						ServerObject outPacket2 = new ServerObject("MESSAGE", "Server", "You already have a pending challenge out!");
						challenger.dout2.writeObject(outPacket2);
						continue;
					}
					System.out.println("Putting " + challenger.account.username + challenged.account.username );
					server.challenges.put(challenger.account.username, challenged.account.username);
					
					if (temp == 0)
					{
						ServerObject outPacket = new ServerObject("MESSAGE", "Server", "You have been challenged by " + packetIn.getSender() + " to a game of Tic Tac Toe.");
						challenged.dout2.writeObject(outPacket);
						
						ServerObject outPacket2 = new ServerObject("MESSAGE", "Server", "You have challenged " + packetIn.getReceiver() + " to a game of Tic Tac Toe.");
						challenger.dout2.writeObject(outPacket2);
					}
					else if (temp == 1)
					{
						ServerObject outPacket = new ServerObject("MESSAGE", "Server", "You have been challenged by " + packetIn.getSender() + " to a game of Chess.");
						challenged.dout2.writeObject(outPacket);
						
						ServerObject outPacket2 = new ServerObject("MESSAGE", "Server", "You have challenged " + packetIn.getReceiver() + " to a game of Chess.");
						challenger.dout2.writeObject(outPacket2);
					}
					else if (temp == 2)
					{
						ServerObject outPacket = new ServerObject("MESSAGE", "Server", "You have been challenged by " + packetIn.getSender() + " to a game of Checkers.");
						challenged.dout2.writeObject(outPacket);
						
						ServerObject outPacket2 = new ServerObject("MESSAGE", "Server", "You have challenged " + packetIn.getReceiver() + " to a game of Checkers.");
						challenger.dout2.writeObject(outPacket2);
					}
					
				}
				
				else if (packetIn.getHeader().equals("CHALLENGES"))
				{
					ServerObject outPacket = new ServerObject("CHALLENGES", "Server", server.challenges);
					sendPacketToAllClients(outPacket);
				}
				
				//////////////////////////////////////////
				///////////     ACCEPTING    /////////////
				//////////////////////////////////////////
				else if (packetIn.getHeader().equals("ACCEPT"))
				{
					ServerConnection player1Connection = null;
					ServerConnection player2Connection = null;
					
					for (int index = 0; index < server.connections.size(); index++)
					{
						ServerConnection find = server.connections.get(index);
						if (find.account.getUsername().equals(packetIn.getSender()))
							player1Connection = find;
						else if (find.account.getUsername().equals(packetIn.getReceiver()))
							player2Connection = find;
					}
					
					GameServer Game = new GameServer(Player.getAccount(player1Connection.account.getUsername()),
							Player.getAccount(player2Connection.account.getUsername()),
							player1Connection, player2Connection, (int)packetIn.getPayload());
				}
				
				//////////////////////////////////////////
				///////////     DECLINING    /////////////
				//////////////////////////////////////////
				
				else if (packetIn.getHeader().equals("DECLINE"))
				{
					ServerConnection challenged = null;
					ServerConnection challenger = null;
					
					for (ServerConnection sc : server.connections)
					{
						if (sc.account.username.equals(packetIn.getReceiver()))
						{
							challenged = sc;
						}
						
						if (sc.account.username.equals(packetIn.getSender()))
						{
							challenger = sc;
						}
					}
					
					server.challenges.remove(packetIn.getSender());
					
					
					//////////// FIX THIS ITS SO CLOSE
					
					
					ServerObject outPacket = new ServerObject("MESSAGE", "Server", "You declined " + packetIn.getSender() + "'s challenge.");
					challenged.dout2.writeObject(outPacket);
					
					ServerObject outPacket2 = new ServerObject("MESSAGE", "Server", packetIn.getReceiver() + " declined your challenge!");
					challenger.dout2.writeObject(outPacket2);
					
				}
			}

			din2.close();
			dout2.close();
			socket.close();
		} 
		catch (IOException e) 
		{
			server.connections.remove(this);
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
