


package overwatch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import overwatch.core.Gui;
import overwatch.db.DatabaseConstraints;
import overwatch.db.EnhancedResultSet;
import overwatch.db.Orders;
import overwatch.db.Personnel;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.SearchPanel;
import overwatch.gui.tabs.OrderTab;
import overwatch.gui.tabs.OrderTabCreateDialog;
import overwatch.security.BackgroundCheck;
import overwatch.security.BackgroundMonitor;
import overwatch.security.LoginManager;
import overwatch.util.DateSys;





/**
 * Implements the program logic for the Orders tab.
 * Controls saving, loading, security checking etc.
 * 
 * @author  Lee Coakley
 * @version 3
 */





public class OrderLogic extends TabController<OrderTab>
{
	private boolean enableSearchPanelEvents;
	
	
	
	
	
	/**
	 * Plug the GUI tab into the controller.
	 * @param tab
	 */
	public OrderLogic( OrderTab tab )
	{
		super( tab );
		enableSearchPanelEvents = true;
		createBackgroundMonitor();
	}
	
	
	
	
	
	public void respondToTabSelect() {	
		populatePanels();
	}

	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////	
	
	private void doCreateOrder() {
		createOrderCreator();
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
			Orders.getOrdersAndSubjectsSentTo( LoginManager.currentUser() )
		);
		
		tab.ordersOut.setSearchableItems(
			Orders.getOrdersAndSubjectsSentBy( LoginManager.currentUser() )
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
		
		tab.messagePanel.sentBy .field.setText( (sentBy != null)  ?  sentBy  :  "<deleted user>" );
		tab.messagePanel.sentTo .field.setText( (sentTo != null)  ?  sentTo  :  "<deleted user>" );
		tab.messagePanel.date   .field.setText( DateSys.format( sentDate ) );
		tab.messagePanel.subject.field.setText( ers.getElemAs( "subject",  String.class ) );
		tab.messagePanel.body         .setText( ers.getElemAs( "body",     String.class ) );
	}
	
	
	
	
	
	private void createBackgroundMonitor()
	{
		BackgroundMonitor bgm = new BackgroundMonitor( 10000 );
		
		bgm.addBackgroundCheck( new BackgroundCheck() {
			public void onCheck()
			{
				// TODO check for messages created in the last 10 seconds
				//refreshSearchPanels();
			}
		});
	}
	
	
	
	
	
	private void createOrderCreator()
	{
		final OrderTabCreateDialog o = new OrderTabCreateDialog( tab.buttCreateNew );
		
		
		o.addSendButtonListener( new ActionListener() {
			public void actionPerformed( ActionEvent e )
			{
				int messageLen = o.message.body.getText().length();
				if (messageLen > 32768) {
					Gui.showErrorDialogue(
						"Message Too Long",
						"Maximum message length is 32768 characters.  You have " + messageLen + "."
					);
				}
				
				String subject = o.message.subject.field.getText();
				String sendTo  = o.message.sentTo .field.getText();
				String body    = o.message.body         .getText();
				
				Orders.create(
					subject,
					body,
					LoginManager.currentUser(),
					Personnel.getNumberFromLogin( sendTo )
				);				
			}
		});
		
		
		o.addValidatorSend( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.isValidName( text )
				    && DatabaseConstraints.personExists( text );
			}
		});
		
		
		o.addValidatorSubject( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.isValidName( text );
			}
		});
	}
	
	
	
	
	
	protected void attachEvents() {
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
		
		// IN
		tab.addOrdersInSelectListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e )
			{				
				if ( ! enableSearchPanelEvents)
					return;
				
				Integer orderNo = IN.getSelectedItem();
				tab.buttMarkAsDone.setEnabled( orderNo != null );
				
				clearSelectionWithoutEvent( OUT );
				
				if (orderNo != null) {
					Orders.markAsRead( orderNo );
					refreshSearchPanels();
					populateMessagePanel( orderNo );
				}
			}
		});
		
		
		// OUT
		tab.addOrdersOutSelectListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ) 
			{				
				if ( ! enableSearchPanelEvents)
					return;
				
				tab.buttMarkAsDone.setEnabled( false );
				
				clearSelectionWithoutEvent( IN );
				
				if (OUT.hasSelectedItem())
					populateMessagePanel( OUT.getSelectedItem() );
			}
		});
	}
	
	
	
	
	
	private void clearSelectionWithoutEvent( SearchPanel<Integer> panel )
	{
		boolean lastVal = enableSearchPanelEvents;
		
		enableSearchPanelEvents = false;
		panel.setSelectedItem( null );
		enableSearchPanelEvents = lastVal;
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
























