


package overwatch.gui.tabs;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import overwatch.core.Gui;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.MessagePanel;




/**
 * Creates a new order dialogue.  It's not modal.
 * 
 * @author lsnk
 *
 */




public class OrderTabCreateDialog extends JDialog
{
	public final MessagePanel message;
	public final JButton      send;
	public final JButton      cancel;
	
	
	
	
	
	public OrderTabCreateDialog( Component relativeTo )
	{
		super( Gui.getCurrentInstance(), "Overwatch - Create Order" );
		
		setLayout( new MigLayout( "", "[grow,fill]", "[grow,fill][]" ) );

		this.message = new MessagePanel();
		this.send    = new JButton( "Send" );
		this.cancel  = new JButton( "Cancel" );
		
		add( message, "grow, wrap" );
		add( send,    "grow 0, split 2, right" );
		add( cancel,  "grow 0" );
		
		message.sentBy.field.setEditable( false );
		message.sentBy.field.setText( "<You>" );
		message.date  .field.setEditable( false );
		message.date  .field.setText( "<Now>" );
		
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		setModalityType( ModalityType.MODELESS );
		
		setSize( 400, 640 );
		setLocationRelativeTo( relativeTo );
		setVisible( true );
	}
	
	
	
	
	
	// Message
	public void addValidatorSend   ( CheckedFieldValidator v ) {  message.sentTo .field.addValidator(v);  }
	public void addValidatorSubject( CheckedFieldValidator v ) {  message.subject.field.addValidator(v);  }
	
	// Buttons
	public void addSendEllipsisListener( ActionListener a ) { message.sentTo.button.addActionListener(a);  }
	public void addSendButtonListener  ( ActionListener a ) { send                 .addActionListener(a);  }
	public void addCancelButtonListener( ActionListener a ) { cancel               .addActionListener(a);  }
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		new OrderTabCreateDialog(  new JFrame()  );
	}
	
}















































