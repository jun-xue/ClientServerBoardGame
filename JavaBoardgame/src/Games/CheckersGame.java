package Games;
/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public class CheckersGame extends Game  {

    CheckersLogic logic;

    CheckersGame(AbstractGameFactory cgf)   {
        super("Checkers", cgf);
        logic = new CheckersLogic(this, state);
    }

    @Override
    void switchTurn()   {
        super.switchTurn();
        logic.takeTurn(isTurn, state);
    }

    @Override
    public void runGame() {
        if (!state.gameOver) {
            GameState returnedState = logic.takeTurn(isTurn, state);
        }
    }

	@Override
	public GameState doMove() {
		// TODO Auto-generated method stub
		return null;
	}
}
