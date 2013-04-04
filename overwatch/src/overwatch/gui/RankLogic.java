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
	EnhancedResultSet results;
	
	
	public RankLogic(RankTab rt)
	{
		results  = Database.query("SELECT * FROM Ranks");		
		rt.populateSearchPanel(results);		
		attatchEvents(rt);
	}
	
	
	
	public void attatchEvents(RankTab rt)
	{
		addNewRank(rt);
		deleteRank(rt);
		saveRank(rt);
		rankListChange(rt);
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
