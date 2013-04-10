


package overwatch.gui.tabs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import overwatch.core.Gui;
import overwatch.gui.ScrollableTextPanel;
import overwatch.gui.SearchPanel;





public class OrdersTab extends JPanel
{
	public final SearchPanel<Integer> leftSearch;
	public final SearchPanel<Integer> rightSearch;
	public final ScrollableTextPanel  textPanel;
	
	
	
	
	
	public OrdersTab()
	{
		super( new MigLayout( "debug", "[][grow,fill][]", "[grow,fill][]" ) );
		
		leftSearch  = new SearchPanel<Integer>( "Orders Received \u2193" );
		rightSearch = new SearchPanel<Integer>( "Orders Sent \u2191" );
		textPanel   = new ScrollableTextPanel( "Message" );
		
		String searchMigParams = "wmin 192px, wmax 224px, height 100%";
		
		add( leftSearch,  searchMigParams );
		add( textPanel );
		add( rightSearch, searchMigParams );
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
