


package overwatch.gui.tabs;

import overwatch.gui.AssignPanel;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldEllipsisTriplet;
import overwatch.gui.LabelFieldPair;





/**
 * Implements the squad management tab.
 *  
 * @author  Lee Coakley
 * @version 2
 */





public class SquadTab extends GenericPanelButtoned<Integer>
{
	public final LabelFieldPair            number;
	public final LabelFieldPair            name;
	public final LabelFieldEllipsisTriplet commander;
	
	public final AssignPanel<Integer> assignTroops;
	public final AssignPanel<Integer> assignVehicles;
	public final AssignPanel<Integer> assignSupplies;
	
	
	
		
	
	public SquadTab()
	{
		super( "Squads" );
		
		number    = addLabelledField( "Number:" );
		name      = addLabelledField( "Name:"   );
		commander = addLabelledFieldWithEllipsis( "Commnader:" );
		
		assignTroops   = new AssignPanel<Integer>( "Squad Troops"   );
		assignVehicles = new AssignPanel<Integer>( "Squad Vehicles" );
		assignSupplies = new AssignPanel<Integer>( "Squad Supplies" );
		
		add( assignTroops,  "newline, span 2, split 3" );
		add( assignVehicles );
		add( assignSupplies );
	}
	
	
	// TODO 
}






























