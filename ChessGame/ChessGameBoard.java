import java.util.ArrayList;

public class ChessGameBoard {
	private Piece[][] gameboard;

	public ChessGameBoard(){
		gameboard = new Piece[8][8];

		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				gameboard[i][j] = null;
			}
		}
		// initialize pawns
//				for(int j = 0; j < 8; j++){
//					gameboard[1][j] = new Pawn("B" + Integer.toString(j+1) + "Pawn", "Black");
//					gameboard[6][j] = new Pawn("W" + Integer.toString(j+1) + "Pawn", "White");
//				}

		//initialize Rooks
//				gameboard[7][0] = new Rook("WLeftRook", "White");
//				gameboard[7][7] = new Rook("WRightRook", "White");
//				gameboard[0][0] = new Rook("BLeftRook", "Black");
//				gameboard[0][7] = new Rook("BRightRook", "Black");
//		
//				gameboard[7][1] = new Knight("WLeftKnight", "White");
//				gameboard[7][6] = new Knight("WRightKnight", "White");
//				gameboard[0][1] = new Knight("BLeftKnight", "Black");
//				gameboard[0][6] = new Knight("BRightKnight", "Black");
//		
//				gameboard[7][2] = new Bishop("WLeftBishop", "White");
//				gameboard[7][5] = new Bishop("WRightBishop", "White");
//				gameboard[0][2] = new Bishop("BLeftBishop", "Black");
//				gameboard[0][5] = new Bishop("BRightBishop", "Black");
//		
//				gameboard[7][4] = new King("WKing", "White");
//				gameboard[7][3] = new Queen("WQueen", "White");
//				gameboard[0][4] = new King("BKing", "Black");
//				gameboard[0][3] = new Queen("BQueen", "Black");
	}
	
	public ChessGameBoard(ChessGameBoard rightGameBoard){
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				rightGameBoard.gameboard[row][col] = this.gameboard[row][col];
			}
		}
	}
	
	public boolean equalsTo(ChessGameBoard rightGameBoard){
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				if(rightGameBoard.getTilePiece(row, col) != this.getTilePiece(row, col)){
					return false;
				}
			}
		}
		return true;
	}
	
	public void copy(ChessGameBoard rightGameBoard){
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				rightGameBoard.gameboard[row][col] = this.gameboard[row][col];
			}
		}
	}

	public Piece getTilePiece(int currentRow, int currentCol){
		return gameboard[currentRow][currentCol];
	}

	public void setTilePiece(int currentRow, int currentCol, Piece piece){
		gameboard[currentRow][currentCol] = piece;
	}

	public String toString(){
		String returner = "";

		for(int row = 0; row < 8; row++){
			returner += Integer.toString(8 - row);
			for(int col = 0; col < 8; col++){
				if(getTilePiece(row, col) != null){
					returner += String.format("%1$3s", getTilePiece(row, col).getLabel());
				} else {
					returner += "   ";
				}
			}
			returner += "\n";
		}

		returner += "  A  B  C  D  E  F  G  H\n";

		return returner;
	}

	public void testMoves(ChessGameBoard gt, int row, int col){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(getTilePiece(row, col) != null 
						&& getTilePiece(row, col).isLegalMove(this, row, col, i, j)){
					gt.setTilePiece(i, j, new Queen("BS", "Black"));
				}
			}	
		}
	}

	
	
}
