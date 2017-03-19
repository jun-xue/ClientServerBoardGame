import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public class TicTacToeFactory implements AbstractGameFactory{

    String gameTitle;
    int boardWidth, boardHeight;
    int rows = 8, cols = 8;
    Color primary, alternate;

    TicTacToeFactory()  {
        gameTitle = "Tic Tac Toe";
        boardWidth = 450;
        boardHeight = 450;
        rows = 3;
        cols = 3;
        primary = new Color(35,180,170);
        alternate = new Color(30,140,135);
    }

    @Override
    public Game createGame(AbstractGameFactory abf) {
        return new TicTacToeGame(abf);
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(boardWidth, boardHeight);
    }

    @Override
    public GameBoard createGameBoard() {
        GameBoard tttBoard = new GameBoard(boardWidth, boardHeight, rows, cols, primary, alternate, gameTitle);
        return tttBoard;
    }

    @Override
    public void loadImages(Player goesFirst, Player two) {
        goesFirst.playerPieces.add(new ImageIcon(new ImageIcon("Images/LightX.png")
                .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
//        goesFirst.playerPieces.add(new ImageIcon(new ImageIcon("Images/DarkX.png")
//                .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        two.playerPieces.add(new ImageIcon(new ImageIcon("Images/LightO.png")
                .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
//        two.playerPieces.add(new ImageIcon(new ImageIcon("Images/darkO.png")
//                .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));

    }

    @Override
    public void setInitOwnership(GameBoard board, Player client, Player opponent) {}


}
