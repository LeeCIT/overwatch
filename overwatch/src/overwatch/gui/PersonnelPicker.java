


package overwatch.gui;

import java.awt.Component;
import overwatch.db.Database;





/**
 * Create a dialogue for picking a person from the database. 
 * The PickListener should be pre-created in the relevant tab class.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class PersonnelPicker extends SearchPicker<Integer>
{
	
	public PersonnelPicker( Component relativeTo, PickListener<Integer> listenerToCall )
	{
		super( 
			relativeTo, 
			"Choose Person",
			"Personnel",
			Database.queryKeyNamePairs( "Personnel", "personNo", "loginName", Integer[].class )
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
