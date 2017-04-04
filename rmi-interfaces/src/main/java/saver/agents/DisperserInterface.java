package saver.agents;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import saver.common.FileInfo;
import saver.common.StorageInfo;
import saver.galoismath.GF8SimpleMatrix;

public interface DisperserInterface extends Remote 
{
	public void disperseFragment(GF8SimpleMatrix M, long fragmentId) throws RemoteException;
	public byte[] testConnection(byte[] testData) throws RemoteException;
	//public void saveFilesInfo(ArrayList<FileInfo> filesInfo, ArrayList<NodeInfo> saversList, int storageId) throws RemoteException;
	public void initProcess(FileInfo f, StorageInfo s, GF8SimpleMatrix A) throws RemoteException, NotBoundException;
	public void finishProcess() throws RemoteException;
	public GF8SimpleMatrix getFragment(long currSlice) throws RemoteException;
	public void saveFilesInfo(StorageInfo s) throws RemoteException;
	ArrayList<FileInfo> getFilesInfo(StorageInfo s) throws RemoteException;
	public void removeFile(FileInfo f, StorageInfo s) throws RemoteException;
}
