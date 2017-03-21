package Games;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;



public class OthelloFactory implements AbstractGameFactory{

	String gameTitle;
	int boardWidth, boardHeight;
	int rows = 8, columns = 8;
	Color primary, alternate;
	
	public OthelloFactory(){
		gameTitle = "Othello";
		boardWidth = 600;
		boardHeight = 600;
		rows = 8;
		columns = 8;
		primary = new Color(5, 105, 5);
		alternate = new Color(10, 140, 10);
	}
	
	@Override
	public Game createGame(AbstractGameFactory abf) {
		return new OthelloGame(abf);
	}

	@Override
	public Dimension getDimension() {
		return new Dimension(boardWidth, boardHeight);
	}

	@Override
	public GameBoard createGameBoard() {
		return new GameBoard(boardWidth, boardHeight, rows, columns, primary, alternate, gameTitle);
	}

	@Override
	public void loadImages(Player goesFirst, Player two) {
		ImageIcon white = new ImageIcon(new ImageIcon("src/Assets/whiteOthello.png").getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
		goesFirst.playerPieces.add(white);
		ImageIcon black = new ImageIcon(new ImageIcon("src/Assets/blackOthello.png").getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
		goesFirst.playerPieces.add(black);
		}

	@Override
	public void setInitOwnership(GameBoard board, Player client, Player opponent) {
		for(Tile[] tt: board.boardMatrix){
			for(Tile t: tt){
				if((t.getRow() == Math.floorDiv(rows, 2) - 1 && t.getColumn() == Math.floorDiv(columns, 2) - 1)
						|| t.getRow() == Math.floorDiv(rows, 2) && t.getColumn() == Math.floorDiv(columns, 2)){
					t.setOwner(client);
					client.addTile(t);
					t.addPiece(client.playerPieces.get(0));
				}
				
				if((t.getRow() == Math.floorDiv(rows, 2) - 1 && t.getColumn() == Math.floorDiv(columns, 2))
						|| t.getRow() == Math.floorDiv(rows, 2) && t.getColumn() == Math.floorDiv(columns, 2) - 1){
					t.setOwner(client);
					client.addTile(t);
					t.addPiece(client.playerPieces.get(1));
				}
			}
		}
		board.updateScreen();
	}
	
}
