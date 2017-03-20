package Games;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import Server.ServerObject;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/5/17
 */
public class TicTacToeGame extends Game{

//    String currentPlayer;
//    String Player1="Player1";
//    String Player2="Player2";
//    Boolean redoMove=false;
	
	TicTacToeLogic gameLogic;

    public TicTacToeGame(AbstractGameFactory tttgf)   {
        super("TicTacToe", tttgf);
        gameLogic= new TicTacToeLogic(this, state);

    }
    
    public void switchTurn(){
    	super.switchTurn();
        gameLogic.takeTurn(isTurn, state);
    }

  

    protected void run(Tile clicked) {
//        ImageIcon piece;
//        currentPlayer=flipTurn(currentPlayer);
//        if (currentPlayer==Player1){piece=client.playerPieces.get(0);}
//        else{piece=opponent.playerPieces.get(1);}
//        if (canMakeMove(clicked.getRow(),clicked.getColumn(), currentPlayer)){
//            board.boardMatrix[clicked.getRow()][clicked.getColumn()].setOwned(currentPlayer);
//            clicked.addPiece(piece);
//            System.out.println("Move made by "+ currentPlayer);
//            redoMove=false;
//            if (checkWinner()){
//                System.out.println("Winner is "+ currentPlayer);
//                System.exit(0);
//            }
//            if (boardFilled()){
//                System.out.println("Board Full! It's a tie");
//                System.exit(0);
//            }
//        }else{
//            System.out.println("Can't make move");
//            redoMove=true;
//        }
    }


    @Override
    public void runGame() {
    	if (!state.gameOver) {
            GameState returnedState = gameLogic.takeTurn(isTurn, state);
        }
    }

//	@Override
//	public void serverMakeMove(ServerObject s) {
//		//currentPlayer=s.getSender();
//		
//	}

	@Override
	public GameState doMove() {
		// TODO Auto-generated method stub
		return null;
	}
}