package Games;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;



public class OthelloGame extends Game{

	OthelloLogic OTHELLO;

	OthelloGame(AbstractGameFactory factory) {
		super("Othello", factory);
		OTHELLO = new OthelloLogic(this, state, OthelloLogic.newGameBoard(8, 8, "W"), "W");
		this.board.updateScreen();
	}

	@Override
	void switchTurn()   {
		super.switchTurn();
		OTHELLO.takeTurn(isTurn, state);
		OTHELLO.setTurn(OTHELLO.oppositeTurn(OTHELLO.getTurn()));
	}

	@Override
	public void runGame() {
		if (!state.gameOver) {
			GameState returnedState = OTHELLO.takeTurn(isTurn, state);
		}
	}

	@Override
	public GameState doMove() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void doMove(){
//
//	}

}
