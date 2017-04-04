package saver.agents;


public interface CustomerInterface 
{
	public void bindCoordinator(CoordinatorInterface newCoord);
	public void bindSubordinator(SubordinatorInterface newCust);
	public void logMessage(String string);
}
