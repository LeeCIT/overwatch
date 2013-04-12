


package overwatch.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;





public class GuiUtil
{
	public static final int defaultFieldWidth = 24;
	
	
	
	
	
	/**
	 * Add a CheckedField and JLabel, then wrap to the next line.
	 * @param panel
	 * @param label
	 * @return LabelFieldPair
	 */
	public static LabelFieldPair addLabelledField( JPanel panel, String label )
	{
		JLabel       l = new JLabel( label );
		CheckedField f = new CheckedField( GuiUtil.defaultFieldWidth );
		
		panel.add( l, "alignx right" );
		panel.add( f, "growx, wrap"  );
		
		return new LabelFieldPair( l,f );
	}
	
	
	
	
	
	/**
	 * Add a CheckedField, JLabel and ellipsis JButton, then wrap to the next line.
	 * @param panel
	 * @param label
	 * @return LabelFieldEllipsisTriplet
	 */
	public static LabelFieldEllipsisTriplet addLabelledFieldWithEllipsis( JPanel panel, String label )
	{
		JLabel       l = new JLabel ( label );
		CheckedField f = new CheckedField( GuiUtil.defaultFieldWidth );
		JButton      b = new JButton( "..." );
		
		panel.add( l, "alignx right"    );
		panel.add( f, "growx, split 2"  );
		panel.add( b, "wmax 32px, wrap" );
		
		return new LabelFieldEllipsisTriplet( l,f,b );
	}
	
	
	
	
	
	/**
	 * Add a separator spanning two columns, then wrap.
	 * @param panel
	 * @return JSeparator
	 */
	public static JSeparator addSeparator( JPanel panel ) {
		JSeparator sep = new JSeparator();
		panel.add( sep, "span 2, growx, wrap" );
		return sep;
	}
	
}























































