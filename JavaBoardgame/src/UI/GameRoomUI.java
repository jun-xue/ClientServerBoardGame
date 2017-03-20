package UI;

import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import Games.AbstractGameFactory;
import Games.Game;
import Games.TicTacToeFactory;
import Games.Tile;

public class GameRoomUI extends JFrame
{
	private static final long serialVersionUID = -1818753098009676013L;
	
	public JTextField text = new JTextField(40);
	public JTextArea message = new JTextArea(8,40);
	public JLabel status = new JLabel("Welcome to the Game");
	public Game gameBoard;
	public JButton leave;
	public String currentGame;
	AbstractGameFactory gf;
	
	public GameRoomUI(String gameType)
	{
		//setUndecorated(true);
		currentGame = gameType;
		setTitle("Challenger Game");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 900, 900);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JScrollPane messagesHolder = new JScrollPane(message);
		messagesHolder.setBounds(0, 700, 900, 150);
		getContentPane().add(messagesHolder);
		message.setEditable(false);
		
		text.setBounds(0, 850, 900, 30);
		getContentPane().add(text);
		
		status.setBounds(375, 10, 300, 25);
		getContentPane().add(status);
		
		leave = new JButton("Exit Game");
		leave.setBounds(780, 10, 100, 50);
		getContentPane().add(leave);
		
		if (currentGame.equals("TTT"))
		{
            gf = new TicTacToeFactory();
            gameBoard = gf.createGame(gf);
			gameBoard.setLayout(new GridLayout(1, 1));
			for (Tile[] item: gameBoard.board.boardMatrix)
			{
				for (Tile test : item)
				{
					System.out.println("");
					System.out.println(test.getRow());
					System.out.println(test.getColumn());
				}
			}
			gameBoard.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			gameBoard.setAlignmentY(CENTER_ALIGNMENT);
			gameBoard.setBounds(new Rectangle(50,50,625,625));
			getContentPane().add(gameBoard);
		}
		else if (currentGame.equals("Chess"))
		{
			
		}
		else if (currentGame.equals("Checkers"))
		{
			
		}
	}
	
	public void setStatus(String newS)
	{
		status.setText(newS);
	}
	
	public static void main(String args[]) {
		GameRoomUI test = new GameRoomUI("TTT");
		test.setVisible(true);
	} 

}
