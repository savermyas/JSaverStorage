package saver.common;

import java.io.Serializable;

/**
 * @author    Saver
 */
public class FilesInfoSaver implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilesInfoSaver(FileInfo[] filesInfo) {
		super();
		this.filesInfo = filesInfo;
	}

	/**
	 * @uml.property  name="filesInfo"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	FileInfo[] filesInfo; 
}
