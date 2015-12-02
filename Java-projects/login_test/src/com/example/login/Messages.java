package com.example.login;

public class Messages
{
	private String id;
	private String fromuid;
	private String touid;
	private String sentdt;
	private String  readstatus;
	private String messagetext;
	private String  subject;
	public String getId()
	{
		return id;
	}
	public void setFromuid(String fromuid)
	{
		this.fromuid = fromuid;
	}
	
	public void settouid(String touid)
	{
		this.touid = touid;
	}
	public void setSentdt(String sentdt)
	{
		this.sentdt = sentdt;
	}
	public void setId(String readstatus)
	{
		this.readstatus = readstatus;
	}
	public void setMessagetext(String message)
	{
		this.messagetext = message;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	public String getfromuid()
	{
		return this.fromuid;
	}
	public String gettouid()
	{
		return this.touid;
	}
	public String getsentdt()
	{
		return this.sentdt;
	}
	public String getreadstatus()
	{
		return this.readstatus;
	}
	public String getmessageText()
	{
		return this.messagetext;
	}
	public String getsubject()
	{
		return this.subject;
	}
	
}
