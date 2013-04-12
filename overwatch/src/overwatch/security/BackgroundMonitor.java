


package overwatch.security;

import java.util.Date;
import java.util.Vector;
import overwatch.db.Database;





/**
 * Runs in the background in a separate thread.
 * Give it things to check with addBackgroundCheck.
 * 
 * @author  Lee Coakley
 * @version 2
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
	 * Checks are performed in-order every 1000ms.
	 */
	public BackgroundMonitor() {
		this( 1000 );
	}
	
	
	
	
	
	/**
	 * As before, but you specify the interval between checking in milliseconds.
	 * @param interval milliseconds to wait before repeating all checks
	 */
	public BackgroundMonitor( int interval )
	{
		synchronized (monitors) {
			monitors.add( this );
		}
		
		checks = new Vector<BackgroundCheck>();
		thread = createThread( interval );
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
				thread.interrupt(); // Stop sleeping
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
		while ( ! monitors.isEmpty()) {
			BackgroundMonitor bgm =  monitors.lastElement();
			bgm.stop();
			monitors.remove( bgm );
		}
	}
	
	
	
	
	
	/**
	 * Add a check to be executed in the background.
	 * Checks are performed sequentially, in the order they're added, once per second.
	 * @param bc
	 */
	public void addBackgroundCheck( BackgroundCheck bc ) {
		synchronized (checks) {
			checks.add( bc );
		}
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private Thread createThread( final int interval )
	{
		Thread t = new Thread( new Runnable() {
			public void run() {
				doChecks( interval );				
			}
		});
		
		t.setName( "BackgroundMonitor " + t );
		
		return t;
	}
	
	
	
	
	
	private void doChecks( int interval )
	{
		while ( ! threadTerminate)
		{
			synchronized (checks) {
				for (BackgroundCheck check: checks) {
					check.onCheck();
				}
			}
			
			
			try { Thread.sleep( interval );
			} catch (InterruptedException ex) {
				// Nothing to do: just exit the thread.
			}
		}
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		BackgroundCheck userLoggedin = new BackgroundCheck() {
			public void onCheck() 
			{					
				java.sql.Connection conn = Database.getConnection();
				System.out.println( "" + new Date().getTime() + ": conn get " + conn );
				
				try { Thread.sleep( (int) (Math.random()*3000.0) ); }
				catch ( InterruptedException e ) {
					e.printStackTrace();
				}
				
				System.out.println( "" + new Date().getTime() + ": conn ret " + conn );
				Database.returnConnection( conn );
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












































