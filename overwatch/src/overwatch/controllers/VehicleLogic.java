


package overwatch.controllers;

import overwatch.core.Gui;
import overwatch.db.*;
import overwatch.gui.*;
import overwatch.gui.tabs.VehicleTab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;





/**
 * Set up the vehicle tab logic
 * @author  John Murphy
 * @author  Lee Coakley
 * @version 7
 */





public class VehicleLogic extends TabController<VehicleTab>
{	
	
	public VehicleLogic( VehicleTab tab ) {
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
		Integer vehicleNo = Vehicles.create();
		
		populateList();
		tab.setSelectedItem( vehicleNo );
	}
	
	
	
	
	
	private void doSave()
	{
		if ( ! tab.areAllFieldsValid()) {
			showFieldValidationError();
			return;
		}
		
		Integer vehicleNo   = tab.getSelectedItem();
		String  vehicleName = tab.name .field.getText();
		String  pilotName   = tab.pilot.field.getText();
		Integer pilotNo     = Personnel.getNumber( pilotName );
		
		try {
			if ( ! Vehicles.save(vehicleNo, vehicleName, pilotNo) ) {
				showDeletedError( "vehicle" );
			}
		}
		catch (DatabaseIntegrityException ex) {
			Gui.showError( "Cannot Save", "The pilot '" + pilotName + "' has been assigned to another vehicle." );
		}
		
		populateList();
		tab.setSelectedItem( vehicleNo );
	}
	
	
	
	
	
	private void doDelete()
	{
		if ( ! confirmDelete( "vehicle" ))
			return;
		
		Integer vehicleNo = tab.getSelectedItem();
		Vehicles.delete( vehicleNo );
		populateList();
	}
	
	
	
	
	
	private void populateList()
	{
		populateFields( null );
		tab.setSearchableItems(
			Database.queryKeyNamePairs( "Vehicles", "vehicleNo", "name", Integer[].class )
		);
	}
	
	
	
	
	
	private void populateFields( Integer vehicleNo )
	{
		if (vehicleNo == null)
		{
			tab.setEnableFieldsAndButtons( false );
			tab.clearFields();
			tab.setEnableNewButton( true );
			return;
		}
		
		
		tab.setEnableFieldsAndButtons( true );
		
		EnhancedResultSet ers = Vehicles.getInfo( vehicleNo );
		
		if (ers.isEmpty()) {
			showDeletedError( "vehicle" );
			return;
		}
		
		Integer pilot = ers.getElemAs( "pilot", Integer.class );
		
		String pilotName = "";
		if (pilot != null) {
			pilotName = Vehicles.getPilotName( vehicleNo );
		}
		
		tab.number.field.setText( "" + ers.getElemAs( "vehicleNo",  Integer.class ));
		tab.name  .field.setText(	   ers.getElemAs( "name",       String .class ));
		tab.pilot .field.setText(      pilotName );
	}
	
	
	
	
	
	protected void attachEvents() {
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
					tab.pilot.field.setText( Personnel.getLoginName(picked) ) ;		
			}
		};
		
		
		tab.pilot.button.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				PersonnelPicker p = new PersonnelPicker( tab.pilot.button, pickListener );
				p.removeItemsNotIn( Vehicles.getPersonnelNotAssignedToVehicles() );
				p.setVisible( true );
			}
		});
		
	}

}





























