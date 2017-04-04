package vstu.edu.ru.expert;

import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import vstu.edu.ru.expert.agents.JSaverCustomer;

/**
 * @author  Saver
 */
public class JExpertFormUpdater extends Thread 
{
	/**
	 * @uml.property  name="progressPanel"
	 */
	private Container progressPanel;
	/**
	 * @uml.property  name="treePanel"
	 */
	private JTree treePanel;
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	private JSaverCustomer c;
	/**
	 * @uml.property  name="app"
	 * @uml.associationEnd  
	 */
	private ExpertApplication app;
	
	public void bindCustomer(JSaverCustomer c)
	{
		this.c = c;
	}
	
	public void bindForm(ExpertApplication a)
	{
		app = a;
	}
	
	/**
	 * @param progressPanel the progressPanel to set
	 */
	public void setProgressPanel(JPanel progressPanel) {
		this.progressPanel = progressPanel;
	}
	/**
	 * @return  the progressPanel
	 * @uml.property  name="progressPanel"
	 */
	public Container getProgressPanel() {
		return progressPanel;
	}
	/**
	 * @param treePanel  the treePanel to set
	 * @uml.property  name="treePanel"
	 */
	public void setTreePanel(JTree treePanel) {
		this.treePanel = treePanel;
	}
	/**
	 * @return  the treePanel
	 * @uml.property  name="treePanel"
	 */
	public Container getTreePanel() {
		return treePanel;
	}
	
	public void run() 
	{
		while(!Thread.currentThread().isInterrupted())
		{
			try 
			{
				app.refreshForm();

				
				
				Thread.sleep((long) (Math.random()*100));
			//	System.out.println("FORM UPDATED");
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.interrupt();
			}
		}
	}
	
}
	
