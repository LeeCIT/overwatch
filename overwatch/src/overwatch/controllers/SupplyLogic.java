


package overwatch.controllers;

import overwatch.core.Gui;
import overwatch.db.Database;
import overwatch.db.DatabaseConstraints;
import overwatch.db.EnhancedResultSet;
import overwatch.db.Supplies;
import overwatch.db.Vehicles;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.tabs.SupplyTab;
import overwatch.util.Validator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;





/**
 * Supply tab logic
 * 
 * @author  John Murphy
 * @version 3
 */





public class SupplyLogic extends TabController
{
	
	private final SupplyTab supplyTab;
	
	
	
	
	
	public SupplyLogic(SupplyTab st)
	{
		this.supplyTab = st;
		attachEvents();
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
	
	
	
	private void doSave()
	{
		Integer supplyNo    = supplyTab.getSelectedItem();
		String  supplyType  = supplyTab.type  .field.getText();
		Integer supplyCount = supplyTab.amount.field.getTextAsInt();
		
		if ( ! Supplies.exists(supplyNo)) {
			Gui.showErrorDialogue( "Failed to save", "The supply no longer exists." );
			populateTabList(); // Reload
			return;
		}
		
		Database.update(
			"UPDATE Supplies "   +
			"SET type =     '"   + supplyType   +
			"', count =      "   + supplyCount  + 
			" WHERE supplyNo = " + supplyNo + ";"
		);
		
		populateTabList();
		supplyTab.setSelectedItem( supplyNo );
	}
	
	
	
	
	
	private void populateTabList()
	{
		populateFields( null );
		
		supplyTab.setSearchableItems(
			Database.queryKeyNamePairs( "Supplies", "supplyNo", "type", Integer[].class )
		);
	}
	
	
	
	
	private void populateFields(Integer supplyNo)
	{
		if(supplyNo == null)
		{
			supplyTab.setEnableFieldsAndButtons( false );
			supplyTab.clearFields();
			return;
		}
		else {
			supplyTab.setEnableFieldsAndButtons( true );
		}
		
		EnhancedResultSet ers = Database.query(
			"SELECT *         " +
		    "FROM Supplies    " +
		    "WHERE supplyNo = " + supplyNo
		);
		
		supplyTab.number.field.setText( "" + ers.getElemAs( "supplyNo", Integer.class ));
		supplyTab.type  .field.setText(      ers.getElemAs( "type",     String .class ));
		supplyTab.amount.field.setText( "" + ers.getElemAs( "count",    Integer.class ));
		
	}
	
	
	
	
	
	private void attachEvents()
	{
		setupButtonActions();
		setupListSelectActions();
		setupFieldValidators();
	}
	
	
	
	
	
	private void setupTabChangeActions() {
		Gui.getCurrentInstance().addTabSelectNotify( this );
	}
	
	
	
	
	
	private void setupButtonActions()
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
				doSave();				
			}
		});
	}
	
	
		
	
	
	private void setupListSelectActions()
	{
		supplyTab.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				populateFields(supplyTab.getSelectedItem());
			}
		});
	}
	
	
	
	
	
	private void setupFieldValidators()
	{
		supplyTab.addTypeValidator(new CheckedFieldValidator() {
			public boolean check(String text) {
				return DatabaseConstraints.isValidName(text);
			}
		});
		
		
		supplyTab.addAmountValidator(new CheckedFieldValidator() {
			public boolean check(String text) {
				return Validator.isPositiveInt( text );
			}
		});
	}
	
}
