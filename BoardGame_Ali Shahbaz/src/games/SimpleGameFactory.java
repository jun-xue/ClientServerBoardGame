package games;

public class SimpleGameFactory {

	public Game createGame(String gameType) {
		Game game = null;
		GameLogic logic = null;
		
		if (gameType.contains("Tic-Tac-Toe")) {
			
			logic = new TicTacToeGameLogic();
			game = new TicTacToeGame(gameType, 3, 3, 2, 2, logic);
			
			
		}
		return game;
	}
	
}
