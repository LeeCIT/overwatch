package overwatch.gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 * Creates the vehicle tab
 * @author john
 *
 */

public class VehicleTab extends JPanel{
	
	private JLabel type;
	private JLabel pilot;
	private JLabel vehicleDetails;
	private JLabel number;
	
	
	private JTextField typeTF;
	private JTextField pilotNameTF;
	private JTextField numberTF;
	
	private JButton newVehicle;
	private JButton save;
	private JButton delete;
	
	private SearchPanel	search;
	
	public VehicleTab()
	{
		type 			= new JLabel("Type:");
		pilot 			= new JLabel("Pilot:");
		typeTF			= new JTextField();
		search			= new SearchPanel("Vehicles"); //Template code to put in.
		newVehicle		= new JButton("New");
		save			= new JButton("Save");
		delete			= new JButton("Delete");
		vehicleDetails 	= new JLabel("Vehicle details");
		pilotNameTF		= new JTextField();
		numberTF		= new JTextField();
		number 			= new JLabel("Number:");
		
		//Set the layout manager
		setLayout(new MigLayout());
		
		//Set the size of the text field
		typeTF.setPreferredSize(new Dimension(200, 25));
		
		//Set the number field to un editable
		numberTF.setEditable(false);
		
		//Add components to the supplies tab
		add(search, "west");
		add(vehicleDetails, "wrap, cell 1 0");
		add(number, "alignx right");
		add(numberTF, "grow, wrap");
		add(type, "align x right");
		add(typeTF, "wrap");		
		add(pilot, "alignx right");
		add(pilotNameTF, "grow, wrap");
		add(newVehicle);
		add(save);
		add(delete);
	}
	
	
	
	//Action listeners for each button
	public void newVehicleActionListner(ActionListener e){
		newVehicle.addActionListener(e);
	}
	
	public void saveActionListner(ActionListener e){
		save.addActionListener(e);
	}
	
	public void deleteActionListner(ActionListener e){
		delete.addActionListener(e);
	}
	
	
	
	//Test 
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.add(new VehicleTab());
		frame.pack();
		frame.setVisible(true);
		
	}

}