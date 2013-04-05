package overwatch.gui.tabs;

import javax.swing.*;

import overwatch.controllers.VehicleLogic;
import overwatch.core.Gui;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldEllipsisTriplet;
import overwatch.gui.LabelFieldPair;


/**
 * Creates the vehicle tab
 * @author john
 * Version 2
 */

public class VehicleTab extends GenericPanelButtoned<Integer>
{
	private LabelFieldPair            number;
	private LabelFieldPair            type;
	private LabelFieldEllipsisTriplet pilot;
	
	
	public VehicleTab()
	{
		super( "Vehicles" );		
		setupComponents();
	}
	

	
	
	
	
	
		
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////

	
	
	private void setupComponents()
	{
		number  = addLabelledField( "Number:" );
		type	= addLabelledField("Type:");
		pilot   = addLabelledFieldWithEllipsis( "Pilot:" );	
	}
	
	
		
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
