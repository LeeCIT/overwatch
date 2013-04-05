


package overwatch.security;

import java.util.Date;
import java.util.Vector;
import com.sun.jmx.snmp.Timestamp;
import overwatch.core.Gui;
import overwatch.db.Database;





/**
 * Runs in the background in a separate thread.
 * Give it things to check with addBackgroundCheck.
 * 
 * @author  Lee Coakley
 * @version 1
 * @see     BackgroundCheck
 */





public class BackgroundMonitor
{
	private Vector<BackgroundCheck> checks;
	private Thread                  thread;
	private boolean					threadTerminate;
	
	
	
	
	
	/**
	 * Create and begin monitoring immediately in a separate thread.
	 * There are no default BackgroundCheck objects, you must add some.
	 */
	public BackgroundMonitor()
	{
		checks = new Vector<BackgroundCheck>();
		thread = createThread();
		thread.start();
	}
	
	
	
	
	
	/**
	 * Stop monitoring.  Terminates the thread.
	 */
	public void stop()
	{
		for (;;) {
			try {
				threadTerminate = true;
				thread.join();
			} catch ( InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
	
	
	public synchronized void addBackgroundCheck( BackgroundCheck bc ) {
		checks.add( bc );
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private Thread createThread()
	{
		Thread t = new Thread( new Runnable() {
			public void run() {
				doChecks();				
			}
		});
		
		t.setName( "BackgroundMonitor" );
		
		return t;
	}
	
	
	
	
	
	private void doChecks()
	{
		while ( ! threadTerminate)
		{
			for (BackgroundCheck check: checks) {
				check.onCheck();
			}
			
			try { Thread.sleep( 1000 );
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		BackgroundCheck userLoggedin = new BackgroundCheck() {
			public void onCheck() {
				if (LoginManager.hasCurrentUser()) {
					int user = 1;//LoginManager.getCurrentUser();
					
					System.out.println( "Get conn @ " + new Date().getTime() );
					java.sql.Connection conn = Database.getConnection();
					System.out.println( conn );
					
					System.out.println( "Checking that " + user + " still exists..." );
					
					if (Database.queryInts( "select personNo from Personnel where personNo = " + user + ";").length == 0)
					{
						Gui.showErrorDialogue(
							"Forced Logout",
							"Your account has been deleted."
						);
						System.exit(0);
					}
					
					System.out.println( "Return conn @ " + new Date().getTime() );
					Database.returnConnection( conn );
				}
			}
		};
		
		
		
		for (int i=0; i<16; i++) {
			BackgroundMonitor bgm = new BackgroundMonitor();
			bgm.addBackgroundCheck( userLoggedin );
			
			try { Thread.sleep( (int) (Math.random()*1000.0) ); }
			catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}
	
}












































