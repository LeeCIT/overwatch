package overwatch.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Set up the vehicle tab logic
 * @author john
 * Version 1
 */

public class VehicleTabLogic {
	
	
	
	
	public VehicleTabLogic(VehicleTab vt)
	{
		attatchEvents(vt);
	}
	
	
	
	public void attatchEvents(VehicleTab vt)
	{
		addNew(vt);
		deleteVehicle(vt);
		saveVehicle(vt);
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
