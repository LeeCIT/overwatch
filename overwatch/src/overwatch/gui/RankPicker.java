


package overwatch.gui;

import javax.swing.JFrame;
import overwatch.db.Database;





/**
 * Create a dialogue for picking a rank from the database. 
 * The PickListener should be pre-created in the relevant tab class.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class RankPicker extends SearchPicker<Integer>
{
	
	public RankPicker( JFrame frame, PickListener<Integer> listenerToCall )
	{
		super( 
			frame, 
			"Choose Rank",
			"Ranks",
			Database.queryKeyNamePairs( "Ranks", "rankNo", "name", Integer[].class )
		);
		
		this.addPickListener( listenerToCall );
	}
	
}
