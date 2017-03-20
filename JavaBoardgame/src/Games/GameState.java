package Games;
import java.io.Serializable;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/16/2017
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    Tile[][] boardState;
    Player isTurn;
    boolean gameOver;

    GameState(Tile[][] initState, Player isTurn) {
        boardState = initState;
        this.isTurn = isTurn;
        gameOver = false;
    }

    GameState(GameState stateIn)    {
        boardState = stateIn.boardState;
        isTurn = stateIn.isTurn;
        gameOver = stateIn.gameOver;
    }

    Tile[][] getState()    {
        return boardState;
    }

    void setState(Tile[][] newState)    {
        boardState = newState;
    }

    Player getTurn()    {
        return isTurn;
    }

    void setTurn(Player nextPlayer) {
        isTurn = nextPlayer;
    }
}
