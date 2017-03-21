package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import Games.AbstractGameFactory;
import Games.CheckersFactory;
import Games.CheckersGame;
import Games.Game;
import Games.OthelloFactory;
import Games.TicTacToeFactory;

import Games.TicTacToeGame;
import Games.Tile;

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
    
    private AbstractGameFactory gameFactory;
    private Game game;
    
    public ServerSocket socket;
    public int port;

    public GameRoom(String roomName)
    {
    	this.roomName = roomName;
    }
    
    public void setUpGame(int gameNumber) throws IOException
    {
    	if (gameNumber == 0)
    	{
    		gameName = "TTT";
    		boardSize = 3;
    		
    		gameFactory = new TicTacToeFactory();
            game = gameFactory.createGame(gameFactory);

    	}
    	else if (gameNumber == 1)
    	{
    		gameName = "Othello";
    		boardSize = 8;
    		
    		gameFactory = new OthelloFactory();
            game = gameFactory.createGame(gameFactory);
    	}
    	else if (gameNumber == 2)
    	{
    		gameName = "Checkers";
    		boardSize = 8;
    		
    		gameFactory = new CheckersFactory();
            game = gameFactory.createGame(gameFactory);
    	}
    	
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
    	private Player a;
    	
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
					
					if (player1 != null && player2 != null)
					{
						game.client.name = player1.username;
						game.opponent.name = player2.username;
						
					}
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
		
		public GameRoomServerHandler(Socket s)
		{
			this.s = s; 
//			synchronized (usernames) 
//			{
//				if(!usernames.contains(acc.username)) 
//				{
//					usernames.add(acc.username);
//				}
//			}
		}
		
		public void run()
		{
			try
			{
				oos = new ObjectOutputStream(s.getOutputStream());
				ois = new ObjectInputStream(s.getInputStream());
				outputStreams.add(oos);
				ArrayList<Tile> moves;

				sendPacketToAllClients(new ServerObject("GAMEINFO", "Server", gameName));
				sendPacketToClient(new ServerObject("REQUESTINFO", "Server", null));
				
				while(!game.state.gameOver)
				{
					if (player1 != null && player2 != null)
					{
						game.client.name = player1.username;
						game.opponent.name = player2.username;	
					}
					
					sendPacketToAllClients(new ServerObject("GAMESTATE", "Server", game.state));
					ServerObject packetIn = (ServerObject)ois.readObject();
					
					if (packetIn.getHeader().equals("MOVE"))
					{
						if (game.state.isTurn.name.equals(packetIn.getSender()))
						{
							moves = new ArrayList<Tile>((ArrayList<Tile>) packetIn.getPayload());
							for (Tile move : moves)
							{
								System.out.println(move.getRow() + " " + move.getColumn());
							}
							
				    		//game.doMove(((Tile[])packetIn.getPayload()));
							//update this game state
							moves.clear();
						}
					}
					else if (packetIn.getHeader().equals("MESSAGE"))
					{
						sendPacketToAllClients(new ServerObject("MESSAGE", packetIn.getSender(), packetIn.getSender() + "> " + packetIn.getPayload() + "\n"));
					}
					else if (packetIn.getHeader().equals("INFO"))
					{
						System.out.println("Info recived from " + packetIn.getSender());
						if (player1 == null && player2 == null)
						{
							player1 = (Player) packetIn.getPayload();
						}
						else if (player1 != null && player2 == null)
						{
							player2 = (Player) packetIn.getPayload();
						}
						else if (player1 == null && player2 != null)
						{
							player1 = (Player) packetIn.getPayload();
						}
					}
					else if (packetIn.getHeader().equals("QUIT"))
					{
						sendPacketToAllClients(new ServerObject("FINISHED", "Server", null));
						System.out.println(player1.username);
						System.out.println(player2.username);
						currentPlayers = 0;
						break;
					}
				}
				
				//GAME WON
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
