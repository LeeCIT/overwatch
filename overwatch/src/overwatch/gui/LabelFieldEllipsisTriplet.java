


package overwatch.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;





/**
 * Immutable struct for GenericPanel return values.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class LabelFieldEllipsisTriplet
{
	public final JLabel     label;
	public final JTextField field;
	public final JButton    button;
	
	
	
	
	
	public LabelFieldEllipsisTriplet( JLabel label, JTextField field, JButton button )
	{
		this.label  = label;
		this.field  = field;
		this.button = button;
	}
}
