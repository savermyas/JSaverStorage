package saver.common;

import java.io.Serializable;

/**
 * @author    Saver
 */
public class FileInfo implements Serializable
{	
	private static final long serialVersionUID = -1193065684873887787L;
	/**
	 * @return    the size
	 * @uml.property  name="size"
	 */
	public long getSize() {
		return size;
	}
	/**
	 * @param size    the size to set
	 * @uml.property  name="size"
	 */
	public void setSize(long size) {
		this.size = size;
	}
	/**
	 * @return    the filename
	 * @uml.property  name="filename"
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename    the filename to set
	 * @uml.property  name="filename"
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return    the date
	 * @uml.property  name="date"
	 */
	public long getDate() {
		return date;
	}
	/**
	 * @param date    the date to set
	 * @uml.property  name="date"
	 */
	public void setDate(long date) {
		this.date = date;
	}
	/**
	 * @param absolutepath    the absolutepath to set
	 * @uml.property  name="absolutepath"
	 */
	public void setAbsolutepath(String absolutepath) {
		this.absolutepath = absolutepath;
	}
	/**
	 * @return    the absolutepath
	 * @uml.property  name="absolutepath"
	 */
	public String getAbsolutepath() {
		return absolutepath;
	}
	private long size;
	private String filename;
	private String absolutepath;
	private long date;

	public String toString() 
	{
		return filename;
	}
}
