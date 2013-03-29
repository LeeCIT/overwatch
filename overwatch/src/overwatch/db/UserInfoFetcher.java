


package overwatch.db;

import java.sql.*;
import overwatch.security.HashSaltPair;





public class UserInfoFetcher
{
	private PreparedStatement psGetPersonNoFromLoginName;
	
	
	
	
	
	public UserInfoFetcher( Db db )
	{
		setupPreparedStatements( db.getConnection() );
	}
	
	
	
	
	
	private void setupPreparedStatements( Connection conn )
	{
		
	}
	
	
	
	
	
	public int getPersonForLogin( String inputUser )
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
	
	public HashSaltPair getHashSaltPair( int personNo )
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	public int getPrivilegeLevel( int currentUser )
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
