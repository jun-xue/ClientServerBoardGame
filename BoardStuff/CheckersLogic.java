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
        this.isTurn = checkers.isTurn;
        grid = checkers.board.boardMatrix;
        canMove = new ArrayList<>();
        legalMoves = new ArrayList<>();
        for(Tile[] tt : grid)   {
            for(Tile t : tt)    {
                t.addMouseListener(this);
            }
        }
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

    boolean getJumps() {
        canMove.clear();
        for (Tile t : isTurn.myTiles) {
            if (canJump(t)) {
                canMove.add(t);
            }
        }
        return !canMove.isEmpty();
    }

    boolean canJump(Tile t)    {
        Player opponent = checkers.playerQueue.peek();
        int row = t.getRow();
        int col = t.getColumn();
        if (isTurn.starts && row>1) {
            if ((col==0 && grid[row-1][1].getOwner()==opponent && grid[row-2][2].free) ||
                    (col==1 && grid[row-1][2].getOwner()==opponent && grid[row-2][3].free)){
                return true;
            }
            if ((col==7 && grid[row-1][6].getOwner()==opponent && grid[row-2][5].free) ||
                    (col==6 && grid[row-1][5].getOwner()==opponent && grid[row-2][4].free)) {
                return true;
            }
            if ((col>1 && col<6 && grid[row-1][col+1].getOwner()==opponent && grid[row-2][col+2].free) ||
                    (col>1 && col<6 && grid[row-1][col-1].getOwner()==opponent && grid[row-2][col-2].free)) {
                return true;
            }
        }
        if (!isTurn.starts && row<6) {
            if ((col==0 && grid[row+1][1].getOwner()==opponent && grid[row+2][2].free) ||
                    (col==1 && grid[row+1][2].getOwner()==opponent && grid[row+2][3].free)){
                return true;
            }
            if ((col==7 && grid[row+1][6].getOwner()==opponent && grid[row+2][5].free) ||
                    (col==6 && grid[row+1][5].getOwner()==opponent && grid[row+2][4].free)) {
                return true;
            }
            if ((col>1 && col<6 && grid[row+1][col+1].getOwner()==opponent && grid[row+2][col+2].free) ||
                    (col>1 && col<6 && grid[row+1][col-1].getOwner()==opponent && grid[row+2][col-2].free)) {
                return true;
            }
            return false;
        }
        return false;
    }

    void getMovables()   {
        canMove.clear();
        for (Tile t : isTurn.myTiles) {
            int row = t.getRow(), col = t.getColumn();
            if (isTurn.starts && row>0) {    //starts means player started at bottom so move up
                if (col != 0 && grid[row-1][col-1].free ||
                        col != 7 && grid[row-1][col+1].free) {
                    canMove.add(t);
                }
            }

            if (!isTurn.starts && row<7) {
                if (col != 0 && grid[row+1][col-1].free ||
                        col != 7 && grid[row+1][col+1].free) {
                    canMove.add(t);
                }
            }
        }
    }

    void getLegalMoves(Tile from)    {
        legalMoves.clear();
        Player opponent = checkers.playerQueue.peek();
        int row = from.getRow(), col = from.getColumn();
        if (isTurn.starts)    {
            if (jump)   {
                if (col==0 && grid[row-1][1].getOwner()==opponent && grid[row-2][2].free)  {
                    legalMoves.add(grid[row-2][2]);
                }
                if (col==1 && grid[row-1][2].getOwner()==opponent && grid[row-2][3].free)  {
                    legalMoves.add(grid[row-2][3]);
                }
                if (col==6 && grid[row-1][5].getOwner()==opponent && grid[row-2][4].free)   {
                    legalMoves.add(grid[row-2][4]);
                }
                if (col==7 && grid[row-1][6].getOwner()==opponent && grid[row-2][5].free)   {
                    legalMoves.add(grid[row-2][5]);
                }
                if (col>1 && col<6 && grid[row-1][col-1].getOwner()==opponent && grid[row-2][col-2].free)   {
                    legalMoves.add(grid[row-2][col-2]);
                }
                if (col>1 && col<6 && grid[row-1][col+1].getOwner()==opponent && grid[row-2][col+2].free)  {
                    legalMoves.add(grid[row-2][col+2]);
                }
                return;
            }
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
            return;
        }
        if (!isTurn.starts)    {
            if (jump)   {
                if (col==0 && grid[row+1][1].getOwner()==opponent && grid[row+2][2].free)  {
                    legalMoves.add(grid[row+2][2]);
                }
                if (col==1 && grid[row+1][2].getOwner()==opponent && grid[row+2][3].free)  {
                    legalMoves.add(grid[row+2][3]);
                }
                if (col==6 && grid[row+1][5].getOwner()==opponent && grid[row+2][4].free)   {
                    legalMoves.add(grid[row+2][4]);
                }
                if (col==7 && grid[row+1][6].getOwner()==opponent && grid[row+2][5].free)   {
                    legalMoves.add(grid[row+2][5]);
                }
                if (col>1 && col<6 && grid[row+1][col-1].getOwner()==opponent && grid[row+2][col-2].free)   {
                    legalMoves.add(grid[row+2][col-2]);
                }
                if (col>1 && col<6 && grid[row+1][col+1].getOwner()==opponent && grid[row+2][col+2].free)  {
                    legalMoves.add(grid[row+2][col+2]);
                }
                return;
            }
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

    void doJump(Tile from, Tile to) {
        int fromRow = from.getRow(), fromCol = from.getColumn();
        int toRow = to.getRow(), toCol = to.getColumn();
        int row = toRow - ((toRow-fromRow)/2);
        int col = toCol - ((toCol-fromCol)/2);
        Tile toRemove = grid[row][col];
        checkers.playerQueue.peek().removeTile(toRemove);
        JLabel jumped = (JLabel)toRemove.getComponent(0);
        toRemove.remove(jumped);
        doMove(from, to);
        jump = false;
        checkWinner();
    }

    void keepJumping(Tile from) {
        moved = false;
        jump = true;
        pieceSelected = true;
        getLegalMoves(from);
        hiLiteLegalDestinations(from);
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
        this.from = null;
        checkWinner();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile clicked = (Tile)e.getComponent();
        if (!moved) {
            if (jump && pieceSelected == true && legalMoves.contains(clicked)) {
                doJump(from, clicked);
                if (canJump(clicked))    {
                    keepJumping(clicked);
                }
                checkers.switchTurn();
                return;
            }
            if (pieceSelected == true && legalMoves.contains(clicked)) {
                doMove(from, clicked);
                checkers.switchTurn();
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
