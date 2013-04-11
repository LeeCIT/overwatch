


package overwatch.gui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;





/**
 * Panel with a ScrollableTextPanel and subject/to/from fields.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class MessagePanel extends JPanel
{
	public final LabelFieldPair      sentBy;
	public final LabelFieldPair      sentTo;
	public final LabelFieldPair      subject;
	public final ScrollableTextPanel body;
	
	
	
	
	
	public MessagePanel()
	{
		super( new MigLayout( "debug", "", "" ) );
		
		sentBy    = new LabelFieldPair     ( "From:"    );
		sentTo    = new LabelFieldPair     ( "To:"      );
		subject   = new LabelFieldPair     ( "Subject:" );
		body      = new ScrollableTextPanel( "Message"  );
		
		
	}
}
















