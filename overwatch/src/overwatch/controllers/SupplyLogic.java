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
 * version 1
 */

public class SupplyLogic {
	
	public SupplyLogic(SupplyTab st)
	{
		attachButtonEvents(st);
	}
	
	
	
	public void attachButtonEvents(SupplyTab st)
	{
		newSupply(st);
		deleteSupply(st);
		saveSupply(st);	
		populateTabList(st);
		supplyListChange(st);
	}
	
	
	
	
	private static void populateTabList(SupplyTab st)
	{
		st.setSearchableItems(
			Database.queryKeyNamePairs( "Supplies", "supplyNo", "type", Integer[].class )
		);
	}
	
	
	
	
	public void supplyListChange(final SupplyTab st)
	{
		st.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			System.out.println(st.getSelectedItem());	
			}
		});
	}
	
	
	
	
	public void newSupply(SupplyTab st)
	{
		st.addNewListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked add new");	
			}
		});
	}
	
	
	
	
	public void deleteSupply(SupplyTab st)
	{
		st.addDeleteListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked delete");
			}
		});
	}
	
	
	
	
	public void saveSupply(SupplyTab st)
	{
		st.addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked save");
			}
		});
	}

}
