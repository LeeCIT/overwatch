package overwatch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import overwatch.core.Gui;
import overwatch.db.Database;
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
	
	
	
	
	private void setupTabChangeActions() {
		Gui.getCurrentInstance().addTabSelectNotify(this);
	}
	
	
	
	private void populateSquadsList(){
		tab.setSearchableItems(
		Database.queryKeyNamePairs("Squads", "squadNo", "squadName", Integer[].class)
		);
	}
	
	
	
	

}
