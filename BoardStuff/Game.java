import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public abstract class Game extends JFrame implements MouseListener  {
    AbstractGameFactory agf;
    GameBoard board;
    ArrayList<ImageIcon> pieces;
    Player isTurn;
    ArrayList<Player> players;

    Game(AbstractGameFactory factory) {
        agf = factory;
        board = agf.createGameBoard();
        pieces = agf.loadImages();
        agf.setUpBoard(board, pieces);
        for (Tile[] tt : board.boardMatrix) {
            for (Tile t : tt)   {
                t.addMouseListener(this);
            }
        }
//        board.tileTrial();
    }

    protected abstract void run();
}
