package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

public class Player implements Serializable, Cloneable
{
	// Variables //
	
	public String 	username;
	public String 	password;
	public int 		wins		= 0;
	public int 		loses		= 0;
	
	private static Hashtable<String, Player> accounts = new Hashtable<String, Player>();
	
	// Constructors //
	
	public Player(String username, String password, int wins, int loses)
	{
		this.username 	= username;
		this.password 	= password;
		this.wins 		= wins;
		this.loses 		= loses;
	}
	
	public Player(String username, String password)
	{
		this.username 	= username;
		this.password 	= password;
	}
	
	public Player(Player toCopy)
	{
		this.username 	= toCopy.getUsername();
		this.password 	= toCopy.getPassword();
		this.wins 		= toCopy.getWins();
		this.loses 		= toCopy.getLoses();
	}
	
	// Getters //
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public int getWins()
	{
		return wins;
	}
	
	public int getLoses()
	{
		return loses;
	}
	
	// Setters //
	
	public void setUsername(String newUsername)
	{
		this.username = newUsername;
	}
	
	public void setPassword(String newPassword)
	{
		this.password = newPassword;
	}
	
	public void incrementWins()
	{
		this.wins++;
	}
	
	public void incrementLoses()
	{
		this.loses++;
	}
	
	// Actions upon the Accounts hashtable //
	
	public static void putNewAccount(Player newPlayer)
	{
		accounts.put(newPlayer.username, newPlayer);
	}
	
	public static boolean checkForAccount(Player player)
	{
		return (accounts.containsKey(player.username));
	}
	
	public static Player getAccount(String username)
	{
		return accounts.get(username);
	}
	
	public static boolean checkPassword(Player player)
	{
		Player p = accounts.get(player.getUsername());
		if (p == null)
		{
			return false;
		}
		return true;
	}
	
	public static void getAccounts()
	{
		File accountFile = new File("acc.obj");
		if(accountFile.exists())
		{
			FileInputStream in;
			try
			{
				in = new FileInputStream(accountFile);
				ObjectInputStream inStream = new ObjectInputStream(in);
				accounts = (Hashtable)inStream.readObject();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}			
		}
	}
	
	public static void saveAccounts()
	{
		FileOutputStream out;
		try
		{
			out = new FileOutputStream("acc.obj");
			ObjectOutputStream outStream = new ObjectOutputStream(out);
			outStream.writeObject(accounts);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// Clonable //
	
	public Player clone()
	{
		Player clone = new Player("", "");
		clone.username = this.username;
		clone.password = this.password;
		
		return clone;
	}
}
