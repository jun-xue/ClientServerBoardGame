
public class Knight extends Piece {

	public Knight(String name, String color) {
		super(name, color);

	}

	@Override
	public boolean isLegalMove(ChessGameBoard board, int currentRow, int currentCol, int newRow, int newCol) {

		if((Math.abs(currentCol - newCol) == 2 && Math.abs(currentRow - newRow) == 1) 
				|| (Math.abs(currentRow - newRow) == 2 && Math.abs(currentCol - newCol) == 1)){
			return true;
		}
		
		return false;
	}

	public String getLabel(){
		return color.substring(0, 1) + "N";
	}
	
}
