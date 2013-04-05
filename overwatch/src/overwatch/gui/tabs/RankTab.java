package overwatch.gui.tabs;

import javax.swing.JFrame;
import overwatch.controllers.RankLogic;
import overwatch.core.Gui;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldPair;





/**
 * Rank tab
 * @author john
 * @Version 5
 */





public class RankTab extends GenericPanelButtoned<Integer>{
	
	public final LabelFieldPair name;
	public final LabelFieldPair privileges;

	
	
	
	public RankTab()
	{
		super( "Rank", "Details" );		
	
		name    	= addLabelledField("Name:");
		privileges  = addLabelledField("Privileges:");
	}
	
	
	
	
	//Validate
	public void addNameValidator(CheckedFieldValidator v){name.field.addValidator(v);	}
	public void addPrivilegesValidator(CheckedFieldValidator v){privileges.field.addValidator(v);	}
	
	
	
	
	
	
	
	
	
	
	
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

