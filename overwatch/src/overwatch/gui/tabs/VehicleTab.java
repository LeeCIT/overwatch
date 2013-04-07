


package overwatch.gui.tabs;

import overwatch.controllers.VehicleLogic;
import overwatch.core.Gui;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldEllipsisTriplet;
import overwatch.gui.LabelFieldPair;
import javax.swing.*;





/**
 * Creates the vehicle tab
 * 
 * @author John Murphy
 * @author Lee Coakley
 * @version 4
 */





public class VehicleTab extends GenericPanelButtoned<Integer>
{
	public final LabelFieldPair            number;
	public final LabelFieldPair            type;
	public final LabelFieldEllipsisTriplet pilot;
	
	
	
	
	
	public VehicleTab()
	{
		super( "Vehicles" );
		
		number  = addLabelledField( "Number:" );
		type	= addLabelledField( "Type:"   );
		pilot   = addLabelledFieldWithEllipsis( "Pilot:" );
		
		number.field.setEditable( false );
	}
	
	
	
	
	
	// Validate
	public void addTypeValidator(CheckedFieldValidator v){type.field.addValidator(v); }
	
	
	
	
	
	
	
	
		
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
