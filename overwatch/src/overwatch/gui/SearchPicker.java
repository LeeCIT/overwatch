


package overwatch.gui;

import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;





/**
 * A modal dialogue for picking stuff from the database.
 * 
 * @author  Lee Coakley
 * @version 1
 * @see 	SearchPanel
 */





public class SearchPicker<T> extends JDialog
{
	private SearchPanel<T> searchPanel;
	private JButton        buttOkay;
	private JButton        buttCancel;
	
	private ArrayList<PickListener<T>> pickListeners;
	
	
	
	
	
	public SearchPicker( JFrame frame, String title, String label, ArrayList<NameRefPair<T>> searchables )
	{
		super( frame, title );
		
		setLayout(  new MigLayout( "debug" )  );
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
		searchPanel = new SearchPanel<T>( label, searchables );
		buttOkay    = new JButton( "OK" );
		buttCancel  = new JButton( "Cancel" );
		
		setupComponents();
		setupActions();
	}
	
	
	
	
	
	public void addPickListener( PickListener<T> pl ) {
		pickListeners.add( pl );
	}
	
	
	
	
		
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private void setupComponents()
	{
		add( searchPanel, "wrap" );
		add( buttOkay, "split 2, alignx right" );
		add( buttCancel );
	}





	private void setupActions()
	{
		buttOkay.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				notifyListeners( searchPanel.getSelectedItem() );				
			}
		});
		
		
		
		this.addWindowListener( new WindowListener() {
			public void windowOpened     ( WindowEvent e ) {}
			public void windowIconified  ( WindowEvent e ) {}
			public void windowDeiconified( WindowEvent e ) {}
			public void windowDeactivated( WindowEvent e ) {}
			public void windowClosing    ( WindowEvent e ) { notifyListeners( null ); }
			public void windowClosed     ( WindowEvent e ) {}
			public void windowActivated  ( WindowEvent e ) {}
		});
		
	}
	
	
	


	private void notifyListeners( T elem )
	{
		System.out.println( "event notify" );
		
		for (PickListener<T> pl: pickListeners) {
			pl.onPick( elem );
		}
	}
		
}






































