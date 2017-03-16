import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/11/2017
 */
public class ChessFactory implements AbstractGameFactory {

    @Override
    public GameBoard createGameBoard() {
        return new GameBoard(600, 600, 8, 8,
                new Color(182,155,76), new Color(43,30,20), "Chess");
    }

    @Override
    public ArrayList<ImageIcon> loadImages() {
        return null;
    }

    @Override
    public void setUpBoard(GameBoard board, ArrayList<ImageIcon> pieces) {

    }
}
