


package overwatch.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;





/**
 * Creating new DB connections is VERY slow.
 * This maintains a pool of them in a separate thread.
 * 
 * @author Lee Coakley
 * @version 1
 */





public class ConnectionPool
{
	private Thread			   thread;
	private boolean            threadLoopController;
	
	private Vector<Connection> freeConns;
	private Vector<Connection> usedConns;
	
	private int connTargetRef;
	private int connTargetCount;
	
	
	
	
	
	public ConnectionPool()
	{
		thread               = createThread();
		threadLoopController = true;
		
		freeConns = new Vector<Connection>();
		usedConns = new Vector<Connection>();
		
		connTargetRef   = 10;
		connTargetCount = connTargetRef;
		
		thread.start();
	}
	
	
	
	
	
	public void shutdown()
	{
		stopThread();
		forceReturnAllConnections();
		closeAllFreeConnections();
	}
	
	
	
	
	
	public Connection takeConnection()
	{
		waitForFreeConnection();
		
		Connection conn = freeConns.remove( 0 );
		usedConns.add( conn );
		
		return conn;
	}
	
	
	
	
	
	public void returnConnection( Connection conn )
	{
		if ( ! usedConns.contains( conn )) {
			throw new RuntimeException( "Tried to return a connection not given by the ConnectionPool." );
		}
		
		usedConns.remove( conn );
		freeConns.add   ( conn );
		
		if (connTargetCount > connTargetRef) {
			deallocateUnusedConnection();
			connTargetCount--;
		}
	}
	
	
	
	
	
	public boolean isConnectionAvailable() {
		return (freeConns.size() > 0);
	}
	
	
	
	
	
	public int getConnectionCount() {
		return freeConns.size() + usedConns.size();
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
		
		if (getConnectionCount() < connTargetCount) {
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
			System.out.println( "Pool: Deallocated unused conn" );
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	
	
	private synchronized void allocateNewConnection()
	{
		try {
			System.out.println( "Pool: allocating conn #" + getConnectionCount() );
			Connection conn = Database.createConnection();
			freeConns.add( conn );
			System.out.println( "Pool: alloc success" );
		}
		catch( Exception ex ) {
			//ex.printStackTrace();
			System.out.println( "Pool: Alloc failed" );
		}
	}
	
	
	
	
	
	private void waitForFreeConnection()
	{
		if ( ! isConnectionAvailable()) {
			connTargetCount++;
			System.out.println( "Pool: growing to meet demand: " + connTargetCount );
			
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
	
		
	
	
	
	public static void main( String[] args )
	{
		ConnectionPool pool = new ConnectionPool();
		
		while (pool.getConnectionCount() < 10)
		{
			// wait
		}
		
		
		
		Connection[] conns = new Connection[ 15 ];
		
		for (int i=0; i<conns.length; i++) {
			conns[i] = pool.takeConnection();
		}
		
		
		
		for (int i=0; i<10; i++) {
			pool.returnConnection( conns[i] );
			conns[i] = null;
		}
		
		
		
		
		
		
		
		System.out.println( "*** Returned, shutting down ***" );
		pool.shutdown();
		System.out.println( "*** Complete ***" );
		
		
		
		
		
		while (pool.getConnectionCount() > 0)
		{
			// wait
		}
	}
}





















































