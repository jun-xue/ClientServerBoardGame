
public abstract class Piece {
	protected String name;
	protected boolean hasMoved;
	protected String color;
	
	public Piece(String name, String color){
		this.name = name;
		this.color = color;
		this.hasMoved = false;
	}
	
	public abstract boolean isLegalMove(ChessGameBoard board, int currentRow, int currentCol, int newRow, int newCol);
	
	public String getName(){
		return this.name;
	}
	
	public boolean getHasMoved(){
		return this.hasMoved;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public void setHasMoved(boolean moved){
		this.hasMoved = moved;
	}
	
	public abstract String getLabel();
}
