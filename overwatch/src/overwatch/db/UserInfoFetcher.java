


package overwatch.db;

import overwatch.security.HashSaltPair;





/**
 * Fetches basic info about a user.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class UserInfoFetcher
{
	
	/**
	 * Find the personNo related to a loginName, if any.
	 * @param loginName
	 * @return personNo if a valid login, -1 otherwise.
	 */
	public int mapLoginToPerson( String loginName )
	{
		Integer[] numbers = Database.queryInts (
			"select personNo   " +
			"from Personnel    " +
			"where loginName = '" + loginName + "';"
		);
		
		return firstOrElse( numbers, -1 );
	}
	
	
	
	
	
	/**
	 * Find the login hash and salt for a person, if they exist.
	 * @param personNo
	 * @return HashSaltPair, or null if there is no such person.
	 */
	public HashSaltPair getHashSaltPair( int personNo )
	{
		String sql = "select loginHash, loginSalt " +
					 "from Personnel              " +
					 "where personNo = " + personNo + ";";
		
		EnhancedResultSet ers   = Database.query( sql );
		HashSaltPair[]    pairs = DatabaseTranslator.translateHashSaltPairs( ers );
			
		return firstOrElse( pairs, null );
	}
	
	
	
	
	
	public int getPrivilegeLevel( int currentUser )
	{
		return -1;
	}
	
	
	
	
	
	private <T> T firstOrElse( T[] array, T ifNone ) {
		return (array.length != 0)  ?  array[0]  :  ifNone;
	}
}
