


package overwatch.core;

import overwatch.db.Database;
import overwatch.db.DatabaseConstraints;
import overwatch.db.EnhancedResultSet;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.PersonnelTab;
import overwatch.gui.PickListener;
import overwatch.gui.RankPicker;
import overwatch.util.Validator;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;





/**
 * Implements the program logic for the personnel tab.
 * Controls saving, loading, security checking etc.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class PersonnelLogic
{
	
	/**
	 * Plugs the GUI into the logic controller.
	 * @param tab
	 */
	public static void attachEvents( PersonnelTab tab )
	{
		setupSelectActions( tab );
		setupButtonActions( tab );
		setupPickActions  ( tab );
		setupValidators   ( tab );
	}
	
	
	
	
	
	private static void respondToRankPicker( Integer rankNo )
	{
		System.out.println( "respondToRankPicker: got " + rankNo );
		// TODO
	}
	
	
	
	
	
	private static void populateTabList( PersonnelTab tab )
	{
		tab.setSearchableItems(
			Database.queryKeyNamePairs( "Personnel", "personNo", "name", Integer[].class )
		);
	}
	
	
	
	
	
	private static void populateTabFields( PersonnelTab tab, Integer personNo )
	{
		if (personNo == null) {
			tab.setEnableFieldsAndButtons( false );
			tab.clearFields();
			return;
		} else {
			tab.setEnableFieldsAndButtons( true );
		}
		
		EnhancedResultSet ers = Database.query(
			"select r.name as rankName,   " +
			"		p.name as personName, " +
			"		personNo,  " +
			"		age,       " +
			"		sex,       " +
			"		salary     " +
			"from Ranks     r, " +
			"	  Personnel p  " +
			"where personNo =  " + personNo + "   " +
			"  and p.rankNo = r.rankNo;"
		);
		
		tab.number.field.setText( "" + ers.getElemAs( "personNo",   Integer   .class ) );
		tab.name  .field.setText(      ers.getElemAs( "personName", String    .class ) );
		tab.age   .field.setText( "" + ers.getElemAs( "age",        Integer   .class ) );
		tab.sex   .field.setText(      ers.getElemAs( "sex",        String    .class ) );
		tab.salary.field.setText( "" + ers.getElemAs( "salary",     BigDecimal.class ) );
		tab.rank  .field.setText(      ers.getElemAs( "rankName",   String    .class ) );
	}
	
	
	
	
	
	private static void setupPickActions( PersonnelTab tab )
	{
		final PickListener<Integer> rankPickListener = new PickListener<Integer>() {
			public void onPick( Integer picked ) {
				System.out.println( ">> respondToRankPicker" );
				respondToRankPicker( picked );
				System.out.println( "<< respondToRankPicker" );
			}
		};
		
		
		System.out.println( rankPickListener );
		
		tab.rank.button.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				System.out.println( "pick create" );
				new RankPicker( Gui.currentInstance, rankPickListener );
				System.out.println( "pick created" );
			}
		});
	}
	
	
	
	
	
	private static void setupSelectActions( final PersonnelTab tab )
	{
		tab.addSearchPanelListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ) {
				populateTabFields( tab, tab.getSelectedItem() );
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
				return Validator.isPositiveBigDecimal( text );
			}
		});
		
		
		tab.addRankValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.rankExists( text );
			}
		});
	}

	
	
	
	
	
	
	
	
	
	
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		
		Gui gui = new Gui();
		
		PersonnelLogic.attachEvents   ( gui.personnelTab );
		PersonnelLogic.populateTabList( gui.personnelTab );
	}
}































































