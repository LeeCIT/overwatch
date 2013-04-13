


package overwatch.gui;

import java.awt.Component;
import overwatch.db.Database;





/**
 * Create a dialogue for picking a rank from the database. 
 * The PickListener should be pre-created in the relevant tab class.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class RankPicker extends SearchPicker<Integer>
{
	
	public RankPicker( Component relativeTo, PickListener<Integer> listenerToCall )
	{
		super( 
			relativeTo, 
			"Choose Rank",
			"Ranks",
			Database.queryKeyNamePairs( "Ranks", "rankNo", "name", Integer[].class )
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
