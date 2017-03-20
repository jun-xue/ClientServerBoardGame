package Games;
import javax.swing.*;
import java.awt.*;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/10/2017
 */
public class CheckersFactory implements AbstractGameFactory{

    String gameTitle;
    int boardWidth, boardHeight;
    int rows = 8, cols = 8;
    Color primary, alternate;

    public CheckersFactory()   {
        gameTitle = "Checkers";
        boardWidth = 600;
        boardHeight = 600;
        rows = 8;
        cols = 8;
        primary = new Color(240,220,130);
        alternate = new Color(76,105,67);
    }

    @Override
    public Game createGame(AbstractGameFactory abf) {
        return new CheckersGame(abf);
    }

    @Override
    public GameBoard createGameBoard() {
        return new GameBoard(boardWidth, boardHeight, rows, cols, primary, alternate, gameTitle);
    }

    @Override
    public void loadImages(Player goesFirst, Player follow) {
        //read in and scale images.
        ImageIcon redChecker = new ImageIcon(new ImageIcon("src/Assets/redChecker.png")
                .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        goesFirst.playerPieces.add(redChecker);
        ImageIcon redKing = new ImageIcon(new ImageIcon("src/Assets/redKing.png")
                .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        goesFirst.playerPieces.add(redKing);
        ImageIcon whiteChecker = new ImageIcon(new ImageIcon("src/Assets/whiteChecker.png")
                .getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        follow.playerPieces.add(whiteChecker);
        ImageIcon whiteKing = new ImageIcon(new ImageIcon("src/Assets/whiteKing.png")
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
                    t.addPiece(opponent.playerPieces.get(0), false, false);
                }
                if (t.getRow() == 1 && (t.getColumn() % 2 == 0)) {
                    t.setOwner(opponent);
                    opponent.addTile(t);
                    t.addPiece(opponent.playerPieces.get(0), false, false);
                }
            }
        }
        for (Tile[] tt : board.boardMatrix) {
            for (Tile t : tt)   {
                if ((t.getRow() == 5 || t.getRow() == 7) && (t.getColumn() % 2 == 0)) {
                    t.setOwner(client);
                    client.addTile(t);
                    t.addPiece(client.playerPieces.get(0), true, false);
                }
                if (t.getRow() == 6 && (t.getColumn() % 2 != 0)) {
                    t.setOwner(client);
                    client.addTile(t);
                    t.addPiece(client.playerPieces.get(0), true, false);
                }
            }
        }
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(boardWidth, boardHeight);
    }
}
