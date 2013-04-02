package overwatch.gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 * Creates the supplies tab
 * @author john
 *
 */

public class Supplies extends JPanel{
	
	private JLabel supplyName;
	private JLabel supplyType;
	private JLabel supplyAmount;
	
	private JTextField supplyNameTF;
	private JTextField supplyTypeTF;
	private JTextField supplyAmountTF;
	
	private JButton newSupply;
	private JButton save;
	private JButton delete;
	
	private SearchPanel	search;
	
	public Supplies()
	{
		supplyName 		= new JLabel("Supply:");
		supplyType 		= new JLabel("Type:");
		supplyAmount 	= new JLabel("Amount");
		supplyNameTF	= new JTextField();
		supplyTypeTF	= new JTextField();
		supplyAmountTF	= new JTextField();
		search			= new SearchPanel("Supplies"); //Template code to put in.
		newSupply		= new JButton("New");
		save			= new JButton("Save");
		delete			= new JButton("Delete");
		
		//Set the layout manager
		setLayout(new MigLayout());
		
		//Set the size of the text field
		supplyNameTF.setPreferredSize(new Dimension(200, 25));
		
		//Add components to the supplies tab
		add(search, "west");
		add(supplyName, "gaptop 25, alignx right, cell 0 2");
		add(supplyNameTF, "wrap");
		add(supplyType, "alignx right");
		add(supplyTypeTF, "wrap, grow");
		add(supplyAmount, "alignx right");
		add(supplyAmountTF, "wrap, grow");
		add(newSupply);
		add(save);
		add(delete);
	}
	
	
	
	//Action listeners for each button
	public void newSupplyActionListner(ActionListener e){
		newSupply.addActionListener(e);
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
		frame.add(new Supplies());
		frame.pack();
		frame.setVisible(true);
		
	}

}
