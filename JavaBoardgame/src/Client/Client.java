package Client;

import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.SwingUtilities;

import UI.LoginDialogUI;

public class Client 
{
	ClientConnection cc;
	private LoginDialogUI loginDialog;
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException
	{
		new Client();
	}
	
	
	public Client() throws UnknownHostException, IOException, ClassNotFoundException
	{
		loginDialog = new LoginDialogUI(null, true);
		cc = new ClientConnection(loginDialog.s, this);
		//GameClient gc = new GameClient(null);
		SwingUtilities.invokeLater(new Runnable() 
		{
		    @Override
		    public void run() 
		    {
				cc.start();
		    	//gc.start();
		    }
		});
	}
}






