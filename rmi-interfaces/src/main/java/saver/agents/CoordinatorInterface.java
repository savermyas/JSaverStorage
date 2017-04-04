package saver.agents;

import java.util.ArrayList;

import saver.common.FileInfo;
import saver.common.StorageInfo;

public interface CoordinatorInterface 
{
	public void bindCustomer(CustomerInterface newCust);
	public void bindSubordinator(SubordinatorInterface newCust);
	public void initModel(); //TODO переместить в агента-субординатора
	public void disperseFile(FileInfo f, StorageInfo s);
	public void recoverFile(FileInfo f, StorageInfo s);
	public void removeFile(FileInfo f, StorageInfo s);
	public void clearStorage(StorageInfo s);
	public ArrayList<FileInfo> getFileInfo(StorageInfo s);
	public void saveFilesInfo(); //TODO добавить параметр StorageInfo s
}
