package overwatch.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class MainGuiTabbedInterface extends JFrame{
	
	private JTabbedPane mainTabbedPane;
	private JPanel personnelTab;
	private JPanel ordersTab;

	
	
	
	public MainGuiTabbedInterface()
	{
		personnelTab = new PersonnelTab();
		ordersTab	 = new OrderTab();
		
		PersonnelTab personnelTabReference	 = new PersonnelTab();
		OrderTab orderTabReference			 = new OrderTab();
		
		mainTabbedPane = new JTabbedPane();
						
		//Add the tabs to the tabbed pane
		mainTabbedPane.addTab("Personnel", personnelTab  );
		mainTabbedPane.addTab("Orders", ordersTab );
		
		//Add stuff to the frame
		add(mainTabbedPane);
		
		//Set the frame up
		pack();
		setVisible(true);
	}
	
	
	
	
	//Test
	public static void main (String[] args)
	{
		MainGuiTabbedInterface mainInt = new MainGuiTabbedInterface();
	}
	

}
