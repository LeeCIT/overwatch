


package overwatch.gui;

import overwatch.core.Gui;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;





public class AssignPanel<T> extends JPanel
{
	private ArrayList<NameRefPair<T>> listItems;
	 
	private JLabel                label;
	private JButton               buttRemove;
	private JButton               buttAdd;
	private JList<NameRefPair<T>> list;
	private JScrollPane           scrollPane;
	
	
	
	
	
	private AssignPanel()
	{
		super(  new MigLayout("filly", "[grow]", "[][fill,grow][]")  );
	}
	
	
	
	
	
	public AssignPanel( String labelText )
	{
		this(  labelText,  new ArrayList<NameRefPair<T>>()  );
	}
	
	
	
	
	
	public AssignPanel( String labelText, ArrayList<NameRefPair<T>> pickables )
	{
		this();
		setup( labelText, pickables );
	}
	
	
	
	
	
	public AssignPanel( String labelText, T[] pickables )
	{
		this();
		setup( labelText, toPairList(pickables) );
	}
	
	
	
	
	
	/**
	 * Generates an event when the selected list item changes.
	 * @param lis
	 */
	public void addListSelectionListener( ListSelectionListener lis ) {
		list.addListSelectionListener( lis );
	}
	
	
	
	
	
	/**
	 * Generates an event when the remove button is used.
	 * @param lis
	 */
	public void addRemoveButtonListener( ActionListener lis ) {
		buttRemove.addActionListener( lis );
	}
	
	
	
	
	
	/**
	 * Generates an event when the add button is used.  This should create a picker of some kind.
	 * @param lis
	 */
	public void addAddButtonListener( ActionListener lis ) {
		buttAdd.addActionListener( lis );
	}
	
	
	
	
	
	/**
	 * Set object/string pairs.
	 */
	public void setListItems( ArrayList<NameRefPair<T>> items ) {		
		listItems = (ArrayList<NameRefPair<T>>) items.clone();
		resetDisplayedItems();
	}
	
	
	
	
	
	/**
	 * Set object/string pairs.
	 */
	public void setListItems( T[] array ) {		
		setListItems( toPairList(array) );
	}
	
	
	
	
	
	/**
	 * As before, except for generic objects.
	 * This would be an overload and a separate constructor, but 
	 * Java's shitty type-erasure-based generics make that impossible. 
	 */
	public void setListItemsByPlainList( ArrayList<T> items )
	{		
		ArrayList<NameRefPair<T>> pairs = new ArrayList<NameRefPair<T>>();
		
		for (T ref: items) {
			pairs.add(  new NameRefPair<T>(ref, ref.toString())  );
		}
		
		setListItems( pairs );
	}
	
	
	
	
	
	public void addItem( T item )
	{
		// TODO
	}
	
	
	
	
	public boolean removeItem( T item )
	{
		// TODO
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private T getSelectedItem()
	{
		if (this.hasSelectedItem()) {
			return list.getSelectedValue().getRef();
		} else {
			return null;
		}
	}
	
	
	
	

	private boolean hasSelectedItem() {
		return (!list.isSelectionEmpty());
	}
	
	
	
	
	
	private void setup( String labelText, ArrayList<NameRefPair<T>> items )
	{
		setupComponents( labelText );
		setupActions();
		setListItems( items );
	}
	
	
	
	
	
	private void setupComponents( String labelText )
	{		
		label       = new JLabel( labelText );
		buttAdd     = new JButton( "Add..." );
		buttRemove  = new JButton( "Remove" );
		
		list = new JList<NameRefPair<T>>();
		list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		scrollPane = new JScrollPane( list );
		scrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scrollPane.setVerticalScrollBarPolicy  ( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS      );
		
		add( label,       "wrap, growy 0" );
		add( scrollPane,  "wrap, grow, hmin 72px" );
		add( buttAdd, 	  "split 2, alignx left"  );
		add( buttRemove,  "alignx right" );
	}
	
	
	
	
	
	private void setupActions()
	{
		list.addKeyListener( new KeyAdapter() {
			public void keyReleased( KeyEvent e ) {}
			public void keyPressed ( KeyEvent e ) {}
			public void keyTyped   ( KeyEvent e ) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
					removeItem( getSelectedItem() );
			}
		});
		
		
		addRemoveButtonListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				removeItem( getSelectedItem() );
			}
		});
	}
	
	
	
	
	
	private void setDisplayedItems( ArrayList<NameRefPair<T>> items ) {
		list.setListData(  items.toArray(  new NameRefPair[items.size()]  )  );
	}
	
	
	
	
	
	private void resetDisplayedItems() {
		setDisplayedItems( listItems );
	}
	
	
	
	
	
	private ArrayList<NameRefPair<T>> toPairList( T[] array )
	{
		ArrayList<NameRefPair<T>> list = new ArrayList<NameRefPair<T>>();
		
		for (T el: array) {
			list.add( new NameRefPair<T>( el, el.toString() ) );
		}
		
		return list;
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args)
	{
		Gui.setNativeStyle();
		
		JFrame frame = new JFrame();
		frame.add( new AssignPanel<Integer>("label") );
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}










































