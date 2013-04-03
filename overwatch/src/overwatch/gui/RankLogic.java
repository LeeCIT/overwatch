package overwatch.gui;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import overwatch.db.Database;
import overwatch.db.EnhancedResultSet;





/**
 * Rank logic
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
	}	
	
	
	
	
	public class RankListChange implements ListSelectionListener
	{

		public void valueChanged(ListSelectionEvent e)
		{
			Object[] row = results.getRow(rankTab.searchPanel.getSelectedIndex());
			rankTab.name.field.setText((String) row[1]);
		}
		
	}
}
