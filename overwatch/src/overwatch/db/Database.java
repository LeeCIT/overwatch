


package overwatch.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;





/**
 * Provides basic global database functions.
 * 
 * @author Lee Coakley
 * @version 3
 */





public class Database
{
	private static final ConnectionPool connectionPool = new ConnectionPool();
	
	
	
	
	
	/**
	 * Get a database connection from the pool.
	 * Return it when finished!
	 * @return Connection
	 * @see ConnectionPool
	 */
	public static Connection getConnection() {
		return connectionPool.getConnection();
	}
	
	
	
	
	
	/**
	 * Return a database connection to the pool so it can be reused.
	 * @see ConnectionPool
	 */
	public static void returnConnection( Connection conn ) {
		connectionPool.returnConnection( conn );
	}
	
	
	
	
	
	/**
	 * Run an SQL query that yields a single set of integers.
	 * @param sql
	 * @return Integer[]
	 */
	public static Integer[] queryInts( String sql ) {
		return query(sql).getColumnAs( 0, Integer[].class );
	}
	
	
	
	
	
	/**
	 * Run a query, get an EnhancedResultSet.
	 * Handles cleanup and conversion automatically.
	 * @param ps
	 * @return EnhancedResultSet
	 * @throws SQLException
	 */
	public static EnhancedResultSet query( PreparedStatement ps )
	{
		EnhancedResultSet ers = null;
		
		try {
	    	ResultSet rs = ps.executeQuery();
	    		ers = new EnhancedResultSet( rs );
	    	rs.close();
		}
		catch( SQLException ex) {
			throw new RuntimeException( ex );
		}
    	
    	return ers;
	}
	
	
	
	
	
	/**
	 * Run a query, get an EnhancedResultSet.
	 * Handles cleanup and conversion automatically.
	 * @param sql
	 * @return EnhancedResultSet
	 */
	public static EnhancedResultSet query( String sql )
	{
		Connection conn = getConnection();
		
			EnhancedResultSet ers = null;
		
			try {
				Statement st = conn.createStatement();
				ers = new EnhancedResultSet( st.executeQuery(sql) );
				st.close();
			}
			catch( SQLException ex) {
				throw new RuntimeException( ex );
			}
		
		returnConnection( conn );
    	
    	return ers;
	}
	
	
	
	
	
	/**
	 * Dump the contents of an entire table into an EnhancedResultSet.
	 * @param tableName
	 * @return EnhancedResultSet
	 */
	public EnhancedResultSet dumpTable( String tableName ) {
	    return query( "SELECT * FROM " + tableName + ";" );
	}
	
}








































