import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.*;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public abstract class Game extends JFrame implements MouseListener  {
    AbstractGameFactory agf;
    GameBoard board;
    ArrayList<ImageIcon> pieces;
    Player isTurn;                  //likely turn into Client when incorporating C/S model
    Player client;
    Player opponent;
    Queue<Player> playerQueue;
    GameState state;

    Game(String gameTitle, AbstractGameFactory factory) {
        super(gameTitle);    //super is JFrame
        agf = factory;
        createPlayers();    //creates a client and a component
        isTurn = client;
        agf.loadImages(client, opponent);
        board = agf.createGameBoard();
        agf.setInitOwnership(board, client, opponent);

        playerQueue = new LinkedList<>();
        playerQueue.add(isTurn);
        playerQueue.add(opponent);

        Dimension desiredFrameSize = agf.getDimension();
        setMinimumSize(desiredFrameSize);
        setMaximumSize(desiredFrameSize);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initializeGameState();
        add(board);
       // pack();
        setVisible(true);
    }

    void createPlayers()   {
        client      = new Player("Self", board);
        opponent    = new Player("Enemy", board);
    }

    void initializeGameState()  {
        state = new GameState(board.boardMatrix, isTurn);
    }

    protected abstract ArrayList<Tile> availableMoves(Player isUp);

    /**
     * This is stub right now to run the games locally
     * These classe can access State because they share the same reference
     * Once we get the interface for server actions we can remove this and just have
     * a method that does something like:
     *    public GameStateGoingBack advanceGame(GameStateReceivedFromServer)    {
     *      this.takeTurn();
     *      return newGameState;....
     *    }
     */
    protected void runGame()    {
        Player isUp;
        while (!state.gameOver) {
            isUp = playerQueue.remove();
            agf.hiLiteAvailableMoves(isUp.getAvailableMoves());
            isUp.makeLegalMove();
            playerQueue.add(isUp);
            //gameState.update();
        }
    }
}
