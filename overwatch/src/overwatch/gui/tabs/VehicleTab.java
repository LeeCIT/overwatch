package overwatch.gui.tabs;

import javax.swing.*;

import overwatch.controllers.VehicleLogic;
import overwatch.core.Gui;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldEllipsisTriplet;
import overwatch.gui.LabelFieldPair;


/**
 * Creates the vehicle tab
 * @author john
 * @version 3
 */

public class VehicleTab extends GenericPanelButtoned<Integer>
{
	private LabelFieldPair            number;
	private LabelFieldPair            type;
	private LabelFieldEllipsisTriplet pilot;
	
	
	public VehicleTab()
	{
		super( "Vehicles" );		
		
		number  = addLabelledField( "Number:" );
		type	= addLabelledField("Type:");
		pilot   = addLabelledFieldWithEllipsis( "Pilot:" );	
	}
	
	
	
	
		// Validate
		public void addNameValidator(CheckedFieldValidator v){type.field.addValidator(v); }
	
	
	
		
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////

	
	
	
		
	//Test
	public static void main(String[] args)
	{
		Gui.setNativeStyle();
		
		JFrame frame = new JFrame();
		VehicleTab vt = new VehicleTab();
		
		frame.add(vt);
		frame.pack();
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		new VehicleLogic(vt);
	}
	
	
	
	

}
