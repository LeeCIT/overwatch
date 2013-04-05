package overwatch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import overwatch.db.Database;
import overwatch.gui.tabs.SupplyTab;


/**
 * Supply tab logic
 * @author john
 * @version 2
 */

public class SupplyLogic {
	
	private final SupplyTab supplyTab;
	
	public SupplyLogic(SupplyTab st)
	{
		this.supplyTab = st;
		attachButtonEvents();
	}
	
	
	
	public void attachButtonEvents()
	{
		setupButtonActions();
		populateTabList();
		supplyListChange();
	}
	
	
	
	
	public void setupButtonActions()
	{
		
		supplyTab.addNewListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked add new");	
			}
		});
	
		supplyTab.addDeleteListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked delete");
			}
		});
	
		supplyTab.addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked save");
			}
		});
	

	}
	
	
	
	
	private void populateTabList()
	{
		supplyTab.setSearchableItems(
			Database.queryKeyNamePairs( "Supplies", "supplyNo", "type", Integer[].class )
		);
	}
	
	
	
	
	public void supplyListChange()
	{
		supplyTab.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			System.out.println(supplyTab.getSelectedItem());	
			}
		});
	}
	
}
