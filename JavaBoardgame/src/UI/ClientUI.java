package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

public class ClientUI
{
	private LoginDialogUI loginDialog;
	Socket socket;
	JFrame frame = new JFrame("Client");
	
	JTextArea message = new JTextArea(8, 120);
	JTextField data = new JTextField(40);
	
	BufferedReader in;
	PrintWriter out;
	
	public ClientUI()
	{
        frame.getContentPane().add(data, "South");
        frame.getContentPane().add(message, "North");
        
        frame.setTitle("Challenger Client");
        frame.setSize(800, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.pack();
	    frame.setVisible(true);
	    
		loginDialog = new LoginDialogUI(frame, true);
		
        data.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                out.println(data.getText());
                data.setText("");
            }
        });
        

	}
	
	public void run() throws IOException {
		socket = new Socket(loginDialog.serverIP, loginDialog.serverPort);
		
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        String line;
        line = in.readLine();
        System.out.println(line);
        line = in.readLine();
        System.out.println(line);
        line = in.readLine();
        System.out.println(line);
        while (in.ready()) 
        {
	        line = in.readLine();
	        System.out.println(line);
	        message.append(line);

        }
	}
}
