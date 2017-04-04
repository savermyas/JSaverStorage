package vstu.edu.ru.expert.agents;

import saver.agents.CoordinatorInterface;
import saver.agents.CustomerInterface;
import saver.agents.SubordinatorInterface;
import vstu.edu.ru.expert.NodeInfoUpdater;


/**
 * @author  Saver
 */
public class JSaverSubordinator implements SubordinatorInterface {

	/**
	 * @uml.property  name="coordinator"
	 * @uml.associationEnd  
	 */
	CoordinatorInterface coordinator = null;
	/**
	 * @uml.property  name="customer"
	 * @uml.associationEnd  
	 */
	CustomerInterface customer = null;
	ThreadGroup updateGroup;
	
//	private ArrayList<NodeInfoUpdater> updateTasks;
	
	@Override
	public void bindCoordinator(CoordinatorInterface newCoord) 
	{
		coordinator = newCoord;
	}

	@Override
	public void bindCustomer(CustomerInterface newCust) 
	{
		customer = newCust;
	}
	
	public void startRefreshing()
	{
		updateGroup = new ThreadGroup("Nodes info update threads"); //Р СљР С•Р В¶Р ВµРЎвЂљ, РЎРЊРЎвЂљР С• Р Р† РЎРѓРЎС“Р С—Р ВµРЎР‚Р Р†Р В°Р в„–Р В·Р С•РЎР‚Р В° Р В·Р В°Р С—Р С‘РЎвЂ¦Р Р…РЎС“РЎвЂљРЎРЉ? 
		for (int i = 0; i < ((JSaverCoordinator) coordinator).nodesInfo.size(); i++)
		{
			((JSaverCoordinator) coordinator).nodesInfo.get(i).timeout = -50;
			NodeInfoUpdater t = new NodeInfoUpdater();
			t.bindCustomer((JSaverCustomer) customer);
			t.setName("Node"+i+"InfoUpdater");
			t.setThreadID(i); 
			new Thread(updateGroup, t, "Node"+i+"InfoUpdater").start(); //РЎРѓР С•Р В·Р Т‘Р В°РЎвЂ�Р С� Р Р…Р С•Р Р†РЎвЂ№Р в„– Р С—Р С•РЎвЂљР С•Р С” Р Р† Р С–РЎР‚РЎС“Р С—Р С—Р Вµ dispGroup c Р С‘Р С�Р ВµР Р…Р ВµР С� "Р пїЅР С�РЎРЏР В¤Р В°Р в„–Р В»Р В°_i"  
		};
	}
	
	public void finalize()
	{
		updateGroup.interrupt();
		System.gc();
	}
}
