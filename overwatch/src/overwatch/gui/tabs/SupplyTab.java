
package overwatch.gui.tabs;

import javax.swing.*;

import overwatch.controllers.SupplyLogic;
import overwatch.core.Gui;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldPair;


/**
 * Implements the supply management tab.
 * 
 * @author  John Murphy
 * @author  Lee Coakley
 * @version 4
 * 
 * TODO: Add uneditable number field
 */




public class SupplyTab extends GenericPanelButtoned<Integer>
{
	
	public final LabelFieldPair name;
	public final LabelFieldPair type;
	public final LabelFieldPair amount;
	public final LabelFieldPair number;
	
	
	public SupplyTab()
	{
		super( "Supplies" );	
		
		number	= addLabelledField("Number:");
		name  	= addLabelledField("Name:");
		type	= addLabelledField("Type:");
		amount  = addLabelledField("Amount:");	
		
		number.field.setEditable(false);
	}
	
	
	
	
	public void addTypeValidator(CheckedFieldValidator v){type.field.addValidator(v); }
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args)
	{
		Gui.setNativeStyle();
		SupplyTab st = new SupplyTab();
		
		JFrame frame = new JFrame();
		frame.add(st);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		new SupplyLogic(st);
	}

}
