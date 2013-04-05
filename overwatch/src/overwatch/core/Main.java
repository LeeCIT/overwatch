


package overwatch.core;

import overwatch.gui.LoginFrame;
import overwatch.gui.LoginListener;
import overwatch.security.LoginManager;





public class Main
{
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		
		createLoginFrame();
	}
	
	
	
	
	
	private static void createLoginFrame()
	{
		final LoginFrame frame = new LoginFrame();
		frame.pack();
		frame.setVisible( true );
		
		frame.addLoginListener( new LoginListener() {
			public void onLoginAttempt( String user, String pass )
			{
				if (LoginManager.doLogin( user, pass )) {
					createMainGui();
					frame.dispose();
				} else {
					Gui.showErrorDialogue( "Invalid Login", "Incorrect login details." );
				}
			}
		});
	}





	private static void createMainGui()
	{
		final Gui gui = new Gui();
		Controller.attachLogicControllers( gui );
	}
}
