


package overwatch.gui;

import overwatch.core.Gui;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;





/**
 * Generic panel with a left-side search panel and right-side main panel.
 * All tabs based on basic editing of attributes should extend this one. 
 * Templated on the SearchPanel type.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class GenericPanel<T> extends JPanel
{
	public static final int defaultFieldWidth = 24;
	
	protected SearchPanel<T> searchPanel;
	protected JPanel         mainPanel;
	protected JLabel         mainLabel;
	
	
	
	
	
	public GenericPanel( String searchLabelText, String mainLabelText )
	{
		super( new MigLayout( "debug", "[shrink 150][grow,fill]", "[grow,fill][]" ) );
		
		this.searchPanel = new SearchPanel<T>( searchLabelText );
		this.mainPanel   = new JPanel( new MigLayout() );
		this.mainLabel   = new JLabel( mainLabelText );
		
		mainPanel.add( mainLabel, "cell 1 0, wrap" );
		
		add( searchPanel, "wmin 72px, wmax 224px" );
		add( mainPanel,   "alignx left"   );
	}
	
	
	
	
	
	public void addToMain( Component comp, String layoutParams ) {
		mainPanel.add( comp, layoutParams );
	}
	
	
	
	
	
	public LabelFieldPair addLabelledField( String label )
	{
		JLabel       l = new JLabel( label );
		CheckedField f = new CheckedField( defaultFieldWidth );
		
		addToMain( l, "alignx right" );
		addToMain( f, "growx, wrap"  );
		
		return new LabelFieldPair( l,f );
	}
	
	
	
	
	
	public LabelFieldEllipsisTriplet addLabelledFieldWithEllipsis( String label )
	{
		JLabel       l = new JLabel ( label );
		CheckedField f = new CheckedField( 16 );
		JButton      b = new JButton( "..." );
		
		addToMain( l, "alignx right"     );
		addToMain( f, "growx, split 2"   );
		addToMain( b, "wmax 32px, wrap"	 );
		
		return new LabelFieldEllipsisTriplet( l,f,b );
	}
	
	
	
	
	
	public void addSearchPanelListSelectionListener( ListSelectionListener lis ) {
		searchPanel.addListSelectionListener( lis );
	}
	
	
	
	
	
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		
		final GenericPanel<Integer> gp = new GenericPanel<Integer>( "SearchLabel", "MainLabel" );
		gp.addLabelledField( "Name:" );
		gp.addLabelledField( "Age:"  );
		
		final LabelFieldEllipsisTriplet lft = gp.addLabelledFieldWithEllipsis( "Rank:" );
		
		final JFrame jf = new JFrame();
		jf.add( gp );
		jf.setSize( new Dimension(640,400) );
		jf.setVisible( true );
		
		lft.button.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				SearchPicker<Integer> pick = new SearchPicker<Integer>( jf, "Title", "Label", new NameRefPairList<Integer>() );
				
				pick.addPickListener( new PickListener<Integer>() {
					public void onPick( Integer picked ) {
						System.out.println( "Picked: " + picked );
						
						if (picked != null) {
							lft.field.setText( picked.toString() );
						}
					}
				});
			}
		});
	}
}


































