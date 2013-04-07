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
 * @author john
 * @Version 4
 */





public class RankLogic implements TabController
{
	private final RankTab rankTab;
		
	
	public RankLogic(RankTab rt)
	{
		this.rankTab = rt;
		attachButtonEvents();	
		setupTabChangeActions();
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
	
	
	
	
	public void setupTabChangeActions()
	{
		Gui.getCurrentInstance().addTabSelectNotify(this);
	}
	
	
	public void attachButtonEvents()
	{
		setupButtonActions();
		populateTabList();
		rankListChange();
		setupFieldValidators();
	}
	
	
	
	
	private void populateTabList()
	{
		rankTab.setSearchableItems(
			Database.queryKeyNamePairs( "Ranks", "rankNo", "name", Integer[].class )
		);
	}
	
	
	
	
	
	public void populateRankFields(Integer rankNo)
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
	
	
	
	
	
	public void setupFieldValidators()
	{
		rankTab.addNameValidator(new CheckedFieldValidator() {
			public boolean check(String text) {				
				return DatabaseConstraints.isValidName(text);
			}
		});
		
		
		rankTab.addNumberValidator(new CheckedFieldValidator() {
			public boolean check(String text) {
				return DatabaseConstraints.numberExists(text);
			}
		});
		
		
		rankTab.addPrivilegesValidator(new CheckedFieldValidator() {
			public boolean check(String text) {
				return Validator.isPositiveInt( text );
			}
		} );
	}
	
	
	
	
	
	public void rankListChange()
	{
		rankTab.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				populateRankFields(rankTab.getSelectedItem());
			}
		});
		
	}
	
	
	
	
	public void setupButtonActions()
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
	
	
	
	
	
	public void doSave()
	{
		int selectedItem = rankTab.getSelectedItem();
		
		Database.update("UPDATE Ranks " +
				"SET name = '" + rankTab.name.field.getText() +
				"', privilegeLevel = " + rankTab.privileges.field.getText() + 
				" WHERE rankNo = " + selectedItem + " ;");
		
		populateTabList();
		rankTab.setSelectedItem(selectedItem);
	}

	
}
