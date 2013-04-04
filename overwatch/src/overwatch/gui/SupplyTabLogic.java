package overwatch.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import overwatch.db.Database;


/**
 * Supply tab logic
 * @author john
 * version 1
 */

public class SupplyTabLogic {
	
	public SupplyTabLogic(SuppliesTab st)
	{
		attatchButtonEvents(st);
	}
	
	
	
	public void attatchButtonEvents(SuppliesTab st)
	{
		newSupply(st);
		deleteSupply(st);
		saveSupply(st);	
		populateTabList(st);
		supplyListChange(st);
	}
	
	
	
	
	private static void populateTabList(SuppliesTab st)
	{
		st.setSearchableItems(
			Database.queryKeyNamePairs( "Supplies", "supplyNo", "type", Integer[].class )
		);
	}
	
	
	
	
	public void supplyListChange(final SuppliesTab st)
	{
		st.searchPanel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			System.out.println(st.searchPanel.getSelectedItem());	
			}
		});
	}
	
	
	
	
	public void newSupply(SuppliesTab st)
	{
		st.buttons.addNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked add new");	
			}
		});
	}
	
	
	
	
	public void deleteSupply(SuppliesTab st)
	{
		st.buttons.delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked delete");
			}
		});
	}
	
	
	
	
	public void saveSupply(SuppliesTab st)
	{
		st.buttons.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked save");
			}
		});
	}

}
