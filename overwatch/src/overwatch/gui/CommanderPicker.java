package overwatch.gui;

import java.awt.Component;
import java.util.ArrayList;





/**
 * Create a dialogue for picking squad troops.
 * 
 * @author  Lee Coakley
 * @author  John Murphy
 * @version 1
 */





public class CommanderPicker extends SearchPicker<Integer>
{
	
	public CommanderPicker( Component relativeTo, PickListener<Integer> listenerToCall, ArrayList<NameRefPair<Integer>> pickables )
	{
		super( 
			relativeTo, 
			"Choose Commander",
			"Commanders",
			pickables
		);
		
		this.addPickListener( listenerToCall );
	}
	
}