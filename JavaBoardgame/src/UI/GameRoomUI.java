package UI;

import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class GameRoomUI extends JFrame
{
	private static final long serialVersionUID = -1818753098009676013L;
	
	public JTextField text = new JTextField(40);
	public JTextArea message = new JTextArea(8,40);
	public JLabel status = new JLabel("Welcome to the Game");
	public JPanel gameBoard;
	public JButton leave;
	public GameRoomUI()
	{
		//setUndecorated(true);
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
		
		gameBoard = new JPanel();
		gameBoard.setLayout(new BoxLayout(gameBoard, BoxLayout.X_AXIS));
		gameBoard.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		gameBoard.setAlignmentY(CENTER_ALIGNMENT);
		gameBoard.setBounds(new Rectangle(10,10,700,700));
		//THIS NEEDS TO BE THE GAME WINDOW 
		// UPDATE METHOD FOR MOVE NEEDED
		// ADD GAME GUI HERE //
	}
	
	public void setStatus(String newS)
	{
		status.setText(newS);
	}
	
	public static void main(String args[]) {
		GameRoomUI test = new GameRoomUI();
		test.setVisible(true);
	} 

}
