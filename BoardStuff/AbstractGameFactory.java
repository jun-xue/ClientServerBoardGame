import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public interface AbstractGameFactory {
    GameBoard createGameBoard();
    ArrayList<ImageIcon> loadImages();
    void setUpBoard(GameBoard board, ArrayList<ImageIcon> pieces);
}
