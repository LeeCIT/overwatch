


package overwatch.core;

import overwatch.controllers.*;





public class Controller
{
	
	/**
	 * Plug the logic controllers into the GUI components.
	 * @param gui Gui instance.
	 */
	public static void attachLogicControllers( final Gui gui )
	{
		new PersonnelLogic( gui.personnelTab ).respondToTabSelect();
		new RankLogic     ( gui.rankTab      );
		new VehicleLogic  ( gui.vehicleTab   );
		new SupplyLogic   ( gui.supplyTab    );
		new SquadLogic	  ( gui.squadTab	 );
		// TODO: insert other controllers here
	}
	
}
