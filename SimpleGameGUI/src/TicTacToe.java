import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TicTacToe extends JFrame {

	JPanel panel = new JPanel();
	XOButton buttons[] = new XOButton[9];
	
	public static void main(String[] args) {
		// not static, make new version
		new TicTacToe();
	}
	
	public TicTacToe(){
		// constructor
		super("TicTacToe");
		setSize(400,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel.setLayout(new GridLayout(3,3));
		for (int i = 0; i < 9 ; i++){
			buttons[i] = new XOButton();
			panel.add(buttons[i]);
		}
		
		add(panel);
		setVisible(true);
	}
	
}
