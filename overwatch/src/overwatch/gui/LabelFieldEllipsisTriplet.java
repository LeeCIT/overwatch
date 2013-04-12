


package overwatch.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;





/**
 * Immutable struct for GenericPanel return values.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class LabelFieldEllipsisTriplet
{
	public final JLabel       label;
	public final CheckedField field;
	public final JButton      button;
	
	
	
	
	
	public LabelFieldEllipsisTriplet( JLabel label, CheckedField field, JButton button )
	{
		this.label  = label;
		this.field  = field;
		this.button = button;
	}
	
	
	
	
	
	public LabelFieldEllipsisTriplet( String labelText )
	{
		this.label  = new JLabel( labelText );
		this.field  = new CheckedField();
		this.button = new JButton( "..." );
	}
}
