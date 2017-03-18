
public class ChessMain {

	public static void main(String[] args) {
		ChessGameBoard gb = new ChessGameBoard();
		ChessGameBoard gt = new ChessGameBoard();
		gb.setTilePiece(3, 3, new Pawn("W", "White"));
		gb.setTilePiece(3, 2, new Pawn("W", "Black"));
		
		System.out.println(gt.equalsTo(gb));
		
		gb.testMoves(gt, 3, 2);
		
		System.out.println(gt.toString());
		System.out.println(gb.toString());
	}
	
}
