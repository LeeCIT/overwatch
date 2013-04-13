


package overwatch.gui;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JFrame;





/**
 * Create a dialogue for picking squad supplies.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class SquadSupplyPicker extends SearchPicker<Integer>
{
	
	public SquadSupplyPicker( Component relativeTo, PickListener<Integer> listenerToCall, ArrayList<NameRefPair<Integer>> pickables )
	{
		super( 
			relativeTo, 
			"Choose Supply",
			"Supplies",
			pickables
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
