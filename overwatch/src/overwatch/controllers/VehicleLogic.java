package overwatch.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import overwatch.db.Database;
import overwatch.db.EnhancedResultSet;
import overwatch.db.Personnel;
import overwatch.gui.PersonnelPicker;
import overwatch.gui.PickListener;
import overwatch.gui.tabs.VehicleTab;


/**
 * Set up the vehicle tab logic
 * @author john
 * @version 2
 */

public class VehicleLogic {
	
	private final VehicleTab vehicleTab;
	
	
	public VehicleLogic(VehicleTab vt)
	{
		this.vehicleTab = vt;
		attachButtonEvents(vt);
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	public void attachButtonEvents(VehicleTab vt)
	{
		setupButtonActions();
		populateTabList();
		vehicleListChange();
		setupFieldValidators();
		setUpPick();
	}
	
	
	
	
	private void populateTabList()
	{
		vehicleTab.setSearchableItems(
			Database.queryKeyNamePairs( "Vehicles", "vehicleNo", "type", Integer[].class )
		);
	}
	
	
	
	
	public void vehicleListChange()
	{
		vehicleTab.addSearchPanelListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {	
				populateVehicleFields(vehicleTab.getSelectedItem());
			}
		});
	}
	
	
	
	
	public void populateVehicleFields(Integer vehicleNo)
	{
		if(vehicleNo == null)
		{
			vehicleTab.setEnableFieldsAndButtons(false);
			vehicleTab.clearFields();
			return;
		}
		else
		{
			vehicleTab.setEnableFieldsAndButtons(true);
		}
		
		
		EnhancedResultSet ers = Database.query(
			"SELECT v.vehicleNo, v.type, v.personNo, p.name AS personName " +
		    "FROM Vehicles v, Personnel p " +
		    "WHERE v.vehicleNo = " + vehicleNo + 
		    "  AND v.personNo  = p.personNo;"
		);
		
		vehicleTab.number.field.setText("" + ers.getElemAs( "vehicleNo",  Integer.class ));
		vehicleTab.type  .field.setText(	 ers.getElemAs( "type",       String.class  ));
		vehicleTab.pilot .field.setText(	 ers.getElemAs( "personName", String.class  ));		
	}
	
	
	
	
	
	public void setupButtonActions()
	{
		vehicleTab.addNewListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked add new");
			}
		});	
	
		vehicleTab.addDeleteListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked delete");				
			}
			
		});	
	
		vehicleTab.addSaveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked save");				
			}
		});
	
	}
	
	
	
	
	public void setupFieldValidators()
	{
		//TODO Add a type validator
	}
	
	
	
	
	public void setUpPick()
	{
		final PickListener<Integer> pickListener = new PickListener<Integer>() {
			public void onPick( Integer picked ) {
				vehicleTab.pilot.field.setText(Personnel.getName(picked)) ;		
			}
		};
		
		final JFrame frame = new JFrame();
		
		vehicleTab.pilot.button.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new PersonnelPicker( frame, pickListener );
			}
		});
		
	}
	
	
	
	
	
	
	
	
	

}