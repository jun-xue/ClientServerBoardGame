
public class Pawn extends Piece {

	private boolean en_passant_able;
	// TO DO 
	// YOU NEED TO IMPLEMENT
	// EN PASSANT
	public Pawn(String name, String color) {
		super(name, color);
		en_passant_able = true;
	}

	@Override
	public boolean isLegalMove(ChessGameBoard board, int currentRow, int currentCol, int newRow, int newCol) {

		// WHITE
		if(color.equals("White")){
			// FIRST TURN MOVE TWO
			if(newRow < currentRow){
				if(!hasMoved && currentRow - newRow == 2 && currentCol == newCol){

					if(board.getTilePiece(currentRow - 1, currentCol) == null &&
							board.getTilePiece(newRow, currentCol) == null){
						hasMoved = true;
						en_passant_able = true;
						return true;
					}
				}

				if(currentRow - newRow == 1){
					// ONE FORWARD
					if(currentCol == newCol && 
							board.getTilePiece(newRow, currentCol) == null){
						hasMoved = true;
						return true;
					}
					// CAPTURE RIGHT
					if(currentCol + 1 == newCol){
						if(board.getTilePiece(newRow, newCol) != null &&
								board.getTilePiece(newRow, newCol).getColor().equals("Black")){
							hasMoved = true;
							return true;
						} else if(board.getTilePiece(currentRow, newCol) != null &&
								board.getTilePiece(currentRow, newCol).getColor().equals("Black") &&
								board.getTilePiece(currentRow, newCol) instanceof Pawn &&
								((Pawn) board.getTilePiece(currentRow, newCol)).en_passant_able){
							hasMoved = true;
							return true;
						}
					}
					// CAPTURE LEFT
					if(currentCol - 1 == newCol){
						if(board.getTilePiece(newRow, newCol) != null &&
								board.getTilePiece(newRow, newCol).getColor().equals("Black")){
							hasMoved = true;
							return true;
						} else if(board.getTilePiece(currentRow, newCol) != null &&
								board.getTilePiece(currentRow, newCol).getColor().equals("Black") &&
								board.getTilePiece(currentRow, newCol) instanceof Pawn &&
								((Pawn) board.getTilePiece(currentRow, newCol)).en_passant_able){
							hasMoved = true;
							return true;
						}
					}
				}
			}
			// BLACK
		} else {
			// FIRST TURN MOVE TWO
			if(currentRow < newRow){
				if(!hasMoved && newRow - currentRow == 2 && currentCol == newCol){ 

					if(board.getTilePiece(currentRow + 1, currentCol) == null &&
							board.getTilePiece(newRow, currentCol) == null){
						hasMoved = true;
						en_passant_able = true;
						return true;
					}
				}

				if(newRow - currentRow == 1){
					// FORWARD ONE
					if(currentCol == newCol &&
							board.getTilePiece(newRow, currentCol) == null){
						hasMoved = true;
						return true;
					}
					// CAPTURE RIGHT 
					if(currentCol + 1 == newCol){
						if(board.getTilePiece(newRow, newCol) != null &&
								board.getTilePiece(newRow, newCol).getColor().equals("White")){
							hasMoved = true;
							return true;
						} else if(board.getTilePiece(currentRow, newCol) != null &&
								board.getTilePiece(currentRow, newCol).getColor().equals("White") &&
								board.getTilePiece(currentRow, newCol) instanceof Pawn &&
								((Pawn) board.getTilePiece(currentRow, newCol)).en_passant_able){
							hasMoved = true;
							return true;
						}
					}
					// CAPTURE LEFT
					if(currentCol - 1 == newCol){
						if(board.getTilePiece(newRow, newCol) != null &&
								board.getTilePiece(newRow, newCol).getColor().equals("White")){
							hasMoved = true;
							return true;
						} else if(board.getTilePiece(currentRow, newCol) != null &&
								board.getTilePiece(currentRow, newCol).getColor().equals("White") &&
								board.getTilePiece(currentRow, newCol) instanceof Pawn &&
								((Pawn) board.getTilePiece(currentRow, newCol)).en_passant_able){
							hasMoved = true;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public String getLabel(){
		return color.substring(0, 1) + "P";
	}
	
	public boolean getEPA(){
		return this.en_passant_able;
	}

	public void setEPA(boolean epa){
		this.en_passant_able = epa;
	}
	
}