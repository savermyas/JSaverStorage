package vstu.edu.ru.expert.agents;

import javax.swing.JTextArea;

import saver.agents.CoordinatorInterface;
import saver.agents.CustomerInterface;
import saver.agents.SubordinatorInterface;
import saver.common.NodeInfo;



/**
 * @author  Saver
 */
public class JSaverCustomer implements CustomerInterface 
{
	/**
	 * @uml.property  name="coordinator"
	 * @uml.associationEnd  
	 */
	CoordinatorInterface coordinator = null;
	/**
	 * @uml.property  name="subordinator"
	 * @uml.associationEnd  
	 */
	SubordinatorInterface subordinator = null;
	
	@Override
	public void bindCoordinator(CoordinatorInterface newCoord) 
	{
		coordinator = newCoord;
	}

	@Override
	public void bindSubordinator(SubordinatorInterface newSub) 
	{
		subordinator = newSub;
	}
	
	/**
	 * @uml.property  name="logArea"
	 */
	private JTextArea logArea;

	public void bindLogArea(JTextArea logArea) {
		this.logArea = logArea;
	}

	/**
	 * @return
	 * @uml.property  name="logArea"
	 */
	public JTextArea getLogArea() {
		return logArea;
	}
	
	public void sendMsgToLog(String msg)
	{
		logArea.append(msg+'\n');
	}

	@Override
	public void logMessage(String msg) {
		logArea.append(msg+'\n');
	}

	public double getRefreshRate() 
	{
		return ((JSaverCoordinator) coordinator).maxRefreshRate;
	}

	public NodeInfo getNodeInfo(int i) 
	{
		return ((JSaverCoordinator) coordinator).nodesInfo.get(i);
	}
}
