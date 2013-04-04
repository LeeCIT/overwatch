


package overwatch.core;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import overwatch.gui.DialogueAnswer;
import overwatch.gui.PersonnelTab;





/**
 * Core GUI class.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class Gui extends JFrame
{
	// Statics
	public static Gui currentInstance; // Main JFrame
	
	
	
	
	
	// Instance GUI elements
	public final JTabbedPane  tabPane;
	public final PersonnelTab personnelTab;
	
	
	
	
	
	public Gui()
	{
		super( "Overwatch" );
		
		currentInstance = this;
		
		// Create
		tabPane      = new JTabbedPane();
		personnelTab = new PersonnelTab();
		
		
		// Tabs
		tabPane.addTab( "Personnel", personnelTab );
		
		
		// Layout
		add( tabPane );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		pack();
		setVisible(true);
	}
	
	
	
	
	
	/**
	 * Set native GUI style based on the current platform.
	 * Must be called FIRST, before any GUI components are created.
	 */
	public static void setNativeStyle()
	{
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}	
	}
	
	
	
	
	
	/**
	 * Modally ask a question, with three possible answers.
	 * @param title
	 * @param question
	 * @return DialogueAnswer
	 */
	public static DialogueAnswer askYesNoCancel( String title, String question )
	{
		int answer = JOptionPane.showConfirmDialog( Gui.currentInstance, question, title, JOptionPane.YES_NO_CANCEL_OPTION );
		
		switch (answer) {
			case JOptionPane.YES_OPTION:    return DialogueAnswer.YES;
			case JOptionPane.NO_OPTION:     return DialogueAnswer.NO;
			default:						return DialogueAnswer.CANCEL;
		}
	}
	
	
	
	
	
	/**
	 * Modally confirm an action.
	 * @param title
	 * @param question
	 * @return boolean
	 */
	public static boolean askConfirm( String title, String question )
	{
		int answer = JOptionPane.showConfirmDialog( Gui.currentInstance, question, title, JOptionPane.YES_NO_OPTION );
		
		switch (answer) {
			case JOptionPane.YES_OPTION: return true;
			default:					 return false;
		}
	}
	
	
	
	
	
	/**
	 * Show a modal warning dialogue.
	 * @param title
	 * @param message
	 */
	public static void showWarningDialogue( String title, String message ) {
		JOptionPane.showMessageDialog( Gui.currentInstance, message, title, JOptionPane.WARNING_MESSAGE );
	}
	
	
	
		
	
	/**
	 * Show a modal error dialogue.
	 * @param title
	 * @param message
	 */
	public static void showErrorDialogue( String title, String message ) {
		JOptionPane.showMessageDialog( Gui.currentInstance, message, title, JOptionPane.ERROR_MESSAGE );
	}
}
