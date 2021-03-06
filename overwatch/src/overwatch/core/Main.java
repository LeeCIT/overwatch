


package overwatch.core;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import overwatch.db.Database;
import overwatch.db.Personnel;
import overwatch.gui.LoginFrame;
import overwatch.gui.LoginListener;
import overwatch.security.BackgroundMonitor;
import overwatch.security.LoginManager;





/**
 * The alpha and omega of the program.
 * Creates the core classes, delegates control, and waits to shut down.  
 * 
 * @author  Lee Coakley
 * @version 3
 */





public class Main
{
	private static final Object shutdownSyncWaiter = new Object();
	
	
	
	
	
	/**
	 * ENTRYPOINT: The program begins here.
	 */
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		createLoginFrame();
		createAndRunShutdownLatchThread();
	}
	
	
	
	
	
	/**
	 * Terminates background threads, disconnects the database, and exits the program.
	 */
	public static void shutdown() {
		synchronized (shutdownSyncWaiter) {
			shutdownSyncWaiter.notify();
		}
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private static void createAndRunShutdownLatchThread()
	{
		Thread shutdownSignalReceiver = new Thread( new Runnable() {
			public void run()
			{
				synchronized (shutdownSyncWaiter) {
					try { shutdownSyncWaiter.wait(); } // Lock until notify, unlocked by Main.shutdown()
					catch (InterruptedException ex) { ex.printStackTrace(); }
					Main.shutdownInternal();
				}
			}
		});
		
		shutdownSignalReceiver.setName( "shutdownSignalReceiver" );
		shutdownSignalReceiver.start();
	}
	
	
	
	
	
	private static void shutdownInternal()
	{
		try {
			BackgroundMonitor.stopAll();
			Database.disconnect();
		}
		catch (Exception ex) {
			// Don't give a crap, we're shutting down come hell or high water
		}
		finally {
			System.exit( 0 );
		}
	}
	
	
	
	
	
	private static void createLoginFrame()
	{
		Thread thread = new Thread( new Runnable() {
			public void run()
			{
				final LoginFrame frame = new LoginFrame();
				frame.pack();
				frame.setLocationByPlatform( true );
				frame.setVisible( true );
				
				frame.addLoginListener( new LoginListener()
				{
					public void onLoginAttempt( String user, String pass ) {
						if (LoginManager.doLogin( user, pass )) {
							 frame.loginButton.setEnabled( false );
						     createMainGui( frame );
						} else {
							Gui.showError( "Invalid Login", "Incorrect login details." );
						}
					}
				});
			}
		});
		
		thread.start();
	}
	
	
	
	
	
	private static void createMainGui( final JFrame creatorFrame )
	{
		Thread thread = new Thread( new Runnable() {
			public void run()
			{
				Dimension size = new Dimension( 860, 512 );
				
				final Gui gui = new Gui();
				Controller.attachLogicControllers( gui );
				gui.pack();
				gui.setSize( size );
				gui.setMinimumSize( size );
				gui.setLocationRelativeTo( creatorFrame );
				gui.setTitleDescription( Personnel.getLoginName( LoginManager.getCurrentUser() ) );
				gui.setVisible( true );
				
				creatorFrame.dispose();
				
				gui.addWindowListener( new WindowAdapter() {
					public void windowClosing( WindowEvent e ) {
						shutdown();
					}
				});
			}
		});
		
		thread.start();
	}
}













