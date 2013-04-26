


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
import overwatch.gui.tabs.PersonnelTabChangePassDialog;
import overwatch.security.LoginManager;
import overwatch.util.Util;
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
 * @version 6
 */





public class PersonnelLogic extends TabController<PersonnelTab>
{	
	
	public PersonnelLogic( PersonnelTab tab ) {
		super( tab );
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
		
		String     name      = tab.name     .field.getText();
		Integer    age       = tab.age      .field.getTextAsInt();
		String     sex       = tab.sex      .field.getText();
		BigDecimal salary    = Util.toBigDecimal( tab.salary.field.getText() );
		String     rankName  = tab.rank     .field.getText();
		String     loginName = tab.loginName.field.getText();
		
		
		
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
		boolean modified = Personnel.save( personNo, name, age, sex, salary, rankNo, loginName );
		
		
		// Check if that actually worked
		if ( ! modified) {
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
		boolean isModified = tab.rank.field.isModifiedByUser();
		
		if (! isModified) {
			return true;
		}
		
		
		Integer personNo     = tab.getSelectedItem();
		Integer currentLevel = LoginManager.getCurrentSecurityLevel();
		Integer personLevel  = Personnel.getPrivilegeLevel( personNo );
		Integer newLevel     = Ranks.getLevel( proposedRank );
		boolean isSelf       = LoginManager.isCurrentUser( personNo );
		
		
		// Self-modify not allowed
		if (isSelf && isModified) {
			Gui.showError(
				"Cannot Modify Own Rank",
				"You can't change your own rank.  Only your superiors may promote or demote you."
			);
			return false;
		}
		
		
		// User is equal/greater
		if (personLevel >= currentLevel) {
			Gui.showError(
				"Cannot Modify Rank",
				"You can't modify the rank of someone who is of equal or greater rank than you."
			);
			return false;
		}
		
		
		// Greater than allowed for your level
		if (newLevel >= currentLevel) {
			Gui.showError(
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
	
		if ( ! confirmDelete( "person" ))
			return;
		
		Personnel.delete( personNo );
		populateList();
	}
	
	
	
	
	
	private boolean doDeletableCheck( Integer personNo )
	{
		if (LoginManager.isCurrentUser( personNo )) {
			Gui.showError(
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
			
			
			Gui.showError(
				"Cannot Delete",
				"Can't delete '" +name + "'.\n\n" + reasons
			);
			
			return false;
		}
		

		return true;
	}
	
	
	
	
	
	private void doPassChange( final Integer personNo )
	{
		final PersonnelTabChangePassDialog p = new PersonnelTabChangePassDialog( tab.passChange );
		
		p.addOkButtonListener( new ActionListener() {
			public void actionPerformed( ActionEvent e )
			{
				String passCur = new String( p.passOld .getPassword() );
				String passNew = new String( p.passNew .getPassword() );
				String passCon = new String( p.passConf.getPassword() );
				
				
				if ( ! LoginManager.isPassValid( personNo, passCur )) {
					Gui.showError( "Wrong Password", "Wrong password!" );
					return;
				}
				
				
				if ( ! passNew.equals( passCon )) {
					Gui.showError( "Password Mismatch", "The entered passwords do not match." );
					return;
				}
				
				
				Personnel.setPass( personNo, passNew );
				Gui.showMessage( "Password Changed", "The password has been changed." );
				
				p.dispose();
			}
		});
		
		
		p.addCancelButtonListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				p.dispose();
			}
		});
		
		
		p.setVisible( true );
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
			tab.setEnableNewButton( true );
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
	
	
	
	
	
	protected void attachEvents()
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
				RankPicker r = new RankPicker( tab.rank.button, rankPickListener );
				r.setVisible( true );
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
		
		
		tab.addPassChangeListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				doPassChange( tab.getSelectedItem() );
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































































