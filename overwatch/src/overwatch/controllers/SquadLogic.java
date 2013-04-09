package overwatch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import overwatch.core.Gui;
import overwatch.db.Database;
import overwatch.db.EnhancedResultSet;
import overwatch.gui.tabs.SquadTab;




/**
 * The squadTabLogic
 * @author  John Murphy
 *
 */




public class SquadLogic extends TabController{
	
	
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
				System.out.println("New");
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
	
	
	
	
	private void setupListSelectActions(){
		tab.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				loadSubPanels();
				populateFields(tab.getSelectedItem());
			}
		});
	}
	
	
	
	
	private void populateSquadsList(){
		populateFields(null);
		
		tab.setSearchableItems(
		Database.queryKeyNamePairs("Squads", "squadNo", "squadName", Integer[].class)
		);
	}
	
	
	
	
	private void loadSubPanels(){
		//TODO
	}
	
	
	
	
	private void populateFields(Integer squadNo)
	{
		if(squadNo == null)
		{
			tab.setEnableFieldsAndButtons( false );
			tab.clearFields();
			return;
		}
		else{
			tab.setEnableFieldsAndButtons( true );
		}
		
		EnhancedResultSet ers = Database.query(
				"SELECT s.squadNo, squadName, p.name AS name     " +
			    "FROM Squads s, SquadCommanders sq, Personnel p     " +
			    "WHERE s.squadNo =  " + squadNo + " " +
			    "AND p.personNo = sq.personNo;"
			);
		
		
		tab.number.field.setText( "" 	+ ers.getElemAs( "squadNo", 	Integer.class ));
		tab.name  .field.setText(      	  ers.getElemAs( "squadName",   String .class ));
		tab.commander.field.setText( "" + ers.getElemAs( "name",    	Integer.class ));		
	}
	
	
	
	
	private void setupTabChangeActions() {
		Gui.getCurrentInstance().addTabSelectNotify(this);
	}
	
	
	
	
	
	
	
	

}
