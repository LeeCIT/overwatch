


package overwatch.controllers;

import overwatch.core.Gui;
import overwatch.db.*;
import overwatch.gui.*;
import overwatch.gui.tabs.VehicleTab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;





/**
 * Set up the vehicle tab logic
 * @author  John Murphy
 * @author  Lee Coakley
 * @version 7
 */





public class VehicleLogic extends TabController
{
	private final VehicleTab tab;
	
	
	
	
	
	public VehicleLogic( VehicleTab vt )
	{
		this.tab = vt;
		attachEvents();
	}
	
	
	
	
	
	public void respondToTabSelect() {
		populateTabList();
	}

	
		
	

	public JPanel getTab() {
		return tab;
	}
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private void doNew()
	{
		Integer vehicleNo = Vehicles.create();
		
		populateTabList();
		tab.setSelectedItem( vehicleNo );
	}
	
	
	
	
	
	private void doSave()
	{
		if ( ! tab.areAllFieldsValid()) {
			Gui.showErrorDialogue(
				"Invalid Fields",
				"Can't save: some fields contain invalid data."
			);
			return;
		}
		
		Integer vehicleNo   = tab.getSelectedItem();
		String  vehicleType = tab.type .field.getText();
		String  pilotName   = tab.pilot.field.getText();
		Integer pilotNo     = Personnel.getNumber( pilotName ); // TODO: not unique, use logins instead?
		
		int mods = Database.update(
			"update Vehicles   " +
			"set type     =   '" + vehicleType + "', " +
			"    personNo =    " + pilotNo     + " "   +
			"where vehicleNo = " + vehicleNo   + ";" 
		);
		
		if (mods <= 0) {
			Gui.showErrorDialogue(
				"Vehicle Deleted",
				"The vehicle has been deleted by someone else!"
			);
		}
		
		populateTabList();
		tab.setSelectedItem( vehicleNo );
	}
	
	
	
	
	
	private void doDelete()
	{
		Integer vehicleNo = tab.getSelectedItem();
		Vehicles.delete( vehicleNo );
		populateTabList();
	}
	
	
	
	
	
	private void populateTabList()
	{
		populateFields( null );
		tab.setSearchableItems(
			Database.queryKeyNamePairs( "Vehicles", "vehicleNo", "type", Integer[].class )
		);
	}
	
	
	
	
	
	private void populateFields(Integer vehicleNo)
	{
		if (vehicleNo == null)
		{
			tab.setEnableFieldsAndButtons(false);
			tab.clearFields();
			return;
		}
		else {
			tab.setEnableFieldsAndButtons(true);
		}
		
		
		EnhancedResultSet vehicle = Database.query(
			"SELECT vehicleNo, " +
			"       type,      " +
			"		personNo   " +
		    "FROM Vehicles     " +
		    "WHERE vehicleNo = " + vehicleNo + ";"
		);
		
		Integer pilot = vehicle.getElemAs( "personNo", Integer.class );
		
		String pilotName = "";
		if (pilot != null) {
			pilotName = Database.querySingle( String.class,
				"select name      " +
				"from Personnel   " +
				"where personNo = " + pilot + ";"
			);
		}
		
		tab.number.field.setText( "" + vehicle.getElemAs( "vehicleNo",  Integer.class ));
		tab.type  .field.setText(	   vehicle.getElemAs( "type",       String .class ));
		tab.pilot .field.setText(      pilotName );		
	}
	
	
	
	
	
	private void attachEvents()
	{
		setupTabChangeActions();
		setupButtonActions();
		setupSelectActions();
		setupFieldValidators();
		setupPickActions();
	}
	
	
	
	
	
	private void setupSelectActions()
	{
		tab.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {	
				populateFields(tab.getSelectedItem());
			}
		});
	}
	
	
	
	
	
	private void setupButtonActions()
	{
		tab.addNewListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doNew();
			}
		});	
		
	
		tab.addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
		
		
		tab.addDeleteListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDelete();	
			}
		});	
	}
	
	
	
	
	
	private void setupTabChangeActions() {
		Gui.getCurrentInstance().addTabSelectNotify(this);
	}
	
	
	
	
	
	private void setupFieldValidators()
	{
		tab.addTypeValidator( new CheckedFieldValidator() {
			public boolean check( String text ){
				return DatabaseConstraints.isValidName( text );
			}
		});
		
				
		tab.addPilotValidator( new CheckedFieldValidator() {
			public boolean check( String text ){
				return text.isEmpty()
					|| DatabaseConstraints.personExists( text );
			}
		});
	}
	
	
	
	
	
	private void setupPickActions()
	{
		final PickListener<Integer> pickListener = new PickListener<Integer>() {
			public void onPick( Integer picked ) {
				if (picked != null)
					tab.pilot.field.setText(Personnel.getName(picked)) ;		
			}
		};
		
		tab.pilot.button.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new PersonnelPicker( Gui.getCurrentInstance(), pickListener );
			}
		});
		
	}

}
