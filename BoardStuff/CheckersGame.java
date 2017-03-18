import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public class CheckersGame extends Game  {

    CheckersGame()   {
        super(new CheckersFactory());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile clicked = (Tile)e.getComponent();
        clicked.addPiece(pieces.get(1));
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

    @Override
    protected void runGame() {
        while (!state.gameOver)  {
            for (Tile[] tt : board.boardMatrix)    {
                for (Tile t : tt)   {
                    t.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        }
    }
}
