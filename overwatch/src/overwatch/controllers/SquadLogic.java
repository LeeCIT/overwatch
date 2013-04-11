package overwatch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import overwatch.core.Gui;
import overwatch.db.Database;
import overwatch.db.EnhancedResultSet;
import overwatch.db.Squads;
import overwatch.gui.tabs.SquadTab;




/**
 * The squadTabLogic
 * @author  John Murphy
 *
 */




public class SquadLogic extends TabController
{
	private final SquadTab tab;
	
	
	
	
	
	public SquadLogic(SquadTab tab)
	{
		this.tab = tab;
		attatchEvents();
		setupTabChangeActions();
	}
	
	
	
	
	
	public void respondToTabSelect(){
		populateSquadsList();
	}
	
	
	
	
	
	public JPanel getTab(){
		return tab;
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private void attatchEvents(){
		setUpButtonActions();
		setupListSelectActions();
	}
	
	
	
	
	
	private void setUpButtonActions(){
		
		tab.addNewListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNew();
			}
		});
		
		tab.addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save");
			}
		});
		
		tab.addDeleteListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Delete");				
			}
		});
	}
	
	
	
	
	
	private void doSave()
	{
		
	}
	
	
	
	
	
	private void createNew()
	{
		Integer squadNo = Squads.create();
		populateSquadsList();
		populateFieldsAndPanels(squadNo);
	}
	
	
	
	
	
	private void delete()
	{
		
	}
	
	
	
	
	
	private void setupListSelectActions(){
		tab.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				populateFieldsAndPanels(tab.getSelectedItem());
			}
		});
	}
	
	
	
	
	
	private void populateSquadsList(){
		populateFieldsAndPanels(null);
		
		tab.setSearchableItems(
		Database.queryKeyNamePairs("Squads", "squadNo", "name", Integer[].class)
		);
	}
	
	
	
	
	
	private void populateAssignPanels( int squadNo ) {
		tab.assignTroops.setListItems  ( Squads.getTroops  ( squadNo, Integer[].class ));
		tab.assignVehicles.setListItems( Squads.getVehicles( squadNo, Integer[].class ));
		tab.assignSupplies.setListItems( Squads.getSupplies( squadNo, Integer[].class ));
	}
	
	
	
	
	
	private void populateFieldsAndPanels(Integer squadNo)
	{
		if(squadNo == null) {
			tab.setEnableFieldsAndButtons( false );
			tab.clearFields();
			return;
		}
		
		
		tab.setEnableFieldsAndButtons( true );
		
		EnhancedResultSet ers = Database.query(
			"SELECT squadNo,  " +
			"		name,     " +
			"       commander " +
		    "FROM Squads " +
		    "WHERE squadNo  = " + squadNo  + ";"
		);
		
		
		if (ers.isEmpty()) {
			showDeletedError( "squad" );
			return;
		}
		
		
		Integer commander = ers.getElemAs( "commander", Integer.class );
		
		String commanderName = "";
		if (commander != null) {
			commanderName = Database.querySingle( String.class,
				"select name      " +
				"from Personnel   " +
				"where personNo = " + commander + ";"
			);
		}
		
		tab.number   .field.setText( "" + ers.getElemAs( "squadNo",    Integer.class ));
		tab.name     .field.setText(      ers.getElemAs( "name",       String .class ));
		tab.commander.field.setText(      commanderName );
		
		//Populate the subpanels
		populateAssignPanels(squadNo);
	}
	
	
	
	
	
	private void setupTabChangeActions() {
		Gui.getCurrentInstance().addTabSelectNotify(this);
	}
	
	
	
	
	
	
	
	

}
