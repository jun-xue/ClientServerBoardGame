package UI;

import java.awt.List;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import Server.Player;
import Server.ServerObject;

public class LoginDialogUI extends JDialog
{
	private static final long serialVersionUID = 7203999506558674676L;
	
	private final JLabel 			serverIPLabel 	= new JLabel("Server IP: ");

    private final JTextField		serverIPText	= new JTextField(20); 

    private final JButton 			loginButton 	= new JButton("Login");

    private final JLabel 			statusLabel 	= new JLabel("");
    public Socket s;
	ObjectOutputStream oos;
	ObjectInputStream ois;
    public String serverIP;
    public String username;
    public String password;
    Player account;

    public LoginDialogUI(final JFrame parent, boolean modal) 
    {
        super(parent, modal);
        this.setSize(300, 120);
        this.setTitle("Welcome Challenger!");
        this.setLayout(null);

        serverIPLabel.setBounds(10, 10, 80, 25);
        this.add(serverIPLabel);
        serverIPText.setBounds(100, 10, 160, 25);
        this.add(serverIPText);
        
        statusLabel.setBounds(10, 35, 290, 25);
        this.add(statusLabel);
        
        loginButton.setBounds(10, 55, 260, 25);
        this.add(loginButton);

      
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {  
            @Override
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });

        loginButton.addActionListener(new ActionListener() {
            @SuppressWarnings("unused")
			@Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                	statusLabel.setText("Connecting to the server!");
					s = new Socket(serverIPText.getText(), 42069);
		            statusLabel.setText("Connected!");
				}
                catch (UnknownHostException e1) 
                {
                	statusLabel.setText("Server does not exist!");
					return;
				} 
                catch (IOException e1) 
                {
                	statusLabel.setText("Server does not exist!");
					return;
				}
	            setVisible(false);
            }
        });
        
        setVisible(true);
    }
}
