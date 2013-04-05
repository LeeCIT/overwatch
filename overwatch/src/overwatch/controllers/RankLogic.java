package overwatch.controllers;
import java.awt.event.*;
import javax.swing.event.*;
import overwatch.db.Database;
import overwatch.db.DatabaseConstraints;
import overwatch.db.EnhancedResultSet;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.tabs.RankTab;





/**
 * RankTab logic
 * @author john
 * @Version 3
 */





public class RankLogic 
{
	private final RankTab rankTab;
		
	
	public RankLogic(RankTab rt)
	{
		this.rankTab = rt;
		attachButtonEvents();		
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
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
		if(rankNo == null)
		{
			rankTab.setEnableFieldsAndButtons(false);
			rankTab.clearFields();
			return;
		}
		else
		{
			rankTab.setEnableFieldsAndButtons(true);
		}
		
		
		EnhancedResultSet ers = Database.query("SELECT *" +
											   "FROM Ranks " +
											   "WHERE rankNo = " + rankNo);
		
		rankTab.number.field.setText("" + ers.getElemAs("rankNo", Integer.class));
		rankTab.name.field.setText(ers.getElemAs( "name", String.class ));
		rankTab.privileges.field.setText("" + ers.getElemAs("privilegeLevel", Integer.class));		
	}
	
	
	
	
	public void setupFieldValidators()
	{
		rankTab.addNameValidator(new CheckedFieldValidator() {
			public boolean check(String text) {				
				return DatabaseConstraints.isValidName(text);
			}
		});
	}
	
	
	
	
	
	public void rankListChange()
	{
		//TODO populate the fields here
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
				System.out.println("Clicked save");		
			}
		});
	}
	
	
}
