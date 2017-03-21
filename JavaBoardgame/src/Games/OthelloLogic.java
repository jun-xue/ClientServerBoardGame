package Games;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JLabel;
public class OthelloLogic implements MouseListener{

	OthelloGame game;
	GameState gamestate;
	Player isTurn;
	boolean moved = false;
	private String[][] board;
	private Tile[][] grid;
	private String turn;
	private static final String BLACK = "B";
	private static final String WHITE= "W";
	private static final String NONE = " ";

	public OthelloLogic(OthelloGame game, GameState gamestate, String[][] board, String turn){
		this.game = game;
		this.gamestate = gamestate;
		this.isTurn = game.isTurn;
		this.grid = game.board.boardMatrix;
		this.board = board;
		this.turn = turn;
		for(Tile[] tt: grid){
			for(Tile t: tt){
				t.addMouseListener(this);
			}
		}
	}

	public OthelloLogic(String[][] board, String turn){
		this.board = board;
		this.turn = turn;
	}

	public OthelloLogic(OthelloLogic toCopy){
		this.setTurn(toCopy.getTurn());
		this.setBoard(toCopy.getBoard());
	}

	public GameState takeTurn(Player playerTurn, GameState gamestate){
		this.isTurn = playerTurn;
		this.gamestate = new GameState(gamestate);
		gamestate.gameOver = (!validMoveAvailable(this) && 
				!validMoveAvailable(new OthelloLogic(this.getBoard(), this.oppositeTurn(this.getTurn()))));
		return gamestate;
	}

	public Tile[][] stringBoardToTileBoard(String[][] board){
		Tile[][] guiBoard = new Tile[board.length][this.columns()];
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[row].length; col++){
				if(board[row][col].equals(NONE)){
					guiBoard[row][col] = null;
				} else if(board[row][col].equals(BLACK)){
					guiBoard[row][col].setOwned(BLACK);
				} else if(board[row][col].equals(WHITE)){
					guiBoard[row][col].setOwned(WHITE);
				}
			}
		}
		return guiBoard;
	}



	public static String[][] newGameBoard(int rows, int columns, String top_left){
		String[][] returner = new String[rows][columns];
		String top_right;

		for(int row = 0; row < rows; row++){
			for(int col = 0; col < columns; col++){
				returner[row][col] = NONE;
			}
		}
		if(top_left.equals(BLACK)){
			top_right = WHITE;
		} else {
			top_right = BLACK;
		}

		returner[Math.floorDiv(rows, 2) - 1][Math.floorDiv(columns, 2) - 1] = top_left;
		returner[Math.floorDiv(rows, 2)][Math.floorDiv(columns, 2)] = top_left;
		returner[Math.floorDiv(rows, 2) - 1][Math.floorDiv(columns, 2)] = top_right;
		returner[Math.floorDiv(rows, 2)][Math.floorDiv(columns, 2) - 1] = top_right;

		return returner;
	}

	public int columns(){
		int columns = 0;
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[row].length; col++){
				columns += 1;
			}
			break;
		}
		return columns;
	}

	public String oppositeTurn(String turn){
		if(turn.equals(BLACK)){
			return WHITE;
		} else {
			return BLACK;
		}
	}

	public OthelloLogic place(int row, int column){
		if(board[row - 1][column - 1].equals(NONE)){
			board[row - 1][column - 1] = turn;
		}
		return this;
	}

	public void setTurn(String turn){
		this.turn = turn;
	}

	public String getTurn(){
		return this.turn;
	}

	public String[][] getBoard(){
		return this.board;
	}

	public void setBoard(String[][] newBoard){
		for(int row = 0; row < newBoard.length; row++){
			for(int col = 0; col < newBoard[row].length; col++){
				board[row][col] = newBoard[row][col];
			}
		}
	}

	public String BLACK(){
		return BLACK;
	}

	public String WHITE(){
		return WHITE;
	}

	public boolean checkLH(OthelloLogic state, int row, int col) {
		boolean truth = false;

		while(col > 0 && state.getBoard()[row][col - 1].equals(state.oppositeTurn(state.getTurn()))){
			col = col - 1;
			if(col > 0){
				if(state.getBoard()[row][col - 1].equals(state.getTurn()) && 
						state.getBoard()[row][col].equals(state.oppositeTurn(state.getTurn()))){
					truth = true;
				} else {
					truth = false;
				}
			}
		}

		return truth;
	}

	public OthelloLogic calculateLH(OthelloLogic state, int row, int col) {
		String[][] returner = state.getBoard();
		returner[row][col] = state.getTurn();
		
		while(state.getBoard()[row][col - 1].equals(state.oppositeTurn(state.getTurn())) && col > 0){
			returner[row][col - 1] = state.getTurn();
			if(state.getTurn().equals(WHITE)){
				grid[row][col - 1].removeAll();
				grid[row][col - 1].addPiece(isTurn.playerPieces.get(0));
			} else {
				grid[row][col - 1].removeAll();
				grid[row][col - 1].addPiece(isTurn.playerPieces.get(1));
			}
			col = col - 1;
		}

		state.setBoard(returner);
		return state;
	}

	public boolean checkRH(OthelloLogic state, int row, int col){
		boolean truth = false;

		while(col < (state.columns() - 1) && state.getBoard()[row][col + 1].equals(state.oppositeTurn(state.getTurn()))){
			col = col + 1;
			if(col < (state.columns() - 1)){
				if(state.getBoard()[row][col + 1].equals(state.getTurn()) && 
						state.getBoard()[row][col].equals(state.oppositeTurn(state.getTurn()))){
					truth = true;
				} else {
					truth = false;
				}
			}
		}

		return truth;
	}

	public OthelloLogic calculateRH(OthelloLogic state, int row, int col) {
		String[][] returner = state.getBoard();
		returner[row][col] = state.getTurn();
		while(state.getBoard()[row][col + 1].equals(state.oppositeTurn(state.getTurn())) && col < (state.columns() - 1)){
			returner[row][col + 1] = state.getTurn();
			if(state.getTurn().equals(WHITE)){
				grid[row][col + 1].removeAll();
				grid[row][col + 1].addPiece(isTurn.playerPieces.get(0));
			} else {
				grid[row][col + 1].removeAll();
				grid[row][col + 1].addPiece(isTurn.playerPieces.get(1));
			}
			col = col + 1;
		}

		state.setBoard(returner);

		return state;
	}

	public boolean checkUV(OthelloLogic state, int row, int col){
		boolean truth = false;

		while(row > 0 && state.getBoard()[row - 1][col].equals(state.oppositeTurn(state.getTurn()))){
			row = row - 1;
			if(row > 0){
				if(state.getBoard()[row - 1][col].equals(state.getTurn()) && 
						state.getBoard()[row][col].equals(state.oppositeTurn(state.getTurn()))){
					truth = true;
				} else {
					truth = false;
				}
			}
		}

		return truth;
	}

	public OthelloLogic calculateUV(OthelloLogic state, int row, int col) {
		String[][] returner = state.getBoard();
		returner[row][col] = state.getTurn();
		while(state.getBoard()[row - 1][col].equals(state.oppositeTurn(state.getTurn())) && row > 0){
			returner[row - 1][col] = state.getTurn();
			if(state.getTurn().equals(WHITE)){
				grid[row - 1][col].removeAll();
				grid[row - 1][col].addPiece(isTurn.playerPieces.get(0));
			} else {
				grid[row - 1][col].removeAll();
				grid[row - 1][col].addPiece(isTurn.playerPieces.get(1));
			}
			row = row - 1;
		}

		state.setBoard(returner);

		return state;
	}

	public boolean checkDV(OthelloLogic state, int row, int col){
		boolean truth = false;

		while(row < state.getBoard().length - 1 && state.getBoard()[row + 1][col].equals(state.oppositeTurn(state.getTurn()))){
			row = row + 1;
			if(row < state.getBoard().length - 1){
				if(state.getBoard()[row + 1][col].equals(state.getTurn()) && 
						state.getBoard()[row][col].equals(state.oppositeTurn(state.getTurn()))){
					truth = true;
				} else {
					truth = false;
				}
			}
		}

		return truth;
	}

	public OthelloLogic calculateDV(OthelloLogic state, int row, int col) {
		String[][] returner = state.getBoard();
		returner[row][col] = state.getTurn();
		while(state.getBoard()[row + 1][col].equals(state.oppositeTurn(state.getTurn())) && row < state.getBoard().length - 1){
			returner[row + 1][col] = state.getTurn();
			if(state.getTurn().equals(WHITE)){
				grid[row + 1][col].removeAll();
				grid[row + 1][col].addPiece(isTurn.playerPieces.get(0));
			} else {
				grid[row + 1][col].removeAll();
				grid[row + 1][col].addPiece(isTurn.playerPieces.get(1));
			}
			row = row + 1;
		}

		state.setBoard(returner);

		return state;
	}

	public boolean checkULD(OthelloLogic state, int row, int col){
		boolean truth = false;

		while(row > 0 && col > 0 && state.getBoard()[row - 1][col - 1].equals(state.oppositeTurn(state.getTurn()))){
			row = row - 1;
			col = col - 1;
			if(row > 0 && col > 0){
				if(state.getBoard()[row - 1][col - 1].equals(state.getTurn()) && 
						state.getBoard()[row][col].equals(state.oppositeTurn(state.getTurn()))){
					truth = true;
				} else {
					truth = false;
				}
			}
		}

		return truth;
	}

	public OthelloLogic calculateULD(OthelloLogic state, int row, int col) {
		String[][] returner = state.getBoard();
		returner[row][col] = state.getTurn();
		while(state.getBoard()[row - 1][col - 1].equals(state.oppositeTurn(state.getTurn())) && 
				row > 0 && col > 0){
			returner[row - 1][col - 1] = state.getTurn();
			if(state.getTurn().equals(WHITE)){
				grid[row - 1][col - 1].removeAll();
				grid[row - 1][col - 1].addPiece(isTurn.playerPieces.get(0));
			} else {
				grid[row - 1][col - 1].removeAll();
				grid[row - 1][col - 1].addPiece(isTurn.playerPieces.get(1));
			}
			row = row - 1;
			col = col - 1;
		}

		state.setBoard(returner);

		return state;
	}

	public boolean checkDLD(OthelloLogic state, int row, int col){
		boolean truth = false;

		while(row < state.getBoard().length - 1 && col > 0 && state.getBoard()[row + 1][col - 1].equals(state.getTurn())){
			row = row + 1;
			col = col - 1;
			if(row < state.getBoard().length - 1 && col > 0){
				if(state.getBoard()[row + 1][col - 1].equals(state.getTurn()) && 
						state.getBoard()[row][col].equals(state.oppositeTurn(state.getTurn()))){
					truth = true;
				} else {
					truth = false;
				}
			}
		}

		return truth;
	}

	public OthelloLogic calculateDLD(OthelloLogic state, int row, int col) {
		String[][] returner = state.getBoard();
		returner[row][col] = state.getTurn();
		while(state.getBoard()[row + 1][col - 1].equals(state.oppositeTurn(state.getTurn())) && 
				row < state.getBoard().length - 1 && col > 0){
			returner[row + 1][col - 1] = state.getTurn();
			if(state.getTurn().equals(WHITE)){
				grid[row + 1][col - 1].removeAll();
				grid[row + 1][col - 1].addPiece(isTurn.playerPieces.get(0));
			} else {
				grid[row + 1][col - 1].removeAll();
				grid[row + 1][col - 1].addPiece(isTurn.playerPieces.get(1));
			}
			row = row + 1;
			col = col - 1;
		}

		state.setBoard(returner);

		return state;
	}

	public boolean checkURD(OthelloLogic state, int row, int col){
		boolean truth = false;

		while(row > 0 && col < (state.columns() - 1) && state.getBoard()[row - 1][col + 1].equals(state.getTurn())){
			row = row - 1;
			col = col + 1;
			if(row > 0 && col < (state.columns() - 1)){
				if(state.getBoard()[row - 1][col + 1].equals(state.getTurn()) && 
						state.getBoard()[row][col].equals(state.oppositeTurn(state.getTurn()))){
					truth = true;
				} else {
					truth = false;
				}
			}
		}

		return truth;
	}

	public OthelloLogic calculateURD(OthelloLogic state, int row, int col) {
		String[][] returner = state.getBoard();
		returner[row][col] = state.getTurn();
		while(state.getBoard()[row - 1][col + 1].equals(state.oppositeTurn(state.getTurn())) && 
				row > 0 && col < (state.columns() - 1)){
			returner[row - 1][col + 1] = state.getTurn();
			if(state.getTurn().equals(WHITE)){
				grid[row - 1][col + 1].removeAll();
				grid[row - 1][col + 1].addPiece(isTurn.playerPieces.get(0));
			} else {
				grid[row - 1][col + 1].removeAll();
				grid[row - 1][col + 1].addPiece(isTurn.playerPieces.get(1));
			}
			row = row - 1;
			col = col + 1;
		}

		state.setBoard(returner);

		return state;
	}

	public boolean checkDRD(OthelloLogic state, int row, int col){
		boolean truth = false;

		while(row < state.getBoard().length - 1 && col < (state.columns() - 1) && 
				state.getBoard()[row + 1][col + 1].equals(state.oppositeTurn(state.getTurn()))){
			row = row + 1;
			col = col + 1;
			if(row < state.getBoard().length - 1 && col < (state.columns() - 1)){
				if(state.getBoard()[row + 1][col + 1].equals(state.getTurn()) && 
						state.getBoard()[row][col].equals(state.oppositeTurn(state.getTurn()))){
					truth = true;
				} else {
					truth = false;
				}
			}
		}

		return truth;
	}

	public OthelloLogic calculateDRD(OthelloLogic state, int row, int col) {
		String[][] returner = state.getBoard();
		returner[row][col] = state.getTurn();
		while(state.getBoard()[row + 1][col + 1].equals(state.oppositeTurn(state.getTurn())) && 
				row < state.getBoard().length - 1 && col < (state.columns() - 1)){
			returner[row + 1][col + 1] = state.getTurn();
			if(state.getTurn().equals(WHITE)){
				grid[row + 1][col + 1].removeAll();
				grid[row + 1][col + 1].addPiece(isTurn.playerPieces.get(0));
			} else {
				grid[row + 1][col + 1].removeAll();
				grid[row + 1][col + 1].addPiece(isTurn.playerPieces.get(1));
			}
			row = row + 1;
			col = col + 1;
		}

		state.setBoard(returner);

		return state;
	}

	public OthelloLogic calculateAll(OthelloLogic state, int row, int col){
		int noneDone = 0;
		if(!state.getBoard()[row][col].equals(NONE)){
			return state;
		}

		if(checkLH(state, row, col)){
			state = calculateLH(state, row, col);
		} else {
			noneDone += 1;
		}

		if(checkRH(state, row, col)){
			state = calculateRH(state, row, col);
		} else {
			noneDone += 1;
		}

		if(checkUV(state, row, col)){
			state = calculateUV(state, row, col);
		} else {
			noneDone += 1;
		}

		if(checkDV(state, row, col)){
			state = calculateDV(state, row, col);
		} else {
			noneDone += 1;
		}

		if(checkULD(state, row, col)){
			state = calculateULD(state, row, col);
		} else {
			noneDone += 1;
		}

		if(checkDLD(state, row, col)){
			state = calculateDLD(state, row, col);
		} else {
			noneDone += 1;
		}

		if(checkURD(state, row, col)){
			state = calculateURD(state, row, col);
		} else {
			noneDone += 1;
		}

		if(checkDRD(state, row, col)){
			state = calculateDRD(state, row, col);
		} else {
			noneDone += 1;
		}

		if(noneDone == 8){
			return state;
		}
		
		moved = true;
		return new OthelloLogic(state.getBoard(), state.oppositeTurn(state.getTurn()));
	}

	public boolean validMoveAvailable(OthelloLogic state){
		for(int row = 0; row < state.getBoard().length; row++){
			for(int col = 0; col < state.getBoard()[row].length; col++){
				if(state.getBoard()[row][col].equals(NONE)){
					if(checkLH(state, row, col) ||
							checkRH(state, row, col) ||
							checkUV(state, row, col) ||
							checkDV(state, row, col) ||
							checkULD(state, row, col) ||
							checkDLD(state, row, col) ||
							checkURD(state, row, col) ||
							checkDRD(state, row, col)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public OthelloLogic switchTurnNoMovesAvailable(OthelloLogic state){
		if(validMoveAvailable(state)){
			return state;
		} else {
			return new OthelloLogic(state.getBoard(), state.oppositeTurn(state.getTurn()));
		}
	}

	public String winner(OthelloLogic state){
		String winner = "";
		int colorB = 0;
		int colorW = 0;
		if(validMoveAvailable(state) || 
				validMoveAvailable(new OthelloLogic(state.getBoard(), state.oppositeTurn(state.getTurn())))){
			winner = " ";
		} else {
			String[][] checker = state.getBoard();
			for(int row = 0; row < state.getBoard().length; row++){
				for(int col = 0; col < state.getBoard()[row].length; col++){
					if(!checker[row][col].equals(NONE)){
						if(checker[row][col].equals(state.BLACK())){
							colorB += 1;
						}
						if(checker[row][col].equals(state.WHITE())){
							colorW += 1;
						}
					}
				}
			}

			if(colorB > colorW){
				winner = state.BLACK();
			} else if(colorW > colorB){
				winner = state.WHITE();
			} else {
				winner = "T";
			}

		}
		return winner;
	}

	public void printBoard(){
		String returner = "  ";

		for(int col = 0; col < this.columns(); col++){
			returner += String.format("%1$3s", Integer.toString(col+1));
		}
		returner += "\n";

		for(int row = 0; row < this.getBoard().length; row++){
			returner += String.format("%1$-2s", Integer.toString(row + 1));
			for(int col = 0; col < this.getBoard()[row].length; col++){
				if(!this.getBoard()[row][col].equals(NONE)){
					returner += "  " + this.getBoard()[row][col];
				} else {
					returner += "  " + ".";
				}
			}
			returner += "\n";
		}
		returner = returner.substring(0, returner.length() - 1);
		System.out.println(returner);
	}

	public void printScore(){
		int colorB = 0;
		int colorW = 0;
		for(int row = 0; row < this.getBoard().length; row++){
			for(int col = 0; col < this.getBoard()[row].length; col++){
				if(!this.getBoard()[row][col].equals(NONE)){
					if(this.getBoard()[row][col].equals(BLACK)){
						colorB += 1;
					} else {
						colorW += 1;
					}
				}
			}
		}
		System.out.println(this.BLACK() + ": " + Integer.toString(colorB) + "\n" +
				this.WHITE() + ": " + Integer.toString(colorW) + "\n");
	}

	public void printGame(){
		printBoard();
		printScore();
		System.out.println("Turn: " + getTurn() + "\n");
	}

	public static void testGame(){
		//EVERYTHING HERE WILL AUTO TEST THE LOGIC OF THE GAME
		String winner = NONE;
		int rows = 4;
		int columns = 4;
		String color = "W";
		String board_setup = "W";
		String who_is_winner = "M";
		int rand_row = 0;
		int rand_column = 0;
		Random random = new Random();
		String currentTurn = color;

		OthelloLogic OTHELLO = new OthelloLogic(OthelloLogic.newGameBoard(rows, columns, board_setup), color);

		System.out.println("\n");

		while(winner.equals(NONE)){
			OTHELLO.printGame();
			who_is_winner = "M";
			currentTurn = OTHELLO.getTurn();

			do{
				rand_row = random.nextInt(rows) + 1;
				rand_column = random.nextInt(columns) + 1;
				//System.out.println("Row: " + Integer.toString(rand_row) + "\nColumn: " + Integer.toString(rand_column) + "\n");
			}while(OTHELLO.calculateAll(OTHELLO, rand_row - 1, rand_column - 1).getTurn().equals(currentTurn));
			OTHELLO = OTHELLO.calculateAll(OTHELLO, rand_row - 1, rand_column - 1);
			OTHELLO = new OthelloLogic(OTHELLO.getBoard(), OTHELLO.oppositeTurn(OTHELLO.getTurn()));
			winner = OTHELLO.winner(OTHELLO);
			OTHELLO = OTHELLO.switchTurnNoMovesAvailable(OTHELLO);
		}
		OTHELLO.printGame();
		if(who_is_winner == "F"){
			System.out.println("Winner is " + winner);
		} else {
			System.out.println("Winner is " + winner);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Tile clicked = (Tile) e.getComponent();
		calculateAll(this, clicked.getRow(), clicked.getColumn());
		if(moved){
			if(this.getTurn().equals(WHITE)){
				clicked.addPiece(isTurn.playerPieces.get(0));
			} else {
				clicked.addPiece(isTurn.playerPieces.get(1));
			}
			grid[clicked.getRow()][clicked.getColumn()].setOwner(isTurn);
			this.setTurn(oppositeTurn(this.getTurn()));
			moved = false;
		}
		
		this.game.board.updateScreen();
		//this.printGame();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}


	//	public static void main(String[] args){
	//		OthelloLogic.testGame();
	//	}

}
