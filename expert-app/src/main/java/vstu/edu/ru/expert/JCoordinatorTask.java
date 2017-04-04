package vstu.edu.ru.expert;

import java.io.File;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import saver.agents.DisperserInterface;
import saver.agents.SaverInterface;
import saver.common.FileInfo;
import saver.common.FileUtils;
import saver.common.JobType;
import saver.common.NodeInfo;
import saver.common.StorageInfo;
import saver.galoismath.GF8;
import saver.galoismath.GF8SimpleMatrix;
/**
 * @author  Saver
 */
public class JCoordinatorTask extends ThreadGroup
{
	public JCoordinatorTask(String name) 
	{
		super(name);
	}

	public JCoordinatorTask(ThreadGroup parent, String name) 
	{
		super(parent, name);
	}

	//private int threadsNum = 0;
	/**
	 * @uml.property  name="jobID"
	 */
	private byte jobID = 0;
	/**
	 * @return  the jobID
	 * @uml.property  name="jobID"
	 */
	public byte getJobID() 
	{
		return jobID;
	}

//	private String filename;
	/**
	 * @uml.property  name="beginTime"
	 */
	private long beginTime;
	private ArrayList<NodeInfo> dispersersList;
//	private int nodesNum;
//	private long minNodesNum;
	private File file;
	/**
	 * @uml.property  name="currStorageInfo"
	 * @uml.associationEnd  
	 */
	private StorageInfo currStorageInfo;
	/**
	 * @uml.property  name="currFileInfo"
	 * @uml.associationEnd  
	 */
	private FileInfo currFileInfo;
	public int finishState = 0;
	private int bufLength;
	
	
	/**
	 * @return  the beginTime
	 * @uml.property  name="beginTime"
	 */
	public long getBeginTime() 
	{
		return beginTime;
	}

	public byte getJobDone()
	{
		int sum = 0;
		Thread[] tl = new Thread[this.activeCount()];
		this.enumerate(tl);
		if(tl!=null)
			for(int i = 0; i<dispersersList.size(); i++)
			{
				if(i<this.activeCount())
				{
					sum+=((JCoordinatorThread)tl[i]).jobDone;
				}
				else
				{
					sum+=100;
				}
			}
		else
			return -100;
		return (byte) ((float)sum/dispersersList.size());
	}
		
	public JCoordinatorTask(String taskname, byte jobID, ArrayList<NodeInfo> currDispersers, ArrayList<NodeInfo> currSavers, File f, int nodesNum, int minNodesNum, int currStorageId) 
	{
		super(taskname);
		//this.filename = f.getName();
		this.dispersersList = currDispersers;
		this.jobID = jobID;
		this.beginTime = System.currentTimeMillis();
		this.file = f;
		//Инициализируем агентов-хранителей
		FileInfo currFileInfo = new FileInfo();
		currFileInfo.setFilename(f.getName());
		currFileInfo.setSize(f.length());
		currFileInfo.setDate(f.lastModified());
		SaverInterface[] remoteSavers = new SaverInterface[currSavers.size()];
		for(int i = 0; i<currSavers.size(); i++)
		{
			try 
			{
				remoteSavers[i] = (SaverInterface) LocateRegistry.getRegistry(currSavers.get(i).addr, 1099).lookup("saver");
				remoteSavers[i].startSavingProcess(currFileInfo, currStorageId);
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//	System.out.println("Task starting "+dispersersList.size());
		for(int i=0; i<dispersersList.size();i++)
		{			
			JCoordinatorThread t = new JCoordinatorThread(this, f.getName()+"_dispersion_"+i);
			t.setThreadID(i); 
			t.setJobId(jobID);
			t.setDisperser(dispersersList.get(i));
			t.setNodesNum(nodesNum);
			t.setMinNodesNum(minNodesNum);
			t.setDispersersNum(dispersersList.size());
			t.setCurrSavers(currSavers);
			t.start();
		}		
	}


	public JCoordinatorTask(String taskname, byte jobID,
			ArrayList<NodeInfo> currDispersers, ArrayList<StorageInfo> storagesInfo) 
	{
		super(taskname);
		this.beginTime = System.currentTimeMillis();
		//System.out.println("fileInfo Task init");
		this.dispersersList = currDispersers;
		this.jobID = jobID;
		this.beginTime = System.currentTimeMillis();
		for(int i=0; i<storagesInfo.size();i++) //для каждого хранилища выцепить список файлов
		{			
			StorageInfo currStorage = storagesInfo.get(i);
			JCoordinatorThread t = new JCoordinatorThread(this, currStorage.name+"_filelist");
			t.setStorageInfo(currStorage);
			t.setFilesInfo(currStorage.filesInfo);
			t.setJobId(jobID);
			t.setDispersers(currDispersers);
			t.setStorageId(i);
			t.start();
			//t.setFile(f);
			//t.setJobId(jobID);
			//t.setDisperser(dispersersList.get(i));
			//t.setNodesNum(nodesNum);
			//t.setMinNodesNum(minNodesNum);
			//t.setDispersersNum(dispersersList.size());
			//t.setCurrSavers(currSavers);
			//t.start();
		}	
		//this.nodesNum = nodesNum;
		//this.minNodesNum = minNodesNum;
		//storagesInfo.
		//filesInfo.
	}

	public JCoordinatorTask(String taskname, byte jobID, StorageInfo s) 
	{
		super(taskname);
		//initAgents();
	}

	public JCoordinatorTask(String taskname, byte jobID, FileInfo f,
			StorageInfo s) throws AccessException, RemoteException, NotBoundException 
	{
		super(taskname);
		this.beginTime = System.currentTimeMillis();
//		System.out.println("Task created...");
		initAgents(f,s);
//		System.out.println("Agents initialized...");
		this.jobID = jobID;
		String threadName = "_unknown_";
		switch(jobID)
		{
		case JobType.FILE_DISPERSION:
			threadName = f.getFilename()+"_dispersion_";
			break;
		case JobType.FILE_RECOVERY:
			threadName = f.getFilename()+"_recovery_";
			break;
		case JobType.SAVE_FILES_INFO:
			threadName = "_filelistsave_";
			break;
		}
		for(int i=0; i<dispersersList.size();i++)
		{			
			JCoordinatorThread t = new JCoordinatorThread(this, threadName+i);
			t.setFileInfo(f);
			t.setCurrStorageInfo(s);
			t.setThreadID(i); 
			t.setDisperser(dispersersList.get(i));
			t.setDispersersNum(dispersersList.size());
			t.setJobId(jobID);
			t.setBufLength(bufLength);
			t.start();
		}		
	}

	private void initAgents(FileInfo f, StorageInfo s) throws AccessException, RemoteException, NotBoundException 
	{
		GF8.generateGf8();
//		System.out.println("Field initialized...");
		GF8SimpleMatrix A = null;
		this.dispersersList = s.getAliveDispersers();
//		System.out.println(s.getAliveDispersers().size()+" alive dispersers found...");
		currStorageInfo = s;
		currFileInfo = f;
		try
		{
			int nodesNum = s.savers.size();
			int minNodesNum = s.minimalnodes;
			long tmpBufLength = f.getSize()/(nodesNum*minNodesNum); 
			int MAXBUF = 10000;
			//bufLength = 0;
			if(tmpBufLength>=MAXBUF)
			{
				bufLength = MAXBUF;
			}
			else
			{
				// TODO Оптимизировать алгоритм вычисления буфера
				bufLength = (int) (tmpBufLength+1);
			}
			A = new GF8SimpleMatrix(nodesNum, minNodesNum, bufLength);
			A.generateVandermond();
//			System.out.println("Matrix A ("+A.getColsNum()+"x"+A.getRowsNum()+"x"+A.getBufSize()+") generated...");
	//System.out.println("A: "+A.getColsNum()+"x"+A.getRowsNum()+"x"+A.getBufSize());
		}
		catch(Exception e)	
		{
		//	e.printStackTrace();
			bufLength = 0;
			A = null;
		}
		for(int i = 0; i<dispersersList.size(); i++)
		{
			DisperserInterface remoteDisperser = (DisperserInterface) LocateRegistry.getRegistry(dispersersList.get(i).addr, 1099).lookup("disperser");
//			System.out.println("Disperser"+i+" found: "+dispersersList.get(i).addr);
			remoteDisperser.initProcess(f, s, A);
//			System.out.println("Disperser"+i+" initialized...");
		}
//		System.out.println("Dispersers initialized...");
		for(int i = 0; i<s.savers.size(); i++)
		{
			SaverInterface remoteSaver = (SaverInterface) LocateRegistry.getRegistry(s.savers.get(i).addr, 1099).lookup("saver");
			remoteSaver.initProcess(f, s);
		}
//		System.out.println("Savers initialized...");
	}

	public void finishProcess() throws AccessException, RemoteException, NotBoundException 
	{

		for(int i = 0; i<dispersersList.size(); i++)
		{
			DisperserInterface remoteDisperser = (DisperserInterface) LocateRegistry.getRegistry(dispersersList.get(i).addr, 1099).lookup("disperser");
			remoteDisperser.finishProcess();
		}
		for(int i = 0; i<currStorageInfo.savers.size(); i++)
		{
			SaverInterface remoteSaver = (SaverInterface) LocateRegistry.getRegistry(currStorageInfo.savers.get(i).addr, 1099).lookup("saver");
			remoteSaver.finishProcess();
		}
		currStorageInfo = null;
	}
	
	/**
	 * @return the filename
	 */
	public String getFilename() 
	{
		return currFileInfo.getFilename();
	}	
	
	/**
	 * @return the filename
	 */
	public String getFilesize() 
	{
		return FileUtils.byteCountToDisplaySize(currFileInfo.getSize());
	}	

}
