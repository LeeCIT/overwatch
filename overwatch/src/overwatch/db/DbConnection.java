


package overwatch.db;



import java.sql.*;
import java.util.ArrayList;





/**
 * Manages database interactions.
 * 
 * @author Lee Coakley
 * @version 1
 * @see EnhancedResultSet
 */





public class DbConnection
{
	private Connection conn;
	
	
	
	
	
	public DbConnection()
	{
		final String socket   = "db.strongholdsolutions.org:3306";
		final String database = "fortress";
		final String user     = "client";
		final String pass     = "ZHQOw:=i>Zv,rLLhWG-?qe|/$5JvZ20";
		
		try {
			connect( socket, database, user, pass );
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	
	
	private void connect( String socket, String database, String user, String pass ) throws SQLException
	{
		String uri = "jdbc:mysql://" + socket + "/" + database;
		
		       DriverManager.setLoginTimeout( 5 );
		conn = DriverManager.getConnection( uri, user, pass );
	}
	
	
	
	
	
	public Connection getConnection() {
		return conn;
	}
	
	
	
	
	
	/**
	 * Terminate the DB connection.
	 * This frees all resources after an undefined length of time (whenever the GC runs).
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException {
		if (conn != null)
			conn.close();
	}
	
	
	
	
	
	/**
	 * Dump the contents of an entire table into an EnhancedResultSet.
	 * @param tableName
	 * @return EnhancedResultSet
	 * @throws SQLException
	 */
	public EnhancedResultSet dumpTable( String tableName ) throws SQLException
	{
    	PreparedStatement ps = conn.prepareStatement( "SELECT * FROM " + tableName + ";" );
    		EnhancedResultSet ers = query( ps );
		ps.close();
	    
	    return ers;
	}
	
	
	
	
	
	/**
	 * Run a query, get an EnhancedResultSet.
	 * @param ps
	 * @return EnhancedResultSet
	 * @throws SQLException
	 */
	public EnhancedResultSet query( PreparedStatement ps ) throws SQLException
	{
    	ResultSet rs = ps.executeQuery();
    		EnhancedResultSet ers = new EnhancedResultSet( rs );
    	rs.close();
    	
    	return ers;
	}
	
	
	
	
	
	
}












































