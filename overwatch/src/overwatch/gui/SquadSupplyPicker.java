


package overwatch.gui;

import java.util.ArrayList;
import javax.swing.JFrame;





/**
 * Create a dialogue for picking squad supplies.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class SquadSupplyPicker extends SearchPicker<Integer>
{
	
	public SquadSupplyPicker( JFrame frame, PickListener<Integer> listenerToCall, ArrayList<NameRefPair<Integer>> pickables )
	{
		super( 
			frame, 
			"Choose Supply",
			"Supplies",
			pickables
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
