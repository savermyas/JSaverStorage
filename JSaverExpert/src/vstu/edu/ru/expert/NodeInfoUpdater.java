package vstu.edu.ru.expert;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import saver.agents.DisperserInterface;
import saver.common.JobType;
import saver.common.NodeInfo;
import vstu.edu.ru.expert.agents.JSaverCoordinator;
import vstu.edu.ru.expert.agents.JSaverCustomer;

/**
 * @author  Saver
 */
public class NodeInfoUpdater extends Thread  
{ 
	public NodeInfoUpdater(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}
	public NodeInfoUpdater(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}
	public NodeInfoUpdater(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public NodeInfoUpdater(ThreadGroup group, Runnable target, String name,
			long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}
	public NodeInfoUpdater(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}
	public NodeInfoUpdater(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}
	public NodeInfoUpdater(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	private JSaverCustomer c;
	private byte jobId = JobType.NO_JOB;
	/**
	 * @uml.property  name="threadID"
	 */
	private int threadID; 
	byte jobDone = 0;
	
	public void bindCustomer(JSaverCustomer c)
	{
		this.c = c;
	}
	
	/**
	 * @return  the threadID
	 * @uml.property  name="threadID"
	 */
	public int getThreadID() {
		return threadID;
	}
	/**
	 * @param threadID  the threadID to set
	 * @uml.property  name="threadID"
	 */
	public void setThreadID(int threadID) {
		this.threadID = threadID;
	}

	public NodeInfoUpdater() 
	{
	}
	
	public NodeInfoUpdater(JSaverCustomer coord) 
	{
		this.c = c;
	}

	public void run() 
	{
		//jobDone = 0;
		while(!Thread.currentThread().isInterrupted())
		{
			long currTime = System.currentTimeMillis();
					try 
					{		
					/*	JStorageInterface remoteHost = (JStorageInterface) LocateRegistry.getRegistry(((NodeInfo) c.nodesInfo.get(threadID)).addr, 1099).lookup("disperser");						
						byte[] testData = new byte[100];
						testData = remoteHost.testConnection(testData); 
						currTime = System.currentTimeMillis()-currTime;*/
						
						DisperserInterface remoteHost = (DisperserInterface) LocateRegistry.getRegistry(((NodeInfo) c.getNodeInfo(threadID)).addr, 1099).lookup("disperser");						
						byte[] testData = new byte[100];
						testData = remoteHost.testConnection(testData); 
						currTime = System.currentTimeMillis()-currTime;
						
						//JSaverClientModel.refreshFilesInfo(); 
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						//System.out.println("REMOTE");
						currTime = -600;
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.out.println("NOT BOUND");
					} 
					if(((NodeInfo) c.getNodeInfo(threadID)).timeout<0)
					{
						((NodeInfo) c.getNodeInfo(threadID)).timeout = currTime;
						//Тут бы событие послать нужно клиенту
						//Coordinator.changeNodeState(threadID, currTime);					
					}
					else
					{
						((NodeInfo) c.getNodeInfo(threadID)).timeout = currTime;
					}
					
					try 
					{
						Thread.sleep((long) (Math.random()*c.getRefreshRate()));
					} 
					catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
					//	e.printStackTrace();
						this.interrupt();
					}
				//break;
				
		}
	}
	
}

//GET /pwfor4/ HTTP/1.0
//Host: hax.tor.hu

