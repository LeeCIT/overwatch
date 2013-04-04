


package overwatch.core;





public class Controller
{
	
	/**
	 * Plug the logic controllers into the GUI components.
	 * @param gui Gui instance.
	 */
	public static void attachLogicControllers( Gui gui )
	{
		PersonnelLogic.attachEvents( gui.personnelTab );
		// TODO: insert other controllers here
	}
	
}
