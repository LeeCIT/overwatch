


package overwatch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import overwatch.core.Gui;
import overwatch.gui.tabs.OrderTab;





/**
 * Implements the program logic for the Orders tab.
 * Controls saving, loading, security checking etc.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class OrderLogic extends TabController
{
	private final OrderTab tab;
	
	
	
	
	
	/**
	 * Plug the GUI tab into the controller.
	 * @param tab
	 */
	public OrderLogic( OrderTab tab )
	{
		this.tab = tab;
		
		attachEvents();
	}
	
	
	
	
	
	public JPanel getTab() {
		return tab;
	}
	
	
	
	
	
	public void respondToTabSelect() {	
		populateLists();
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private void doCreateOrder()
	{
		// TODO
		System.out.println( "create" );
	}
	
	
	
	
	
	private void doMarkAsDone()
	{
		// TODO Personnel save
		System.out.println( "mark as done" );
	}
	
	
	
	
	
	private void populateLists()
	{
		
	}
	
	
	
	
	
	private void populateMessagePanel( Integer orderNo )
	{
		
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
		
	}
	
	
	
	
	
	private void setupButtonActions()
	{
		tab.addCreateOrderListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				doMarkAsDone();
			}
		});
		
		
		tab.addMarkAsDoneListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				doMarkAsDone();
			}
		});
	}
	
}
























