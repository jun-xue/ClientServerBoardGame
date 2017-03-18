import com.sun.jmx.remote.internal.ArrayQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
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

    Game(AbstractGameFactory factory) {
        super(factory.getGameTitle());    //super is JFrame
        agf = factory;
        createPlayers();    //creates a client and a component
        isTurn = client;
        agf.loadImages(client, opponent);
        board = agf.createGameBoard();
        agf.setInitOwnership(board, client, opponent);

        playerQueue = new LinkedList<>();
        playerQueue.add(isTurn);
        playerQueue.add(opponent);

        setSize(agf.getDimension());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initializeGameState();
        add(board);
        pack();
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

    protected void runGame()    {
        Player isUp;
        while (!state.gameOver) {
            isUp = playerQueue.remove();
            isUp.getAvailableMoves();
            isUp.makeLegalMove();
            playerQueue.add(isUp);
            //gameState.update();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
