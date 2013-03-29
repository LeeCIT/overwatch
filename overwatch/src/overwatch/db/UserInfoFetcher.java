


package overwatch.db;

import java.sql.*;
import overwatch.security.HashSaltPair;





public class UserInfoFetcher
{
	
	public static int getPersonForLogin( String inputUserName )
	{
		Connection conn = Database.connectionPool.takeConnection();
		
		conn.prepareStatement(
			select 
		);
		
		
	}
	
	
	
	
	
	public HashSaltPair getHashSaltPair( int personNo )
	{
		
	}
	
	
	
	
	
	public int getPrivilegeLevel( int currentUser )
	{
		
	}
}
