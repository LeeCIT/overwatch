


package overwatch.gui;

import java.awt.Component;
import java.util.ArrayList;





/**
 * Create a dialogue for picking squad troops.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class SquadTroopPicker extends SearchPicker<Integer>
{
	
	public SquadTroopPicker( Component relativeTo, PickListener<Integer> listenerToCall, ArrayList<NameRefPair<Integer>> pickables )
	{
		super( 
			relativeTo, 
			"Choose Trooper",
			"Troopers",
			pickables
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
