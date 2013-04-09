


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
		commander = addLabelledFieldWithEllipsis( "Commander:" );
		
		assignTroops   = new AssignPanel<Integer>( "Troops"   );
		assignVehicles = new AssignPanel<Integer>( "Vehicles" );
		assignSupplies = new AssignPanel<Integer>( "Supplies" );
		
		add( assignTroops,  "newline, span 2, split 3, height 75%" );
		add( assignVehicles );
		add( assignSupplies );
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






























