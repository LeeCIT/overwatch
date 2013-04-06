


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
		new PersonnelLogic( gui.personnelTab );
		new RankLogic     ( gui.rankTab      );
		new VehicleLogic  ( gui.vehicleTab   );
		new SupplyLogic   ( gui.supplyTab    );
		// TODO: insert other controllers here
	}
	
}
