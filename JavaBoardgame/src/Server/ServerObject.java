package Server;

import java.io.Serializable;

public class ServerObject implements Serializable
{
	private static final long serialVersionUID = -6843436851432057223L;
	
	private Object message;

	public ServerObject(Object inMessage)
	{
		message = inMessage;
	}
	
	public Object getMessage()
	{
		return message;
	}
}
