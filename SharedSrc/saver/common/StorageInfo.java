package saver.common;

import java.io.Serializable;
import java.util.ArrayList;

public class StorageInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1020817792688356620L;
	public ArrayList<NodeInfo> dispersers;
	public ArrayList<NodeInfo> savers;
	public ArrayList<FileInfo> filesInfo;
	public String name;
	public int minimalnodes;
	public int totalnodes;
	public int id;
	public String toString()
	{
		return name;
	}
	public String getInfo()
	{
		String rez = "<HTML>Storage name: "+name+"<BR>";
		rez = rez+"Savers count: "+ savers.size()+"<BR>";
		rez = rez+"&nbsp;&nbsp;&nbsp;&nbsp;Savers alive: "+ getAliveSavers().size()+"<BR>";
		rez = rez+"Dispersers count: "+ dispersers.size()+"<BR>";
		rez = rez+"&nbsp;&nbsp;&nbsp;&nbsp;Dispersers alive: "+ getAliveDispersers().size()+"<BR>";
		rez = rez+"Min. available savers required: "+ this.minimalnodes+"</HTML>";
		//System.out.println(rez);
		return rez;
	}
	public boolean containsFile(FileInfo f)
	{
		for(int i = 0; i<filesInfo.size(); i++)
		{
			if(filesInfo.get(i).getFilename().equals(f.getFilename()))
				return true;
		}
		return false;
	}
	public boolean storageIsOn() 
	{
		boolean rez = true;
		rez = rez && (getAliveDispersers().size()>0);
		rez = rez && (getAliveSavers().size()>0);
		return rez;
	}
	public ArrayList<NodeInfo> getAliveDispersers() 
	{
		ArrayList<NodeInfo> rez = new ArrayList<NodeInfo>();
		for(int i = 0; i<dispersers.size();i++)
		{
			NodeInfo currNode = dispersers.get(i); 
			if(currNode.timeout>=0)
				rez.add(currNode);
		}
		return rez;
	}
	public ArrayList<NodeInfo> getAliveSavers() 
	{
		ArrayList<NodeInfo> rez = new ArrayList<NodeInfo>();
		for(int i = 0; i<savers.size();i++)
		{
			NodeInfo currNode = savers.get(i); 
			if(currNode.timeout>=0)
				rez.add(currNode);
		}
		return rez;
	}
	public ArrayList<NodeInfo> getFastestSavers() //не работает пока что должным образом
	{
		ArrayList<NodeInfo> rez = new ArrayList<NodeInfo>();
		for(int i = 0; i<this.minimalnodes;i++)
		{
			NodeInfo currNode = savers.get(i).clone(); 
			if(currNode.timeout>=0)
			{
				currNode.id = i;
				rez.add(currNode);
			}
				
		}
		return rez;
	}
	public String[] getFileList() 
	{
		String[] rez = new String[this.filesInfo.size()];
		for(int i = 0; i<this.filesInfo.size();i++)
		{
			rez[i] = filesInfo.get(i).toString();
		//	System.out.println(rez[i]);
		}
		// TODO Auto-generated method stub
		return rez;
	}
	public boolean canDisperse() {
		return (this.getAliveSavers().size()==this.savers.size());
	}
	
}
