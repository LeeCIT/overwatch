


package overwatch.db;

import java.sql.*;
import overwatch.security.HashSaltPair;



// This is very nasty.  I'll abstract it further when finished testing.



public class UserInfoFetcher
{
	
	public int getPersonForLogin( String inputUserName )
	{
		EnhancedResultSet ers = null;
		
		
		Connection conn = Database.connectionPool.takeConnection();
		
			try {
				PreparedStatement ps = conn.prepareStatement(
					"    select personNo         " +
					"    from Personnel          " +
					"    where loginName = ?;    " );
				
					ps.setString( 1, inputUserName );
					
					ers = Database.query( ps );
				
				ps.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		
		Database.connectionPool.returnConnection( conn );
		
		
		if (ers.getRowCount() > 0)
			return (int) ers.get( 0, 0 );
		else
			return -1;
	}
	
	
	
	
	
	public HashSaltPair getHashSaltPairForPerson( int personNo )
	{
		EnhancedResultSet ers = null;
		
		
		Connection conn = Database.connectionPool.takeConnection();
		
			try {
				PreparedStatement ps = conn.prepareStatement(
					"    select loginHash, loginSalt    " +
					"    from Personnel                 " +
					"    where personNo = ?;            " );
				
					ps.setInt( 1, personNo );
					
					ers = Database.query( ps );
				
				ps.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		
		Database.connectionPool.returnConnection( conn );
		
		
		if (ers.getRowCount() > 0)
		{
			String hash = (String) ers.get( ers.getColumnIndex( "loginHash" ), 0 );
			String salt = (String) ers.get( ers.getColumnIndex( "loginSalt" ), 0 );
			
			return new HashSaltPair( hash, salt );
		}
		else 
		{
			return null;
		}
	}
	
	
	
	
	
	public int getPrivilegeLevel( int currentUser )
	{
		return -1;
	}
}
