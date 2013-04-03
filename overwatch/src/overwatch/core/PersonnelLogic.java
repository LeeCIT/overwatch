


package overwatch.core;

import overwatch.db.Database;
import overwatch.db.DatabaseConstraints;
import overwatch.db.EnhancedResultSet;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.NameRefPair;
import overwatch.gui.PersonnelTab;
import overwatch.util.Validator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;





/**
 * Implements the program logic for the personnel tab.
 * Controls saving, loading, security checking etc.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class PersonnelLogic
{
	
	public static void attachEvents( PersonnelTab tab )
	{
		setupSelectActions( tab );
		setupButtonActions( tab );
		setupValidators   ( tab );
	}
	
	
	
	
	
	private static void populateTabList( PersonnelTab tab )
	{
		tab.setSearchableItems(
			Database.queryKeyNamePairs( "Personnel", "personNo", "name", Integer[].class )
		);
	}
	
	
	
	
	
	private static void populateTabFields( PersonnelTab tab )
	{
		
	}
	
	
	
	
	
	private static void setupSelectActions( PersonnelTab tab )
	{
		tab.addSearchPanelListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ) {
				System.out.println( "select" );
			}
		});
	}
	
	
	
	
	
	private static void setupButtonActions( PersonnelTab tab )
	{
		tab.addNewListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				System.out.println( "new" );
			}
		});
		
		
		tab.addSaveListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				System.out.println( "save" );
			}
		});
		
		
		tab.addDeleteListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				System.out.println( "delete" );
			}
		});
	}
	
	
	
	
	
	private static void setupValidators( PersonnelTab tab )
	{
		tab.addNameValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.isValidName( text );
			}
		});
		
		
		tab.addAgeValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return Validator.isPositiveInt( text );
			}
		});
		
		
		tab.addSexValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.isValidSex( text );
			}
		});
		
		
		tab.addSalaryValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return Validator.isPositiveInt( text );
			}
		});
		
		
		tab.addRankValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.rankExists( text );
			}
		});
	}

}































































