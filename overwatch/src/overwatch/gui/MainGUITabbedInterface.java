package overwatch.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class MainGUITabbedInterface {
	
	//Classes where the panels are made for each tab
	PersonnelTab personnelTab = new PersonnelTab();
	
	
	private JTabbedPane mainTabbedPane;
	private JPanel personnel;
	private JFrame frame;
	
	
	
	
	
	
	public MainGUITabbedInterface()
	{
		frame = new JFrame();
		mainTabbedPane = new JTabbedPane();
		
		//Create the panels for the tabs
		personnel =  personnelTab.personnelPanelCreate();
		
		//Add the tabs to the tabbed pane
		mainTabbedPane.addTab("Personnel", personnel);
		
		//Add stuff to the frame
		frame.getContentPane().add(mainTabbedPane);
		
		//Set the frame up
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	
	//Test
	public static void main (String[] args)
	{
		MainGUITabbedInterface mainInt = new MainGUITabbedInterface();
	}
	

}
