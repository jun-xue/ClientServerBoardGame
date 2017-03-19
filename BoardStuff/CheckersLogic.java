import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/18/2017
 */
public class CheckersLogic implements MouseListener {

    CheckersGame checkers;
    GameState state;
    Player isTurn;
    Tile[][] grid;
    boolean pieceSelected = false;
    boolean moved = false;
    boolean jump = false;
    Tile from;
    ArrayList<Tile> canMove;
    ArrayList<Tile> legalMoves;

    public CheckersLogic(CheckersGame checkers, GameState state)    {
        this.checkers = checkers;
        this.state = state;
        grid = checkers.board.boardMatrix;
        canMove = new ArrayList<>();
        legalMoves = new ArrayList<>();
    }

    GameState takeTurn(Player isTurn,GameState state)   {
        this.isTurn = isTurn;
        this.state = new GameState(state);
        moved = false;
        jump = getJumps();
        if (!jump)  {
            getMovables();
        }
        hiLiteMoveOptions();
        //turn continued in mouselistener handler
        state.gameOver = checkWinner();
        return state;
    }

    boolean getJumps()    {
        canMove.clear();
        Player opponent = checkers.playerQueue.peek();
        for (Tile t : isTurn.myTiles) {
            int row = t.getRow();
            int col = t.getColumn();
            if (isTurn.starts) {
                if ((col == 0 && grid[row - 1][col + 1].getOwner() == opponent && grid[row - 2][col + 2].free) ||
                        (col == 7 && grid[row - 1][col - 1].getOwner() == opponent && grid[row - 2][col - 2].free) ||
                        (col > 0 && col < 7 && grid[row - 1][col + 1].getOwner() == opponent && grid[row - 2][col + 2].free) ||
                        (col > 0 && col < 7 && grid[row - 1][col - 1].getOwner() == opponent && grid[row - 2][col - 2].free)) {
                    canMove.add(t);
                    t.addMouseListener(this);
                    return true;
                }
            }

        }
        return false;
    }

    void getMovables()   {
        canMove.clear();
        if (isTurn.starts) {    //starts means player started at bottom so move up
            for (Tile t : isTurn.myTiles) {
                int row = t.getRow(), col = t.getColumn();
                if (col != 0 && grid[row-1][col-1].free ||
                        col != 7 && grid[row-1][col+1].free) {
                    t.addMouseListener(this);
                    canMove.add(t);
                }
            }
        }
        if (!isTurn.starts) {
            for (Tile t : isTurn.myTiles) {
                int row = t.getRow(), col = t.getColumn();
                if (col != 0 && grid[row+1][col-1].free ||
                        col != 7 && grid[row+1][col+1].free) {
                    t.addMouseListener(this);
                    canMove.add(t);
                }
            }
        }
    }

    void getLegalMoves(Tile from)    {
        legalMoves.clear();
        int row = from.getRow(), col = from.getColumn();
        if (isTurn.starts)    {
            if (col == 0 && grid[row-1][col+1].free)   {
                legalMoves.add(grid[row-1][col+1]);
            }   else if (col == 7 && grid[row-1][col-1].free)   {
                legalMoves.add(grid[row-1][col-1]);
            }   else {
                if (grid[row-1][col-1].free) {
                    legalMoves.add(grid[row-1][col-1]);
                }
                if (grid[row-1][col+1].free) {
                    legalMoves.add(grid[row-1][col+1]);
                }
            }
        }
        if (!isTurn.starts)    {
            if (col == 0 && grid[row+1][col+1].free)   {
                legalMoves.add(grid[row+1][col+1]);
            }   else if (col == 7 && grid[row+1][col-1].free)   {
                legalMoves.add(grid[row+1][col-1]);
            }   else {
                if (grid[row+1][col-1].free) {
                    legalMoves.add(grid[row+1][col-1]);
                }
                if (grid[row+1][col+1].free) {
                    legalMoves.add(grid[row+1][col+1]);
                }
            }
        }
        for (Tile t : legalMoves)   {
            t.addMouseListener(this);
        }
    }

    void hiLiteMoveOptions() {
        for (Tile t : canMove) {
            t.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        }
    }

    void hiLiteLegalDestinations(Tile from)  {
        from.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        for (Tile t : legalMoves) {
            t.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        }
    }

    boolean checkWinner()  {
        if (checkers.client.myTiles.size()==0 || checkers.opponent.myTiles.size()==0)   {
            return true;
        }
        return false;
    }

    void doMove(Tile from, Tile to)  {
        JLabel piece = (JLabel)from.getComponent(0);
        from.remove(piece);
        isTurn.removeTile(from);
        to.add(piece);
        isTurn.addTile(to);
        checkers.board.updateScreen();
        canMove.clear();
        legalMoves.clear();
        pieceSelected = false;
        moved = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile clicked = (Tile)e.getComponent();
        if (!moved) {
            if (pieceSelected == true && legalMoves.contains(clicked)) {
                doMove(from, clicked);
                checkers.switchTurn();
                checkers.runGame();
                return;
            }
            if (clicked.getOwner() == isTurn && pieceSelected == false) {
                checkers.board.clearHiLites();
                from = clicked;
                pieceSelected = true;
                getLegalMoves(from);
                hiLiteLegalDestinations(from);
                return;
            }
        }
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
}
