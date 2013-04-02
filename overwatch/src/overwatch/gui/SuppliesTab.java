


package overwatch.gui;

import java.awt.event.ActionListener;
import javax.swing.*;
import overwatch.core.Gui;
import net.miginfocom.swing.MigLayout;





/**
 * Implements the supply management tab.
 * 
 * @author  John Murphy
 * @author  Lee Coakley
 * @version 2
 */





public class SuppliesTab extends JPanel
{
	private JLabel labelName;
	private JLabel labelType;
	private JLabel labelAmount;
	
	private JTextField fieldName;
	private JTextField fieldType;
	private JTextField fieldAmount;
	
	private JButton buttonNew;
	private JButton buttonSave;
	private JButton buttonDelete;
	
	private SearchPanel<Integer> searchPanel;
	
	
	
	
	
	public SuppliesTab()
	{
		super( new MigLayout() );
		setupComponents();
	}

	
	
	
	
	//Action listeners for each button
	public void addNewSupplyListener(ActionListener e) {
		buttonNew.addActionListener(e);
	}
	
	
	
	public void addSaveListener( ActionListener e) {
		buttonSave.addActionListener(e);
	}
	
	
	
	public void addDeleteListener(ActionListener e){
		buttonDelete.addActionListener(e);
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private void setupComponents()
	{
		labelName 	 = new JLabel( "Supply:" );
		labelType 	 = new JLabel( "Type:"   );
		labelAmount  = new JLabel( "Amount:" );
		fieldName	 = new JTextField();
		fieldType	 = new JTextField();
		fieldAmount	 = new JTextField();
		searchPanel	 = new SearchPanel<Integer>("Supplies"); //Template code to put in.
		buttonNew	 = new JButton("New");
		buttonSave	 = new JButton("Save");
		buttonDelete = new JButton("Delete");
		
		
		add(searchPanel, "west");
		add(labelName,   "gaptop 25, alignx right, cell 0 2");
		add(fieldName,   "wrap");
		add(labelType,   "alignx right");
		add(fieldType,   "wrap, grow");
		add(labelAmount, "alignx right");
		add(fieldAmount, "wrap, grow");
		add(buttonNew);
		add(buttonSave);
		add(buttonDelete);
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args)
	{
		Gui.setNativeStyle();
		
		JFrame frame = new JFrame();
		frame.add(new SuppliesTab());
		frame.pack();
		frame.setVisible(true);
		
	}

}
