package UI;

import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class LoadInUI extends JFrame
{
	private static final long serialVersionUID = 7750787899452742259L;
	public JList<String> players;
	public JList<String> games;
	
	public JButton hostGame;
	
	public JTextField text = new JTextField(40);
	public JTextArea message = new JTextArea(10,40);
	
	public LoadInUI()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 575);
		setResizable(false);
		getContentPane().setLayout(null);
		
		message.setEditable(false);
		JScrollPane messagesHolder = new JScrollPane(message);
		messagesHolder.setBounds(500, 0, 300, 500);
		text.setBounds(500, 500, 300, 50);
		getContentPane().add(messagesHolder);
		getContentPane().add(text);
		
		players = new JList<String>();
		players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane namesHolder = new JScrollPane(players);
		namesHolder.setBounds(0, 0, 200, 575);
		getContentPane().add(namesHolder);
		
		games = new JList<String>();
		games.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane gamesHolder = new JScrollPane(games);
		gamesHolder.setBounds(200, 0, 300, 500);
		getContentPane().add(gamesHolder);
		
		hostGame = new JButton("Host Game");
		hostGame.setBounds(200, 500, 300, 50);
		getContentPane().add(hostGame);
	}
	
	public void updatePlayers(String[] playerss)
	{
		players.setListData(Arrays.copyOfRange(playerss, 2, playerss.length));
	}
	
	public void updateRooms(String[] rooms)
	{
		games.setListData(Arrays.copyOfRange(rooms, 2, rooms.length));
	}
	
	public static void main(String args[]){
		LoadInUI test = new LoadInUI();
		test.setVisible(true);
	}
}
