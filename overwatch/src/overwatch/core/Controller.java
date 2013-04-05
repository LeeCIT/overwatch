


package overwatch.core;

import overwatch.controllers.PersonnelLogic;




// TODO: use tabPane.addChangeListener() in conjunction with tabPane.getSelectedComponent
//       to reload panels on change




public class Controller
{
	
	/**
	 * Plug the logic controllers into the GUI components.
	 * @param gui Gui instance.
	 */
	public static void attachLogicControllers( Gui gui )
	{
		new PersonnelLogic( gui.personnelTab );
		// TODO: insert other controllers here
	}
	
}
