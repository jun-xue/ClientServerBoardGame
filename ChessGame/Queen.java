
public class Queen extends Piece {

	public Queen(String name, String color) {
		super(name, color);

	}

	@Override
	public boolean isLegalMove(ChessGameBoard board, int currentRow, int currentCol, int newRow, int newCol) {
		
		//This is a rook combined with a bishop
		boolean isLegalMoveForRookAndBishop = new Rook(name, color).isLegalMove(board, currentRow, 
				currentCol, newRow, newCol) || new Bishop(name, color).isLegalMove(board, currentRow, 
						currentCol, newRow, newCol);
		
		return isLegalMoveForRookAndBishop;
	}

	public String getLabel(){
		return color.substring(0, 1) + "Q";
	}
	
}
