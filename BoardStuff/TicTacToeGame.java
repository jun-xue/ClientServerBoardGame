import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * Created by ACKD151 on 3/10/2017.
 */
public class TicTacToeGame extends Game {
    Player currentPlayer;

    TicTacToeGame() {
        super(new TicTacToeFactory());
    }


    public boolean checkWinner(){
        return   ((board.boardMatrix[0][0].owned!=null && board.boardMatrix[0][0].owned==board.boardMatrix[0][1].owned && board.boardMatrix[0][0].owned==board.boardMatrix[0][2].owned)
                ||(board.boardMatrix[1][0].owned!=null && board.boardMatrix[1][0].owned==board.boardMatrix[1][1].owned && board.boardMatrix[1][0].owned==board.boardMatrix[1][2].owned)
                ||(board.boardMatrix[2][0].owned!=null && board.boardMatrix[2][0].owned==board.boardMatrix[2][1].owned && board.boardMatrix[2][0].owned==board.boardMatrix[2][2].owned)
                ||(board.boardMatrix[0][0].owned!=null && board.boardMatrix[0][0].owned==board.boardMatrix[1][0].owned && board.boardMatrix[0][0].owned==board.boardMatrix[2][0].owned)
                ||(board.boardMatrix[0][1].owned!=null && board.boardMatrix[0][1].owned==board.boardMatrix[1][1].owned && board.boardMatrix[0][1].owned==board.boardMatrix[2][1].owned)
                ||(board.boardMatrix[0][2].owned!=null && board.boardMatrix[0][2].owned==board.boardMatrix[1][2].owned && board.boardMatrix[0][2].owned==board.boardMatrix[2][2].owned)
                ||(board.boardMatrix[0][0].owned!=null && board.boardMatrix[0][0].owned==board.boardMatrix[1][1].owned && board.boardMatrix[0][0].owned==board.boardMatrix[2][2].owned)
                ||(board.boardMatrix[0][2].owned!=null && board.boardMatrix[0][2].owned==board.boardMatrix[1][1].owned && board.boardMatrix[0][2].owned==board.boardMatrix[2][0].owned)
        );
    }

    public boolean boardFilled(){
        for (Tile[] tt : board.boardMatrix) {
            for (Tile t : tt)   {
                if (t.owned==null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMakeMove(int row, int column, Player player){
        return (player==currentPlayer && board.boardMatrix[row][column].owned==null );
        
    }
    
	@Override
	protected void run() {
		// TODO Auto-generated method stub
		
	}

    protected void run(Tile clicked) {
    	if (canMakeMove(clicked.getRow(),clicked.getColumn(), currentPlayer)){
            board.boardMatrix[clicked.getRow()][clicked.getColumn()].owned=currentPlayer;
            currentPlayer=currentPlayer;
            clicked.addPiece(pieces.get(0));
            System.out.println("Selected row: " + clicked.getRow() + ", column: " + clicked.getColumn() + ".");}
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



}