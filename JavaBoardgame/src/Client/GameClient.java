package Client;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameClient extends Thread
{
	JFrame frame = new JFrame("Game Client");
	
	JPanel gameWindow = new JPanel();
	JButton quit = new JButton("Quit Game");
	JLabel status = new JLabel("Welcome to the Game!", JLabel.CENTER);
	
	public GameClient(Socket socket)
	{
		
		gameWindow.setBounds(10, 10, 800, 800);
		frame.getContentPane().add(status, "North");
		frame.getContentPane().add(gameWindow, "Center");
		frame.getContentPane().add(quit, "South");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}
	
	
	public void setStatusBar(String statusText)
	{
		status.setText(statusText);
	}
}