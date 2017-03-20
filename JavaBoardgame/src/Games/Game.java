package Games;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public abstract class Game extends JPanel {
    AbstractGameFactory agf;
    GameBoard board;
    ArrayList<ImageIcon> pieces;
    Player isTurn;
    Player client;
    Player opponent;
    Queue<Player> playerQueue;  //only used locally
    GameState state;

    Game(String gameTitle, AbstractGameFactory factory) {
        //super(gameTitle);    //super is JFrame
        agf = factory;
        board = agf.createGameBoard();
        createPlayers();    //creates a client and a component
        isTurn = client;
        agf.loadImages(client, opponent);
        agf.setInitOwnership(board, client, opponent);

        playerQueue = new LinkedList<>();
        playerQueue.add(opponent);

        Dimension desiredFrameSize = agf.getDimension();
        setMinimumSize(desiredFrameSize);
        setMaximumSize(desiredFrameSize);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initializeGameState();
        add(board);
        setVisible(true);
    }

    void createPlayers()   {
        client      = new Player("Self");
        client.starts = true;
        opponent    = new Player("Enemy");
    }

    void initializeGameState()  {
        state = new GameState(board.boardMatrix, isTurn);
    }

    public abstract void runGame();
}
