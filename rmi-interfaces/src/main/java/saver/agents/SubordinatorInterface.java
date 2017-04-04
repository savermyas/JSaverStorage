/**
 * 
 */
package saver.agents;

/**
 * @author Saver
 *
 */
public interface SubordinatorInterface 
{
	/**
	 * Привязать агента-координатора
	 * Bind agent-coordinaror
	 * @param ci объект, реализующий интерфейс координатора, object that implements coordinator's interface 
	 */
	public void bindCoordinator(CoordinatorInterface ci);
	/**
	 * Привязать агента-заказчика
	 * Bind agent-customer
	 * @param cusi объект, реализующий интерфейс заказчика, object that implements customer's interface 
	 */
	public void bindCustomer(CustomerInterface cusi);
	//TODO добавить initModel()
}
