package overwatch.controllers;
import java.awt.event.*;
import javax.swing.event.*;
import overwatch.db.Database;
import overwatch.gui.tabs.RankTab;





/**
 * RankTab logic
 * @author john
 * Version 3
 */





public class RankLogic 
{
		
	
	public RankLogic(RankTab rt)
	{
		attachButtonEvents(rt);		
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	public void attachButtonEvents(RankTab rt)
	{
		setupButtonActions(rt);
		populateTabList(rt);
		rankListChange(rt);
	}
	
	
	
	
	private static void populateTabList(RankTab rt)
	{
		rt.setSearchableItems(
			Database.queryKeyNamePairs( "Ranks", "rankNo", "name", Integer[].class )
		);
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
	
	
	
	
	
	
	public void rankListChange(final RankTab rt)
	{
		//TODO populate the fields here
		rt.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				System.out.println(rt.getSelectedItem());	
			}
		});
		
	}
	
	
	
	
	
	
	
}
