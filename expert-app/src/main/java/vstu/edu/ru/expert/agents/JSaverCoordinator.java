package vstu.edu.ru.expert.agents;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import saver.agents.CoordinatorInterface;
import saver.agents.CustomerInterface;
import saver.agents.DisperserInterface;
import saver.agents.SaverInterface;
import saver.agents.SubordinatorInterface;
import saver.common.FileInfo;
import saver.common.JobType;
import saver.common.NodeInfo;
import saver.common.StorageInfo;
import saver.galoismath.GF8;
import vstu.edu.ru.expert.JCoordinatorTask;
import vstu.edu.ru.expert.xml.JSaverConfigParser;


/**
 * @author  Saver
 */
public class JSaverCoordinator implements CoordinatorInterface 
{
	public JSaverCoordinator() 
	{
		super();
		GF8.generateGf8();
		coordinatorTasks = new ArrayList<JCoordinatorTask>();
	}

	/**
	 * @uml.property  name="bufLength"
	 */
	private int bufLength;
	
	public ArrayList<JCoordinatorTask> coordinatorTasks;
	
	public long maxRefreshRate;
	public ArrayList<NodeInfo> nodesInfo;
	public ArrayList<StorageInfo> storagesInfo;
	/**
	 * @uml.property  name="subordinator"
	 * @uml.associationEnd  
	 */
	private SubordinatorInterface subordinator = null;
	/**
	 * @uml.property  name="customer"
	 * @uml.associationEnd  
	 */
	private CustomerInterface customer = null;
	private ArrayList<NodeInfo> currDispersers;
	/**
	 * @uml.property  name="currStorageInfo"
	 * @uml.associationEnd  
	 */
	private StorageInfo currStorageInfo;

	@Override
	public void bindCustomer(CustomerInterface newCust) 
	{
		customer = newCust;
	}

	@Override
	public void bindSubordinator(SubordinatorInterface newSub) 
	{
		subordinator = newSub;
	}
	
	@Override 
	public void disperseFile(FileInfo f, StorageInfo s) 
	{
		if(!s.containsFile(f))
		{
			String taskname = f.getFilename()+"_dispersion";
			JCoordinatorTask dt = null;
			try 
			{
				dt = new JCoordinatorTask(taskname,JobType.FILE_DISPERSION,f,s);
			} catch (AccessException e) 
			{
				e.printStackTrace();
				sendMsgToLog("Check java policy. Access exception.");
			//	dt.finishProcess();
			} catch (RemoteException e) 
			{
				sendMsgToLog("Remote exception. Please, retry.");
				e.printStackTrace();
			} catch (NotBoundException e) 
			{
				sendMsgToLog("Remote object not bound.");
				e.printStackTrace();
			}
			coordinatorTasks.add(dt);
			s.filesInfo.add(f);
			currStorageInfo = s;
		}
		else
		{
			customer.logMessage("\""+s.name+"\" already contains a file \""+f.getFilename()+"\"");
		}
}
	public void sendMsgToLog(String msg)
	{
		((JSaverCustomer) customer).sendMsgToLog(msg);
	}
	
	@Override
	public void recoverFile(FileInfo f, StorageInfo s) 
	{
		if(s.containsFile(f))
		{
			String taskname = f.getFilename()+"_recovery";
			JCoordinatorTask dt = null;
			try 
			{
				dt = new JCoordinatorTask(taskname,JobType.FILE_RECOVERY,f,s);	
			} catch (AccessException e) 
			{
				e.printStackTrace();
				sendMsgToLog("Check java policy. Access exception.");
			} catch (RemoteException e) 
			{
				sendMsgToLog("Remote exception. Please, retry.");
				e.printStackTrace();
			} catch (NotBoundException e) 
			{
				sendMsgToLog("Remote object not bound.");
				e.printStackTrace();
			}
			coordinatorTasks.add(dt);
		}
		else
		{
			sendMsgToLog("\""+s.name+"\" doesn't contain a file \""+f.getFilename()+"\"");
		}
		//s.filesInfo.add(f);
}
	@Override
	public void saveFilesInfo() 
	{
		JCoordinatorTask fl;
		try 
		{
			fl = new JCoordinatorTask("fileinfoupdater",JobType.SAVE_FILES_INFO, null,currStorageInfo);
			coordinatorTasks.add(fl);
		} catch (AccessException e) {
			
			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (NotBoundException e) {

			e.printStackTrace();
		}
	}

	public NodeInfo getNode(int i) 
	{
		for(int j = 0; j < nodesInfo.size(); j++)
		{
			if(((NodeInfo) nodesInfo.get(j)).id == i)
			{
				return nodesInfo.get(j);
			}
		}
		return null;
	}

	public String[] getStorageNames() 
	{
		String[] rez = new String[storagesInfo.size()];
		for(int i = 0; i<storagesInfo.size(); i++)
		{
			rez[i] = storagesInfo.get(i).name; 
		}
		return rez;
	}

	@Override
	public void initModel() 
	{
		nodesInfo = new ArrayList<NodeInfo>();
		storagesInfo = new ArrayList<StorageInfo>();
		JSaverConfigParser x = new JSaverConfigParser("xmlconfig.xml");
		try 
		{
			x.bindCoordinator(this);
			x.initScheme();
		} 
		catch (SAXException e) 
		{
		
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		for(int i = 0; i<storagesInfo.size();i++)
		{
			StorageInfo currStorage = storagesInfo.get(i);
			currStorage.filesInfo = new ArrayList<FileInfo>();
		}
	}
	
	public NodeInfo getNodeInfoById(int id)
	{
		for(int i = 0; i<nodesInfo.size(); i++)
		{
			NodeInfo currNodeInfo = nodesInfo.get(i);
			if(currNodeInfo.id == id)
			{
				return currNodeInfo;
			}
		}
		return null;
	}
	
	public JCoordinatorTask getTask(String name)
	{
		try {
			JCoordinatorTask rez = null;
			for(int i = 0; i<coordinatorTasks.size(); i++)
			{
				rez = coordinatorTasks.get(i); 
				if(rez.getName().equals(name))
				{
					return rez;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public StorageInfo getStorageById(int i) 
	{
		return storagesInfo.get(i);
	}

	@Override
	public ArrayList<FileInfo> getFileInfo(StorageInfo s) 
	{
//	System.out.println("Disperser get size");
		ArrayList<FileInfo> rez = new ArrayList <FileInfo> ();
		currDispersers = s.getAliveDispersers();
		DisperserInterface[] currRemoteDispersers = new DisperserInterface[currDispersers.size()];
		for(int i = 0; i<currDispersers.size(); i++)
		{
			try {
				currRemoteDispersers[i] = (DisperserInterface) LocateRegistry.getRegistry(currDispersers.get(i).addr, 1099).lookup("disperser");
				rez = currRemoteDispersers[i].getFilesInfo(s);
//	System.out.println("Disperser "+currDispersers.get(i).label+" fileinfo size: "+rez.size());
			} catch (AccessException e) {
			
				e.printStackTrace();
			} catch (RemoteException e) {
			
				e.printStackTrace();
			} catch (NotBoundException e) {
			
				e.printStackTrace();
			}
		}
		return rez;
	}

	@Override
	public void clearStorage(StorageInfo s) 
	{
		SaverInterface[] currRemoteSavers = new SaverInterface[s.savers.size()];
		for(int i = 0; i<s.savers.size(); i++)
		{
			try {
				currRemoteSavers[i] = (SaverInterface) LocateRegistry.getRegistry(s.savers.get(i).addr, 1099).lookup("saver");
				currRemoteSavers[i].clearStorage(s);
//	System.out.println("Disperser "+currDispersers.get(i).label+" fileinfo size: "+rez.size());
			} catch (AccessException e) {
			
				e.printStackTrace();
			} catch (RemoteException e) {
			
				e.printStackTrace();
			} catch (NotBoundException e) {
			
				e.printStackTrace();
			}
		}
		s.filesInfo = new ArrayList<FileInfo>();
		
	}

	@Override
	public void removeFile(FileInfo f, StorageInfo s) 
	{
		//ArrayList<FileInfo> rez = new ArrayList <FileInfo> ();
		//ArrayList<FileInfo> currSavers = s.savers;
		SaverInterface[] currRemoteSavers = new SaverInterface[s.savers.size()];
		for(int i = 0; i<s.savers.size(); i++)
		{
			try {
				currRemoteSavers[i] = (SaverInterface) LocateRegistry.getRegistry(s.savers.get(i).addr, 1099).lookup("saver");
				currRemoteSavers[i].removeFile(f, s);
//	System.out.println("Disperser "+currDispersers.get(i).label+" fileinfo size: "+rez.size());
			} catch (AccessException e) {
			
				e.printStackTrace();
			} catch (RemoteException e) {
			
				e.printStackTrace();
			} catch (NotBoundException e) {
			
				e.printStackTrace();
			}
		}
		int currIndex = -1;
		for(int i = 0; i<s.filesInfo.size(); i++)
		{
			if(s.filesInfo.get(i).getFilename().equals(f.getFilename()))
			{
				currIndex = i;
			}
		}
		if(currIndex>=0)
		s.filesInfo.remove(currIndex);
	}

	/**
	 * @param bufLength  the bufLength to set
	 * @uml.property  name="bufLength"
	 */
	public static void setBufLength(int bufLength) {
		bufLength = bufLength;
	}

	/**
	 * @return  the bufLength
	 * @uml.property  name="bufLength"
	 */
	public int getBufLength() {
		return bufLength;
	}

	/**
	 * @return  the subordinator
	 * @uml.property  name="subordinator"
	 */
	public SubordinatorInterface getSubordinator() {
		return subordinator;
	}
	
}