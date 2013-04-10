


package overwatch.gui.tabs;

import overwatch.gui.AssignPanel;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldEllipsisTriplet;
import overwatch.gui.LabelFieldPair;





/**
 * Implements the squad management tab.
 *  
 * @author  Lee Coakley
 * @version 3
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
		commander = addLabelledFieldWithEllipsis( "Commander:" );
		
		assignTroops   = new AssignPanel<Integer>( "Troops"   );
		assignVehicles = new AssignPanel<Integer>( "Vehicles" );
		assignSupplies = new AssignPanel<Integer>( "Supplies" );
		
		addToMain( assignTroops,   "newline, spanx 2, split 3, height 85%" );
		addToMain( assignVehicles, "height 85%");
		addToMain( assignSupplies, "height 85%" );
	}
	
	
	
	
	
	/**
	 * Enable/disable everything: fields, buttons and asssignment panels.
	 * @param enable
	 */
	public void setEnabledAll( boolean enable ) {
		super.setEnableFieldsAndButtons( enable );
		assignVehicles.setEnabled( enable );
		assignSupplies.setEnabled( enable );
		assignTroops  .setEnabled( enable );
	}
	
	
	
	
	
	/**
	 * Clear everything editable: fields and assignment panels.
	 */
	public void clearAll() {
		super.clearFields();
		assignTroops  .clearItems();
		assignVehicles.clearItems();
		assignSupplies.clearItems();
	}
}






























