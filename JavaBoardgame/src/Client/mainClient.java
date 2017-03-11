package Client;

import java.io.IOException;
import javax.swing.SwingUtilities;
import UI.ClientUI;

public class mainClient 
{
	public static void main(String[] args) throws Exception
	{
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		ClientUI client = new ClientUI();
        		try {
					client.run();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}
}