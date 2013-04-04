


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
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;





/**
 * Generic panel with a left-side search panel and right-side main panel.
 * All tabs based on basic editing of attributes should extend this one. 
 * Templated on the SearchPanel type.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class GenericPanel<T> extends JPanel
{
	public static final int defaultFieldWidth = 24;
	
	protected SearchPanel<T> searchPanel;
	protected JPanel         mainPanel;
	protected JLabel         mainLabel;
	protected JPanel         subPanel;
	
	
	
	
	
	public GenericPanel( String searchLabelText, String mainLabelText )
	{
		super( new MigLayout( "debug", "[shrink 100][grow,fill]", "[grow,fill][]" ) );
		
		this.searchPanel = new SearchPanel<T>( searchLabelText );
		this.mainPanel   = new JPanel( new MigLayout() );
		this.mainLabel   = new JLabel( mainLabelText );
		this.subPanel    = new JPanel( new MigLayout() );
		
		mainPanel.add( mainLabel, "cell 1 0, wrap" );
		
		add( searchPanel, "wmin 96px, wmax 224px, spany 2" );
		add( mainPanel,   "alignx left, wrap"     );
		add( subPanel,    "alignx right, skip 1"  );
	}
	
	
	
	
	
	public <C extends Component> C addToMain( C comp, String layoutParams ) {
		mainPanel.add( comp, layoutParams );
		return comp;
	}
	
	
	
	
	
	public <C extends Component> C addToSub( C comp, String layoutParams ) {
		subPanel.add( comp, layoutParams );
		return comp;
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
		CheckedField f = new CheckedField( defaultFieldWidth );
		JButton      b = new JButton( "..." );
		
		addToMain( l, "alignx right"     );
		addToMain( f, "growx, split 2"   );
		addToMain( b, "wmax 32px, wrap"	 );
		
		return new LabelFieldEllipsisTriplet( l,f,b );
	}
	
	
	
	
	
	public StandardButtonTriplet addNewSaveDeleteButtons()
	{
		JButton a = new JButton( "New"    );
		JButton s = new JButton( "Save"   );
		JButton d = new JButton( "Delete" );
		
		addToSub( a, "alignx right, split 3" );
		addToSub( s, "" );
		addToSub( d, "gap left 32px" );
		
		return new StandardButtonTriplet( a,s,d );
	}
	
	
	
	
	
	public void setSearchableItems( ArrayList<NameRefPair<T>> pairs )
	{
		searchPanel.setSearchableItems( pairs );
	}
	
	
	
	
	public void addSearchPanelListSelectionListener( ListSelectionListener lis ) {
		searchPanel.addListSelectionListener( lis );
	}
	
	
	
	
	
	public T getSelectedItem() {
		return searchPanel.getSelectedItem();
	}
	
	
	
	
	
	public boolean hasSelectedItem() {
		return searchPanel.hasSelectedItem();
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		
		final GenericPanel<Integer> gp = new GenericPanel<Integer>( "SearchLabel", "MainLabel" );
		gp.addLabelledField( "Name:" );
		gp.addLabelledField( "Age:"  );

		final LabelFieldEllipsisTriplet lft = gp.addLabelledFieldWithEllipsis( "Rank:" );
		
		final StandardButtonTriplet sbt = gp.addNewSaveDeleteButtons();
		
		
		lft.field.addValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return text.length() == 4;
			}
		});
		
		
		
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


































