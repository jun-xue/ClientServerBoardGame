package Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class mainClient 
{
	BufferedReader in;
	PrintWriter out;
	JFrame frame = new JFrame("Client");
	JTextField data = new JTextField(40);
	JTextArea message = new JTextArea(8, 60);
	
	public mainClient()
	{
		// Layout GUI
        message.setEditable(false);
        frame.getContentPane().add(data, "North");
        frame.getContentPane().add(new JScrollPane(message), "Center");

        // Add Listeners
        data.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(data.getText());
                   String response;
                try {
                    response = in.readLine();
                    if (response == null || response.equals("")) {
                          System.exit(0);
                      }
                } catch (IOException ex) {
                       response = "Error: " + ex;
                }
                message.append(response + "\n");
                data.selectAll();
            }
        });
	}
	
    public void connectToServer() throws IOException {

        String serverAddress = JOptionPane.showInputDialog(frame, "Enter address of the server:", "Welcome Challenger!", JOptionPane.QUESTION_MESSAGE);
        Socket socket = new Socket(serverAddress, 42069);
        
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        
        for (int i = 0; i < 3; i++) {
            message.append(in.readLine() + "\n");
        }
    }
	public static void main(String[] args) throws Exception
	{
		mainClient client = new mainClient();
	    client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client.frame.pack();
	    client.frame.setVisible(true);
	    client.connectToServer();
	}
	
}