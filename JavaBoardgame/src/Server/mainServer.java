package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class mainServer	
{
	public static void main(String[] args) throws Exception
	{
		ServerSocket listener = new ServerSocket(42069);
		int clientNumber = 0;
		try 
		{
			while(true)
			{
				new Tester(listener.accept(), clientNumber++).start();
			}
		}
		finally
		{
			listener.close();
		}
	}
	
	private static class Tester extends Thread 
	{
		private Socket socket;
		private int clientNumber;
		
		public Tester(Socket socket, int clientNumber)
		{
			this.socket = socket;
			this.clientNumber = clientNumber;
			System.out.println("A challenger, " + clientNumber + " connected at " + socket);
		}
		
		public void run()
		{
			try
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				
				out.println("Welcome challenger, you are client #" + clientNumber);
				out.println("Enter a line with only a period to quit\n");
				
				
				while(true)
				{
					String input = in.readLine();
					if (input == null || input.equals("."))
					{
						break;
					}
					else
					{
						out.println(input.toUpperCase());
					}
				}
			}
			catch (IOException e)
			{
				System.out.println("Error Handling challenger #" + clientNumber + ": " + e);
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

