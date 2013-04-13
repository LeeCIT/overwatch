


package overwatch.controllers;

import java.awt.Component;
import overwatch.core.Gui;





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
	
	
	
	
	
	public void showDeletedError( String itemName )
	{
		Gui.showErrorDialogue(
			"Deleted By Other User",
			"The " + itemName + " has been deleted by another user!"
		);
	}
	
	
	
	
	
	public void showFieldValidationError()
	{
		Gui.showErrorDialogue(
				"Invalid Fields",
				"Some of the fields contian invalid inputs.  All must be valid before saving."
			);
	}
}
