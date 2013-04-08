


package overwatch.controllers;

import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.event.*;
import overwatch.core.Gui;
import overwatch.db.Database;
import overwatch.db.DatabaseConstraints;
import overwatch.db.EnhancedResultSet;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.tabs.RankTab;
import overwatch.util.Validator;





/**
 * RankTab logic
 * 
 * @author john
 * @Version 4
 */





public class RankLogic extends TabController
{
	private final RankTab rankTab;
		
	
	
	
	
	public RankLogic(RankTab rt)
	{
		this.rankTab = rt;
		attachEvents();
	}
	
		
	
	public void respondToTabSelect() {
		populateTabList();
	}
	
	

	public JPanel getTab() {
		return rankTab;
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private void doSave()
	{
		int selectedItem = rankTab.getSelectedItem();
		
		// TODO Validate
		
		Database.update("UPDATE Ranks " +
				"SET name = '" + rankTab.name.field.getText() +
				"', privilegeLevel = " + rankTab.privileges.field.getText() + 
				" WHERE rankNo = " + selectedItem + " ;");
		
		populateTabList();
		rankTab.setSelectedItem(selectedItem);
	}
	
	
	
	
	
	private void populateTabList()
	{
		populateFields( null );
		rankTab.setSearchableItems(
			Database.queryKeyNamePairs( "Ranks", "rankNo", "name", Integer[].class )
		);
	}
	
	
	
	
	
	private void populateFields(Integer rankNo)
	{
		if (rankNo == null)
		{
			rankTab.setEnableFieldsAndButtons(false);
			rankTab.clearFields();
			return;
		}
		else {
			rankTab.setEnableFieldsAndButtons(true);
		}
		
		
		EnhancedResultSet ers = Database.query(
		    "SELECT *       " +
		    "FROM Ranks     " +
		    "WHERE rankNo = " + rankNo);

		rankTab.number    .field.setText("" + ers.getElemAs( "rankNo",         Integer.class ));
		rankTab.name      .field.setText(     ers.getElemAs( "name",           String .class ));
		rankTab.privileges.field.setText("" + ers.getElemAs( "privilegeLevel", Integer.class ));		
	}
	
	
	
	
	
	private void attachEvents() {
		setupTabChangeActions();
		setupButtonActions();
		setupListSelectActions();
		setupFieldValidators();
	}
	
	
	
	
	
	private void setupTabChangeActions() {
		Gui.getCurrentInstance().addTabSelectNotify(this);
	}
	
	
	
	
	
	private void setupFieldValidators()
	{
		rankTab.addNameValidator(new CheckedFieldValidator() {
			public boolean check(String text) {				
				return DatabaseConstraints.isValidName(text);
			}
		});
		
		
		rankTab.addPrivilegesValidator(new CheckedFieldValidator() {
			public boolean check(String text) {
				return Validator.isPositiveInt( text );
			}
		} );
	}
	
	
	
	
	
	private void setupListSelectActions()
	{
		rankTab.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				populateFields(rankTab.getSelectedItem());
			}
		});
		
	}
	
	
	
	
	
	private void setupButtonActions()
	{
		
		rankTab.addNewListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked add new");				
			}
		});	
	
		rankTab.addDeleteListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked delete");
			}
		});		
	
		rankTab.addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
	}

	
}
