


package overwatch.gui;

import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import overwatch.db.EnhancedResultSet;





/**
 * Implements the Personnel tab for the main interface.
 * The search panel here relates by personNo.
 * 
 * @author  John Murphy
 * @author  Lee Coakley
 * @version 2
 *
 */





public class PersonnelTab extends GenericPanel<Integer>
{
	private LabelFieldPair            name;
	private LabelFieldPair            age;
	private LabelFieldPair            sex;
	private LabelFieldPair            salary;
	private LabelFieldEllipsisTriplet rank;
	private StandardButtonTriplet     buttons;
	
	private JButton changeLoginDetails;
	
	
	
	
	
	public PersonnelTab()
	{
		super( "Personnel", "Details" );
		
		setupComponents();
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
	
	
	
	public void addNewListener( ActionListener e ) {
		buttons.addNew.addActionListener(e);
	}
	
	
	
	public void addSaveListener( ActionListener e ) {
		buttons.save.addActionListener(e);
	}
	
	
	
	public void addDeleteListener( ActionListener e ) {
		buttons.delete.addActionListener(e);
	}
	
	
	
	public void addChangeLoginListener( ActionListener e ) {
		changeLoginDetails.addActionListener(e);
	}	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private void setupComponents()
	{
		name    = addLabelledField( "Name:" );
		age     = addLabelledField( "Age"   );
		sex     = addLabelledField( "Sex:"  );
		salary  = addLabelledField( "Salary:" );
		rank    = addLabelledFieldWithEllipsis( "Rank:" );
		buttons = addNewSaveDeleteButtons();		
	}

}













































