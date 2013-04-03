


package overwatch.gui;

import javax.swing.JLabel;
import javax.swing.JTextField;





/**
 * Immutable struct for GenericPanel return values.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class LabelFieldPair
{
	public final JLabel     label;
	public final JTextField field;
	
	
	
	
	
	public LabelFieldPair( JLabel label, JTextField field )
	{
		this.label  = label;
		this.field  = field;
	}
}
