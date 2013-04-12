


package overwatch.gui;

import overwatch.core.Gui;
import overwatch.db.Personnel;
import overwatch.util.Util;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import net.miginfocom.swing.MigLayout;





/**
 * Generic panel which allows users to assemble a list of items.
 * See main() in this file for a usage example. 
 * 
 * @author  Lee Coakley
 * @version 2
 */





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
		super(  new MigLayout("filly", "[192px]", "[][fill,grow][]")  );
	}
	
	
	

	
	public AssignPanel( String labelText, ArrayList<NameRefPair<T>> pickables )
	{
		this();
		setup( labelText, pickables );
	}
	
	
	
	
	
	public AssignPanel( String labelText )
	{
		this(  labelText,  new ArrayList<NameRefPair<T>>()  );
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
	 * Generates an event when the add button is used.  This should create a picker of some kind.
	 * @param lis
	 */
	public void addAddButtonListener( ActionListener lis ) {
		buttAdd.addActionListener( lis );
	}
	
	
	
	
	
	/**
	 * Generates an event when the remove button is used.
	 * @param lis
	 */
	public void addRemoveButtonListener( ActionListener lis ) {
		buttRemove.addActionListener( lis );
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Set object/string pairs.
	 */
	public void setListItems( ArrayList<NameRefPair<T>> items ) {
		
		listItems = (ArrayList<NameRefPair<T>>) items.clone();
		refreshDisplayedItems();
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
	
	
	
	
	
	/**
	 * Add an item to the list.
	 * Null items are ignored.
	 * @param item
	 * @param displayAs
	 */
	public void addItem( T item, String displayAs )
	{
		if (item == null)
			return;
		
		listItems.add( new NameRefPair<T>(item, displayAs) );
		refreshDisplayedItems();
	}
	
	
	
	
	
	/**
	 * Get an ArrayList of all the items currently in the panel's list.
	 * @return ArrayList
	 */
	public ArrayList<T> getItems()
	{
		ArrayList<T> items = new ArrayList<T>();
		
		for (NameRefPair<T> pair: listItems)
			items.add( pair.ref );
		
		return items;
	}
	
	
	
	
	
	/**
	 * Remove item from the list.
	 * Note: The remove button already does this for you.
	 * @param item
	 * @return Whether it was actually there
	 */
	public boolean removeItem( T item )
	{
		boolean wasRemoved = listItems.remove( new NameRefPair<T>(item,"") );
		refreshDisplayedItems();
		return wasRemoved;
	}
	
	
	
	
	
	/**
	 * Remove items from the list.
	 * Items given which are not present in the list are simply ignored.
	 * @param items
	 */
	public void removeItems( T[] items )
	{
		ArrayList<NameRefPair<T>> remList = new NameRefPairList<T>( items );
		listItems.removeAll( remList );
		refreshDisplayedItems();
	}
	
	
	
	
	
	/**
	 * Remove items from the list which are NOT in the given array.
	 * Items not present are ignored.
	 * @param items
	 */
	public void removeItemsNotIn( T[] items )
	{
		ArrayList<NameRefPair<T>> remList = new NameRefPairList<T>( items );
		listItems.retainAll( remList );
		refreshDisplayedItems();
	}
	
	
	
	
	
	/**
	 * Clears the list.
	 */
	public void clearItems()
	{
		listItems.clear();
		refreshDisplayedItems();
	}
	
	
	
	
	
	/**
	 * Enable/disable the panel and buttons.
	 * @param enable
	 */
	public void setEnabled( boolean enable )
	{
		super     .setEnabled( enable );
		list      .setEnabled( enable );
		buttAdd   .setEnabled( enable );
		buttRemove.setEnabled( enable );
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private T getSelectedItem()
	{
		if (this.hasSelectedItem()) {
			return list.getSelectedValue().ref;
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
		add( buttAdd, 	  "split 2, right"  );
		add( buttRemove,  "split 2, right" );
	}
	
	
	
	
	
	private void setupActions()
	{
		list.addKeyListener( new KeyAdapter() {
			public void keyTyped( KeyEvent e ) {
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
	
	
	
	
	
	private void refreshDisplayedItems() {
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
		final JFrame frame = new JFrame();
		
		
		final AssignPanel<Integer> ap = new AssignPanel<Integer>("label");
		
		
		
		final PickListener<Integer> pickListener = new PickListener<Integer>() {
			public void onPick( Integer picked ) {
				ap.addItem( picked, Personnel.getLoginName(picked) );		
			}
		};
		
		
		
		ap.addAddButtonListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				new PersonnelPicker( frame, pickListener );
			}
		});
	
		
		
		
		frame.add( ap );
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}










































