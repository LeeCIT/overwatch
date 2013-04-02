


package overwatch.gui;

import javax.swing.*;





public class MainGuiTabbedInterface extends JFrame
{
	
	//Classes where the panels are made for each tab
	private JTabbedPane  mainTabbedPane;
	private PersonnelTab personnelTab;
	
	
	
	
	
	public MainGuiTabbedInterface()
	{
		mainTabbedPane = new JTabbedPane();
		personnelTab   = new PersonnelTab();
		
		// Add the tabs to the tabbed pane
		mainTabbedPane.addTab( "Personnel", personnelTab );
		
		// Add stuff to the frame
		add( mainTabbedPane );
		
		// Set the frame up
		pack();
		setVisible(true);
	}
	
	
	
	
	
	//Test
	public static void main (String[] args)
	{
		MainGuiTabbedInterface mainInt = new MainGuiTabbedInterface();
	}
	

}
