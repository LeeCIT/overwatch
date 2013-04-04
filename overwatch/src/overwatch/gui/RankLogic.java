package overwatch.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import overwatch.db.Database;
import overwatch.db.EnhancedResultSet;





/**
 * RankTab logic
 * @author john
 * Version 3
 */





public class RankLogic 
{
		
	
	public RankLogic(RankTab rt)
	{
		attatchButtonEvents(rt);
		
	}
	
	
	
	public void attatchButtonEvents(RankTab rt)
	{
		addNewRank(rt);
		deleteRank(rt);
		saveRank(rt);
		populateTabList(rt);
		rankListChange(rt);
	}
	
	
	
	
	private static void populateTabList(RankTab rt)
	{
		rt.setSearchableItems(
			Database.queryKeyNamePairs( "Ranks", "rankNo", "name", Integer[].class )
		);
	}
	
	
	
	
	
	public void rankListChange(final RankTab rt)
	{
		//TODO populate the fields here
		rt.searchPanel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			System.out.println(rt.searchPanel.getSelectedItem());	
			}
		});
		
	}
	
	
	
	public void addNewRank(RankTab rt)
	{
		rt.buttons.addNew.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked addnew");				
			}
		});	
	}
	
	
	
	public void deleteRank(RankTab rt) 
	{
		rt.buttons.delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked delete");
			}
		});		
	}
	
	
	
	public void saveRank(RankTab rt)
	{
		rt.buttons.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked save");		
			}
		});
	}
}
