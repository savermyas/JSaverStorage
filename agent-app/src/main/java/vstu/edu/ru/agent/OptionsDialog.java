package vstu.edu.ru.agent;
import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import saver.common.FileUtils;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class OptionsDialog extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6962671099185828997L;
	private JLabel jLabel1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				OptionsDialog inst = new OptionsDialog();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public OptionsDialog() {
		super();
		initGUI();
	}
	
	public long getFileSize(File folder)
	{
		 long foldersize = 0;
		 File[] filelist = folder.listFiles();
		    for (int i = 0; i < filelist.length; i++) {
		      if (filelist[i].isDirectory()) {
		        foldersize += getFileSize(filelist[i]);
		      } else {
		     //   totalFile++;
		        foldersize += filelist[i].length();
		      }
		    }
		    return foldersize;
	}
	 
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("JSaverStorage Agent Options");
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, BorderLayout.CENTER);
				File storage = new File("./storage");
				//sendFileSize(currFile.getName(), currFile.length()+"");
				
				
				
				jLabel1.setText("Storage capacity is: "+FileUtils.byteCountToDisplaySize(getFileSize(storage))); 
				jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
				//jLabel1.setText("Options are not available yet... "+storage.length());
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

}
