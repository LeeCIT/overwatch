


package overwatch.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;





/**
 * Exposes basic global database functions.
 * 
 * @author Lee Coakley
 * @version 1
 */





public class Database
{
	public static final ConnectionPool connectionPool = null;//new ConnectionPool();
	
	
	
	public static Connection createConnection() throws SQLException
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
	
	
	
	
	
	/**
	 * Run a query, get an EnhancedResultSet.
	 * Handles cleanup and conversion.
	 * @param ps
	 * @return EnhancedResultSet
	 * @throws SQLException
	 */
	public static EnhancedResultSet query( PreparedStatement ps ) throws SQLException
	{
    	ResultSet rs = ps.executeQuery();
    		EnhancedResultSet ers = new EnhancedResultSet( rs );
    	rs.close();
    	
    	return ers;
	}
	
}
