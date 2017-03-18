package UI;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameRoomUI extends JFrame
{
	private static final long serialVersionUID = -1818753098009676013L;
	
	JTextField text = new JTextField(40);
	JTextArea message = new JTextArea(8,40);
	
	public GameRoomUI()
	{
		setTitle("Game Room");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setResizable(false);
		getContentPane().setLayout(null);
	}
	

	public static void main(String args[]) {
		GameRoomUI test = new GameRoomUI();
		test.setVisible(true);
	} 

}
