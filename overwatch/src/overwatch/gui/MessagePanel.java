


package overwatch.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import overwatch.core.Gui;
import net.miginfocom.swing.MigLayout;





/**
 * Panel with a ScrollableTextPanel and subject/to/from fields.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class MessagePanel extends JPanel
{
	public final LabelFieldPair            sentBy;
	public final LabelFieldEllipsisTriplet sentTo;
	public final LabelFieldPair            date;
	public final LabelFieldPair            subject;
	public final ScrollableTextPanel       body;
	
	
	
	
	
	public MessagePanel()
	{
		super( new MigLayout( "", "[][grow]", "[][][][][grow,fill]" ) );
		
		sentBy  = GuiUtil.addLabelledField            ( this, "From:"    );
		sentTo  = GuiUtil.addLabelledFieldWithEllipsis( this, "To:"      );
		subject = GuiUtil.addLabelledField            ( this, "Subject:" );
		date    = GuiUtil.addLabelledField            ( this, "Date:"    );
		body    = new ScrollableTextPanel( "Message"  );
		add( body, "spanx 2, grow" );
	}
	
	
	
	
	
	/**
	 * Make all elements editable or not.
	 * @param editable
	 */
	public void setEditable( boolean editable ) {
		sentBy .field.setEditable( editable );
		sentTo .field.setEditable( editable );
		sentTo .button.setEnabled( editable );
		subject.field.setEditable( editable );
		date   .field.setEditable( editable );
		body         .setEditable( editable );
	}
	
	
	
	
	
	/**
	 * Clear all fields.
	 */
	public void clearAll() {
		sentBy .field.setText( "" );
		sentTo .field.setText( "" );
		subject.field.setText( "" );
		date   .field.setText( "" );
		body         .setText( "" );
	}
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		
		JFrame       frame = new JFrame();
		MessagePanel ot    = new MessagePanel();
		
		frame.add(ot);
		frame.pack();
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
















