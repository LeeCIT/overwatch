


package overwatch.gui.tabs;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import net.miginfocom.swing.MigLayout;
import overwatch.core.Gui;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.MessagePanel;





public class PersonnelTabChangePassDialog extends JDialog
{
	public final JLabel         passOldLabel;
	public final JLabel         passNewLabel;
	public final JLabel         passConfLabel;
	public final JPasswordField passOld;
	public final JPasswordField passNew;
	public final JPasswordField passConf;
	public final JButton        okay;
	public final JButton        cancel;
	
	
	
	
	
	public PersonnelTabChangePassDialog( Component relativeTo )
	{
		super( Gui.getCurrentInstance(), "Overwatch - Change Login Password" );
		
		setLayout( new MigLayout( "debug", "[][grow]", "[][][]" ) );
		setIconImage( new ImageIcon("images/ico16px.png").getImage() );

		passOldLabel  = new JLabel( "Old pass:"     );
		passNewLabel  = new JLabel( "New pass:"     );
		passConfLabel = new JLabel( "Confirm pass:" );
		passOld       = new JPasswordField();
		passNew       = new JPasswordField();
		passConf      = new JPasswordField();
		okay          = new JButton( "OK"   );
		cancel        = new JButton( "Cancel" );
		
		add( passOldLabel,  "alignx right" );
		add( passOld,       "growx, wrap" );
		add( passNewLabel,  "alignx right" );
		add( passNew,       "growx, wrap" );
		add( passConfLabel, "alignx right" );
		add( passConf,      "growx, wrap" );
		add( okay,          "grow 0, span 2, split 2, right" );
		add( cancel,        "grow 0" );
		
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		setModalityType( ModalityType.APPLICATION_MODAL );
		
		pack();
		setSize( 256, getSize().height );
		setLocationRelativeTo( relativeTo );
		setVisible( true );
	}
	
	
	
	// Buttons
	public void addOkayButtonListener  ( ActionListener a ) { okay.addActionListener(a);  }
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		new PersonnelTabChangePassDialog(  new JFrame()  );
	}
	
}
















































