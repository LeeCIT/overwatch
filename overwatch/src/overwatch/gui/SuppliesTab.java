
package overwatch.gui;

import javax.swing.*;

import overwatch.core.Gui;


/**
 * Implements the supply management tab.
 * 
 * @author  John Murphy
 * @author  Lee Coakley
 * @version 3
 */




public class SuppliesTab extends GenericPanelButtoned<Integer>
{
	
	private LabelFieldPair name;
	private LabelFieldPair type;
	private LabelFieldPair amount;
	
	
	public SuppliesTab()
	{
		super( "Supplies", "Supply" );		
		setupComponents();
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private void setupComponents()
	{
		name  	= addLabelledField("Name:");
		type	= addLabelledField("Type:");
		amount  = addLabelledField("Amount:");	
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args)
	{
		Gui.setNativeStyle();
		SuppliesTab st = new SuppliesTab();
		
		JFrame frame = new JFrame();
		frame.add(st);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		new SupplyTabLogic(st);
	}

}
