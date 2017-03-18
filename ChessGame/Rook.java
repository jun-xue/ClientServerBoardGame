
public class Rook extends Piece {

	public Rook(String name, String color) {
		super(name, color);
	}

	@Override
	public boolean isLegalMove(ChessGameBoard board, int currentRow, int currentCol, int newRow, int newCol) {
		
		if(currentCol != newCol && currentRow != newRow){
			return false;
		}
		
		if(currentCol == newCol && currentRow == newRow){
			return false;
		}
		
		// if in same column
		if(currentCol == newCol){
			// if row number increased check all tiles in between in empty
			// MOVED UP
			if(currentRow > newRow){
				for(int row = currentRow - 1; row > newRow; row--){
					if(board.getTilePiece(row, currentCol) != null){
						return false;
					}
				}
			// if row number decreased check all tiles in between in empty
			// MOVED DOWN
			} else {
				for(int row = currentRow + 1; row < newRow; row++){
					if(board.getTilePiece(row, currentCol) != null){
						return false;
					}
				}
				
			}
		}
		
		// do the above for the rows
		if(currentRow == newRow){
			// MOVE LEFT
			if(currentCol > newCol){
				for(int col = currentCol - 1; col > newCol; col--){
					if(board.getTilePiece(currentRow, col) != null){
						return false;
					}
				}
			// MOVE RIGHT
			} else {
				for(int col = currentCol + 1; col < newCol; col++){
					if(board.getTilePiece(currentRow, col) != null){
						return false;
					}
				}
				
			}
		}
		
		return true;
	}
	
	public String getLabel(){
		return color.substring(0, 1) + "R";
	}
	
}
