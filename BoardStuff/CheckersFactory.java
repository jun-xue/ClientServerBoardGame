import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public class CheckersFactory implements AbstractGameFactory{

    @Override
    public GameBoard createGameBoard() {
        return new GameBoard(600, 600, 8, 8,
                new Color(240,220,130), new Color(76,105,67), "Checkers");
    }

    @Override
    public ArrayList<ImageIcon> loadImages() {
        //read in and scale images.
        ImageIcon redChecker = new ImageIcon(new ImageIcon(
                "C:\\Users\\ACKD151\\workspace\\122_Final_Project\\src\\Images\\redChecker.png")
                .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        ImageIcon whiteChecker = new ImageIcon(new ImageIcon(
                "C:\\Users\\ACKD151\\workspace\\122_Final_Project\\src\\Images\\whiteChecker.png")
                .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        ArrayList<ImageIcon> images = new ArrayList<>(4);
        images.add(redChecker);
        images.add(whiteChecker);
//        images.add(redKing);
//        images.add(whiteKing);
        return images;
    }

    @Override
    public void setUpBoard(GameBoard board, ArrayList<ImageIcon> pieces) {
        for (Tile[] tt : board.boardMatrix) {
            for (Tile t : tt)   {
                if ((t.getRow() == 0 || t.getRow() == 2) && (t.getColumn() % 2 != 0)) {
                    t.addPiece(pieces.get(0));
                }
                if (t.getRow() == 1 && (t.getColumn() % 2 == 0)) {
                    t.addPiece(pieces.get(0));
                }
            }
        }
        for (Tile[] tt : board.boardMatrix) {
            for (Tile t : tt)   {
                if ((t.getRow() == 5 || t.getRow() == 7) && (t.getColumn() % 2 == 0)) {
                    t.addPiece(pieces.get(1));
                }
                if (t.getRow() == 6 && (t.getColumn() % 2 != 0)) {
                    t.addPiece(pieces.get(1));
                }
            }
        }
    }
}
