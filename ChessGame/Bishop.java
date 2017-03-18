
public class Bishop extends Piece {

	public Bishop(String name, String color) {
		super(name, color);
		
	}

	@Override
	public boolean isLegalMove(ChessGameBoard board, int currentRow, int currentCol, int newRow, int newCol) {
		
		if((currentCol == newCol || currentRow == newRow) || (Math.abs(newRow - currentRow) != Math.abs(newCol - currentCol))){
			return false;
		}
		
		
		// Bishop goes top right
		if(currentCol < newCol && currentRow > newRow){
			for(int i = currentRow - 1; i > newRow; i--){
				for(int j = currentCol + 1; j < newCol; j++){
					if(board.getTilePiece(i, j) != null){
						return false;
					}
				}
			}
		}
		
		// Bishop goes top left
		if(currentCol > newCol && currentRow > newRow){
			for(int i = currentRow - 1; i > newRow; i--){
				for(int j = currentCol - 1; j > newCol; j--){
					if(board.getTilePiece(i, j) != null){
						return false;
					}
				}
			}
		}
		// Bishop goes bottom left
		if(currentCol > newCol && currentRow < newRow){
			for(int i = currentRow + 1; i < newRow; i++){
				for(int j = currentCol- 1; j > newCol; j--){
					if(board.getTilePiece(i, j) != null){
						return false;
					}
				}
			}
		}
		
		// Bishop goes bottom right
		if(currentCol < newCol && currentRow < newRow){
			for(int i = currentRow + 1; i < newRow; i++){
				for(int j = currentCol + 1; j < newCol; j++){
					if(board.getTilePiece(i, j) != null){
						return false;
					}
				}
			}
		}
		
		return true;
	}

	public String getLabel(){
		return color.substring(0, 1) + "B";
	}
	
}
