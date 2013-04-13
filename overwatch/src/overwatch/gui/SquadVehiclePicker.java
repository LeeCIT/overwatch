


package overwatch.gui;

import java.awt.Component;
import java.util.ArrayList;





/**
 * Create a dialogue for picking squad supplies.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class SquadVehiclePicker extends SearchPicker<Integer>
{
	
	public SquadVehiclePicker( Component relativeTo, PickListener<Integer> listenerToCall, ArrayList<NameRefPair<Integer>> pickables )
	{
		super( 
			relativeTo,
			"Choose Vehicle",
			"Vehicles",
			pickables
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
