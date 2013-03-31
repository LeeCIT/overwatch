


package overwatch.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;





/**
 * Multithreaded database connection pooler.
 * 
 * Creating new DB connections can be very slow. This spawns many of
 * them in the background without holding up the rest of the program.
 * 
 * @author Lee Coakley
 * @version 5
 */





public class ConnectionPool
{
	private boolean showDebugOutput = false;
	
	private          Thread	 thread;
	private volatile boolean threadLoopController;
	
	private Vector<Connection> freeConns;
	private Vector<Connection> usedConns;
	
	private int connTargetBasis;
	private int connTargetNow;
		
	
	
	
	
	/**
	 * Create a new connection pool and [optionally] begin making connections.
	 * When started the pool will keep making connections until the amount given is reached.
	 * The pool automatically expands if there aren't enough and shrinks if there are too many unused ones.
	 * @param initialConns How many connections to make on startup.
	 */
	public ConnectionPool( int initialConns, boolean immediateStart )
	{
		freeConns = new Vector<Connection>();
		usedConns = new Vector<Connection>();
		
		connTargetBasis = initialConns;
		connTargetNow   = connTargetBasis;
		
		if (immediateStart) {
			start();
		}
	}
	
	
	
	
	
	/**
	 * Starts the background thread and begins allocating connections.
	 */
	public void start()
	{
		if (thread != null)
		if (thread.isAlive())
			throw new RuntimeException( "Pool is already started!" );
		
		debugOut( "Start" );
		
		startThread();
	}
	
	
	
	
	
	/**
	 * Shuts down the connection pool.
	 * This stops the background thread and closes all connections.
	 * The function blocks until this has been accomplished.
	 */
	public void stop()
	{
		if ( ! thread.isAlive())
			throw new RuntimeException( "Pool is already stopped." );
		
		debugOut( "Stopping" );
		stopThread();
		forceReturnAllConnections();
		closeAllFreeConnections();
		debugOut( "Stopped" );
	}
	
	
	
	
	
	/**
	 * Check whether the pool has a connection ready to use.
	 * This is purely informational.  You don't need to check before calling getConnection().  
	 * @return Whether a connection is ready.
	 */
	public boolean isConnectionAvailable() {
		return (freeConns.size() > 0);
	}
	
	
	
	
	
	/**
	 * Get the total number of active connections.
	 * @return total
	 */
	public int getConnectionCount() {
		return freeConns.size() + usedConns.size();
	}
	
	
	
	
	
	/**
	 * Get a connection from the pool.  If none are available it creates a new one.
	 * Make sure you put it back when finished using returnConnection().
	 * Blocks until a connection is available.
	 * @return Connection
	 */
	public Connection getConnection()
	{
		debugOut( "getConnection started" );
		
		if ( ! threadLoopController || ! thread.isAlive())
			throw new RuntimeException( "Can't get connection: pool isn't active." );
		
		Connection conn = allocateConnection();
		
		makeGrowDecision();
		
		debugOut( "getConnection completed" );
		
		return conn;
	}
	
	
	
	
	
	/**
	 * Returns a connection back into the pool so it can be reused elsewhere.
	 * @param conn Connection
	 */
	public void returnConnection( Connection conn )
	{
		debugOut( "returnConnection" );
		
		if ( ! threadLoopController || ! thread.isAlive())
			throw new RuntimeException( "Can't return connection: pool isn't active." );		
		
		deallocateConnection( conn );
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private Connection allocateConnection()
	{
		waitForFreeConnection();
		
		Connection conn = freeConns.remove( 0 );
		usedConns.add( conn );	
		
		return conn;
	}
	
	
	
	
	
	private void deallocateConnection( Connection conn )
	{		
		usedConns.remove( conn );
		freeConns.add   ( conn );
	}
	
	
	
	
	
	private boolean areConnectionsBeingCreated() {
		return (connTargetNow > getConnectionCount());
	}
	
	
	
	
	
	private int getFreeConnectionCount() {
		return freeConns.size();
	}
	
	
	
	
	
	private int getUsedConnectionCount() {
		return usedConns.size();
	}
	
	
	
	
	
	private Thread createThread()
	{
		return new Thread() {
			public void run() 
			{
				while (threadLoopController) 
				{
					manageConnections();
					
					try {
						if (areConnectionsBeingCreated())
						     { Thread.sleep( 50); } // Work quickly
						else { Thread.sleep(500); } // Work slowly
					}
					catch (InterruptedException ex) {
						this.interrupt();	
					}
				}
			}
		};
	}
	
	
	
	
	
	private void startThread()
	{
		thread               = createThread();
		threadLoopController = true;
		thread.start();
	}
	
	
	
	
	
	private void stopThread()
	{
		debugOut( "Stopping thread" );
		
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
		debugOut( "<managing> [" + connTargetNow + "/" + connTargetBasis + "]  f:"+getFreeConnectionCount() + "  u:"+getUsedConnectionCount() );
		
		if (getConnectionCount() < connTargetNow) {
			createAndPoolNewConnection();
		}
		
		
		makeShrinkDecision();
	}
	
	
	
	
	
	private void makeGrowDecision()
	{		
		if (freeConns.isEmpty() 
	    && !areConnectionsBeingCreated()) {
			debugOut( "Pre-emptive grow + 2" );
			connTargetNow += 2;
		}
	}
	
	
	
	
	
	private void makeShrinkDecision()
	{
		if (connTargetNow            > connTargetBasis)
		if (getFreeConnectionCount() > connTargetBasis/2) {
			connTargetNow--;
			deallocateUnusedConnection();
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
			debugOut( "Deallocated excess conn" );
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	
	
	private synchronized void createAndPoolNewConnection()
	{
		for (;;)
		{
			try {
				debugOut( "Creating conn #" + getConnectionCount() );
				Connection conn = createNewConnection();
				freeConns.add( conn );
				debugOut( "Create success" );
				return;
			}
			catch( Exception ex ) {
				//ex.printStackTrace();
				debugOut( "Create failed, retrying..." );
			}
		}
	}
	
	
	
	
	
	private Connection createNewConnection() throws SQLException
	{
		// This information isn't stored in the repo because it's public.
		// Read the internal docs to get it.
		
		String socket   = ConnectionDetails.socket;
		String database = ConnectionDetails.database;
		String user     = ConnectionDetails.user;
		String pass     = ConnectionDetails.pass;
		
		String uri = "jdbc:mysql://" + socket + "/" + database;
		
			   DriverManager.setLoginTimeout( 5 );
	    return DriverManager.getConnection( uri, user, pass );
	}
	
	
	
	
	
	private void waitForFreeConnection()
	{
		if ( ! isConnectionAvailable()) {			
			while ( ! isConnectionAvailable()) {
				Thread.yield(); // Wait
			}
		}
	}
	
	
	
	
	
	private void forceReturnAllConnections()
	{
		debugOut( "forceReturnAllConnections" );
		
		for (int i=usedConns.size()-1;  i>=0;  i--) {
			Connection conn = usedConns.get(i);
			deallocateConnection( conn );
		}
	}
	
	
	
	
	
	private void closeAllFreeConnections()
	{
		debugOut( "closeAllFreeConnections" );
		
		while ( ! freeConns.isEmpty())
		{
			for (int i=freeConns.size()-1; i>=0; i--) {
				Connection conn = freeConns.get(i);
				try {
					conn.close();
					freeConns.remove( conn );
					debugOut( "  Freed conn #" + i );
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	
	
	private void debugOut( String str )
	{
		if (showDebugOutput) {
			System.out.println( "Pool: " + str );
		}
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		ConnectionPool pool = new ConnectionPool(10,true);
		pool.stop();
		
		for (int i=0; i<8; i++) {
			pool.start();
			pool.stop();
		}
		
		pool.start();
		pool.returnConnection( pool.getConnection() );
		pool.stop();
		
		pool.start();
		
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
		
		
		
		
		for (Connection conn: connsAgain) {
			pool.returnConnection( conn );
			
			try {
				Thread.sleep( (int) (Math.random() * 2500.0) );
			}
			catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		

		
		System.out.println( "*** Calling shutdown ***" );
		pool.stop();
		System.out.println( "*** Complete ***" );
		
		
		
		
		
		while (pool.getConnectionCount() > 0) {
			// wait
		}
	}
	
}





















































