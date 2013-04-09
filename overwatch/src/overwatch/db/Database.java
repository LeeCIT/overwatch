


package overwatch.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import overwatch.gui.NameRefPair;
import overwatch.gui.NameRefPairList;





/**
 * Provides basic global database functions.
 * 
 * @author Lee Coakley
 * @version 4
 */





public class Database
{
	private static final ConnectionPool connPool = new ConnectionPool( 4, true );
	
	
	
	
	
	/**
	 * Start making connections to the database.
	 * For the moment, this is automatic and doesn't need to be used.
	 */
	public static void connect() {
		connPool.start();
	}
	
	
	
	
	
	/**
	 * Disconnect from the database.
	 * Closes all connections and cleans up resources.
	 */
	public static void disconnect() {
		connPool.stop();
	}
	
	
	
	
	
	/**
	 * Get a database connection from the pool.
	 * Return it when finished!
	 * @return Connection
	 * @see ConnectionPool
	 */
	public static Connection getConnection() {
		return connPool.getConnection();
	}
	
	
	
	
	
	/**
	 * Return a database connection to the pool so it can be reused.
	 * @param conn
	 * @see ConnectionPool
	 */
	public static void returnConnection( Connection conn ) {
		connPool.returnConnection( conn );
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
			EnhancedResultSet ers = query( conn, sql );
		returnConnection( conn );
    	
    	return ers;
	}
	
	
	
	
	
	/**
	 * Run a query, get an EnhancedResultSet.
	 * @param conn Connection to use (needed for locks)
	 * @param sql
	 * @return EnhancedResultSet
	 */
	public static EnhancedResultSet query( Connection conn, String sql )
	{
		EnhancedResultSet ers = null;
		
		try {
			Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery( sql );
	    			ers = new EnhancedResultSet( rs );
	    		rs.close();
			st.close();
		}
		catch (SQLException ex) {
			throw new DatabaseException( ex );
		}
    	
    	return ers;
	}
	
	
	
	
	
	/**
	 * Get an ArrayList of NameRefPairs from a table, based on two columns.
	 * Intended for use with the GUI's SearchPanel functions.
	 * @param table	Table to query.
	 * @param keyColumn Name of column to key on (e.g. personNo)
	 * @param nameColumn Name of column with associated names.  Must be a varchar/char type column.
	 * @param keyType Type of key.  Usually Integer[].
	 * @return ArrayList<NameRefPair<T>>
	 */
	public static <T> ArrayList<NameRefPair<T>> queryKeyNamePairs( String table, String keyColumn, String nameColumn, Class<? extends T[]> keyType )
	{
		EnhancedResultSet ers = query(
			"select " + keyColumn + ", " + nameColumn + " " +
		    "from " + table + ";"
		);
		
		T[]      keys  = ers.getColumnAs( keyColumn,  keyType        );
		String[] names = ers.getColumnAs( nameColumn, String[].class );
		
		return new NameRefPairList<T>( keys, names );
	}
	
	
	
	
	
	/**
	 * Convenience function for single-column single-row queries.
	 * Returns null if the set is empty.
	 * @param sql
	 * @param type Type of element.
	 * @return T
	 */
	public static <T> T querySingle( String sql, Class<T> type ) {
		EnhancedResultSet ers = query( sql );
		return ( ! ers.isEmpty())  ?  ers.getElemAs(0,type)  :  null;
	}
	
	
	
	
	
	/**
	 * Convenience function for single-column multiple-row queries.
	 * @param sql
	 * @param type Type of array
	 * @return T
	 */
	public static <T> T[] queryArray( String sql, Class<? extends T[]> type ) {
		return query(sql).getColumnAs(0,type);
	}
	
	
	
	
	
	/**
	 * Dump the contents of an entire table into an EnhancedResultSet.
	 * @param tableName
	 * @return EnhancedResultSet
	 */
	public static EnhancedResultSet dumpTable( String tableName ) {
	    return query( "SELECT * FROM " + tableName + ";" );
	}
	
	
	
	
	
	/**
	 * Run update/insert/delete SQL and get back the number of rows modified.
	 * @param sql
	 * @return number rows modified
	 */
	public static int update( String sql )
	{	
		Connection conn = getConnection();
			int rowsModified = update( conn, sql ); 
		returnConnection( conn );
		
		return rowsModified; 
	}
	
	
	
	
	
	/**
	 * Run update/insert/delete SQL and get back the number of rows modified.
	 * @param conn Connection to use (needed for locks)
	 * @param sql
	 * @return number rows modified
	 */
	public static int update( Connection conn, String sql )
	{
		int rowsModified = -1;
	
			try {
				Statement st = conn.createStatement();
				rowsModified = st.executeUpdate( sql );
				st.close();
			}
			catch (SQLException ex) {
				throw new DatabaseException( ex );
			}
		
		return rowsModified; 
	}
	
	
	
	
	
	/**
	 * Prevent writes from being made to a table.  They're queued until it's unlocked again.
	 * Make damn sure to unlock() after!
	 * @param table
	 */
	public static void lockWrite( Connection conn, String table ) {
		Database.update( conn, "lock tables " + table + " write;" );
	}
	
	
	
	
	
	/**
	 * Unlock a previously locked table.
	 */
	public static void unlock( Connection conn ) {
		Database.update( conn, "unlock tables;" );
	}
	
}
































































