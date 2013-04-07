package overwatch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import overwatch.core.Gui;
import overwatch.db.Database;
import overwatch.db.DatabaseConstraints;
import overwatch.db.EnhancedResultSet;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.tabs.SupplyTab;


/**
 * Supply tab logic
 * @author john
 * @version 3
 */

public class SupplyLogic implements TabController{
	
	private final SupplyTab supplyTab;
	
	public SupplyLogic(SupplyTab st)
	{
		this.supplyTab = st;
		attachButtonEvents();
		setupTabChangeActions();
	}
	
	
	
	
	public void respondToTabSelect() {
		populateTabList();
	}


	public JPanel getTab() {
		return supplyTab;
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	public void setupTabChangeActions()
	{
		Gui.getCurrentInstance().addTabSelectNotify(this);
	}
	
	
	
	public void attachButtonEvents()
	{
		setupButtonActions();
		populateTabList();
		supplyListChange();
		setupFieldValidators();
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
			populateSupplyFields(supplyTab.getSelectedItem());
			}
		});
	}
	
	
	
	
	public void populateSupplyFields(Integer supplyNo)
	{
		if(supplyNo == null)
		{
			supplyTab.setEnableFieldsAndButtons(false);
			supplyTab.clearFields();
			return;
		}
		else
		{
			supplyTab.setEnableFieldsAndButtons(true);
		}
		
		
		EnhancedResultSet ers = Database.query("SELECT *" +
											   "FROM Supplies " +
											   "WHERE supplyNo = " + supplyNo);
		
		supplyTab.number.field.setText("" + ers.getElemAs("supplyNo", Integer.class));
		supplyTab.type.field.setText(ers.getElemAs( "type", String.class ));
		supplyTab.amount.field.setText("" + ers.getElemAs("count", Integer.class));
		
	}
	
	
	
	
	public void setupFieldValidators()
	{
		supplyTab.addTypeValidator(new CheckedFieldValidator() {
			public boolean check(String text) {
				return DatabaseConstraints.isValidName(text);
			}
		});
		
		supplyTab.addAmountValidator(new CheckedFieldValidator() {
			public boolean check(String text) {
				return DatabaseConstraints.isValidAmount(text);
			}
		});
		
		supplyTab.addNumberValidator(new CheckedFieldValidator() {
			public boolean check(String text) {
				return DatabaseConstraints.numberExists(text);
			}
		});		
	}
	
}
