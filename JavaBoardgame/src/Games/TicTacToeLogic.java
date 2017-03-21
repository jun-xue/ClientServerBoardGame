package Games;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TicTacToeLogic implements MouseListener {
	
	TicTacToeGame ttt;
    GameState state;
    Player isTurn;
    Tile[][] tileBoard;
    boolean redoMove=true;
	public TicTacToeLogic(TicTacToeGame game, GameState state) {
		this.ttt=game;
		this.state=state;
		this.isTurn=game.isTurn;
		this.tileBoard=game.board.boardMatrix;
		for(Tile[] tt : tileBoard)   {
            for(Tile t : tt)    {
                t.addMouseListener(this);
            }
        }
	}
	
    public boolean checkWinner(){
        return   ((tileBoard[0][0].getOwner()!=null && tileBoard[0][0].getOwner()==tileBoard[0][1].getOwner() && tileBoard[0][0].getOwner()==tileBoard[0][2].getOwner())
                ||(tileBoard[1][0].getOwner()!=null && tileBoard[1][0].getOwner()==tileBoard[1][1].getOwner() && tileBoard[1][0].getOwner()==tileBoard[1][2].getOwner())
                ||(tileBoard[2][0].getOwner()!=null && tileBoard[2][0].getOwner()==tileBoard[2][1].getOwner() && tileBoard[2][0].getOwner()==tileBoard[2][2].getOwner())
                ||(tileBoard[0][0].getOwner()!=null && tileBoard[0][0].getOwner()==tileBoard[1][0].getOwner() && tileBoard[0][0].getOwner()==tileBoard[2][0].getOwner())
                ||(tileBoard[0][1].getOwner()!=null && tileBoard[0][1].getOwner()==tileBoard[1][1].getOwner() && tileBoard[0][1].getOwner()==tileBoard[2][1].getOwner())
                ||(tileBoard[0][2].getOwner()!=null && tileBoard[0][2].getOwner()==tileBoard[1][2].getOwner() && tileBoard[0][2].getOwner()==tileBoard[2][2].getOwner())
                ||(tileBoard[0][0].getOwner()!=null && tileBoard[0][0].getOwner()==tileBoard[1][1].getOwner() && tileBoard[0][0].getOwner()==tileBoard[2][2].getOwner())
                ||(tileBoard[0][2].getOwner()!=null && tileBoard[0][2].getOwner()==tileBoard[1][1].getOwner() && tileBoard[0][2].getOwner()==tileBoard[2][0].getOwner())
        );
    }

    public boolean boardFilled(){
        for (Tile[] tt : tileBoard) {
            for (Tile t : tt)   {
                if (t.getOwner()==null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
	public GameState takeTurn(Player playerTurn, GameState state) {
        this.isTurn = playerTurn;
        this.state = new GameState(state);
        redoMove = true;
        state.gameOver=(checkWinner() || boardFilled());
		return state;		
	}
	
    public boolean canMakeMove(int row, int column){
        return (tileBoard[row][column].getOwner()==null );
    }
    
    public void makeMove(int row, int column, Tile clicked){
    	tileBoard[row][column].setOwner(isTurn);
        clicked.addPiece(isTurn.playerPieces.get(0));
        ttt.board.updateScreen();
        System.out.println("Move made by "+ isTurn.name);
        redoMove=false;
    }
    

	@Override
	public void mouseClicked(MouseEvent e) {
		Tile clicked = (Tile)e.getComponent();
		if (redoMove){
			if (canMakeMove(clicked.getRow(), clicked.getColumn())){
				makeMove(clicked.getRow(), clicked.getColumn(), clicked);
		        if (checkWinner()){
		        	System.out.println("Winner is "+ isTurn.name);
		        }
		        if (boardFilled()){
		        	System.out.println("Board Full! It's a tie");
		        }
				ttt.switchTurn();
			}else{
	            System.out.println("Can't make move");
	            redoMove=true;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
