
public class King extends Piece {

	
	public King(String name, String color) {
		super(name, color);
	}

	@Override
	public boolean isLegalMove(ChessGameBoard board, int currentRow, int currentCol, int newRow, int newCol) {
		
		if((Math.abs(currentCol - newCol) + Math.abs(currentRow - newRow)) > 0 &&
				(Math.abs(currentCol - newCol) < 2 && Math.abs(currentRow - newRow) < 2)){
			if(board.getTilePiece(newRow, newCol) == null){
				return true;
			}
		}
		
		// CASTLING
		if(!hasMoved && Math.abs(currentCol - newCol) == 2 && currentRow == newRow){
			
			// Castle King side
			if(newCol > currentCol && board.getTilePiece(currentRow, currentCol + 1) == null 
					&& board.getTilePiece(currentRow, newCol) == null){
				hasMoved = true;
				return true;
				
			// Castle Queen side
			} else if(newCol < currentCol && board.getTilePiece(currentRow, currentCol - 1) == null 
					&& board.getTilePiece(currentRow, newCol) == null){
				hasMoved = true;
				return true;
			}
		}
		
		return false;
	}

	public String getLabel(){
		return color.substring(0, 1) + "K";
	}
	
}
