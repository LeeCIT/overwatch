


package overwatch.gui;

import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;





/**
 * Panel for searching a list of short text items.
 * 
 * @author  Lee Coakley
 * @version 2
 * @see     NameRefPair
 */





public class SearchPanel extends JPanel
{
	private ArrayList<NameRefPair> searchableItems;
	 
	private JLabel             label;	
	private JTextField         searchField;
	private JButton            searchClear;
	private JList<NameRefPair> searchList;
	private JScrollPane        scrollPane;
	
	
	
	
	
	public SearchPanel( String labelText, ArrayList<NameRefPair> searchableItems )
	{
		super(  new MigLayout("fill", "[]", "[][fill,grow][]")  );
		
		setupComponents( labelText );
		setupActions();
		setSearchableItems( searchableItems );
	}
	
	
	
	
	
	public void addListSelectionListener( ListSelectionListener lis ) {
		searchList.addListSelectionListener( lis );
	}
	
	
	
	public void addClearButtonListener( ActionListener lis ) {
		searchClear.addActionListener( lis );
	}
	
	
	
	
	
	/**
	 * Set which strings are considered for searching/display.
	 * Searching does not alter this data.
	 * If the search field is empty then everything is displayed.
	 * @param items Array of strings.
	 */
	public void setSearchableItems( ArrayList<NameRefPair> items )
	{		
		searchableItems = (ArrayList<NameRefPair>) items.clone();
		resetDisplayedItems();
	}
	
	
	
	
	
	public Object getSelectedItem()
	{
		if (this.hasSelectedItem()) {
			return searchList.getSelectedValue().getRef();
		} else {
			return null;
		}
	}
	
	
	
	
	
	public void setSelectedItem( Object item ) 
	{
		doSearchClear();
		
		for (int i=0; i<getSearchableItemCount(); i++) {
			if (searchableItems.get( i ).getRef() == item) {
				setSelectedIndex( i );
				return;
			}
		}
	}
	
	
	
	

	public boolean hasSelectedItem() {
		return (!searchList.isSelectionEmpty());
	}
	
	
	
	public int getSearchableItemCount() {
		return searchableItems.size();
	}
	
	
	
	public int getDisplayedItemCount() {
		return searchList.getModel().getSize();
	}
	
	
	
	public int getSelectedIndex() {
		return searchList.getSelectedIndex();
	}
	
	
	
	public void setSelectedIndex( int index ) {
		searchList.setSelectedIndex( index );
	}
	
	
	
	public String getSearchInput() {
		return searchField.getText();
	}
	
	
	
	public void clearSearch() {
		doSearchClear();
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private void setupComponents( String labelText )
	{		
		label       = new JLabel( labelText );
		searchField = new JTextField( 12 );
		searchClear = new JButton( "Clear" );
		
		searchList = new JList<NameRefPair>();
		searchList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		scrollPane = new JScrollPane( searchList );
		scrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scrollPane.setVerticalScrollBarPolicy  ( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS      );
		
		add( label,       "wrap, growy 0" );
		add( scrollPane,  "wrap, grow, hmin 72px" );
		add( searchField, "growx, split 2" );
		add( searchClear );
	}
	
	
	
	
	
	private void setupActions()
	{
		// Search textfield
		searchField.addKeyListener( new KeyListener() {
			public void keyTyped   (KeyEvent ev) {}
			public void keyPressed (KeyEvent ev) {}
			public void keyReleased(KeyEvent ev) {  doSearchAndDisplayResults();  } 
		} );
		
		
		// Search clear button
		searchClear.addActionListener( new ActionListener() { 
			public void actionPerformed(ActionEvent ev) {  doSearchClear();  }
		} );
	}
	
	
	
	
	
	private void doSearchAndDisplayResults()
	{
		String userStr = searchField.getText();
		
		if (userStr.isEmpty()) {
			resetDisplayedItems();
		}
		else 
		{
			ArrayList<NameRefPair> searchResult = new ArrayList<NameRefPair>();
			
			for (NameRefPair item: searchableItems) {
				if (searchCompare( item.getName(), userStr )) {
					searchResult.add( item );
				}
			}
			
			setDisplayedItems( searchResult );
		}
	}
	
	
	
	
	
	/**
	 * Compare the list item string with the user's search input.
	 * Any partial case-insensitive match counts.
	 * @param ref Reference string (internal)
	 * @param com Comparison string (user input)
	 * @return Bool, whether com matches ref according to the search criterion.
	 */
	private boolean searchCompare( String ref, String com )
	{
		String refLower = ref.toLowerCase();
		String comLower = com.toLowerCase();
		return refLower.contains( comLower );
	}
	
	
	
	
	
	private void doSearchClear() {
		searchField.setText( "" );
		resetDisplayedItems();
	}
	
	

	private void setDisplayedItems( ArrayList<NameRefPair> items ) {
		searchList.setListData(  items.toArray(  new NameRefPair[items.size()]  )  );
	}
	
	
	
	private void resetDisplayedItems() {
		setDisplayedItems( searchableItems );
	}
}













































