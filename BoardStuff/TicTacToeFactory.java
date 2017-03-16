import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public class TicTacToeFactory implements AbstractGameFactory{

    @Override
    public GameBoard createGameBoard() {
        GameBoard tttBoard = new GameBoard(450, 450, 3, 3,
                new Color(35,180,170), new Color(30,140,135), "Tic Tac Toe");
        tttBoard.addTicTacToeBorders();
        return tttBoard;
    }

    @Override
    public ArrayList<ImageIcon> loadImages() {

        ImageIcon lightX = new ImageIcon(//new ImageIcon(
                "Images\\LightX.png");
               // .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        ImageIcon darkX = new ImageIcon(//new ImageIcon(
                "Images\\DarkX.png");
        // .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        ImageIcon lightO = new ImageIcon(//new ImageIcon(
                "Images\\LightO.png");
        // .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        ImageIcon darkO = new ImageIcon(//new ImageIcon(
                "Images\\darkO.png");
        // .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        ArrayList<ImageIcon> images = new ArrayList<>(4);
        images.add(lightX);
        images.add(darkX);
        images.add(lightO);
        images.add(darkO);

        return images;
    }

    @Override
    public void setUpBoard(GameBoard board, ArrayList<ImageIcon> pieces) {

    }

}
