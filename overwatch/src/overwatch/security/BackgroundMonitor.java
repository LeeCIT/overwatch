


package overwatch.security;

import java.util.Date;
import java.util.Vector;
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
	private static Vector<BackgroundMonitor> monitors = new Vector<BackgroundMonitor>();
	
	
	
	private Vector<BackgroundCheck> checks;
	private Thread                  thread;
	private boolean					threadTerminate;
	
	
	
	
	
	/**
	 * Create and begin monitoring immediately in a separate thread.
	 * There are no default BackgroundCheck objects, you must add some.
	 */
	public BackgroundMonitor()
	{
		monitors.add( this );
		
		checks = new Vector<BackgroundCheck>();
		thread = createThread();
		thread.start();
	}
	
	
	
	
	
	/**
	 * Stop monitoring.  Terminates the thread.
	 * WARNING: Do not call this from within a BackgroundChecker!
	 */
	public void stop()
	{		
		for (;;) {
			try {
				threadTerminate = true;
				thread.join();
				monitors.remove( this );
				return;
			} catch ( InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
	
	
	/**
	 * Stops all background monitors that exist.
	 */
	public static void stopAll() {
		
		System.out.println( "stopping all" );
		while ( ! monitors.isEmpty()) {
			BackgroundMonitor bgm =  monitors.lastElement();
			System.out.println( "stopping " + bgm );
			bgm.stop();
			monitors.remove( bgm );
		}
	}
	
	
	
	
	
	/**
	 * Add a check to be executed in the background.
	 * Checks are performed sequentially, in the order they're added, once per second.
	 * @param bc
	 */
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
				if (LoginManager.hasUser()) {
					int user = 1;//LoginManager.getCurrentUser();
					
					
					java.sql.Connection conn = Database.getConnection();
					System.out.println( "" + new Date().getTime() + ": conn get " + conn );
					
					//System.out.println( "Checking that " + user + " still exists..." );
					
//					if (Database.queryInts( "select personNo from Personnel where personNo = " + user + ";").length == 0)
//					{
//						Gui.showErrorDialogue(
//							"Forced Logout",
//							"Your account has been deleted."
//						);
//						System.exit(0);
//					}
					
					try { Thread.sleep( (int) (Math.random()*3000.0) ); }
					catch ( InterruptedException e ) {
						e.printStackTrace();
					}
					
					System.out.println( "" + new Date().getTime() + ": conn ret " + conn );
					Database.returnConnection( conn );
				}
			}
		};
		
		
		
		for (int i=0; i<32; i++) {
			BackgroundMonitor bgm = new BackgroundMonitor();
			bgm.addBackgroundCheck( userLoggedin );
			
			try { Thread.sleep( (int) (Math.random()*1000.0) ); }
			catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}
	
}












































