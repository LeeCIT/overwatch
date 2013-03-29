


package overwatch.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;





/**
 * Creating new DB connections is VERY slow.
 * This maintains a pool of them in a separate thread.
 * 
 * @author Lee Coakley
 * @version 2
 */





public class ConnectionPool
{
	private Thread	thread;
	private boolean threadLoopController;
	
	private Vector<Connection> freeConns;
	private Vector<Connection> usedConns;
	
	private int connTargetBasis;
	private int connTargetNow;
	private int connTargetReserve;
	
	private boolean waitingOnConnect;
	
	
	
	
	
	public ConnectionPool()
	{
		thread               = createThread();
		threadLoopController = true;
		
		freeConns = new Vector<Connection>();
		usedConns = new Vector<Connection>();
		
		connTargetBasis   = 10;
		connTargetNow     = connTargetBasis;
		connTargetReserve = connTargetBasis / 2; 
		waitingOnConnect  = false;
		
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
	
	
	
	
	
	public Connection getConnection()
	{
		System.out.println( "Pool: [CON GET]" );
		waitForFreeConnection();
		
		Connection conn = freeConns.remove( 0 );
		usedConns.add( conn );
		
		if (freeConns.isEmpty()) {
			System.out.println( "Pool: Growing in anticipation of demand" );
			connTargetNow += 2;
		}
		
		return conn;
	}
	
	
	
	
	
	public void returnConnection( Connection conn )
	{
		System.out.println( "Pool: [CON RETURN]" );
		if ( ! usedConns.contains( conn )) {
			throw new RuntimeException( "Tried to return a connection not given by the ConnectionPool." );
		}
		
		usedConns.remove( conn );
		freeConns.add   ( conn );
		
		if (connTargetNow > connTargetBasis)
		if (getFreeConnectionCount() > connTargetReserve) {
			System.out.println( "Pool: shrinking" );
			deallocateUnusedConnection();
			connTargetNow--;
		}
	}
	
	
	
	
	
	public boolean isConnectionAvailable() {
		return (freeConns.size() > 0);
	}
	
	
	
	
	
	public int getConnectionCount() {
		return freeConns.size() + usedConns.size();
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
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

				    try { Thread.sleep(500); }
				    catch (InterruptedException ex) { this.interrupt();	}
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
		waitingOnConnect = true;
		
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
		
		waitingOnConnect = false;
	}
	
	
	
	
	
	private void waitForFreeConnection()
	{
		if ( ! isConnectionAvailable()) {
			
			if (!waitingOnConnect) {
				connTargetNow++;
				System.out.println( "Pool: No connection available or being established: growing to meet demand" );
			}
			
			while ( ! isConnectionAvailable()) {
				Thread.yield(); // Wait
			}
		}
	}
	
	
	
	
	
	private void forceReturnAllConnections()
	{
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
				Thread.sleep( (int) Math.rint( 2500.0 ) );
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





















































