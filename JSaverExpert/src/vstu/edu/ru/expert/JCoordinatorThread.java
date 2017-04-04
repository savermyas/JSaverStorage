package vstu.edu.ru.expert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Arrays;

import saver.agents.DisperserInterface;
import saver.common.FileInfo;
import saver.common.FileSortTypes;
import saver.common.JobType;
import saver.common.NodeInfo;
import saver.common.StorageInfo;
import saver.galoismath.GF8SimpleMatrix;

/**
 * @author  Saver
 */
public class JCoordinatorThread extends Thread  
{ 
	public static synchronized byte[] readBytes(File currFile, long curr_pos, int bufLength) 
	{	
		byte[] buf = new byte[bufLength];
		int bytesRead = 0;
		try 
		{
			RandomAccessFile from = new RandomAccessFile(currFile, "r");
			from.seek(curr_pos);
			bytesRead = from.read(buf);
			if(bytesRead>0)
			{
				if(bytesRead<buf.length)
					buf = java.util.Arrays.copyOf(buf, bytesRead);
			}
			else
			{
				buf = null;
			}
			from.close();
		} 
		catch (FileNotFoundException e) 
		{
			buf = null;
			e.printStackTrace();
		} 
		catch (IOException e) {
			buf = null;
			e.printStackTrace();
		}
		return buf;
	}
	public static synchronized void writeBytes(String string, long curr_pos, byte[] buf, int len) 
	{	
		try 
		{
			RandomAccessFile to = new RandomAccessFile(string, "rwd");
			to.seek(curr_pos);
			byte[] buf1 = new byte[len];
			for(int i = 0; i<len; i++)
				buf1[i] = buf[i];
			to.write(buf1, 0, len);
			//to.write(buf);
			to.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*private static synchronized void decJobDone() 
	{
		jobDone--;		
	}*/
	public static synchronized void writeBytes(String string, byte[] buf, long curr_pos) 
	{
		try 
		{
			RandomAccessFile to = new RandomAccessFile(string, "rwd");
			to.seek(curr_pos);
			to.write(buf);
			to.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//private String filename;
	byte jobDone = 0;
//	private File currFile;
	/**
	 * @uml.property  name="jobId"
	 */
	private byte jobId = JobType.NO_JOB;
	/**
	 * @uml.property  name="threadID"
	 */
	private int threadID;
	/**
	 * @uml.property  name="currDisperser"
	 * @uml.associationEnd  
	 */
	private NodeInfo currDisperser;
	private int nodesNum;
	//private long dispersersNum;
	private int minNodesNum;
	private ArrayList<NodeInfo> currSavers;
	private long dispersersNum;
	/**
	 * @uml.property  name="filesInfo"
	 */
	private ArrayList<FileInfo> filesInfo;
	/**
	 * @uml.property  name="currStorageInfo"
	 * @uml.associationEnd  
	 */
	private StorageInfo currStorageInfo;
	private ArrayList<NodeInfo> currDispersers;
	private int storageId;
	/**
	 * @uml.property  name="currFileInfo"
	 * @uml.associationEnd  
	 */
	private FileInfo currFileInfo;
	private int bufLength;


	/**
	 * @param storageId  the storageId to set
	 * @uml.property  name="storageId"
	 */
	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}
	/**
	 * @param dispersersNum  the dispersersNum to set
	 * @uml.property  name="dispersersNum"
	 */
	public void setDispersersNum(long dispersersNum) {
		this.dispersersNum = dispersersNum;
	}
	/**
	 * @param currSavers  the currSavers to set
	 * @uml.property  name="currSavers"
	 */
	public void setCurrSavers(ArrayList<NodeInfo> currSavers) 
	{
		this.currSavers = currSavers;
	}
	/**
	 * @param nodesNum  the nodesNum to set
	 * @uml.property  name="nodesNum"
	 */
	public void setNodesNum(int nodesNum) {
		this.nodesNum = nodesNum;
	}
	/**
	 * @param minNodesNum  the minNodesNum to set
	 * @uml.property  name="minNodesNum"
	 */
	public void setMinNodesNum(int minNodesNum) {
		this.minNodesNum = minNodesNum;
	}
	/*public static synchronized long getJobBeginTime() {
		return jobBeginTime;
	}
	public static synchronized void setJobBeginTime(long jobBeginTime) {
		JExpertDataThread.jobBeginTime = jobBeginTime;
	}
	/*public static synchronized int getJobDone() {
		return jobDone;
	}
	public static synchronized void setJobDone(int job_done) {
		JExpertDataThread.jobDone = job_done;
	}*/
	public JCoordinatorThread() 
	{
	}
	public JCoordinatorThread(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}
	public JCoordinatorThread(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	} 
	public JCoordinatorThread(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	
	public JCoordinatorThread(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}
	public JCoordinatorThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}
	public JCoordinatorThread(ThreadGroup group, Runnable target, String name,
			long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}
	public JCoordinatorThread(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	private void changeProgress(byte jobDone2) 
	{
		jobDone = jobDone2;
	}
	//public String getFilename() {
	//	return filename;
	//}
	/**
	 * @return  the jobId
	 * @uml.property  name="jobId"
	 */
	public byte getJobId() {
		return jobId;
	}
	/**
	 * @return  the threadID
	 * @uml.property  name="threadID"
	 */
	public int getThreadID() {
		return threadID;
	}
	public void run() 
	{
		jobDone = 0;
		
		int nodesNum = currStorageInfo.savers.size();
		int minNodesNum = currStorageInfo.minimalnodes;
		
		while(!Thread.currentThread().isInterrupted())
		{
			//System.out.println(this.getName()+" "+jobId);
			switch(jobId)
			{
			case JobType.FILE_DISPERSION:
			/*	NodeInfo currDisperser = null;
				for(int j = 0; j<currStorageInfo.dispersers.size(); j++)
				{
					if(currStorageInfo.dispersers.get(j).id==threadID)
					currDisperser = currStorageInfo.dispersers.get(j);
				}*/
				DisperserInterface remoteDisperser = null;
				try 
				{
					remoteDisperser = (DisperserInterface) LocateRegistry.getRegistry(currDisperser.addr, 1099).lookup("disperser");
				} catch (AccessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (NotBoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				GF8SimpleMatrix M = new GF8SimpleMatrix(minNodesNum,nodesNum,bufLength);
	//System.out.println(minNodesNum+" "+nodesNum+" "+bufLength);			
				long currPart;
				byte[] buf; 
				
				currPart = this.getThreadID()*M.getData().length;
				buf = new byte[M.getData().length];
				//java.util.Arrays.fill(buf, 0, buf.length, 333);
				long currSlice = this.getThreadID(); 
	
				File currFile = new File(currFileInfo.getAbsolutepath());
	//System.out.println(currFile.getAbsolutePath());
				int mdataLength = minNodesNum*nodesNum*bufLength;
	//			System.out.println("Mr: "+M.getRow(0).length+" Mc: "+M.getCol(0).length);
	//System.out.println(mdataLength);
	//System.out.println(currSlice+" "+currPart+currFile.length());
				while(currPart<=currFile.length()&&!Thread.currentThread().isInterrupted())
				{		
					M.data = readBytes(currFile, currPart, mdataLength);
	//System.out.println("read "+M.getData().length+" bytes");
					if(M.data!=null)
					{
						if(M.data.length<mdataLength)
						{
	//						System.out.print("fixed");
							M.fixDataLength(mdataLength);
						}
						//M.SaveToFile("M.txt");
						try 
						{
							remoteDisperser.disperseFragment(M, currSlice);
	//System.out.println("sent "+currSlice+" "+currPart);
						} catch (RemoteException e) 
						{
							// TODO Auto-generated catch block
							//e.printStackTrace();
							changeProgress((byte) -100);
							((JCoordinatorTask) this.getThreadGroup()).finishState = -100;
							Thread.currentThread().interrupt();	
						}
						currSlice+=dispersersNum;
						currPart+=(dispersersNum*mdataLength);	// [......][......][......]
						changeProgress((byte) (((double)currPart/currFile.length())*100));
	//System.out.println(currFile.getName()+" "+currPart);
						System.gc();
					}
					else
					{
						changeProgress((byte) (100));
					}
				}
				//System.out.println("Dispersion complete");
				Thread.currentThread().interrupt();					
			break;
			case JobType.GET_FILES_INFO:
		/*		DisperserInterface[] currRemoteDispersers = new DisperserInterface[currDispersers.size()];
				for(int i = 0; i<currDispersers.size(); i++)
				{
					try {
						currRemoteDispersers[i] = (DisperserInterface) LocateRegistry.getRegistry(currDispersers.get(i).addr, 1099).lookup("disperser");
						this.filesInfo = currRemoteDispersers[i].getFilesInfo();
					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				this.jobId = JobType.NO_JOB; 
				Thread.currentThread().interrupt();		*/
			case JobType.SAVE_FILES_INFO:
				//System.out.println("Starting File Info Saving");
				try 
				{
					remoteDisperser = (DisperserInterface) LocateRegistry.getRegistry(currDisperser.addr, 1099).lookup("disperser");
					remoteDisperser.saveFilesInfo(currStorageInfo);
				} catch (AccessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (NotBoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				Thread.currentThread().interrupt();	
				
			break;
				case JobType.SET_FILE_ATTRIBUTES:
					
	/*				File currFile;
					try 
					{
						JStorageInterface remoteHost1 = (JStorageInterface) LocateRegistry.getRegistry(Coordinator.getProp("node"+threadID), 1099).lookup("test");
			//			currFile = new File(filename); 
						remoteHost1.setFileSize(currFile.getName(), currFile.length());
					
					} catch (AccessException e1) { 
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
						//remoteHost.setFileSort(FileSortTypes.BY_NAME);
						//String currFileList[] = remoteHost.getFileList();   
						//JSaverClientModel.compareFileData(currFileList);
					Thread.currentThread().interrupt();		
			*/	break;
				case JobType.RW_TEST:	
		/*			curr_pos = this.getThreadID()*bufLength;
					buf = new byte[bufLength];
					while(buf!=null)
					{							
	//						buf = readBytes(currFile, curr_pos, bufLength);
							if(buf!=null)
							{
	//							writeBytes("rez"+currFile.getName(),buf,curr_pos);
								curr_pos = curr_pos+(nodesNum*bufLength);									
							}
					}		
			//		JExpertDataThread.decJobDone();
					Thread.currentThread().interrupt();*/
				 
				break;
				case JobType.FILE_RECOVERY:
					remoteDisperser = null;
					try 
					{
						remoteDisperser = (DisperserInterface) LocateRegistry.getRegistry(currDisperser.addr, 1099).lookup("disperser");
					} catch (AccessException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (NotBoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
	//				GF8SimpleMatrix A1 = new GF8SimpleMatrix(minNodesNum, minNodesNum, bufLength);
					int matrixSize = nodesNum*minNodesNum*bufLength;
					int slicesNum = (int) (currFileInfo.getSize()/matrixSize);
					int lastSliceSize = (int) (currFileInfo.getSize()-(slicesNum*matrixSize));
					currSlice = getThreadID();
				try {
					//System.out.println(currSlice);			
					while(currSlice<slicesNum)
					{
						//System.out.println(currSlice+" of "+slicesNum+" "+lastSliceSize+" "+matrixSize+" "+bufLength);
						GF8SimpleMatrix rezM = remoteDisperser.getFragment(currSlice);
						writeBytes("."+File.separator+"restored"+File.separator+currFileInfo.getFilename(), rezM.getData() ,currSlice*matrixSize);
						currSlice+=dispersersNum;
						changeProgress((byte) ((float)currSlice*100/slicesNum));
					}
					if(currSlice==slicesNum)
					{
						//System.out.println(currSlice+" of "+slicesNum+" "+lastSliceSize+" "+matrixSize+" "+bufLength);
						//System.out.println(currSlice+" of "+slicesNum+" "+lastSliceSize);
						GF8SimpleMatrix rezM = remoteDisperser.getFragment(currSlice);
						byte[] lastPart = rezM.getData();//Arrays.copyOf(rezM.getData(), lastSliceSize); //последний кусо
						//System.out.println(lastPart.length);
						writeBytes("."+File.separator+"restored"+File.separator+currFileInfo.getFilename(), currSlice*matrixSize, lastPart, lastSliceSize);
						//System.out.println();
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					changeProgress((byte) 100);
	//	System.out.println(slicesNum+" "+lastSliceSize);			
		//			currPart;
		//			byte[] buf; 
					
			
										
	//				M = new GF8SimpleMatrix(Coordinator.minNodesNum,Coordinator.nodesNum, bufLength);
	//				A = new GF8SimpleMatrix(Coordinator.nodesNum, Coordinator.minNodesNum, bufLength);
	//				A.generateVandermond();

	//				GF8SimpleMatrix A1 = new GF8SimpleMatrix(Coordinator.minNodesNum,Coordinator.minNodesNum, bufLength);

	/*				int[] currNodes = Coordinator.getOptimalNodes();
					for(int h = 0; h<Coordinator.minNodesNum; h++)
					{
						A1.setRow(h, A.getRow(currNodes[h])); //!!
					}
					A1 = A1.obr();
					
					GF8SimpleMatrix zzz = M.mult1(A1);
					
					int currSlice = this.getThreadID();//*M.getData().length; RandomAccessFile to = new RandomAccessFile("."+File.separator+"restored"+File.separator+filename, "rwd");
					buf = new byte[M.getData().length];
					int slicesNum = (int) (fileSize/zzz.getData().length);
					int lastSliceSize = (int) (fileSize-(slicesNum*zzz.getData().length));
					
				try 
				{
					
					for (int f = 0; f<Coordinator.minNodesNum; f++)
					{
						String addr=Coordinator.getProp("node"+currNodes[f]); 
						remoteHosts[f] = (JStorageInterface) LocateRegistry.getRegistry(addr, 1099).lookup("test"); //получили узлы, с которыми будем работать
					}
					 
					while(currSlice<slicesNum)
					{
						for (int f = 0; f<Coordinator.minNodesNum; f++)
						{
							buf = remoteHosts[f].getFragment(filename, currSlice, currNodes[f]);
							M.setRow(f, buf); 
						}
						GF8SimpleMatrix rezM = M.mult1(A1);
						writeBytes("."+File.separator+"restored"+File.separator+filename, rezM.getData() ,currSlice*M.getData().length);
						currSlice+=Coordinator.threadsNum;
						changeProgress((byte) ((float)currSlice/slicesNum));
					}
					if(currSlice==(slicesNum))
					{
						for (int f = 0; f<Coordinator.minNodesNum; f++)
						{
							M.setRow(f, remoteHosts[f].getFragment(filename, currSlice, currNodes[f]));
						}
						GF8SimpleMatrix rezM = M.mult1(A1); 
						byte[] lastPart = Arrays.copyOf(rezM.getData(), lastSliceSize); //последний кусо
						writeBytes("."+File.separator+"restored"+File.separator+filename, lastPart ,currSlice*M.getData().length);
					}
					changeProgress((byte) 100);
				} catch (AccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			*/	Thread.currentThread().interrupt();		
				break;
				case JobType.NODE_TEST:
	/*				long currTime = System.currentTimeMillis();
					try 
					{						
						JStorageInterface remoteHost = (JStorageInterface) LocateRegistry.getRegistry(Coordinator.getProp("node"+threadID), 1099).lookup("test");						
						byte[] testData = new byte[100];
						testData = remoteHost.testConnection(testData); 
						currTime = System.currentTimeMillis()-currTime;
						//JSaverClientModel.refreshFilesInfo(); 
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//System.out.println("REMOTE");
						currTime = -600;
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//System.out.println("NOT BOUND");
					} 
					if(currTime*Coordinator.getNodeRating(threadID)<0)
					{
						Coordinator.setNodeTimeout(threadID, currTime);
						Coordinator.changeNodeState(threadID, currTime);					
					}
					else
					{
						Coordinator.setNodeTimeout(threadID, currTime);
					}
					
					try 
					{
						Thread.sleep((long) (Math.random()*Coordinator.maxRefreshRate));
					} 
					catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						this.interrupt();
					}
		*/		break;
	/*			case JobType.GET_FILES_INFO:
					try 
					{
						JStorageInterface remoteHost = (JStorageInterface) LocateRegistry.getRegistry(Coordinator.getProp("node"+threadID), 1099).lookup("test");
						remoteHost.setFileSort(FileSortTypes.BY_NAME);
						String currFileList[] = remoteHost.getFileList();  
						Coordinator.compareFileData(currFileList);
					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Thread.currentThread().interrupt();		
				break;
			*/	
				case JobType.OLOLO:
		/*			bytesRead = 1;	
			//		int bufLength = 10000;
					curr_pos = this.getThreadID()*bufLength;
					buf = new byte[bufLength];
					while(bytesRead>0)
					{
						try {
							bytesRead = 0;
							RandomAccessFile from = new RandomAccessFile(filename, "r");
							from.seek(curr_pos);
							curr_pos = curr_pos+bufLength;
							bytesRead = from.read(buf);
							System.out.print("read ");
							if(bytesRead >0)
							{
								RandomAccessFile to = new RandomAccessFile("rez.avi", "rwd");
								to.seek(curr_pos);
								to.write(buf, 0, bytesRead);
								to.close();
								System.out.println("writed "+this.getThreadID()+" "+curr_pos);
							}
							from.close();
							 
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						
					}*/
					this.jobId = 0;					
				break;
			}
		}
	}
	
	public void setFilename(String filename) {
//		this.filename = filename;
	}
	
	/**
	 * @param jobId  the jobId to set
	 * @uml.property  name="jobId"
	 */
	public void setJobId(byte jobId) {
		this.jobId = jobId;
	}
	
	
	/**
	 * @param threadID  the threadID to set
	 * @uml.property  name="threadID"
	 */
	public void setThreadID(int threadID) {
		this.threadID = threadID;
	}
	public void setDisperser(NodeInfo nodeInfo) 
	{
		currDisperser = nodeInfo;
	}
	/**
	 * @param filesInfo  the filesInfo to set
	 * @uml.property  name="filesInfo"
	 */
	public void setFilesInfo(ArrayList<FileInfo> filesInfo) {
		this.filesInfo = filesInfo;
	}
	/**
	 * @return  the filesInfo
	 * @uml.property  name="filesInfo"
	 */
	public ArrayList<FileInfo> getFilesInfo() {
		return filesInfo;
	}
	public void setStorageInfo(StorageInfo currStorage) 
	{
		currStorageInfo = currStorage;
	}
	public void setDispersers(ArrayList<NodeInfo> currDispersers) 
	{
		this.currDispersers = currDispersers;
	}
	public void setFileInfo(FileInfo f) 
	{
		currFileInfo = f;
	}
	/**
	 * @param s
	 * @uml.property  name="currStorageInfo"
	 */
	public void setCurrStorageInfo(StorageInfo s) 
	{
		currStorageInfo = s;
	}
	/**
	 * @param bufLength
	 * @uml.property  name="bufLength"
	 */
	public void setBufLength(int bufLength) 
	{
		this.bufLength = bufLength; 
		
	}
}

//GET /pwfor4/ HTTP/1.0
//Host: hax.tor.hu

