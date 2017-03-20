package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import Games.AbstractGameFactory;
import Games.Game;
import Games.TicTacToeFactory;


public class GameRoom 
{
	private HashSet<String> usernames = new HashSet<String>();
    private HashSet<ObjectOutputStream> outputStreams = new HashSet<ObjectOutputStream>();
    
    public String roomName;
    public int maxPlayers = 2;
    public int currentPlayers = 0;
    public Player player1;
    public Player player2;
    public int boardSize;
    public String gameName;
    
    // private gameFactory
    
    public ServerSocket socket;
    public int port;

    public GameRoom(String roomName)
    {
    	this.roomName = roomName;
    	//gameFactory = new gameFactory();
    }
    
    public void setUpGame(int gameNumber) throws IOException
    {
    	if (gameNumber == 0)
    	{
    		gameName = "TTT";
    		boardSize = 3;
    		//tictactoe
            AbstractGameFactory tttgf = new TicTacToeFactory();
            Game tictactoe = tttgf.createGame(tttgf);
            tictactoe.runGame();
            
    	}
    	else if (gameNumber == 1)
    	{
    		gameName = "Chess";
    		boardSize = 8;

    		//chess
    	}
    	else if (gameNumber == 2)
    	{
    		gameName = "Checkers";
    		boardSize = 8;

    		//checkers
    	}
    	socket = new ServerSocket(0); // using 0 will just assign it to an unused one.
    	port = socket.getLocalPort();
    }
    
    public void createGameServer(ServerSocket gameSocket, Player account)
    {
    	new GameRoomServer(gameSocket, account).start();
    }
    
    public class GameRoomServer extends Thread
    {
    	private ServerSocket s;
    	private Player a;
    	
    	public GameRoomServer(ServerSocket s, Player a)
    	{
    		this.s = s;
    		this.a = a;
    	}
    	
    	public void run()
    	{
			System.out.println("Starting new game room server thread");
			while(currentPlayers != maxPlayers)
			{
				try 
				{
					if (player1 == null && player2 == null)
					{
						player1 = this.a;
					}
					else if (player1 != null && player2 == null)
					{
						player2 = this.a;
					}
					else if (player1 == null && player2 != null)
					{
						player1 = this.a;
					}
					
					new GameRoomServerHandler(s.accept(), a).start();
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
		
		public GameRoomServerHandler(Socket s, Player account)
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
				
				sendPacketToAllClients(new ServerObject("GAMEINFO", "Server", gameName));
				
				while(gameNotWon)
				{
					//oos.writeObject(new ServerObject("GAMESTATE", "Server", game.));
					ServerObject packetIn = (ServerObject)ois.readObject();
					
					if(packetIn.getHeader().equals("START"))
					{
						//game.start()
					}
					else if (packetIn.getHeader().equals("MOVE"))
					{
			    		
					}
					else if (packetIn.getHeader().equals("MESSAGE"))
					{
						sendPacketToAllClients(new ServerObject("MESSAGE", packetIn.getSender(), packetIn.getSender() + "> " + packetIn.getPayload() + "\n"));
					}
					else if (packetIn.getHeader().equals("QUIT"))
					{
						//award win to other player
						sendPacketToAllClients(new ServerObject("FINISHED", "Server", null));
						currentPlayers = 0;
						break;
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
