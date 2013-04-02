


package overwatch.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;





public class PersonnelTab extends JPanel
{
	private JList namesList;
	
	private JLabel personnelListLabel;
	private JLabel personnelNameLabel;
	private JLabel detailsForLabel;
	private JLabel personnelAgeLabel;
	private JLabel personnelSexLabel;
	private JLabel personnelRankLabel;
	private JLabel personnelSalaryLabel;	
	
	private JTextField personnelName;
	private JTextField personnelAge;
	private JTextField personnelSex;
	private JTextField personnelRank;
	private JTextField personnelSalary;
	
	private JButton changeLogIn;
	private JButton addNew;
	private JButton save;
	private JButton delete;
	
	
	
	
	
	public PersonnelTab()
	{
		//Create panel and components for the panel
		super( new MigLayout("debug") );
		
		namesList				= new JList();
		personnelListLabel 		= new JLabel("Personnel List");
		personnelName			= new JTextField();
		personnelNameLabel		= new JLabel("Name:");
		detailsForLabel			= new JLabel("Details for ");  //A variable with the whatever selected from the list will go here
		personnelAgeLabel		= new JLabel("Age:");
		personnelAge			= new JTextField();
		personnelSexLabel		= new JLabel("Sex:");
		personnelRankLabel		= new JLabel("Rank:");
		personnelSalaryLabel	= new JLabel("Salary");
		personnelSex			= new JTextField();
		personnelRank			= new JTextField();
		personnelSalary			= new JTextField();
		changeLogIn				= new JButton("Change Login details");
		addNew					= new JButton("Add new");
		save					= new JButton("Save");
		delete					= new JButton("Delete");
					
		//Add stuff to the main panel
		add( personnelListLabel, "north");
		add( detailsForLabel, "wrap");
		add( personnelNameLabel);
		add( personnelName, "wrap");
		add( personnelAgeLabel);
		add( personnelAge, "grow, wrap");
		add( personnelSexLabel);
		add( personnelSex, "grow, wrap");
		add( personnelRankLabel);
		add( personnelRank, "grow, wrap");
		add( personnelSalaryLabel);
		add( personnelSalary, "grow, wrap");
		add( changeLogIn, "wrap");
		add( addNew );
		add( save );
		add( delete );
		add( namesList, "west" );
				
		//Set  the size for the list
		namesList.setPreferredSize(new Dimension(200,400));
		personnelName.setPreferredSize(new Dimension(200, 25));
	};
	
	
	
	
	
	//Add action listeners
	//Save button
	public void addSaveListener( ActionListener e ) {
		save.addActionListener(e);
	}
	
	
	
	
	
	//DeleteButton
	public void addDeleteListener( ActionListener e ) {
		delete.addActionListener(e);
	}
	
	
	
	
	
	//Add new button
	public void addNewListener( ActionListener e ) {
		addNew.addActionListener(e);
	}
	
	
	
	
	
	//Change login details
	public void addChangeLoginListener( ActionListener e ) {
		changeLogIn.addActionListener(e);
	}	

}













































