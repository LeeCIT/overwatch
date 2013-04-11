



package overwatch.gui.tabs;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import overwatch.core.Gui;
import overwatch.gui.LabelFieldPair;
import overwatch.gui.MessagePanel;
import overwatch.gui.ScrollableTextPanel;
import overwatch.gui.SearchPanel;





public class OrderTab extends JPanel
{
	public final SearchPanel<Integer> ordersIn;
	public final SearchPanel<Integer> ordersOut;
	public final MessagePanel         messagePanel;
	public final JButton			  buttMarkAsDone;
	public final JButton			  buttCreateNew;
	
	
	
	
	
	public OrderTab()
	{
		super( new MigLayout( "", "[][grow,fill][]", "[grow,fill][]" ) );
		
		ordersIn       = new SearchPanel<Integer>( "Orders Received \u2193" );
		ordersOut      = new SearchPanel<Integer>( "Orders Sent \u2191" );
		messagePanel   = new MessagePanel();
		buttMarkAsDone = new JButton( "Mark as done" );
		buttCreateNew  = new JButton( "Create order..." );
		
		String searchMigParams = "wmin 192px, wmax 192px, height 100%";
		
		add( ordersIn,     searchMigParams );
		add( messagePanel, "wmin 224px, hmin 128px" );
		add( ordersOut,    searchMigParams + ", wrap" );
		add( buttMarkAsDone );
		add( buttCreateNew, "growx" );
		
		messagePanel.setEditable( false );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		
		JFrame frame = new JFrame();
		OrderTab ot = new OrderTab();
		
		frame.add(ot);
		frame.pack();
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
