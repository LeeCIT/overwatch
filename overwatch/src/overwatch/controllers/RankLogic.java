package overwatch.controllers;
import java.awt.event.*;
import javax.swing.event.*;
import overwatch.db.Database;
import overwatch.db.EnhancedResultSet;
import overwatch.gui.tabs.RankTab;





/**
 * RankTab logic
 * @author john
 * Version 3
 */





public class RankLogic 
{
	RankTab rankTab;
		
	
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
		setupButtonActions(rankTab);
		populateTabList(rankTab);
		rankListChange(rankTab);
	}
	
	
	
	
	private static void populateTabList(RankTab rt)
	{
		rt.setSearchableItems(
			Database.queryKeyNamePairs( "Ranks", "rankNo", "name", Integer[].class )
		);
	}
	
	
	
	
	
	public void populateRankFields(Integer rankNo)
	{
		if(rankNo == null)
		{
			rankTab.setEnableFieldsAndButtons(false);
			rankTab.clearFields();
		}
		else
		{
			rankTab.setEnableFieldsAndButtons(true);
		}
		
		
		EnhancedResultSet ers = Database.query("SELECT *" +
											   "FROM Ranks " +
											   "WHERE rankNo = " + rankNo);
		
		rankTab.name.field.setText(ers.getElemAs( "name", String.class ));
		
	}
	
	
	
	
	
	public void rankListChange(final RankTab rt)
	{
		//TODO populate the fields here
		rt.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				System.out.println(rt.getSelectedItem());	
				populateRankFields(rt.getSelectedItem());
			}
		});
		
	}
	
	
	
	
	public void setupButtonActions(final RankTab rt)
	{
		
		rt.addNewListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked add new");				
			}
		});	
	
		rt.addDeleteListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked delete");
			}
		});		
	
		rt.addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked save");		
			}
		});
	}
	
	
}
