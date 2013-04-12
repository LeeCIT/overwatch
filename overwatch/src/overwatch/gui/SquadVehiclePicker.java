


package overwatch.gui;

import java.util.ArrayList;
import javax.swing.JFrame;





/**
 * Create a dialogue for picking squad supplies.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class SquadVehiclePicker extends SearchPicker<Integer>
{
	
	public SquadVehiclePicker( JFrame frame, PickListener<Integer> listenerToCall, ArrayList<NameRefPair<Integer>> pickables )
	{
		super( 
			frame,
			"Choose Vehicle",
			"Vehicles",
			pickables
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
