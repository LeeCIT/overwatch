


package overwatch.gui;

import javax.swing.JLabel;





/**
 * Immutable struct for GenericPanel return values.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class LabelFieldPair
{
	public final JLabel       label;
	public final CheckedField field;
	
	
	
	
	
	public LabelFieldPair( JLabel label, CheckedField field )
	{
		this.label  = label;
		this.field  = field;
	}
	
	
	
	
	
	public LabelFieldPair( String labelText )
	{
		this.label = new JLabel( labelText );
		this.field = new CheckedField( GenericPanel.defaultFieldWidth );
	}
}
