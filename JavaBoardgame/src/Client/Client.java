package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
}






