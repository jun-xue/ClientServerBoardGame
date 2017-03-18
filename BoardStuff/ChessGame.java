import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/11/2017
 */
public class ChessGame extends Game {

    public ChessGame()   {
        super(new ChessFactory());
    }

    @Override
    protected void runGame() {

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

    @Override
    protected ArrayList<Tile> availableMoves(Player isUp) {
        return null;
    }
}
