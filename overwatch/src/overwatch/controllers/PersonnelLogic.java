


package overwatch.controllers;

import overwatch.core.Gui;
import overwatch.db.Database;
import overwatch.db.DatabaseConstraints;
import overwatch.db.EnhancedResultSet;
import overwatch.db.Personnel;
import overwatch.db.Ranks;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.PickListener;
import overwatch.gui.RankPicker;
import overwatch.gui.tabs.PersonnelTab;
import overwatch.security.LoginManager;
import overwatch.util.Validator;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;





/**
 * Implements the program logic for the personnel tab.
 * Controls saving, loading, security checking etc.
 * 
 * @author  Lee Coakley
 * @version 5
 */





public class PersonnelLogic extends TabController
{
	private final PersonnelTab tab;
	
	
	
	
	
	/**
	 * Plug the GUI tab into the controller.
	 * @param tab
	 */
	public PersonnelLogic( PersonnelTab tab )
	{
		this.tab = tab;
		
		attachEvents();
	}
	
	
	
	
	
	public JPanel getTab() {
		return tab;
	}
	
	
	
	
	
	public void respondToTabSelect() {	
		populateList();
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private void doNew()
	{
		Integer personNo = Personnel.create();
		
		populateList();
		tab.setSelectedItem( personNo );
	}
	
	
	
	
	
	private void doSave( Integer personNo )
	{
		if ( ! tab.areAllFieldsValid()) {
			showFieldValidationError();
			return;
		}
		
		String name      = tab.name     .field.getText();
		String age       = tab.age      .field.getText();
		String sex       = tab.sex      .field.getText();
		String salary    = tab.salary   .field.getText();
		String rankName  = tab.rank     .field.getText();
		String loginName = tab.loginName.field.getText();
		
		if ( ! Personnel.exists(personNo)) {
			showDeletedError( "person" );
			populateList(); // Reload
			return;
		}
		
		
		// Fetch rank key
		Integer rankNo = Ranks.getNumber( rankName );
		
		if (rankNo == null) {
			showDeletedError( "rank '" + rankName + "'" );
			return;
		}
		
		
		if ( ! doRankModifySecurityChecks(rankNo)) {
			return;
		}
		
		
		// Commit changes
		int modRows = Database.update(
			"update Personnel "      +
			"set name           = '" + name       + "', " +
			"    age            =  " + age       + ",  " +
			"    sex            = '" + sex       + "', " +
			"    salary         =  " + salary    + ",  " +
			"    rankNo         =  " + rankNo    + ",  " +
			"    loginName      = '" + loginName + "'  " +
			"where personNo =      " + personNo  + " ;"
		);
		
		
		// Check if that actually worked
		if (modRows <= 0) {
			showDeletedError( "person" );
			populateList();
			return;
		}
		
		
		// Update title bar
		if (LoginManager.isCurrentUser( personNo ))
			Gui.getCurrentInstance().setTitleDescription( loginName );
		
		
		populateList();
		tab.setSelectedItem( personNo );
	}
	
	
	
	
	
	private boolean doRankModifySecurityChecks( Integer proposedRank )
	{
		Integer personNo     = tab.getSelectedItem();
		Integer currentLevel = LoginManager.currentSecurityLevel();
		Integer personLevel  = Personnel.getPrivilegeLevel( personNo );
		Integer newLevel     = Ranks.getLevel( proposedRank );
		boolean isSelf       = LoginManager.isCurrentUser( personNo );
		boolean isModified   = tab.rank.field.isModifiedByUser();
		
		// Self-modify not allowed
		if (isSelf && isModified) {
			Gui.showErrorDialogue(
				"Cannot Modify Own Rank",
				"You can't change your own rank.  Only your superiors may promote or demote you."
			);
			return false;
		}
		
		
		// User is equal/greater
		if (personLevel >= currentLevel) {
			Gui.showErrorDialogue(
				"Cannot Modify Rank",
				"You can't modify the rank of someone who is of equal or greater rank than you."
			);
			return false;
		}
		
		
		// Greater than allowed for your level
		if (newLevel >= currentLevel) {
			Gui.showErrorDialogue(
				"Cannot Modify Rank",
				"You can't promote someone to a rank equal to or greater than your own."
			);
			return false;
		}
		
		return true;
	}
	
	
	
	
	
	private void doDelete( Integer personNo )
	{
		if ( ! doDeletableCheck( personNo ))
			return;		
		
		Personnel.delete( personNo );
		populateList();
	}
	
	
	
	
	
	private boolean doDeletableCheck( Integer personNo )
	{
		if (LoginManager.isCurrentUser( personNo )) {
			Gui.showErrorDialogue(
				"Cannot Delete Self",
				"You can't delete yourself.  You've got so much to live for!"
			);
			return false;
		}
		
		
		if (Personnel.isInSquadOrVehicle( personNo ))
		{
			String name = Personnel.getLoginName( personNo );
			
			String vehicle = Database.querySingle( String.class,
				"select v.name   " +
				"from Vehicles v " +
				"where pilot =   " + personNo + ";"
			);
			
			String squadCom = Database.querySingle( String.class,
				"select name       " +
				"from Squads       " +
				"where commander = " + personNo + ";"
			);
			
			String squad = Database.querySingle( String.class,
				"select s.name                 " +
				"from Squads s, SquadTroops st " +
				"where st.squadNo  = s.squadNo " +
				"  and st.personNo = " + personNo + ";"
			);
			
			
			
			String reasons = "";
			
			if (vehicle != null)
				reasons += "They are assigned as the pilot of vehicle '" + vehicle + "'.\n";
			
			if (squadCom != null)
				reasons += "They are assigned as commander of squad '" + squadCom + "'.\n";
			
			if (squad != null)
				reasons += "They are assigned as a trooper in squad '" + squad + "'.\n";
			
			
			Gui.showErrorDialogue(
				"Cannot Delete",
				"Can't delete '" +name + "'.\n\n" + reasons
			);
			
			return false;
		}
		

		return true;
	}
	
	
	
	
	
	private void respondToRankPicker( Integer rankNo ) {
		if (rankNo != null)
			tab.rank.field.setText( Ranks.getName(rankNo) );
	}
	
	
	
	
	
	private void populateList()
	{
		populateFields( null );
		tab.setSearchableItems(
			Database.queryKeyNamePairs( "Personnel", "personNo", "loginName", Integer[].class )
		);
	}
	
	
	
	
	
	private void populateFields( Integer personNo )
	{
		if (personNo == null) {
			tab.setEnableFieldsAndButtons( false );
			tab.clearFields();
			return;
		}
		
		
		tab.setEnableFieldsAndButtons( true );
		
		EnhancedResultSet ers = Database.query(
			"select r.name as rankName,   " +
			"		p.name as personName, " +
			"		personNo,    " +
			"		age,         " +
			"		sex,         " +
			"		salary,      " +
			"       loginName    " +
			"from Ranks     r,   " +
			"	  Personnel p    " +
			"where p.personNo =  " + personNo + " " +
			"  and p.rankNo   = r.rankNo;"
		);
		
		if (ers.isEmpty()) {
			showDeletedError( "person" );
			return;
		}
		
		tab.number   .field.setText( "" + ers.getElemAs( "personNo",   Integer   .class ) );
		tab.name     .field.setText(      ers.getElemAs( "personName", String    .class ) );
		tab.age      .field.setText( "" + ers.getElemAs( "age",        Integer   .class ) );
		tab.sex      .field.setText(      ers.getElemAs( "sex",        String    .class ) );
		tab.salary   .field.setText( "" + ers.getElemAs( "salary",     BigDecimal.class ) );
		tab.rank     .field.setText(      ers.getElemAs( "rankName",   String    .class ) );
		tab.loginName.field.setText(      ers.getElemAs( "loginName",  String    .class ) );
	}
	
	
	
	
	
	private void attachEvents()
	{
		setupTabChangeActions();
		setupSelectActions   ();
		setupButtonActions   ();
		setupPickActions     ();
		setupFieldValidators ();
	}
	
	
	
	
	
	private void setupTabChangeActions() {
		Gui.getCurrentInstance().addTabSelectNotify( this );	
	}
	
	
	
	
	
	private void setupPickActions()
	{
		final PickListener<Integer> rankPickListener = new PickListener<Integer>() {
			public void onPick( Integer picked ) {
				respondToRankPicker( picked );
			}
		};
		
		
		tab.rank.button.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new RankPicker( tab.rank.button, rankPickListener );
			}
		});
	}
	
	
	
	
	
	private void setupSelectActions()
	{
		tab.addSearchPanelListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ) {
				populateFields( tab.getSelectedItem() );
			}
		});
	}
	
	
	
	
	
	private void setupButtonActions()
	{
		tab.addNewListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				doNew();
			}
		});
		
		
		tab.addSaveListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				doSave( tab.getSelectedItem() );				
			}
		});
		
		
		tab.addDeleteListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				doDelete( tab.getSelectedItem() );
			}
		});
	}
	
	
	
	
	
	private void setupFieldValidators()
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
				return DatabaseConstraints.isValidSalary( text );
			}
		});
		
		
		tab.addRankValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.rankExists( text );
			}
		});
		
		
		tab.addLoginValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.isValidName( text );
			}
		});
	}
	
}































































