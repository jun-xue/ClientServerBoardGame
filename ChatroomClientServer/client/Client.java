package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	ClientConnection cc;

	public static void main(String[] args){
		
		new Client();
	}
	
	
	public Client(){
		
		try {
			Socket s = new Socket("localhost", 2345);
			cc = new ClientConnection(s, this);
			cc.start();
			
			
			listenForInput();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	public void listenForInput(){
		
		Scanner console = new Scanner(System.in);
		
		//wait until line from console
		while(true){
			while(!console.hasNextLine()){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
			
			
			
			//write that out to server
			String input = console.nextLine();
			
			
			//if user types "quit" or "QUIT" program will break
			if (input.toLowerCase().equals("quit")){
				break;
			}
			cc.sendStringToServer(input);
		}
		cc.close();
		
	}
	
}






