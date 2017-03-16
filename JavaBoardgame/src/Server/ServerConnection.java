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
					
					
					//this is just me trying to not get an error from typecasting a 
					//ServerObject packetIn to a String.
					Object payloadToString = packetIn.getPayload();
					String textMessageContent = payloadToString.toString();
					
					//if a message in chat starts with the word "challenge" it will check accounts 
					//to see if who was challenged was valid, if so, players are thrown into a 
					//GameServer instance together. Messages are split by a space
					//so an example would be: "challenge player1 checkers"
					
					if (textMessageContent.length() > 9 && textMessageContent.substring(0,9).equals("challenge")){	
						String [] temp = textMessageContent.split(" ");
						//if player exists,
						String name = temp[1];
						String game = temp[2];
						
						if(Player.checkForAccount(Player.getAccount(name))){	
							String challengee = name;
						
							//print in chat that player1 has challenged player2
							ServerObject sendoutAnnouncement = new ServerObject("SERVER ANNOUNCEMENT", null, 
																packetIn.getSender() 
																+ " has challenged "
																+ challengee + "!!!");
							sendPacketToAllClients(sendoutAnnouncement);
							
							
							
							//System.out.println("*sick ass UI comes up where challenger select game*");
							
							//challengee gets an accept or decline choice
							
							
							//challengee indicates to accept
							
							
							//the challenger: packetIn.getSender() = player1
							//the challengee: challengee = player2
							
							
							//find appropriate client connections from Player account usernames
							ServerConnection player1Connection = null;
							ServerConnection player2Connection = null;
							
							for (int index = 0; index < server.connections.size(); index++)
							{
								ServerConnection find = server.connections.get(index);
								if (find.account.getUsername().equals(packetIn.getSender()))
									player1Connection = find;
								else if (find.account.getUsername().equals(challengee))
									player2Connection = find;
							}
							
							//i hope this works
							GameServer Game = new GameServer(Player.getAccount(player1Connection.account.getUsername()),
												Player.getAccount(player2Connection.account.getUsername()),
												player1Connection, player2Connection, game);
	
						}
					}

					else{
						
						//otherwise, treat message as normal send message in chatroom
						sendPacketToAllClients(packetIn);
					}
					
					
	
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
					System.out.println("Sending Clients");
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
					
					ServerObject outPacket = new ServerObject("MESSAGE", "Server", "You have been challenged by " + packetIn.getSender() + " to a game of " + temp + ".");
					challenged.dout2.writeObject(outPacket);
					
					ServerObject outPacket2 = new ServerObject("MESSAGE", "Server", "You have challenged " + packetIn.getReceiver() + " to a game of " + temp + ".");
					challenger.dout2.writeObject(outPacket2);
				}
				
				else if (packetIn.getHeader().equals("ACCEPT"))
				{
					if (server.challenges.contains(this))
					{
						// START NEW SERVER WITH GAME MODE IN IT
						// GameServer gs = new GameServer(this, Somehow get the person who challenged, server.challenges.get(this));
						server.challenges.remove(this);
					}
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
