


package overwatch.controllers;

import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.event.*;
import overwatch.core.Gui;
import overwatch.db.Database;
import overwatch.db.DatabaseConstraints;
import overwatch.db.EnhancedResultSet;
import overwatch.db.Ranks;
import overwatch.db.Supplies;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.tabs.RankTab;
import overwatch.util.Validator;





/**
 * RankTab logic
 * 
 * @author john
 * @Version 5
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
		Integer rankNo    = rankTab.getSelectedItem();
		String  rankName  = rankTab.name.      field.getText();
		Integer rankLevel = rankTab.privileges.field.getTextAsInt();
		
		if ( ! Ranks.exists(rankNo)) {
			Gui.showErrorDialogue( "Failed to save", "The rank no longer exists." );
			populateTabList(); // Reload
			return;
		}
		
		Database.update(
			"UPDATE Ranks "          +
			"SET name           = '" + rankName  + "'," +
			"    privilegeLevel = "  + rankLevel + " "  +
			"WHERE rankNo = " + rankNo + " ;"
		);
		
		populateTabList();
		rankTab.setSelectedItem(rankNo);
	}
	
	
	
	
	private void createNew()
	{
		Integer rankNo			= Ranks.getNewRankId();
		String 	rankName		= Integer.toString(rankNo);
		Integer privilegeNum	= 0;
		
		Database.update("INSERT INTO Ranks VALUES(" +
				rankNo + ", '" + rankName + "', " + privilegeNum + ");");
		
		populateTabList();
		rankTab.setSelectedItem(rankNo);
		rankTab.name.field.setText(rankName);
		rankTab.privileges.field.setText("" + privilegeNum);
	}
	
	
	
	
	private void delete()
	{
		Integer rankNum = rankTab.getSelectedItem();
		Database.update("DELETE FROM Ranks WHERE rankNo = " + rankNum + ";");
		
		populateTabList();
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
				createNew();				
			}
		});	
	
		rankTab.addDeleteListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});		
	
		rankTab.addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
	}

	
}
