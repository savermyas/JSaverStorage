/**
 * 
 */
package vstu.edu.ru.expert;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import saver.common.JobType;
import vstu.edu.ru.expert.agents.JSaverCoordinator;

/**
 * @author  saver
 */
public class ProgressMonitor extends Thread 
{

	JPanel b;
	/**
	 * @uml.property  name="t"
	 * @uml.associationEnd  
	 */
	JCoordinatorTask t;
	//Container p;
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	private JSaverCoordinator c;
	/**
	 * @uml.property  name="app"
	 * @uml.associationEnd  
	 */
	private ExpertApplication app;
	 
	public ProgressMonitor(ExpertApplication myApp, JCoordinatorTask task, JSaverCoordinator agentCoordinator) 
	{
		this.app = myApp;
		this.b = myApp.jProgressBarPanel;
		this.t = task;
		this.c = agentCoordinator;
		this.setName("ProgressBarUpdater");
	} 

	public void run() 
	{
		app.disableButtons();
		byte percentDone = 100;
		
		try
		{
			percentDone = t.getJobDone();
			while((percentDone<100)&&(!this.isInterrupted()))
			{
				percentDone = t.getJobDone();
				((JProgressBar) b.getComponent(0)).setValue(percentDone);
			}
			String jobName = "";
			switch(t.getJobID())
			{
				case JobType.FILE_DISPERSION:
					if(t.finishState>=0)
						c.saveFilesInfo();
					jobName = "dispersion";
				break;
				case JobType.FILE_RECOVERY:
					jobName = "recovery";
				break;
			}
			if(t.finishState>=0)
			{
				c.sendMsgToLog("\""+t.getFilename()+"\" ("+t.getFilesize()+") "+jobName+" complete in "+(float)(System.currentTimeMillis()-t.getBeginTime())/1000+" s.");
				app.jRecoveryStoragesTree.clearSelection();
				app.jFilesTree.clearSelection();
			}
			else
			{
				c.sendMsgToLog("\""+t.getFilename()+"\" ("+t.getFilesize()+") "+jobName+" error! Check nodes status!");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//JPanel bParent = (JPanel) b.getParent();
		//bParent.remove(b);
		b.remove(0);
		b.revalidate();
		b.repaint();
		app.refreshButtons();
		//bParent.revalidate();
		//bParent.repaint();
		try 
		{
			t.finishProcess();
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
		c.coordinatorTasks.remove(t); //COOOL =)
		
		//c.saveFilesInfo();
		
		Thread.currentThread().interrupt();	
		

	}

	/**
	 * @param app  the app to set
	 * @uml.property  name="app"
	 */
	public void setApp(ExpertApplication app) {
		this.app = app;
	}

	/**
	 * @return  the app
	 * @uml.property  name="app"
	 */
	public ExpertApplication getApp() {
		return app;
	}

}
