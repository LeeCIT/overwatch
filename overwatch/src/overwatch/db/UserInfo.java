


package overwatch.db;

import overwatch.security.HashSaltPair;
import overwatch.security.LoginCrypto;





/**
 * Fetches basic info about a user.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class UserInfo
{
	
	/**
	 * Find the personNo related to a loginName, if any.
	 * @param loginName
	 * @return personNo if a valid login, -1 otherwise.
	 */
	public static int mapLoginToPerson( String loginName )
	{
		Integer[] numbers = Database.queryInts(
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
	public static HashSaltPair getHashSaltPair( int personNo )
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
	public static int getPrivilegeLevel( int personNo )
	{
		Integer[] numbers = Database.queryInts(
			"select privilegeLevel     " +
			"from Ranks r, Personnel p " +
			"where p.rank     = r.rank " +
			"  and p.personNo = " + personNo + ";"
		);
		
		return firstOrElse( numbers, -1 );
	}
	
	
	
	
	
	/**
	 * Change a person's login passphrase.
	 * @param personNo
	 * @param pass
	 * @return Whether the change was successful
	 */
	public static boolean setUserPass( int personNo, String pass )
	{
		HashSaltPair pair = LoginCrypto.generateHashSaltPair( pass );
		
		int rowsChanged = Database.update( 
			"update Personnel   " +
			"set loginHash  =  '" + pair.hash + "', " +
			"    loginSalt  =  '" + pair.salt + "'  "  +
			"where personNo =   " + personNo  + ";"
		);
		
		return (rowsChanged == 1);
	}
	
	
	
	
	
	public static String getName( Integer personNo )
	{
		EnhancedResultSet ers = Database.query(
			"select name      " +
		    "from Personnel   " +
		    "where personNo = " + personNo + ";"
		);
		
		return ers.getElemAs( "name", String.class );
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private static <T> T firstOrElse( T[] array, T ifNone ) {
		return (array.length != 0)  ?  array[0]  :  ifNone;
	}	
}



























































