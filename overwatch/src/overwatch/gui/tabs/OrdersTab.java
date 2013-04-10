


package overwatch.gui.tabs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import overwatch.core.Gui;
import overwatch.gui.LabelFieldPair;
import overwatch.gui.ScrollableTextPanel;
import overwatch.gui.SearchPanel;





public class OrdersTab extends JPanel
{
	public final LabelFieldPair       sentBy;
	public final LabelFieldPair       sentTo;
	public final LabelFieldPair       subject;
	public final SearchPanel<Integer> ordersIn;
	public final SearchPanel<Integer> ordersOut;
	public final ScrollableTextPanel  textPanel;
	
	
	
	
	
	public OrdersTab()
	{
		super( new MigLayout( "debug", "[][grow,fill][]", "[grow,fill][]" ) );
		
		sentBy    = new LabelFieldPair( "From:" );
		sentTo    = new LabelFieldPair( "To:" );
		subject   = new LabelFieldPair( "Subject:" );
		ordersIn  = new SearchPanel<Integer>( "Orders Received \u2193" );
		ordersOut = new SearchPanel<Integer>( "Orders Sent \u2191" );
		textPanel = new ScrollableTextPanel( "Message" );
		
		String searchMigParams = "wmin 192px, wmax 224px, height 100%";
		
		add( ordersIn,  searchMigParams );	
//		add( sentBy .label, "split 2, alignx right" );
//		add( sentBy .field, "wrap, skip 1"    );
//		add( sentTo .label, "split 2, alignx right" );
//		add( sentTo .field, "wrap"    );
//		add( subject.label, "split 2, alignx right" );
//		add( subject.field, "wrap"    );
		add( textPanel );
		add( ordersOut, searchMigParams );
		
		
		// TODO make message panel
	}
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		
		JFrame frame = new JFrame();
		OrdersTab ot = new OrdersTab();
		
		frame.add(ot);
		frame.pack();
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
