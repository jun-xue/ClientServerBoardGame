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

    void switchTurn()   {
        playerQueue.add(isTurn);
        isTurn = playerQueue.remove();
    }

    @Override
    public void runGame() {
        if (!state.gameOver) {
            GameState returnedState = logic.takeTurn(isTurn, state);
        }
    }
}
