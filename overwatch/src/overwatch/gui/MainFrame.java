


package overwatch.gui;

import javax.swing.*;
import overwatch.core.Gui;
import overwatch.core.PersonnelLogic;





public class MainFrame extends JFrame
{
	
	public final JTabbedPane  tabPane;
	public final PersonnelTab personnelTab;
	
	
	
	
	
	public MainFrame()
	{
		tabPane      = new JTabbedPane();
		personnelTab = new PersonnelTab();
		
		// Add the tabs to the tabbed pane
		tabPane.addTab( "Personnel", personnelTab );
		
		// Add stuff to the frame
		add( tabPane );
		
		// Set the frame up
		pack();
		setVisible(true);
	}
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main (String[] args)
	{
		Gui.setNativeStyle();
		
		MainFrame mainInt = new MainFrame();
	}
	

}
