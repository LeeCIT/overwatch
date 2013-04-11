


package overwatch.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
	public final LabelFieldPair      sentBy;
	public final LabelFieldPair      sentTo;
	public final LabelFieldPair      date;
	public final LabelFieldPair      subject;
	public final ScrollableTextPanel body;
	
	
	
	
	
	public MessagePanel()
	{
		super( new MigLayout( "debug", "[][grow]", "[][][][][grow,fill]" ) );
		
		sentBy  = new LabelFieldPair     ( "From:"    );
		sentTo  = new LabelFieldPair     ( "To:"      );
		subject = new LabelFieldPair     ( "Subject:" );
		date    = new LabelFieldPair     ( "Date:"    );
		body    = new ScrollableTextPanel( "Message"  );
		
		addLabelFieldPair( sentBy  );
		addLabelFieldPair( sentTo  );
		addLabelFieldPair( date    );
		addLabelFieldPair( subject );
		add( body, "spanx 2, grow" );
	}
	
	
	
	
	
	/**
	 * Make all elements editable or not.
	 * @param editable
	 */
	public void setEditable( boolean editable ) {
		sentBy .field.setEditable( editable );
		sentTo .field.setEditable( editable );
		subject.field.setEditable( editable );
		date   .field.setEditable( editable );
		body   .setEditable( editable );
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private void addLabelFieldPair( LabelFieldPair lfp ) {
		add( lfp.label, "alignx right" );
		add( lfp.field, "growx, wrap" );
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
















