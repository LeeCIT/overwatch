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
 *
 */





public class RankLogic 
{
	RankTab rankTab;
	EnhancedResultSet results;
	
	public RankLogic(RankTab rt)
	{
		rankTab = rt;
		
		results  = Database.query("SELECT * FROM Ranks");
		
		rankTab.populateSearchPanel(results);
		rankTab.searchPanel.addListSelectionListener(new RankListChange() );
		rankTab.buttons.addNew.addActionListener((new AddNewRank() ));
		rankTab.buttons.delete.addActionListener(new DeleteRank() );
		rankTab.buttons.save.addActionListener(new SaveRank() );
	}	
	
	
	
	/**
	 * When the list is selected populate the fields
	 * @author john
	 *
	 */
	public class RankListChange implements ListSelectionListener
	{

		public void valueChanged(ListSelectionEvent e)
		{
			Object[] row = results.getRow(rankTab.searchPanel.getSelectedIndex());//Gets the row depending on what was selected
			rankTab.name.field.setText((String) row[1]);//Gets the name of the rank
		}
		
	}
	
	
	
	/**
	 * Add a new rank
	 * @author john
	 *
	 */
	public class AddNewRank implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//TODO		
		}
		
	}
	
	
	
	/**
	 * Delete a rank
	 * @author john
	 *
	 */
	public class DeleteRank implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// TODO			
		}
		
	}
	
	
	
	
	/**
	 * Save a rank
	 * @author john
	 *
	 */
	public class SaveRank implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			// TODO			
		}
		
	}
}
