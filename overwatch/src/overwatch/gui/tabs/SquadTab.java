


package overwatch.gui.tabs;

import overwatch.gui.AssignPanel;
import overwatch.gui.GenericPanelButtoned;





/**
 * Implements the squad management tab.
 *  
 * @author  Lee Coakley
 * @version 2
 */





public class SquadTab extends GenericPanelButtoned<Integer>
{
	public final AssignPanel<Integer> assignTroops;
	public final AssignPanel<Integer> assignVehicles;
	public final AssignPanel<Integer> assignSupplies;
	
	
	
		
	
	public SquadTab()
	{
		super( "Squads" );
		
		assignTroops   = new AssignPanel<Integer>( "Squad Troops"   );
		assignVehicles = new AssignPanel<Integer>( "Squad Vehicles" );
		assignSupplies = new AssignPanel<Integer>( "Squad Supplies" );
		
		add( assignTroops,  "newline, span 2, split 3" );
		add( assignVehicles );
		add( assignSupplies );
	}
	
}
