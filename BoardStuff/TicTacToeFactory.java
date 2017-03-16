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
                new Color(30,140,135), new Color(30,140,135), "Tic Tac Toe");
        tttBoard.addTicTacToeBorders();
        return tttBoard;
    }

    @Override
    public ArrayList<ImageIcon> loadImages() {

//        ImageIcon lightX = new ImageIcon("C:\\Users\\ACKD151\\IdeaProjects\\GitHub\\ClientServerBoardGame\\BoardStuff\\Images\\LightX.png");
//        ImageIcon lightO = new ImageIcon("C:\\Users\\ACKD151\\IdeaProjects\\GitHub\\ClientServerBoardGame\\BoardStuff\\Images\\LightO.png");
        ImageIcon darkO = new ImageIcon("C:\\Users\\ACKD151\\IdeaProjects\\GitHub\\ClientServerBoardGame\\BoardStuff\\Images\\DarkO.png");
        ImageIcon darkX = new ImageIcon("C:\\Users\\ACKD151\\IdeaProjects\\GitHub\\ClientServerBoardGame\\BoardStuff\\Images\\DarkX.png");

        ArrayList<ImageIcon> images = new ArrayList<>(4);
//        images.add(lightX);
//        images.add(lightO);
        images.add(darkX);
        images.add(darkO);

        return images;
    }

    @Override
    public void setUpBoard(GameBoard board, ArrayList<ImageIcon> pieces) {

    }

}
