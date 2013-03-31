


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
		EnhancedResultSet ers = Database.query(
			"select loginHash, loginSalt " +
			"from Personnel              " +
			"where personNo = " + personNo + ";"
		);

		HashSaltPair[] pairs = DatabaseTranslator.translateHashSaltPairs( ers );
			
		return firstOrElse( pairs, null );
	}
	
	
	
	
	
	/**
	 * Get a person's privilegeLevel.  This is determined by their rank.
	 * @param personNo
	 * @return level, or -1 if no such person exists.
	 */
	public int getPrivilegeLevel( int personNo )
	{
		Integer[] numbers = Database.queryInts (
			"select privilegeLevel     " +
			"from Ranks r, Personnel p " +
			"where p.rank     = r.rank " +
			"  and p.personNo = " + personNo + ";"
		);
		
		return firstOrElse( numbers, -1 );
	}
	
	
	
	
	
	private <T> T firstOrElse( T[] array, T ifNone ) {
		return (array.length != 0)  ?  array[0]  :  ifNone;
	}
	
}
