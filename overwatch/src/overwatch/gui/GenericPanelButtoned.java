


package overwatch.gui;

import java.awt.event.ActionListener;





/**
 * GenericPanel with new/save/delete buttons pre-integrated.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class GenericPanelButtoned<T> extends GenericPanel<T>
{
	protected StandardButtonTriplet buttons;
	
	
	
	
	
	public GenericPanelButtoned( String searchLabelText, String mainLabelText )
	{
		super( searchLabelText, mainLabelText );
		buttons = addNewSaveDeleteButtons();
	}
	
	
	
	
	
	// Button events
	public void addNewListener   ( ActionListener e ) { buttons.addNew.addActionListener(e); }
	public void addSaveListener  ( ActionListener e ) { buttons.save  .addActionListener(e); }
	public void addDeleteListener( ActionListener e ) { buttons.delete.addActionListener(e); }
}
