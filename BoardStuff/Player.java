import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/9/2017
 */
class Player {
    boolean isTurn;
    ArrayList<ImageIcon> playerPieces;
    ArrayList<Tile> myTiles;
    String name;
    GameBoard board;

    Player(String name, GameBoard gameBoard) {
        board = gameBoard;
        playerPieces = new ArrayList<>(2);
        this.name = name;
        myTiles = new ArrayList<>(12);
    }

    public String getName() {
        return name;
    }

    ArrayList<Tile> getAvailableMoves()    {
        //Flip for different sides of board (make red/black player side???
        ArrayList<Tile> movable = new ArrayList<>();
        for (Tile check : myTiles)  {
            int checksRow = check.getRow(), checksColumn = check.getColumn();
            if (checksColumn != 0 && board.boardMatrix[checksRow + 1][checksColumn -1].getOwner() == null ||
                    checksColumn != 7 && board.boardMatrix[checksRow + 1][checksColumn +1].getOwner() == null)   {
                movable.add(check);
            }
        }
        return movable;
    }

    void makeLegalMove()    {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public void addTile(Tile mine)  {
        myTiles.add(mine);
    }
}
