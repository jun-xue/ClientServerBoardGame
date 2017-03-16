package Server;

import java.io.Serializable;

public class ServerObject implements Serializable
{
	private static final long serialVersionUID = -6843436851432057223L;
	
	private String header;
	private Object payload;
	private String sender;
	private String receiver;

	public ServerObject(String header, String sender, Object payload)
	{
		this.header = header;
		this.payload = payload;
		this.sender = sender;
	}
	public ServerObject(String header, String sender, String receiver, Object payload)
	{
		this.header = header;
		this.payload = payload;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public Object getPayload()
	{
		return payload;
	}
	
	public String getHeader()
	{
		return header;
	}
	
	public String getSender()
	{
		return sender;
	}
	
	public String getReceiver()
	{
		return receiver;
	}
}
