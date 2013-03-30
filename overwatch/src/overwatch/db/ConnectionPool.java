


package overwatch.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;





/**
 * Creating new DB connections is VERY slow.
 * This maintains a pool of them in a separate thread.
 * 
 * @author Lee Coakley
 * @version 3
 */





public class ConnectionPool
{
	private Thread	thread;
	private boolean threadLoopController;
	
	private Vector<Connection> freeConns;
	private Vector<Connection> usedConns;
	
	private int connTargetBasis;
	private int connTargetNow;
		
	
	
	
	
	public ConnectionPool()
	{
		thread               = createThread();
		threadLoopController = true;
		
		freeConns = new Vector<Connection>();
		usedConns = new Vector<Connection>();
		
		connTargetBasis = 10;
		connTargetNow   = connTargetBasis;
		
		thread.start();
	}
	
	
	
	
	
	public void shutdown()
	{
		System.out.println( "Shutting down" );
		stopThread();
		forceReturnAllConnections();
		closeAllFreeConnections();
		System.out.println( "Shutdown complete" );
	}
	
	
	
	
	
	/**
	 * Get a connection from the pool.
	 * Make sure you put it back when finished using returnConnection().
	 * @return Connection
	 */
	public Connection getConnection()
	{
		System.out.println( "Pool: [CONN GET]" );
		waitForFreeConnection();
		
		Connection conn = freeConns.remove( 0 );
		usedConns.add( conn );
		
		makeGrowDecision();
		
		System.out.println( "Pool: [CONN GET COMPLETE]" );
		
		return conn;
	}
	
	
	
	
	
	/**
	 * Returns a connection back into the pool so it can be reused elsewhere.
	 * @param conn Connection
	 */
	public void returnConnection( Connection conn )
	{
		System.out.println( "Pool: [CONN RETURN]" );
		if ( ! usedConns.contains( conn )) {
			throw new RuntimeException( "Tried to return a connection not given by the ConnectionPool." );
		}
		
		usedConns.remove( conn );
		freeConns.add   ( conn );
		
		System.out.println( "Pool: [CONN RETURN COMPLETE]" );
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private boolean isConnectionAvailable() {
		return (freeConns.size() > 0);
	}
	
	
	
	
	
	private int getConnectionCount() {
		return freeConns.size() + usedConns.size();
	}
	
	
	
	
	
	private void makeGrowDecision()
	{		
		if (freeConns.isEmpty() 
	    && !areConnectionsBeingAllocated()) {
			System.out.println( "Pool: Pre-emptive grow" );
			connTargetNow += 2;
		}
	}
	
	
	
	
	
	private boolean areConnectionsBeingAllocated() {
		return (connTargetNow > getConnectionCount());
	}
	
	
	
	
	
	private int getFreeConnectionCount() {
		return freeConns.size();
	}
	
	
	
	
	
	private Thread createThread()
	{
		return new Thread() {
			public void run() 
			{
				while (threadLoopController) 
				{
					manageConnections();
						
					if (! areConnectionsBeingAllocated()) { // Take it easy
						try { Thread.sleep(500); }
						catch (InterruptedException ex) { this.interrupt();	}
					}
				}
			}
		};
	}
	
	
	
	
	
	private void stopThread()
	{
		for(;;) {
			try {
				threadLoopController = false;				
				thread.join();
				break;
			}
			catch( InterruptedException ex) {
				thread.interrupt();
			}
		}		
	}
	
	
	
	
	
	private synchronized void manageConnections()
	{
		System.out.println( "Pool: <managing>" );
		
		if (getConnectionCount() < connTargetNow) {
			allocateNewConnection();
		}
	}
	
	
	
	
	
	private void deallocateUnusedConnection()
	{
		if (freeConns.isEmpty()) {
			return;
		}
		
		try {
			Connection conn = freeConns.remove(0);
			conn.close();
			System.out.println( "Pool: Deallocated excess conn" );
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	
	
	private synchronized void allocateNewConnection()
	{
		for (;;)
		{
			try {
				System.out.println( "Pool: allocating conn #" + getConnectionCount() );
				Connection conn = Database.createConnection();
				freeConns.add( conn );
				System.out.println( "Pool: alloc success" );
				break;
			}
			catch( Exception ex ) {
				//ex.printStackTrace();
				System.out.println( "Pool: Alloc failed, retrying" );
			}
		}
	}
	
	
	
	
	
	private void waitForFreeConnection()
	{
		if ( ! isConnectionAvailable())
		{			
			while ( ! isConnectionAvailable()) {
				Thread.yield(); // Wait
			}
		}
	}
	
	
	
	
	
	private void forceReturnAllConnections()
	{
		System.out.println( "Pool: Forcing conn return" );
		
		for (int i=usedConns.size()-1;  i>=0;  i--) {
			Connection conn = usedConns.get(i);
			returnConnection( conn );
		}
	}
	
	
	
	
	
	private void closeAllFreeConnections()
	{
		while ( ! freeConns.isEmpty())
		{
			for (int i=freeConns.size()-1; i>=0; i--) {
				Connection conn = freeConns.get(i);
				try {
					conn.close();
					freeConns.remove( conn );
					System.out.println( "Pool: freed conn #" + i );
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		ConnectionPool pool = new ConnectionPool();
		
		while (pool.getConnectionCount() < 10) {
			// wait
		}
		
		
		
		Connection[] conns = new Connection[ 15 ];
		for (int i=0; i<conns.length; i++) {
			conns[i] = pool.getConnection();
		}
		
		
		
		for (int i=0; i<15; i++) {
			pool.returnConnection( conns[i] );
		}
		
		
		
		for (int i=0; i<10; i++) {
			conns[i] = pool.getConnection();
			pool.returnConnection( conns[i] );
		}
		
		
		
		
		Connection[] connsAgain = new Connection[ 15 ];
		for (int i=0; i<connsAgain.length; i++) {
			connsAgain[i] = pool.getConnection();
			
			try {
				Thread.sleep( (int) (Math.random() * 2500.0) );
			}
			catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			
		}
		

		
		System.out.println( "*** Calling shutdown ***" );
		pool.shutdown();
		System.out.println( "*** Complete ***" );
		
		
		
		
		
		while (pool.getConnectionCount() > 0) {
			// wait
		}
	}
}





















































