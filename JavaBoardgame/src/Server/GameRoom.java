package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class GameRoom 
{
	private HashSet<String> usernames = new HashSet<String>();
    private HashSet<ObjectOutputStream> outputStreams = new HashSet<ObjectOutputStream>();
    
    public String roomName;
    private int maxPlayers = 2;
    private int currentPlayers = 0;
    
    // private gameFactory
    // private game
    
    public ServerSocket socket;
    public int port;
    
    public Player account;

    public GameRoom(String roomName)
    {
    	this.roomName = roomName;
    	//gameFactory = new gameFactory();
    }
    
    public void setUpGame(int gameNumber) throws IOException
    {
    	//game = gameFactory.createGame(gameNumber);
    	socket = new ServerSocket(0); // using 0 will just assign it to an unused one.
    	port = socket.getLocalPort();
    }
    
    public void createGameServer(ServerSocket gameSocket)
    {
    	new GameRoomServer(gameSocket).start();
    }
    
    public class GameRoomServer extends Thread
    {
    	private ServerSocket s;
    	
    	public GameRoomServer(ServerSocket s)
    	{
    		this.s = s;
    	}
    	
    	public void run()
    	{
			System.out.println("Starting new game room server thread");
			while(currentPlayers != maxPlayers)
			{
				try 
				{
					new GameRoomServerHandler(s.accept()).start();
					currentPlayers++;
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
    	}
    }
    
    public class GameRoomServerHandler extends Thread
    {
    	private Player acc;
    	private Socket s;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private boolean gameNotWon = true;
		
		public GameRoomServerHandler(Socket s)
		{
			this.s = s; 
			this.acc = account;
			synchronized (usernames) 
			{
				if(!usernames.contains(acc.username)) 
				{
					usernames.add(acc.username);
				}
			}
		}
		
		public void run()
		{
			try
			{
				oos = new ObjectOutputStream(s.getOutputStream());
				ois = new ObjectInputStream(s.getInputStream());
				outputStreams.add(oos);
				
				while(gameNotWon)
				{
					ServerObject packetIn = (ServerObject)ois.readObject();
					
					if(packetIn.getHeader().equals("START"))
					{
						//game.start()
					}
					else if (packetIn.getHeader().equals("MOVE"))
					{
						//move logic
					}
					else if (packetIn.getHeader().equals("MESSAGE"))
					{
						sendPacketToAllClients(new ServerObject("MESSAGE", packetIn.getSender(), packetIn.getPayload()));
					}
				}
				
				//GAME WON
				sendPacketToAllClients(new ServerObject("MESSAGE", "Server", "Someone Won the game!"));
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
				try
				{
					s.close();
					sendPacketToAllClients(new ServerObject("MESSAGE", "Server", "Challenger " + account.username + " has joined the server!"));
				}
				catch (IOException e)
				{
					System.out.print("Error Closing GameServerRoom Connection");
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
