package saver.agents;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import saver.common.FileInfo;
import saver.common.StorageInfo;

public interface SaverInterface extends Remote 
{
	public String test() throws RemoteException;
	//public void saveSlice(byte[] row, long fileId, long sliceId) throws RemoteException;
	public void saveSlice(byte[] row, long sliceId, int nodeNum) throws RemoteException;
	public void startSavingProcess(FileInfo f, int storageId) throws RemoteException;
	//public void saveFilesInfo(ArrayList<FileInfo> filesInfo, int StorageId) throws RemoteException;
	//public ArrayList<FileInfo> getFilesInfo(int StorageId) throws RemoteException;
	public void initProcess(FileInfo f, StorageInfo s) throws RemoteException;
	public void finishProcess() throws RemoteException;
	public ArrayList<FileInfo> getFilesInfo(StorageInfo s) throws RemoteException;
	public byte[] getSlice(long currSlice, int nodeNum) throws RemoteException;
	public void removeFile(FileInfo f, StorageInfo s) throws RemoteException;
	public void clearStorage(StorageInfo s) throws RemoteException;
	void saveFilesInfo(ArrayList<FileInfo> filesInfo, StorageInfo s)
			throws RemoteException;
}
