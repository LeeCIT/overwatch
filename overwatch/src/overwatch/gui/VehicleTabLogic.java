package overwatch.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import overwatch.db.Database;


/**
 * Set up the vehicle tab logic
 * @author john
 * Version 1
 */

public class VehicleTabLogic {
	
	
	
	
	public VehicleTabLogic(VehicleTab vt)
	{
		attatchButtonEvents(vt);
	}
	
	
	
	public void attatchButtonEvents(VehicleTab vt)
	{
		addNew(vt);
		deleteVehicle(vt);
		saveVehicle(vt);
		populateTabList(vt);
	}
	
	
	
	
	private static void populateTabList(VehicleTab vt)
	{
		vt.setSearchableItems(
			Database.queryKeyNamePairs( "Vehicles", "vehicleNo", "type", Integer[].class )
		);
	}
	
	
	
	public void addNew(VehicleTab vt)
	{
		vt.buttons.addNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked add new");
			}
		});
	}
	
	
	
	public void deleteVehicle(VehicleTab vt)
	{
		vt.buttons.delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked delete");				
			}
		});
	}
	
	
	
	public void saveVehicle(VehicleTab vt)
	{
		vt.buttons.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked save");				
			}
		});
	}

}
