


package overwatch.core;

import overwatch.controllers.*;




// TODO: use tabPane.addChangeListener() in conjunction with tabPane.getSelectedComponent
//       to reload panels on change




public class Controller
{
	
	/**
	 * Plug the logic controllers into the GUI components.
	 * @param gui Gui instance.
	 */
	public static void attachLogicControllers( final Gui gui )
	{
		new PersonnelLogic( gui.personnelTab ).respondToTabSelect();
		new RankLogic     ( gui.rankTab      ).respondToTabSelect();
		new VehicleLogic  ( gui.vehicleTab   ).respondToTabSelect();
		new SupplyLogic   ( gui.supplyTab    ).respondToTabSelect();
		// TODO: insert other controllers here
	}
	
}
