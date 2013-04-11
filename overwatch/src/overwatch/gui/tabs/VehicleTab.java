


package overwatch.gui.tabs;

import overwatch.controllers.VehicleLogic;
import overwatch.core.Gui;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldEllipsisTriplet;
import overwatch.gui.LabelFieldPair;
import javax.swing.*;





/**
 * Implements the vehicle tab GUI.
 * 
 * @author John Murphy
 * @author Lee Coakley
 * @version 4
 */





public class VehicleTab extends GenericPanelButtoned<Integer>
{
	public final LabelFieldPair            number;
	public final LabelFieldPair            name;
	public final LabelFieldEllipsisTriplet pilot;
	
	
	
	
	
	public VehicleTab()
	{
		super( "Vehicles" );
		
		number  = addLabelledField( "Number:" );
		name	= addLabelledField( "Name:"   );
		pilot   = addLabelledFieldWithEllipsis( "Pilot:" );
		
		number.field.setEditable( false );
	}
	
	
	
	
	
	// Validate
	public void addTypeValidator ( CheckedFieldValidator v ) { name .field.addValidator(v); }
	public void addPilotValidator( CheckedFieldValidator v ) { pilot.field.addValidator(v); }
	
	

	
	
	
		
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////

	
	public static void main(String[] args)
	{
		Gui.setNativeStyle();
		
		JFrame frame = new JFrame();
		VehicleTab vt = new VehicleTab();
		
		frame.add(vt);
		frame.pack();
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		new VehicleLogic( vt );
	}

}
