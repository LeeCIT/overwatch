


package overwatch.gui;

import java.util.ArrayList;
import javax.swing.JFrame;





/**
 * Create a dialogue for picking squad troops.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class SquadTroopPicker extends SearchPicker<Integer>
{
	
	public SquadTroopPicker( JFrame frame, PickListener<Integer> listenerToCall, ArrayList<NameRefPair<Integer>> pickables )
	{
		super( 
			frame, 
			"Choose Trooper",
			"Troopers",
			pickables
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
