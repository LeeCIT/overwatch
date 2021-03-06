


package overwatch.gui.tabs;

import javax.swing.JFrame;
import overwatch.controllers.RankLogic;
import overwatch.core.Gui;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldPair;





/**
 * Rank tab
 * 
 * @author john
 * @version 5
*/





public class RankTab extends GenericPanelButtoned<Integer>
{

	public final LabelFieldPair number;
	public final LabelFieldPair name;
	public final LabelFieldPair securityLevel;	
	
	
	
	
	
	public RankTab()
	{

		super( "Rank" );		
		
		number		  = addLabelledField( "Number:"         );
		name    	  = addLabelledField( "Name:"           );
		securityLevel = addLabelledField( "Security Level:" );
		
		number.field.setEditable(false);
	}
	
	
	
	
	
	// Validate
	public void addNameValidator      (CheckedFieldValidator v)	{ name         .field.addValidator(v); }
	public void addNumberValidator    (CheckedFieldValidator v)	{ number       .field.addValidator(v); }
	public void addPrivilegesValidator(CheckedFieldValidator v)	{ securityLevel.field.addValidator(v); }

	
	
	
	
	
	
	
	
	//Test
	public static void main(String[] args)
	{
		Gui.setNativeStyle();
		
		JFrame frame = new JFrame();		
		RankTab rt 	 = new RankTab();
		
		frame.add(rt);
		frame.pack();
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		new RankLogic(rt);
	}

}

