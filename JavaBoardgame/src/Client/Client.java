package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import UI.LoginDialogUI;

public class Client 
{
	ClientConnection cc;
	private LoginDialogUI loginDialog;
	
	public static void main(String[] args)
	{
		new Client();
	}
	
	
	public Client()
	{
		try 
		{
			loginDialog = new LoginDialogUI(null, true);
			Socket s = new Socket(loginDialog.serverIP, 42069);
        	cc = new ClientConnection(s, this, loginDialog.username, loginDialog.password);
			SwingUtilities.invokeLater(new Runnable() 
			{
	            @Override
	            public void run() 
	            {
	        		cc.start();
	            }
	        });
			
			listenForInput();
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void listenForInput()
	{
		Scanner console = new Scanner(System.in);
		
		//wait until line from console
		while(true)
		{
			while(!console.hasNextLine())
			{
				try 
				{
					Thread.sleep(1);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}	
			}
			
			//write that out to server
			String input = console.nextLine();
			
			//if user types "quit" or "QUIT" program will break
			if (input.toLowerCase().equals("quit"))
			{
				break;
			}
			cc.sendStringToServer(input);
		}
		cc.close();
	}
}






