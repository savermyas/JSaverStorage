package vstu.edu.ru.agent;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import saver.agents.DisperserInterface;
import saver.agents.SaverInterface;
import saver.common.FileInfo;
import saver.common.NodeInfo;
import saver.common.StorageInfo;
import saver.galoismath.GF8;
import saver.galoismath.GF8SimpleMatrix;

/**
 * @author   Saver
 */
public class JDisperserImpl extends UnicastRemoteObject implements DisperserInterface 
{

	/**
	 * @uml.property  name="a"
	 * @uml.associationEnd  
	 */
	GF8SimpleMatrix A;
	/**
	 * @uml.property  name="remoteSavers"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	SaverInterface [] remoteSavers;
	/**
	 * @uml.property  name="currFile"
	 * @uml.associationEnd  
	 */
	private FileInfo currFile;
	/**
	 * @uml.property  name="currStorage"
	 * @uml.associationEnd  
	 */
	private StorageInfo currStorage;
	/**
	 * @uml.property  name="a1"
	 * @uml.associationEnd  
	 */
	private GF8SimpleMatrix A1;
	
	protected JDisperserImpl() throws RemoteException 
	{
		super();
		GF8.generateGf8();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8355135276516590468L;

	@Override
	synchronized public void disperseFragment(GF8SimpleMatrix M, long sliceId) throws RemoteException 
	{
//	System.out.println("Ac: "+A.getColsNum()+" Ar: "+A.getRowsNum()+" Ab: "+A.getBufSize());
//	System.out.println("Mc: "+M.getColsNum()+" Mr: "+M.getRowsNum()+" Mb: "+M.getBufSize());
		//GF8.generateGf8();
		GF8SimpleMatrix rez = A.mult(M);
//	System.out.println("Rc: "+rez.getColsNum()+" Rr: "+rez.getRowsNum()+" Rb: "+rez.getBufSize());
		//rez.SaveToFiles();
	//	System.out.println(rez);
		for (int f = 0; f<currStorage.savers.size(); f++)
		{	
			remoteSavers[f].saveSlice(rez.getRow(f), sliceId, f);	
		}
	}
	@Override
	synchronized public byte[] testConnection(byte[] testData) {
		// TODO Auto-generated method stub
		return testData; 
	}
	
	public void test()
	{
		
	}

	@Override
	public ArrayList<FileInfo> getFilesInfo(StorageInfo s) throws RemoteException 
	{
		ArrayList<NodeInfo> fastestSavers = s.getFastestSavers();
		ArrayList<FileInfo> rez = new ArrayList <FileInfo> ();
		for(int i = 0; i< fastestSavers.size(); i++)
		{
			try 
			{
				SaverInterface remoteSaver = (SaverInterface) LocateRegistry.getRegistry(fastestSavers.get(i).addr, 1099).lookup("saver");
				//remoteSaver.saveFilesInfo(s.filesInfo, s.id);
				rez = remoteSaver.getFilesInfo(s);
	//System.out.println("remoteSaver fileInfo size: "+rez.size());
			} catch (NotBoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rez;
	}

	@Override
	public void saveFilesInfo(StorageInfo s)
			throws RemoteException 
	{
		for(int i = 0; i<s.savers.size(); i++)
		{
			try 
			{
				SaverInterface remoteSaver = (SaverInterface) LocateRegistry.getRegistry(s.savers.get(i).addr, 1099).lookup("saver");
				remoteSaver.saveFilesInfo(s.filesInfo, s);
			} catch (NotBoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	synchronized public void initProcess(FileInfo f, StorageInfo s, GF8SimpleMatrix A) throws RemoteException, NotBoundException 
	{
		this.A = A;
//		System.out.println("Got A matrix: "+A.getColsNum()+"x"+A.getRowsNum()+"x"+A.getBufSize());
//		System.out.println(A.toString());
		this.currStorage = s;
		this.currFile = f;
		remoteSavers = new SaverInterface[s.savers.size()];
//		System.out.println(s.savers.size()+" savers found...");
		for(int i = 0; i<s.savers.size(); i++)
		{
			remoteSavers[i] = (SaverInterface) LocateRegistry.getRegistry(s.savers.get(i).addr, 1099).lookup("saver");
		}
		if(A!=null)
//		System.out.println("Desperser savers initialized...");
		try 
		{
//			System.out.println("Searching fastest savers...");
			ArrayList<NodeInfo> fastestSavers = currStorage.getFastestSavers();
//			System.out.println(fastestSavers.size()+" fastest savers found");
			A1 = new GF8SimpleMatrix(currStorage.minimalnodes,currStorage.minimalnodes, A.getBufSize());
//			System.out.println("Empty A1 created...");
			//System.out.println(A);
			for(int h = 0; h<currStorage.minimalnodes; h++)
			{
			//	System.out.println(h+" "+fastestSavers.get(h).id+": A: "+A.getRow(fastestSavers.get(h).id).length+" A1: "+A1.getRow(h).length);
				A1.setRow(h, A.getRow(fastestSavers.get(h).id)); //!!
			}
//			System.out.println(A1);
			/*byte[] d = A1.det();
			System.out.print(d.length+": ");
			for(int i = 0; i<d.length; i++)
			{
				System.out.print(d[i]+" ");
			}
			System.out.println();*/
			A1 = A1.obr();
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("A1: "+A1.getColsNum()+"x"+A1.getRowsNum()+"x"+A1.getBufSize());
//		System.out.println(A1.toString());
		
		
		
	}
	@Override
	synchronized public void finishProcess() throws RemoteException 
	{
		A = null;
		A1 = null;
		remoteSavers = null;
		currFile = null;
		currStorage = null;
		System.gc();
	}
	
	@Override
	public GF8SimpleMatrix getFragment(long currSlice) throws RemoteException 
	{
		GF8SimpleMatrix M = new GF8SimpleMatrix(currStorage.minimalnodes,currStorage.savers.size(),A.getBufSize());
		for(int h = 0; h<currStorage.minimalnodes; h++)
		{
			M.setRow(h, remoteSavers[h].getSlice(currSlice,h)); //!!
		}
	//	System.out.println(M);
		return M.mult1(A1);
		
	}
	@Override
	public void removeFile(FileInfo f, StorageInfo s) throws RemoteException 
	{
		for(int i = 0; i<s.savers.size(); i++)
		{
			try 
			{
				SaverInterface remoteSaver = (SaverInterface) LocateRegistry.getRegistry(s.savers.get(i).addr, 1099).lookup("saver");
				remoteSaver.removeFile(f, s);
			} catch (NotBoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
