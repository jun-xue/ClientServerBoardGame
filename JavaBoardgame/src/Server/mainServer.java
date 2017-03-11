package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class mainServer	
{
	
	static ArrayList<Socket> connectedUsers = new ArrayList<Socket>();
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
	
	public static void main(String[] args) throws Exception
	{
		ServerSocket listener = new ServerSocket(42069);
		try 
		{
			while(true)
			{
				Socket temp = listener.accept();
				new Person(temp).start();
				connectedUsers.add(temp);
			}
		}
		finally
		{
			listener.close();
		}
	}
	
	private static class Person extends Thread 
	{
		private String username;
		private Socket socket;
		
		private BufferedReader in;
	    private PrintWriter out;
		
		public Person(Socket socket)
		{
			this.socket = socket;
			System.out.println("A challenger, " + username + " connected at " + socket);
		}
		
		public void run()
		{
			try
			{
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				writers.add(out);
				
				out.println("Welcome " + username + ", welcome to the chatroom!");
				
				while(true)
				{
					String input = in.readLine();
                    for (PrintWriter writer : writers) 
                    {
                        writer.println(socket + " > " + input);
                    }
				}
			}
			catch (IOException e)
			{
				System.out.println("Error Handling challenger " + username + ": " + e);
			}
			finally
			{
				try
				{
					socket.close();
				}
				catch (IOException e)
				{
					System.out.println("The socket seems to be pretty ducked...");
				}
			}
		}
	}
}

