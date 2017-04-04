/**
 * 
 */
package saver.common;

/**
 * @author saver
 *
 */
public class FileUtils {
	  public FileUtils() { }
	 
	      /**
	       * The number of bytes in a kilobyte.
	       */
	      public static final long ONE_KB = 1024;
	 
	      /**
	       * The number of bytes in a megabyte.
	       */
	      public static final long ONE_MB = ONE_KB * ONE_KB;
	 	       /**
	       * The number of bytes in a gigabyte.
	        */
	       public static final long ONE_GB = ONE_KB * ONE_MB;
	  
	       /**
	        * Returns a human-readable version of the file size (original is in
	        * bytes).
	        *
	        * @param size The number of bytes.
	        * @return A human-readable display value (includes units).
	        * @todo need for I18N?
	        */
	       public static String byteCountToDisplaySize(long size) {
	           String displaySize;
	  
	           if (size / ONE_GB > 0) 
	           {
	        	   displaySize = String.format("%.2f", (double)size / ONE_GB)+" GB";
	               //displaySize = String.valueOf((double)size / ONE_GB) + " GB"; 
	           } else if (size / ONE_MB > 0) 
	           {
	        	   displaySize = String.format("%.2f", (double)size / ONE_MB)+" MB";
	               //displaySize = String.valueOf((double)size / ONE_MB) + " MB";
	           } else if (size / ONE_KB > 0) {
	        	   displaySize = String.format("%.2f", (double)size / ONE_KB)+" KB";
	               //displaySize = String.valueOf((double)size / ONE_KB) + " KB";
	           } else 
	           {
	               displaySize = String.valueOf(size) + " bytes";
	           }
	  
	           return displaySize;
	       }

}
