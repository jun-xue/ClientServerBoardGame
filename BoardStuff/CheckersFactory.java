import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public class CheckersFactory implements AbstractGameFactory{

    String gameTitle;
    int boardWidth, boardHeight;
    int rows = 8, cols = 8;
    Color primary, alternate;

    CheckersFactory()   {
        gameTitle = "Checkers";
        boardWidth = 600;
        boardHeight = 600;
        rows = 8;
        cols = 8;
        primary = new Color(240,220,130);
        alternate = new Color(76,105,67);
    }

    @Override
    public String getGameTitle() {
        return gameTitle;
    }

    @Override
    public GameBoard createGameBoard() {
        return new GameBoard(boardWidth, boardHeight, rows, cols, primary, alternate, gameTitle);
    }

    @Override
    public void loadImages(Player goesFirst, Player follow) {
        //read in and scale images.
        ImageIcon redChecker = new ImageIcon(new ImageIcon("images/redChecker.png")
                .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        goesFirst.playerPieces.add(redChecker);
        ImageIcon redKing = new ImageIcon(new ImageIcon("images/redKing.png")
                .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        goesFirst.playerPieces.add(redKing);
        ImageIcon whiteChecker = new ImageIcon(new ImageIcon("images/whiteChecker.png")
                .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        follow.playerPieces.add(whiteChecker);
        ImageIcon whiteKing = new ImageIcon(new ImageIcon("images/whiteKing.png")
                .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        follow.playerPieces.add(whiteKing);
    }

    @Override
    public void setInitOwnership(GameBoard board, Player client, Player opponent) {
        for (Tile[] tt : board.boardMatrix) {
            for (Tile t : tt)   {
                if ((t.getRow() == 0 || t.getRow() == 2) && (t.getColumn() % 2 != 0)) {
                    t.setOwner(opponent);
                    opponent.addTile(t);
                    t.addPiece(opponent.playerPieces.get(0));
                }
                if (t.getRow() == 1 && (t.getColumn() % 2 == 0)) {
                    t.setOwner(opponent);
                    opponent.addTile(t);
                    t.addPiece(opponent.playerPieces.get(0));
                }
            }
        }
        for (Tile[] tt : board.boardMatrix) {
            for (Tile t : tt)   {
                if ((t.getRow() == 5 || t.getRow() == 7) && (t.getColumn() % 2 == 0)) {
                    t.setOwner(client);
                    client.addTile(t);
                    t.addPiece(client.playerPieces.get(0));
                }
                if (t.getRow() == 6 && (t.getColumn() % 2 != 0)) {
                    t.setOwner(client);
                    client.addTile(t);
                    t.addPiece(client.playerPieces.get(0));
                }
            }
        }
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(boardWidth, boardHeight);
    }
}
