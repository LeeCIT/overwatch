


package overwatch.core;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import overwatch.gui.DialogueAnswer;





public class Gui extends JFrame
{
	private static Gui currentInstance;
	
	
	
	
	
	public Gui()
	{
		currentInstance = this;
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
