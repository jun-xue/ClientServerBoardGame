
public class ChessLogic {
	ChessGameBoard gameBoard;
	String turn;

	public ChessLogic(){
		this.gameBoard = new ChessGameBoard();
		turn = "White";

	}

	public boolean checked(ChessGameBoard board, String color){
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				if(gameBoard.getTilePiece(row, col) != null && 
						!gameBoard.getTilePiece(row, col).getColor().equals(color) && 
						gameBoard.getTilePiece(row, col).isLegalMove(gameBoard, row, col, 
								findKing(board, color)[0], findKing(board, color)[0])){
					return true;
				}
			}
		}

		return false;
	}

	public int[] findKing(ChessGameBoard gb, String color){
		int[] returner = new int[2];

		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				if(gb.getTilePiece(row, col) != null &&
						gb.getTilePiece(row, col) instanceof King && 
						gb.getTilePiece(row, col).getColor().equals(color)){
					returner[0] = row;
					returner[1] = col;
				}
			}
		}
		return returner;
	}

	public boolean isCheckMate(ChessGameBoard board, String color){
		ChessGameBoard testBoard = new ChessGameBoard(board);
		return false;
	}

	public String oppositeColor(String color){
		if(color.equals("White")){
			return "Black";
		} else {
			return "White";
		}
	}

	public boolean canPromotePawn(Piece piece, int currentRow, int currentCol, String color){

		if(gameBoard.getTilePiece(currentRow, currentCol) instanceof Pawn){

			if(color.equals("White")){
				if(currentRow == 0){
					return true;
				}
			} else {
				if(currentRow == 7){
					return true;
				}
			}
		}
		return false;
	}

	public void PromotePawn(int currentRow, int currentCol, String color, String pieceToPromote){

		gameBoard.setTilePiece(currentRow, currentCol, null);

		switch(pieceToPromote){
		case "N":
			gameBoard.setTilePiece(currentRow, currentCol, new Knight(color.substring(0, 1) + 
					"PromotedKnight", color));
			break;
		case "B":
			gameBoard.setTilePiece(currentRow, currentCol, new Bishop(color.substring(0, 1) + 
					"PromotedBishop", color));
			break;
		case "R":
			gameBoard.setTilePiece(currentRow, currentCol, new Rook(color.substring(0, 1) + 
					"PromotedRook", color));
			break;
		default:
			gameBoard.setTilePiece(currentRow, currentCol, new Queen(color.substring(0, 1) + 
					"PromotedQueen", color));
			break;
		}
	}

	public boolean move(Piece piece, int currentRow, int currentCol, int newRow, int newCol){
		
		// CurrentMove is checkmate
		if(isCheckMate(gameBoard, turn)){
			return false;
		}
		
		ChessGameBoard tester = predictNextMove(piece, currentRow, currentCol, newRow, newCol);
		
		if(piece == null){
			return false;
		}
		
		// No moves made or tried to place on tile with same color piece
		if(tester.equalsTo(gameBoard)){
			return false;
		}
		
		// Current move checked and next move checked 
		if(checked(gameBoard, turn) && checked(tester, turn)){
			return false;
		}

		if(piece.getColor().equals(turn) && 
				piece.isLegalMove(gameBoard, currentRow, currentCol, newRow, newCol) &&
				!checked(tester, turn)){

			// There is an enemy where I want to move
			if(gameBoard.getTilePiece(newRow, newCol) != null && 
					gameBoard.getTilePiece(newRow, newCol).getColor().equals(oppositeColor(turn))){

				piece.setHasMoved(true);
				gameBoard.setTilePiece(currentRow, currentCol, null);
				gameBoard.setTilePiece(newRow, newCol, piece);

				// There is nothing to where I want to move
			} else if(gameBoard.getTilePiece(newRow, newCol) == null) {

				// Castle Black King side
				if(gameBoard.getTilePiece(currentRow, currentCol) instanceof King &&
						!gameBoard.getTilePiece(currentRow, currentCol).getHasMoved() && 
						gameBoard.getTilePiece(currentRow, currentCol).getColor().equals("Black") &&
						newCol - currentCol == 2 &&
						gameBoard.getTilePiece(0, 7) != null && 
						gameBoard.getTilePiece(0, 7) instanceof Rook &&
						!gameBoard.getTilePiece(0, 7).getHasMoved()){
					Rook rookCastle = (Rook) gameBoard.getTilePiece(0, 7);
					rookCastle.setHasMoved(true);
					gameBoard.setTilePiece(0, 5, rookCastle);
					gameBoard.setTilePiece(0, 7, null);
				}
				
				// Castle Black Queen side
				else if(gameBoard.getTilePiece(currentRow, currentCol) instanceof King &&
						!gameBoard.getTilePiece(currentRow, currentCol).getHasMoved() && 
						gameBoard.getTilePiece(currentRow, currentCol).getColor().equals("Black") &&
						currentCol - newCol == 2 &&
						gameBoard.getTilePiece(0, 0) != null && 
						gameBoard.getTilePiece(0, 0) instanceof Rook &&
						!gameBoard.getTilePiece(0, 0).getHasMoved()){
					Rook rookCastle = (Rook) gameBoard.getTilePiece(0, 0);
					rookCastle.setHasMoved(true);
					gameBoard.setTilePiece(0, 2, rookCastle);
					gameBoard.setTilePiece(0, 0, null);
				}
				
				// Castle white King side
				else if(gameBoard.getTilePiece(currentRow, currentCol) instanceof King &&
						!gameBoard.getTilePiece(currentRow, currentCol).getHasMoved() && 
						gameBoard.getTilePiece(currentRow, currentCol).getColor().equals("White") &&
						newCol - currentCol == 2 &&
						gameBoard.getTilePiece(7, 7) != null && 
						gameBoard.getTilePiece(7, 7) instanceof Rook &&
						!gameBoard.getTilePiece(7, 7).getHasMoved()){
					Rook rookCastle = (Rook) gameBoard.getTilePiece(7, 7);
					rookCastle.setHasMoved(true);
					gameBoard.setTilePiece(7, 5, rookCastle);
					gameBoard.setTilePiece(7, 7, null);
				}
				
				// Castle white Queen side
				else if(gameBoard.getTilePiece(currentRow, currentCol) instanceof King &&
						!gameBoard.getTilePiece(currentRow, currentCol).getHasMoved() && 
						gameBoard.getTilePiece(currentRow, currentCol).getColor().equals("White") &&
						currentCol - newCol == 2 &&
						gameBoard.getTilePiece(7, 0) != null && 
						gameBoard.getTilePiece(7, 0) instanceof Rook &&
						!gameBoard.getTilePiece(7, 0).getHasMoved()){
					Rook rookCastle = (Rook) gameBoard.getTilePiece(7, 0);
					rookCastle.setHasMoved(true);
					gameBoard.setTilePiece(7, 2, rookCastle);
					gameBoard.setTilePiece(7, 0, null);
				}
				

				
				piece.setHasMoved(true);
				gameBoard.setTilePiece(newRow, newCol, piece);
			}
			return true;
		}
		return false;
	}

	public ChessGameBoard predictNextMove(Piece piece, int currentRow, int currentCol, int newRow, int newCol){
		ChessGameBoard returner = new ChessGameBoard(gameBoard);

		if(piece.getColor().equals(turn) &&
				piece.isLegalMove(returner, currentRow, currentCol, newRow, newCol)){

			if(gameBoard.getTilePiece(newRow, newCol) != null &&
					gameBoard.getTilePiece(newRow, newCol).getColor().equals(oppositeColor(turn))){

				piece.setHasMoved(true);
				gameBoard.setTilePiece(currentRow, currentCol, null);
				gameBoard.setTilePiece(newRow, newCol, piece);

			} else if(gameBoard.getTilePiece(newRow, newCol) == null) {

				// Castle Black King side
				if(gameBoard.getTilePiece(currentRow, currentCol) instanceof King &&
						!gameBoard.getTilePiece(currentRow, currentCol).getHasMoved() && 
						gameBoard.getTilePiece(currentRow, currentCol).getColor().equals("Black") &&
						newCol - currentCol == 2 &&
						gameBoard.getTilePiece(0, 7) != null && 
						gameBoard.getTilePiece(0, 7) instanceof Rook &&
						!gameBoard.getTilePiece(0, 7).getHasMoved()){
					Rook rookCastle = (Rook) gameBoard.getTilePiece(0, 7);
					rookCastle.setHasMoved(true);
					gameBoard.setTilePiece(0, 5, rookCastle);
					gameBoard.setTilePiece(0, 7, null);
				}
				
				// Castle Black Queen side
				else if(gameBoard.getTilePiece(currentRow, currentCol) instanceof King &&
						!gameBoard.getTilePiece(currentRow, currentCol).getHasMoved() && 
						gameBoard.getTilePiece(currentRow, currentCol).getColor().equals("Black") &&
						currentCol - newCol == 2 &&
						gameBoard.getTilePiece(0, 0) != null && 
						gameBoard.getTilePiece(0, 0) instanceof Rook &&
						!gameBoard.getTilePiece(0, 0).getHasMoved()){
					Rook rookCastle = (Rook) gameBoard.getTilePiece(0, 0);
					rookCastle.setHasMoved(true);
					gameBoard.setTilePiece(0, 2, rookCastle);
					gameBoard.setTilePiece(0, 0, null);
				}
				
				// Castle white King side
				else if(gameBoard.getTilePiece(currentRow, currentCol) instanceof King &&
						!gameBoard.getTilePiece(currentRow, currentCol).getHasMoved() && 
						gameBoard.getTilePiece(currentRow, currentCol).getColor().equals("White") &&
						newCol - currentCol == 2 &&
						gameBoard.getTilePiece(7, 7) != null && 
						gameBoard.getTilePiece(7, 7) instanceof Rook &&
						!gameBoard.getTilePiece(7, 7).getHasMoved()){
					Rook rookCastle = (Rook) gameBoard.getTilePiece(7, 7);
					rookCastle.setHasMoved(true);
					gameBoard.setTilePiece(7, 5, rookCastle);
					gameBoard.setTilePiece(7, 7, null);
				}
				
				// Castle white Queen side
				else if(gameBoard.getTilePiece(currentRow, currentCol) instanceof King &&
						!gameBoard.getTilePiece(currentRow, currentCol).getHasMoved() && 
						gameBoard.getTilePiece(currentRow, currentCol).getColor().equals("White") &&
						currentCol - newCol == 2 &&
						gameBoard.getTilePiece(7, 0) != null && 
						gameBoard.getTilePiece(7, 0) instanceof Rook &&
						!gameBoard.getTilePiece(7, 0).getHasMoved()){
					Rook rookCastle = (Rook) gameBoard.getTilePiece(7, 0);
					rookCastle.setHasMoved(true);
					gameBoard.setTilePiece(7, 2, rookCastle);
					gameBoard.setTilePiece(7, 0, null);
				}
				
				piece.setHasMoved(true);
				gameBoard.setTilePiece(newRow, newCol, piece);
			}
		}
		return returner;
	}
}
