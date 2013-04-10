package overwatch.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Creates a message dialog
 * The string passed is displayed to the user
 * The type of error is displayed at the top of the box
 * @author john
 *
 */




public class MessageDialog extends JOptionPane{
	
	
	public MessageDialog(){}
	
	public MessageDialog(String Message, String typeOfError)
	{
		JFrame frame = new JFrame();
		
		JOptionPane.showMessageDialog(frame, Message, Message, JOptionPane.ERROR_MESSAGE, icon);
	}

}
