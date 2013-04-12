


package overwatch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import overwatch.core.Gui;
import overwatch.db.EnhancedResultSet;
import overwatch.db.Orders;
import overwatch.db.Personnel;
import overwatch.gui.SearchPanel;
import overwatch.gui.tabs.OrderTab;
import overwatch.security.BackgroundCheck;
import overwatch.security.BackgroundMonitor;
import overwatch.security.LoginManager;
import overwatch.util.DateSys;





/**
 * Implements the program logic for the Orders tab.
 * Controls saving, loading, security checking etc.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class OrderLogic extends TabController
{
	private final OrderTab tab;
	
	private boolean enableSearchPanelEvents;
	
	
	
	
	/**
	 * Plug the GUI tab into the controller.
	 * @param tab
	 */
	public OrderLogic( OrderTab tab )
	{
		this.tab = tab;
		
		enableSearchPanelEvents = true;
		
		attachEvents();
		createBackgroundMonitor();
	}
	
	
	
	
	
	public JPanel getTab() {
		return tab;
	}
	
	
	
	
	
	public void respondToTabSelect() {	
		populatePanels();
	}

	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////	
	
	private void doCreateOrder()
	{
		// TODO make JDialog with MessagePanel in it
		System.out.println( "create" );
	}
	
	
	
	
	
	private void doMarkAsDone( Integer orderNo ) {
		Orders.markAsDone( orderNo );
		refreshSearchPanels();
	}
	
	
	
	
	
	private void populatePanels()
	{
		populateMessagePanel( null );
		populateSearchPanels();
	}
	
	
	
	
	
	private void populateSearchPanels()
	{
		tab.ordersIn.setSearchableItems(
			Orders.getOrdersAndSubjectsSentTo( LoginManager.getUser() )
		);
		
		tab.ordersOut.setSearchableItems(
			Orders.getOrdersAndSubjectsSentBy( LoginManager.getUser() )
		);
	}
	
	
	
	
	
	private void refreshSearchPanels()
	{
		enableSearchPanelEvents = false;
		
			Integer inSel  = tab.ordersIn .getSelectedItem();
			Integer outSel = tab.ordersOut.getSelectedItem();
			
			populateSearchPanels();
			
			if (inSel  != null) tab.ordersIn .setSelectedItem( inSel  );
			if (outSel != null) tab.ordersOut.setSelectedItem( outSel );
			
		enableSearchPanelEvents = true;
	}
	
	
	
	
	
	private void populateMessagePanel( Integer orderNo )
	{
		if (orderNo == null) {
			tab.messagePanel.clearAll();
			return;
		}
		
		EnhancedResultSet ers = Orders.getMessageContents( orderNo );
		
		if (ers.isEmpty()) {
			showDeletedError( "order" );
			return;
		}
		
		String sentBy   = Personnel.getLoginName(  ers.getElemAs( "sentBy",   Integer.class   )           );
		String sentTo   = Personnel.getLoginName(  ers.getElemAs( "sentTo",   Integer.class   )           );
		Date   sentDate = new Date(                ers.getElemAs( "sentDate", Timestamp.class ).getTime() );
		
		tab.messagePanel.sentBy .field.setText( sentBy );
		tab.messagePanel.sentTo .field.setText( sentTo );
		tab.messagePanel.date   .field.setText( DateSys.format( sentDate ) );
		tab.messagePanel.subject.field.setText( ers.getElemAs( "subject",  String.class ) );
		tab.messagePanel.body         .setText( ers.getElemAs( "body",     String.class ) );
	}
	
	
	
	
	
	private void createBackgroundMonitor()
	{
		BackgroundMonitor bgm = new BackgroundMonitor( 10000 );
		
		bgm.addBackgroundCheck( new BackgroundCheck() {
			public void onCheck() {
				refreshSearchPanels();
			}
		});
	}
	
	
	
	
	
	private void attachEvents()
	{
		setupTabChangeActions();
		setupSelectActions   ();
		setupButtonActions   ();
	}
	
	
	
	
	
	private void setupTabChangeActions() {
		Gui.getCurrentInstance().addTabSelectNotify( this );	
	}
	
	
	
	
	
	private void setupSelectActions()
	{
		final SearchPanel<Integer> IN  = tab.ordersIn;
		final SearchPanel<Integer> OUT = tab.ordersOut; 
		
		// Incoming
		tab.addOrdersInSelectListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e )
			{
				if ( ! enableSearchPanelEvents)
					return;
				
				if (IN.hasSelectedItem()) {
					Integer orderNo = IN.getSelectedItem();
					populateMessagePanel( orderNo );
					tab.buttMarkAsDone.setEnabled( orderNo != null );
				}
			}
		});
		
		
		// Outgoing
		tab.addOrdersOutSelectListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ) 
			{
				if ( ! enableSearchPanelEvents)
					return;
				
				if (OUT.hasSelectedItem())
					populateMessagePanel( OUT.getSelectedItem() );
			}
		});
	}
	
	
	
	
	
	private void setupButtonActions()
	{
		tab.addCreateOrderListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				doCreateOrder();
			}
		});
		
		
		tab.addMarkAsDoneListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				doMarkAsDone( tab.ordersIn.getSelectedItem() );
			}
		});
	}
	
}
























