


package overwatch.core;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
		
		
		gui.addTabChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e ) {
				System.out.println( gui.tabPane.getSelectedComponent() );	
			}
		});
	}
	
}
