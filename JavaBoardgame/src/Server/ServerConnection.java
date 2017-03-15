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
					
					
					//this is just me trying to not get an error from typecasting a 
					//ServerObject packetIn to a String. Still needs to be fixed.
					Object payloadToString = packetIn.getPayload();
					String textMessageContent = payloadToString.toString();
					
					//if a message in chat starts with the word "challenge" it will check accounts 
					//to see if who was challenged was valid, if so, players are thrown into a 
					//GameServer instance together
					if (textMessageContent.length() > 9 && textMessageContent.substring(0,9).equals("challenge")){	
						
						//if player exists,
						if(Player.checkForAccount(Player.getAccount(textMessageContent.substring(10)))){
							
							String challengee = textMessageContent.substring(10);
							sendStringToAllClients(packetIn.getSender() + "has challenged "+ challengee);
							
							System.out.println("*sick ass UI comes up where challenger select game*");
							
							//challengee gets an accept or decline choice
							
							
							//accepts
							
							
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
							//"TTT" = Tic-Tac-Toe
							GameServer Game = new GameServer(Player.getAccount(player1Connection.account.getUsername()),
												Player.getAccount(player2Connection.account.getUsername()),
												player1Connection, player2Connection, "TTT");
							
							
						}
					}

					else{
						
						//otherwise, treat message as normal send message in chatroom
						sendStringToAllClients(packetIn);
					}
					
					
	
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
