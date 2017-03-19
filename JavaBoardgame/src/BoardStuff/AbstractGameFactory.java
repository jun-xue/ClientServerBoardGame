package BoardStuff;
import java.awt.*;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public interface AbstractGameFactory {
    String getGameTitle();
    Dimension getDimension();
    GameBoard createGameBoard();
    void loadImages(Player goesFirst, Player two);
    void setInitOwnership(GameBoard board, Player client, Player opponent);
}
