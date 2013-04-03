


package overwatch.gui;

import javax.swing.JFrame;
import overwatch.db.Database;





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
