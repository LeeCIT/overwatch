


package overwatch.gui;

import javax.swing.*;





public class MainFrame extends JFrame
{
	
	private JTabbedPane  mainTabbedPane;
	private PersonnelTab personnelTab;
	
	
	
	
	
	public MainFrame()
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
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main (String[] args)
	{
		MainFrame mainInt = new MainFrame();
	}
	

}