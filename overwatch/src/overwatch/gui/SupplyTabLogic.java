package overwatch.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Supply tab logic
 * @author john
 * version 1
 */

public class SupplyTabLogic {
	
	public SupplyTabLogic(SuppliesTab st)
	{
		attatchButtonEvents(st);
	}
	
	
	
	public void attatchButtonEvents(SuppliesTab st)
	{
		newSupply(st);
		deleteSupply(st);
		saveSupply(st);		
	}
	
	
	
	
	public void newSupply(SuppliesTab st)
	{
		st.buttons.addNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked add new");	
			}
		});
	}
	
	
	
	
	public void deleteSupply(SuppliesTab st)
	{
		st.buttons.delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked delete");
			}
		});
	}
	
	
	
	
	public void saveSupply(SuppliesTab st)
	{
		st.buttons.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked save");
			}
		});
	}

}
