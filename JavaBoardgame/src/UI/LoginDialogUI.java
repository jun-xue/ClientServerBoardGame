package UI;

import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class LoginDialogUI extends JDialog
{
	private static final long serialVersionUID = 7203999506558674676L;
	
	private final JLabel 			serverIPLabel 	= new JLabel("Server IP: ");
    private final JLabel 			usernameLabel 	= new JLabel("Username : ");
    private final JLabel 			passwordLabel 	= new JLabel("Password : ");

    private final JTextField		serverIPText	= new JTextField(20); 
    private final JTextField 		usernameText 	= new JTextField(20);
    private final JPasswordField 	passwordText 	= new JPasswordField();

    private final JButton 			loginButton 	= new JButton("Login");
    private final JButton 			registerButton 	= new JButton("Register");

    private final JLabel 			statusLabel 	= new JLabel("God fuck me please");
    String serverIP;
    int serverPort;
    String username;
    String password;

    public LoginDialogUI(final JFrame parent, boolean modal) 
    {
        super(parent, modal);
        this.setSize(300, 200);
        this.setTitle("Welcome Challenger!");
        this.setLayout(null);

        serverIPLabel.setBounds(10, 10, 80, 25);
        this.add(serverIPLabel);
        serverIPText.setBounds(100, 10, 160, 25);
        this.add(serverIPText);
        
        usernameLabel.setBounds(10, 40, 80, 25);
        this.add(usernameLabel);
        usernameText.setBounds(100, 40, 160, 25);
        this.add(usernameText);
        
        passwordLabel.setBounds(10, 70, 80, 25);
        this.add(passwordLabel);
        passwordText.setBounds(100, 70, 160, 25);
        this.add(passwordText);
        
        statusLabel.setBounds(10, 100, 290, 25);
        this.add(statusLabel);
        
        loginButton.setBounds(10, 130, 80, 25);
        this.add(loginButton);
        registerButton.setBounds(100, 130, 160, 25);
        this.add(registerButton);
      
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {  
            @Override
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	// Checking to see if the socket exists
                try 
                {
                	statusLabel.setText("Connecting to the server!");
					boolean test = hostAvailabilityCheck(serverIPText.getText(), 42069);
					if (test == true)
					{
		                statusLabel.setText("Connected!");
		                serverIP = serverIPText.getText();
		                serverPort = 42069;
		                username = usernameText.getText();
		                password =  passwordText.getPassword().toString();
		                setVisible(false);
					}
					else
					{
	                	statusLabel.setText("Could not connect to the server!");
	                	return;
					}
				} finally
                {
					
				} 
                
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });
        setVisible(true);
    }
    
    public static boolean hostAvailabilityCheck(String SERVER_ADDRESS, int TCP_SERVER_PORT) { 
        try (Socket s = new Socket(SERVER_ADDRESS, TCP_SERVER_PORT)) {
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }
}
