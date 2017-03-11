package Server;

public class Player 
{
	// Variables //
	
	public String 	username;
	public String 	password;
	public int 		wins		= 0;
	public int 		loses		= 0;
	
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
}
