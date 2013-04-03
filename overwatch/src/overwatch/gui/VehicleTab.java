package overwatch.gui;

import javax.swing.*;


/**
 * Creates the vehicle tab
 * @author john
 *
 */

public class VehicleTab extends GenericPanel<Integer>
{
	private LabelFieldPair            number;
	private LabelFieldPair            type;
	private LabelFieldEllipsisTriplet pilot;
	private StandardButtonTriplet     buttons;
	
	
	public VehicleTab()
	{
		super( "Vehicles", "Details" );		
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
		buttons = addNewSaveDeleteButtons();		
	}
	
	
	//Test
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.add(new VehicleTab());
		frame.pack();
		frame.setVisible(true);		
	}
	
	
	
	

}