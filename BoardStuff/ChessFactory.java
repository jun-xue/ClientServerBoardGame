import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/11/2017
 */
public class ChessFactory implements AbstractGameFactory {

    @Override
    public String getGameTitle() {
        return null;
    }

    @Override
    public Dimension getDimension() {
        return null;
    }

    @Override
    public GameBoard createGameBoard() {
        return new GameBoard(600, 600, 8, 8,
                new Color(182,155,76), new Color(43,30,20), "Chess");
    }

    @Override
    public void loadImages(Player goesFirst, Player two) {

    }

    @Override
    public void setInitOwnership(GameBoard board, Player client, Player opponent) {

    }
}
