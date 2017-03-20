package Games;
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
        getMoves();
        hiLiteMoves();
        //turn continued in mouselistener handler
        state.gameOver = checkWinner();
        return state;
    }

    void getMoves() {
        if (!(jump = checkForJumps()))  {
            checkForMoves();
        }
    }

    void getDestinations(Tile from)   {
            if (jump)   {
                getJumpDestinations(from);
            }   else    {
                getMoveDestinations(from);
            }
    }

    boolean checkForJumps() {
        canMove.clear();
        for (Tile t : isTurn.myTiles) {
            BoardPiece checkerType = (BoardPiece) t.getComponent(0);
            int rowFrom = t.getRow(), colFrom = t.getColumn();
            if (checkJump(checkerType.isKing, rowFrom, colFrom, rowFrom + 1,
                    colFrom + 1, rowFrom + 2, colFrom + 2)) {
                canMove.add(t);
            }
            if (checkJump(checkerType.isKing, rowFrom, colFrom, rowFrom + 1,
                    colFrom - 1, rowFrom + 2, colFrom - 2)) {
                canMove.add(t);
            }
            if (checkJump(checkerType.isKing, rowFrom, colFrom, rowFrom - 1,
                    colFrom + 1, rowFrom - 2, colFrom + 2)) {
                canMove.add(t);
            }
            if (checkJump(checkerType.isKing, rowFrom, colFrom, rowFrom - 1,
                    colFrom - 1, rowFrom - 2, colFrom - 2)) {
                canMove.add(t);
            }
        }
        return !canMove.isEmpty();
    }

    boolean checkForMoves()  {
        canMove.clear();
        for (Tile t : isTurn.myTiles) {
            int rowFrom = t.getRow(), colFrom = t.getColumn();
            BoardPiece checkerType = (BoardPiece) t.getComponent(0);
            if (checkMove(checkerType.isKing, rowFrom, colFrom, rowFrom + 1, colFrom + 1)) {
                canMove.add(t);
            }
            if (checkMove(checkerType.isKing, rowFrom, colFrom, rowFrom + 1, colFrom - 1)) {
                canMove.add(t);
            }
            if (checkMove(checkerType.isKing, rowFrom, colFrom, rowFrom - 1, colFrom + 1)) {
                canMove.add(t);
            }
            if (checkMove(checkerType.isKing, rowFrom, colFrom, rowFrom - 1, colFrom - 1)) {
                canMove.add(t);
            }
        }
        return !canMove.isEmpty();
    }

    boolean getJumpDestinations(Tile t) {
        legalMoves.clear();
        BoardPiece checkerType = (BoardPiece)t.getComponent(0);
        int rowFrom = t.getRow(), colFrom = t.getColumn();
        if (checkJump(checkerType.isKing, rowFrom, colFrom, rowFrom+1,
                colFrom+1, rowFrom+2, colFrom+2))    {
            legalMoves.add(grid[rowFrom+2][colFrom+2]);
        }
        if (checkJump(checkerType.isKing, rowFrom, colFrom, rowFrom+1,
                colFrom-1, rowFrom+2, colFrom-2))    {
            legalMoves.add(grid[rowFrom+2][colFrom-2]);
        }
        if (checkJump(checkerType.isKing, rowFrom, colFrom, rowFrom-1,
                colFrom+1, rowFrom-2, colFrom+2))    {
            legalMoves.add(grid[rowFrom-2][colFrom+2]);
        }
        if (checkJump(checkerType.isKing, rowFrom, colFrom, rowFrom-1,
                colFrom-1, rowFrom-2, colFrom-2))    {
            legalMoves.add(grid[rowFrom-2][colFrom-2]);
        }
        return !legalMoves.isEmpty();
    }

    boolean getMoveDestinations(Tile t)  {
        legalMoves.clear();
        int rowFrom = t.getRow(), colFrom = t.getColumn();
        BoardPiece checkerType = (BoardPiece) t.getComponent(0);
        if (checkMove(checkerType.isKing, rowFrom, colFrom, rowFrom + 1, colFrom + 1)) {
            legalMoves.add(grid[rowFrom + 1][colFrom + 1]);
        }
        if (checkMove(checkerType.isKing, rowFrom, colFrom, rowFrom + 1, colFrom - 1)) {
            legalMoves.add(grid[rowFrom + 1][colFrom - 1]);
        }
        if (checkMove(checkerType.isKing, rowFrom, colFrom, rowFrom - 1, colFrom + 1)) {
            legalMoves.add(grid[rowFrom - 1][colFrom + 1]);
        }
        if (checkMove(checkerType.isKing, rowFrom, colFrom, rowFrom - 1, colFrom - 1)) {
            legalMoves.add(grid[rowFrom - 1][colFrom - 1]);
        }
        return !legalMoves.isEmpty();
    }

    boolean checkJump(boolean isKing, int rowFrom, int colFrom, int rowJump, int colJump, int rowTo, int colTo)   {
        if (rowTo<0 || rowTo>7 || colTo<0 || colTo>7) {   //jumping off the board
            return false;
        }
        if (grid[rowJump][colJump].free || grid[rowJump][colJump].getOwner()==isTurn)   {   //no opponent to jump
            return false;
        }
        if (!grid[rowTo][colTo].free)   {   //jumpTo Tile is not free
            return false;
        }
        if (!isKing)    {
            if (isTurn.starts && rowTo>rowFrom)   { //red non-king can't jump backwards
                return false;
            }
            if (!isTurn.starts && rowTo<rowFrom)    {   //white non-king can't jump backwards
                return false;
            }
        }
        return true;
    }

    boolean checkMove(boolean isKing, int rowFrom,int colFrom, int rowTo,int colTo)  {
        if (rowTo<0 || rowTo>7 || colTo<0 || colTo>7) {   //moving off the board
            return false;
        }
        if (!grid[rowTo][colTo].free)   {   //Tile is not free
            return false;
        }
        if (!isKing)    {
            if (isTurn.starts && rowTo>rowFrom)   { //red non-king can't move backwards
                return false;
            }
            if (!isTurn.starts && rowTo<rowFrom)    {   //white non-king can't move backwards
                return false;
            }
        }
        return true;
    }

    void hiLiteMoves() {
        for (Tile t : canMove) {
            t.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        }
    }

    void hiLiteDestinations(Tile from)  {
        from.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        for (Tile t : legalMoves) {
            t.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        }
    }

    boolean checkWinner()  {
        if (checkers.client.myTiles.size()==0 || checkers.opponent.myTiles.size()==0)   {
            System.out.println("Winner: " + isTurn.getName());
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
        checkKingMe(to);
        checkWinner();
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
        checkKingMe(to);
        checkWinner();
    }

    void keepJumping() {
        jump = false;
        pieceSelected = false;
        takeTurn(isTurn, state);
    }

    void checkKingMe(Tile t)    {
        BoardPiece checker = (BoardPiece)t.getComponent(0);
        if (!checker.isKing && isTurn.starts && t.getRow()==0)    {
            t.remove(checker);
            t.add(new BoardPiece(isTurn.playerPieces.get(1), true, true));
            return;
        }
        if (!checker.isKing && !isTurn.starts && t.getRow()==7)    {
            t.remove(checker);
            t.add(new BoardPiece(isTurn.playerPieces.get(1), false, true));
        }
    }

    void resetTurn()    {
        checkers.board.clearHiLites();
        pieceSelected = false;
        getMoves();
        hiLiteMoves();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile clicked = (Tile)e.getComponent();
        if (!moved) {
            if (jump && pieceSelected && legalMoves.contains(clicked)) {
                doJump(from, clicked);
                if (getJumpDestinations(clicked))    {
                    keepJumping();
                    return;
                }
                checkers.switchTurn();
                return;
            }
            if (pieceSelected && legalMoves.contains(clicked)) {
                doMove(from, clicked);
                checkers.switchTurn();
                return;
            }
            if (clicked.getOwner()==isTurn && !pieceSelected) {
                checkers.board.clearHiLites();
                from = clicked;
                pieceSelected = true;
                getDestinations(from);
                hiLiteDestinations(from);
                return;
            }
            if (clicked.getOwner()==isTurn && pieceSelected && from==clicked)   {
                resetTurn();
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
