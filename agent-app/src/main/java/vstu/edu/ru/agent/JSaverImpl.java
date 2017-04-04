package vstu.edu.ru.agent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import saver.agents.SaverInterface;
import saver.common.FileInfo;
import saver.common.StorageInfo;

/**
 * @author   Saver
 */
public class JSaverImpl extends UnicastRemoteObject implements SaverInterface 
{

	/**
	 * @uml.property  name="currFileInfo"
	 * @uml.associationEnd  
	 */
	private FileInfo currFileInfo;
	//private int currStorageId;
	/**
	 * @uml.property  name="currStorageInfo"
	 * @uml.associationEnd  
	 */
	private StorageInfo currStorageInfo;

	protected JSaverImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1782727138900478714L;

	@Override
	public String test() {
		File configfile = new File("."+File.separator+"storage");
		configfile.mkdirs();
		configfile = new File("."+File.separator+"storage"+File.separator+"test");		
		try {
			configfile.createNewFile();				
			OutputStream  out = new FileOutputStream(configfile);
			out.write(new String("ololo").getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}

	@Override
	synchronized public void saveSlice(byte[] row, long sliceId, int nodeNum)
			throws RemoteException 
	{
		//System.out.println("sliceId="+sliceId);
		String output = "."+File.separator+"storage"+File.separator+currStorageInfo.id+File.separator+nodeNum+File.separator+getFilename(currFileInfo);
		File f = new File(output);
		if (row.length>0)
		try 
		{
			//System.out.println();
			f.mkdirs();
			DataOutputStream os = new DataOutputStream(new FileOutputStream(output+File.separator+sliceId+".part"));
			os.write(row, 0, row.length);
			os.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();	
		}
		row = null;
	}
	
	public String md5(String input) 
	{
		 String md5 = null;
          try {
            StringBuffer code = new StringBuffer(); 
            java.security.MessageDigest messageDigest =  java.security.MessageDigest.getInstance("MD5");
            byte bytes[] = input.getBytes();
            byte digest[] = messageDigest.digest(bytes);
            
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }
            
            md5 = code.toString();
        } catch(Exception e) { }
        
        return md5;
    }
 
	@Override
	public void startSavingProcess(FileInfo f, int storageId) throws RemoteException {
		currFileInfo = f;
	//	currStorageId = storageId;
	}

	@Override
	public void saveFilesInfo(ArrayList<FileInfo> filesInfo, StorageInfo s)
			throws RemoteException 
		{
		
		String filename = "."+File.separator+"storage"+File.separator+s.id+File.separator+"filesinfo.dat";
		try 
		{
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(filesInfo);
			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void finishProcess() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initProcess(FileInfo f, StorageInfo s) throws RemoteException 
	{
		this.currFileInfo = f;
		this.currStorageInfo = s;
	}

	@Override
	public ArrayList<FileInfo> getFilesInfo(StorageInfo s) 
	{
		String filename = "."+File.separator+"storage"+File.separator+s.id+File.separator+"filesinfo.dat";
		ArrayList<FileInfo> rez = new ArrayList<FileInfo>();
		try 
		{
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fis);
			rez = (ArrayList<FileInfo>) in.readObject();
	//System.out.println("Saver read "+rez.size()+" files");
			in.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rez;
	}

	@Override
	public byte[] getSlice(long currSlice, int nodeNum) throws RemoteException {
		String input = "."+File.separator+"storage"+File.separator+currStorageInfo.id+File.separator+nodeNum+File.separator+getFilename(currFileInfo);
		byte[] rez = null;
		try 
		{
			//System.out.println();
	//		f.mkdirs();
			
			input = input+File.separator+currSlice+".part";
			File f = new File(input);
			//if (row.length>0)
			rez = new byte[(int) f.length()];
			DataInputStream is = new DataInputStream(new FileInputStream(input));
			int bytesRead = is.read(rez);
			is.close();
/*			for(int i = 0; i<rez.length;i++)
				System.out.print(rez[i]);
			System.out.println();
			System.out.println(bytesRead+" bytes read from "+input);*/
		} 
		catch (Exception e) 
		{
		//	e.printStackTrace();	
		}
		return rez;
	}

	@Override
	public void removeFile(FileInfo f, StorageInfo s) throws RemoteException 
	{
		for(int i = 0; i< s.savers.size(); i++)
		{
			String filename = "."+File.separator+"storage"+File.separator+s.id+File.separator+i+File.separator+getFilename(f);
			this.recursiveDelete(filename);
		}
		ArrayList<FileInfo> currFileList = this.getFilesInfo(s);
		int currIndex = -1;
		for(int i = 0; i<currFileList.size(); i++)
		{
			if(currFileList.get(i).getFilename().equals(f.getFilename()))
			{
				currIndex = i;
			}
		}
		if(currIndex>=0)
		 currFileList.remove(currIndex);
		this.saveFilesInfo(currFileList, s);
	}
	
	private String getFilename(FileInfo f) 
	{
		return(md5(f.getFilename()));
	}

	private void recursiveDelete(String currPath)
	{
		File f = new File(currPath);
		//System.out.println(currPath);
		if(!f.delete())
		{
			String [] filenames = f.list();
			if(filenames!=null)
			for(int i = 0; i<filenames.length; i++)
			{
				recursiveDelete(currPath+File.separator+filenames[i]);
				//if(isDebug())logMessage("");
				//System.out.println(currPath+File.separator+filenames[i]);
			}
			f.delete();
		}
		
	}

	@Override
	public void clearStorage(StorageInfo s) throws RemoteException {
		for(int i = 0; i< s.savers.size(); i++)
		{
			String filename = "."+File.separator+"storage"+File.separator+s.id;
			this.recursiveDelete(filename);
		}
		ArrayList<FileInfo> currFileList = new ArrayList<FileInfo>();

		this.saveFilesInfo(currFileList, s);
		
	}
	
}
