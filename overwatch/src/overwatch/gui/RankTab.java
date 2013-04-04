package overwatch.gui;

import javax.swing.JFrame;

import overwatch.core.Gui;
import overwatch.db.EnhancedResultSet;





/**
 * Rank tab
 * @author john
 * Version 4
 */





public class RankTab extends GenericPanelButtoned<Integer>{
	
	LabelFieldPair name;
	LabelFieldPair privileges;

	
	
	
	public RankTab()
	{
		super( "Rank", "Details" );		
		setupComponents();
	}
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////

	
	
	private void setupComponents()
	{
		name    	= addLabelledField( "Name:");
		privileges  = addLabelledField("Privileges:");
	}
		
	
	
	
	
	
	
	
	//Test
	public static void main(String[] args)
	{
		Gui.setNativeStyle();
		
		JFrame frame = new JFrame();		
		RankTab rt 	 = new RankTab();
		
		frame.add(rt);
		frame.pack();
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		new RankLogic(rt);
	}

}

