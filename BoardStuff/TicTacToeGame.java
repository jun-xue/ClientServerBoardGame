import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

/**
 * Created by ACKD151 on 3/10/2017.
 */
public class TicTacToeGame extends Game {
    String currentPlayer="Player2";
    String Player1="Player1";
    String Player2="Player2";
    Boolean redoMove=false;

    TicTacToeGame() {
        super(new TicTacToeFactory());
    }


    public boolean checkWinner(){
        return   ((board.boardMatrix[0][0].getOwned()!=null && board.boardMatrix[0][0].getOwned()==board.boardMatrix[0][1].getOwned() && board.boardMatrix[0][0].getOwned()==board.boardMatrix[0][2].getOwned())
                ||(board.boardMatrix[1][0].getOwned()!=null && board.boardMatrix[1][0].getOwned()==board.boardMatrix[1][1].getOwned() && board.boardMatrix[1][0].getOwned()==board.boardMatrix[1][2].getOwned())
                ||(board.boardMatrix[2][0].getOwned()!=null && board.boardMatrix[2][0].getOwned()==board.boardMatrix[2][1].getOwned() && board.boardMatrix[2][0].getOwned()==board.boardMatrix[2][2].getOwned())
                ||(board.boardMatrix[0][0].getOwned()!=null && board.boardMatrix[0][0].getOwned()==board.boardMatrix[1][0].getOwned() && board.boardMatrix[0][0].getOwned()==board.boardMatrix[2][0].getOwned())
                ||(board.boardMatrix[0][1].getOwned()!=null && board.boardMatrix[0][1].getOwned()==board.boardMatrix[1][1].getOwned() && board.boardMatrix[0][1].getOwned()==board.boardMatrix[2][1].getOwned())
                ||(board.boardMatrix[0][2].getOwned()!=null && board.boardMatrix[0][2].getOwned()==board.boardMatrix[1][2].getOwned() && board.boardMatrix[0][2].getOwned()==board.boardMatrix[2][2].getOwned())
                ||(board.boardMatrix[0][0].getOwned()!=null && board.boardMatrix[0][0].getOwned()==board.boardMatrix[1][1].getOwned() && board.boardMatrix[0][0].getOwned()==board.boardMatrix[2][2].getOwned())
                ||(board.boardMatrix[0][2].getOwned()!=null && board.boardMatrix[0][2].getOwned()==board.boardMatrix[1][1].getOwned() && board.boardMatrix[0][2].getOwned()==board.boardMatrix[2][0].getOwned())
        );
    }

    public boolean boardFilled(){
        for (Tile[] tt : board.boardMatrix) {
            for (Tile t : tt)   {
                if (t.getOwned()==null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMakeMove(int row, int column, String player){
        return (player==currentPlayer && board.boardMatrix[row][column].getOwned()==null );
    }

    public String flipTurn(String currentPlayer){
        if (currentPlayer==Player2 && !redoMove){
            currentPlayer=Player1;
        }
        else if (currentPlayer==Player1 && !redoMove){
            currentPlayer=Player2;
        }
        return currentPlayer;
    }

    protected void run(Tile clicked) {
        ImageIcon piece;
        currentPlayer=flipTurn(currentPlayer);
        if (currentPlayer==Player1){piece=pieces.get(0);}
        else{piece=pieces.get(1);}
        if (canMakeMove(clicked.getRow(),clicked.getColumn(), currentPlayer)){
            board.boardMatrix[clicked.getRow()][clicked.getColumn()].setOwned(currentPlayer);
            clicked.addPiece(piece);
            System.out.println("Move made by "+ currentPlayer);
            redoMove=false;
            if (checkWinner()){
                System.out.println("Winner is "+ currentPlayer);
                System.exit(0);
            }
            if (boardFilled()){
                System.out.println("Board Full! It's a tie");
                System.exit(0);
            }
        }else{
            System.out.println("Can't make move");
            redoMove=true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile clicked = (Tile)e.getComponent();
        run(clicked);
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

    @Override
    protected ArrayList<Tile> availableMoves(Player isUp) {
        return null;
    }

    @Override
    protected void runGame() {

    }
}