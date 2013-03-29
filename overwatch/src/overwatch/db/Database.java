


package overwatch.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;





public class Database
{
	//public static final ConnectionPool connectionPool = new ConnectionPool();
	
	
	
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
	
}
