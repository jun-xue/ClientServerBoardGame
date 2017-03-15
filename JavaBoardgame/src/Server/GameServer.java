package Server;

import java.net.Socket;

public class GameServer {
	
	Player player1;
	Player player2;
	
	ServerConnection player1connection;
	ServerConnection player2connection;
	
	GameBoard gameBoard;
	
	
	GameServer (Player player1, Player player2, 
				ServerConnection connection1, 
				ServerConnection connection2, 
				String gameType)
	{
		
		
		this.player1 = player1;
		this.player2 = player2;
		
		this.player1connection = connection1;
		this.player2connection = connection2;
		

		//finish later
		if(gameType.equals("TTT"))
			this.gameBoard = null;
		else if (gameType.equals("Checkers"))
			this.gameBoard = null;
		else if (gameType.equals("Chess"))
			this.gameBoard = null;
		
	}
	
	
	public void playGame(){
		
		//do it
	}
	
	

}
