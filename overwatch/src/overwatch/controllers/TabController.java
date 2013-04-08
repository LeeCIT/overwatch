


package overwatch.controllers;

import java.awt.Component;





/**
 * Specifies a common external interface for tab controllers.
 * Used in the Gui class for registering controllers to receive tab change notifications.
 * 
 * @author  Lee Coakley
 * @version 2
 * @see     Gui
 */





public abstract class TabController
{
	public abstract void      respondToTabSelect();
	public abstract Component getTab();
}
