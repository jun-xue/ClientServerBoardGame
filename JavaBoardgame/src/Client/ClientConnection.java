package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import UI.LoginDialogUI;

public class ClientConnection extends Thread {

	Socket s;
	String u;
	DataInputStream din;
	DataOutputStream dout;
	boolean shouldRun = true;

	JFrame frame = new JFrame("Client");
	
	JTextArea message = new JTextArea(8, 120);
	JTextField data = new JTextField(40);

	public ClientConnection(Socket socket, Client client, String username, String password)
	{
		s = socket;
		u = username;
		
        frame.getContentPane().add(data, "South");
        frame.getContentPane().add(message, "North");
        
        frame.setTitle("Challenger Client");
        frame.setSize(800, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.pack();
	    frame.setVisible(true);
	    
        data.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                sendStringToServer(data.getText());
                data.setText("");
            }
        });
	}



	public void sendStringToServer(String text){
		try {
			dout.writeUTF(text);
			dout.flush();
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
		
		
	}





	public void run(){
		//text input/output communication streams
		try {
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			while(shouldRun){
				try {
					while(din.available() == 0){
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					//finally when something does show up, prints to console
					String reply = din.readUTF();
					message.append(u + " > " + reply + "\n");
				} catch (IOException e) {
					e.printStackTrace();
					close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
		
		
	}
	
	public void close(){
		//close all the things
		try {
			din.close();
			dout.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
