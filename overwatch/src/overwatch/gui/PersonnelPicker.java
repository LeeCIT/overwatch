


package overwatch.gui;

import javax.swing.JFrame;
import overwatch.db.Database;





/**
 * Create a dialogue for picking a person from the database. 
 * The PickListener should be pre-created in the relevant tab class.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class PersonnelPicker extends SearchPicker<Integer>
{
	
	public PersonnelPicker( JFrame frame, PickListener<Integer> listenerToCall )
	{
		super( 
			frame, 
			"Choose Person",
			"Personnel",
			Database.queryKeyNamePairs( "Personnel", "personNo", "name", Integer[].class )
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
