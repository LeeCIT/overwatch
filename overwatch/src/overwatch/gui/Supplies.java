package overwatch.gui;

import java.awt.TextField;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class Supplies extends JPanel{
	
	private JLabel suppliesLabel;
	private JLabel supplyName;
	private JLabel supplyType;
	private JLabel supplyAmount;
	
	private JTextField supplyNameTF;
	private JTextField supplyTypeTF;
	private JTextField supplyAmountTF;
	
	public Supplies()
	{
		suppliesLabel 	= new JLabel("Supplies");
		supplyName 		= new JLabel("Name:");
		supplyType 		= new JLabel("Type:");
		supplyAmount 	= new JLabel("Amount");
		supplyNameTF	= new JTextField();
		supplyTypeTF	= new JTextField();
		supplyAmountTF	= new JTextField();
		
		//Set the layout manager
		setLayout(new MigLayout());
		
		add(supplyName);
		add(supplyNameTF, "wrap");
		add(supplyType);
		add(supplyTypeTF, "wrap");
		add(supplyAmount);
		add(supplyAmountTF);
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.add(new Supplies());
		frame.pack();
		frame.setVisible(true);
		
	}

}
