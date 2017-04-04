package saver.common;

import java.io.Serializable;

public class NodeInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5615982437961993559L;
	public String addr;
	public String label;
	public long timeout;
	public boolean disperser = false;
	public boolean saver = false;
	public int id;
	
	public String toString()
	{
		return label;
	}

	public NodeInfo clone()
	{
		NodeInfo rez = new NodeInfo();
		rez.addr = new String(addr);
		rez.label = new String(label);
		rez.id = id;
		rez.timeout = timeout;
		return rez;
	}
	
	public String getInfo() 
	{
		String rez = "<HTML>Address: "+addr;
		rez = rez + "<BR>";
		String status = "offline";
		if(this.timeout>=0)
			status = "online";
		rez = rez + "Status: "+status;
		rez = rez+"</HTML>";
		return rez;
	}
}
