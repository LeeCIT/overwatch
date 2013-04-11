


package overwatch.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;





/**
 * Scrollable text panel.  
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class ScrollableTextPanel extends JPanel
{
	private JLabel      label;
	private JTextArea   textArea;
	private JScrollPane scrollPane;
	
	
	
	
	
	public ScrollableTextPanel( String labelText )
	{
		super( new MigLayout( "", "[grow,fill]", "[][grow,fill]") );
		
		label = new JLabel( labelText );
		
		textArea = new JTextArea( "" );
		textArea.setLineWrap( true );
		textArea.setTabSize( 4 );
		
		scrollPane = new JScrollPane( textArea );
		scrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		scrollPane.setVerticalScrollBarPolicy  ( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS  );
		
		add( label, "wrap" );
		add( scrollPane );
	}
	
	
	
	
	
	/**
	 * Set text in the text area
	 * @param text
	 */
	public void setText( String text ) {
		textArea.setText( text );
	}
	
	
	
	
	/**
	 * Get the entered text
	 * @return text
	 */
	public String getText() {
		return textArea.getText();
	}
	
	
	
	
	
	/**
	 * Clear the text area
	 */
	public void clear() {
		setText("");
	}
	
	
	
	
	
	/**
	 * Make textArea editable or not.
	 */
	public void setEditable( boolean editable ) {
		textArea.setEditable( editable );
	}
	
	
	
	
		
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		javax.swing.JFrame frame = new javax.swing.JFrame();
		
		ScrollableTextPanel sta = new ScrollableTextPanel( "Test" );
		
		frame.add( sta );
		frame.pack();
		frame.setVisible( true );
	}
}
