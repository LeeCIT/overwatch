


package overwatch.controllers;





/**
 * Specifies a common external interface for tab controllers.
 * Used in the Gui class for registering controllers to receive tab change notifications.
 * 
 * @author  Lee Coakley
 * @version 1
 * @see     Gui
 */





public interface TabController
{
	public void respondToTabSelect();
}
