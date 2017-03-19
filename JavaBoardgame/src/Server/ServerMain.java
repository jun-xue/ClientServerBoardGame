package Server;

import java.awt.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ServerMain 
{
	
	// PORT 42069 WILL BE FOR THE HUB
	// All OTHER PORTS AER FREEGAME
	
    private static HashSet<String> usernames = new HashSet<String>();
    private static HashSet<ObjectOutputStream> outputStreams = new HashSet<ObjectOutputStream>();
    private static ArrayList<GameRoom> gameRoomsList = new ArrayList<GameRoom>();
    
	public static void main(String[] args) throws IOException
	{
		// SERVER START //
		Player.getAccounts();
		ServerMain server = new ServerMain();
		ServerSocket ss = new ServerSocket(42069);
		System.out.println("Server is running on Port: " + 42069);
		
		try
		{
			while(true)
			{
				server.new ServerConnection(ss.accept()).start();
			}
		}
		finally
		{
			Player.saveAccounts();
			ss.close();
		}
	}
	
	private class ServerConnection extends Thread
	{
		// Variables
		
		private Player account;
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		
		// Constructor
		
		public ServerConnection(Socket socket)
		{
			this.socket = socket;
		}
		
		// Thread Runner
		
		public void run()
		{
			System.out.println("New Thread Started");
			try
			{
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				
				// Get the account of this player by rerunning until a correct response is sent
				while (true)
				{
					boolean received = false;
					// Send a request to the client for a Name Object
					sendPacketToClient(new ServerObject("NAMEREQUEST", "Server", null));
					
					ServerObject packetIn = (ServerObject)ois.readObject();
					
					// If the response is to register
					if (packetIn.getHeader().equals("REGISTER"))
					{
						if (Player.checkForAccount((Player)packetIn.getPayload()) == true) // If the name is already registered
						{
							ServerObject outPacket = new ServerObject("INVALID", null, null);
							sendPacketToClient(outPacket);
							
							System.out.println("Account invalid");
						}
						else // if the name is not registered already
						{
							Player.putNewAccount((Player)packetIn.getPayload());
							Player.saveAccounts();
							
							ServerObject outPacket = new ServerObject("VALID", null, (Player)packetIn.getPayload());
							sendPacketToClient(outPacket);
							account = (Player)packetIn.getPayload();
							System.out.println("New user " + account.username + " has connected with " + account.wins + " wins and " + account.loses + " loses!\n");
							received = true;
						}
					}
					
					// If the response is to login
					else if (packetIn.getHeader().equals("LOGIN"))
					{
						
						if (Player.checkForAccount((Player)packetIn.getPayload()) == true && Player.checkPassword((Player)packetIn.getPayload()) == true) // if the name does exist and the password is right
						{
							ServerObject outPacket = new ServerObject("VALID", null, (Player)packetIn.getPayload());
							sendPacketToClient(outPacket);
							
							account = (Player)packetIn.getPayload();
							
							System.out.println("User " + account.username + " has connected with " + account.wins + " wins and " + account.loses + " loses!\n");
							received = true;
						}
						else // the name either doesn't exist or had the wrong password
						{
							ServerObject outPacket = new ServerObject("INVALID", null, null);
							sendPacketToClient(outPacket);
							System.out.println("Account invalid");
						}
					}
					
					// Since we are updating usernames across different threads, we need to use synchronized
					if (received == true)
					{		
						synchronized (usernames)
						{
							if (!usernames.contains(account.username))
							{
								usernames.add(account.username);
								break;
							}
						}
					}
				}
				
				// Since the Name is Accepted, send an okay to the clients.
				outputStreams.add(oos);
				
				// Notify all users of someone joining the server.
				sendPacketToAllClients(new ServerObject("MESSAGE", "Server", "Challenger " + account.username + " has joined the server!\n"));
				sendPacketToAllClients(new ServerObject("UPDATEPLAYERS", "Server", usernames.toArray(new String[usernames.size()])));
				
				//The while loop that takes all requests
				while(true)
				{
					ServerObject packetIn = (ServerObject)ois.readObject();
					
					// Chat Message to be sent to all other clients
					if (packetIn.getHeader().equals("MESSAGE"))
					{
						sendPacketToAllClients(new ServerObject("MESSAGE", packetIn.getSender(), packetIn.getPayload()));
					}
					
					// The Request to make a room
					else if(packetIn.getHeader().equals("MAKEROOM"))
					{
						GameRoom newRoom;
						//payload[0] == room name
						//payload[1] == gametype
						synchronized (gameRoomsList) 
						{
							newRoom = new GameRoom(((String[])packetIn.getPayload())[0]);
							newRoom.setUpGame(Integer.parseInt(((String[])packetIn.getPayload())[1]));
							gameRoomsList.add(newRoom);
						}
						ServerSocket gameSocket = new ServerSocket(0);
						newRoom.port = gameSocket.getLocalPort();
						newRoom.createGameServer(gameSocket, account);
						sendPacketToClient(new ServerObject("CONNECTTOROOM", "Server", newRoom.port));
						
						
					}
					
					// The Request to join a room
					else if(packetIn.getHeader().equals("JOINROOM"))
					{
						sendPacketToClient(new ServerObject("CONNECTTOROOM", "Server", gameRoomsList.get((int) packetIn.getPayload()).port));

					
					}
					else if(packetIn.getHeader().equals("REFRESHPLAYERS"))
					{
						ArrayList<String> names = new ArrayList<String>();
						for(String name: usernames) 
						{ 
							names.add(name);
						}
						
						sendPacketToClient(new ServerObject("UPDATEPLAYERS", "Server", names.toArray(new String[names.size()])));
					}
					
					else if(packetIn.getHeader().equals("REFRESHGAMES"))
					{
						ArrayList<String> rooms = new ArrayList<String>();
						synchronized (gameRoomsList) 
						{
							ArrayList<Integer> removeThese = new ArrayList<Integer>();
							for(GameRoom room: gameRoomsList) 
							{
								if(room.currentPlayers == room.maxPlayers ) 
								{
									removeThese.add(gameRoomsList.indexOf(room));
								}
								if(room.currentPlayers == 0)
								{
									removeThese.add(gameRoomsList.indexOf(room));
								}
							}
							for(int index : removeThese) 
							{
								gameRoomsList.remove(index);
							}
							
							if (gameRoomsList.size() != 0)
							{
								for(GameRoom name: gameRoomsList) 
								{ 
									rooms.add(name.roomName);
								}
							}
							sendPacketToClient(new ServerObject("UPDATEROOMS", "Server", rooms.toArray(new String[rooms.size()])));
						}
					}
					synchronized (gameRoomsList) 
					{
						ArrayList<Integer> removeThese = new ArrayList<Integer>();
						for(GameRoom room: gameRoomsList) 
						{
							if(room.currentPlayers == room.maxPlayers) 
							{
								removeThese.add(gameRoomsList.indexOf(room));
							}
						}
						for(int index : removeThese) 
						{
							gameRoomsList.remove(index);
						}
					}
				}	
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
			finally
			{
				if(account.username != null || oos != null)
				{
					usernames.remove(account.username);
					outputStreams.remove(oos);
				}
				
				try 
				{
					Player.saveAccounts();
					socket.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		// Send packet to the user
		public void sendPacketToClient(Object packet) throws IOException
		{
			oos.writeObject(packet);
		}
		
		// Send packet to all users
		public void sendPacketToAllClients(Object packet) throws IOException
		{
			for (ObjectOutputStream ooss : outputStreams)
			{
				ooss.writeObject(packet);
			}
		}
	}
}
