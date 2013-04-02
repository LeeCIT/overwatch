


package overwatch.gui;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import overwatch.db.Database;
import overwatch.db.EnhancedResultSet;
import net.miginfocom.swing.MigLayout;




/**
 * Implements the Personnel tab for the main interface.
 * The search panel here relates by personNo.
 * 
 * @author  John Murphy
 * @author  Lee Coakley
 * @version 2
 *
 */




public class PersonnelTab extends JPanel
{
	private SearchPanel<Integer> searchPanel;
	
	private JLabel personnelNameLabel;
	private JLabel detailsForLabel;
	private JLabel personnelAgeLabel;
	private JLabel personnelSexLabel;
	private JLabel personnelRankLabel;
	private JLabel personnelSalaryLabel;
	
	private JTextField personnelName;
	private JTextField personnelAge;
	private JTextField personnelSex;
	private JTextField personnelRank;
	private JTextField personnelSalary;
	
	private JButton changeLogIn;
	private JButton addNew;
	private JButton save;
	private JButton delete;
	
	
	
	
	
	public PersonnelTab()
	{
		super( new MigLayout("debug") );
		setupComponents();
		
		
		EnhancedResultSet ers = Database.query(
			"select personNo, name from Personnel;"
		);
		populateSearchPanel( ers );
	};
	
	
	
	
	
	public void populateSearchPanel( EnhancedResultSet ers )
	{
		System.out.println( ers );
		Integer[] nums  = ers.getColumnAs( "personNo", Integer[].class );
		String[]  names = ers.getColumnAs( "name",     String[].class  );
		
		NameRefPairList<Integer> pairs = new NameRefPairList<Integer>( nums, names );
		
		searchPanel.setSearchableItems( pairs );
	}
	
	
	
	
	
	public void addSearchPanelListSelectionListener( ListSelectionListener lis ) {
		searchPanel.addListSelectionListener( lis );
	}
	
	public void addSaveListener( ActionListener e ) {
		save.addActionListener(e);
	}
	
	public void addDeleteListener( ActionListener e ) {
		delete.addActionListener(e);
	}
	
	public void addNewListener( ActionListener e ) {
		addNew.addActionListener(e);
	}
	
	public void addChangeLoginListener( ActionListener e ) {
		changeLogIn.addActionListener(e);
	}	
	
	
	
	
	
	
	

	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private void setupComponents()
	{
		searchPanel				= new SearchPanel<Integer>( "Personnel" );
		personnelName			= new JTextField();
		personnelNameLabel		= new JLabel("Name:");
		detailsForLabel			= new JLabel("Details for ");  //A variable with the whatever selected from the list will go here
		personnelAgeLabel		= new JLabel("Age:");
		personnelAge			= new JTextField();
		personnelSexLabel		= new JLabel("Sex:");
		personnelRankLabel		= new JLabel("Rank:");
		personnelSalaryLabel	= new JLabel("Salary");
		personnelSex			= new JTextField();
		personnelRank			= new JTextField();
		personnelSalary			= new JTextField();
		changeLogIn				= new JButton("Change Login details");
		addNew					= new JButton("Add new");
		save					= new JButton("Save");
		delete					= new JButton("Delete");
					
		// Add stuff to the main panel
		add( searchPanel,          "west" );
		add( detailsForLabel,      "wrap" );
		add( personnelNameLabel,   "alignx right" );
		add( personnelName,        "wrap" );
		add( personnelAgeLabel,    "alignx right" );
		add( personnelAge,         "grow, wrap" );
		add( personnelSexLabel,    "alignx right" );
		add( personnelSex, 		   "grow, wrap" );
		add( personnelRankLabel,   "alignx right" );
		add( personnelRank,        "grow, wrap" );
		add( personnelSalaryLabel, "alignx right");
		add( personnelSalary,      "grow, wrap");
		add( changeLogIn, 		   "wrap");
		add( addNew );
		add( save );
		add( delete );		
	}
	
	
	
	
	
	

}













































