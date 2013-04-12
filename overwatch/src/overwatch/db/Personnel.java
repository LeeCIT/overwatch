


package overwatch.db;

import java.math.BigInteger;
import overwatch.security.HashSaltPair;
import overwatch.security.LoginCrypto;
import overwatch.util.Util;





/**
 * Get/set various aspects of personnel information from the database.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class Personnel
{
	
	/**
	 * Check if a person exists.
	 * @param personNo
	 * @return name or null
	 */
	public static boolean exists( Integer personNo )
	{
		EnhancedResultSet ers = Database.query(
			"select personNo  " +
		    "from Personnel   " +
		    "where personNo = " + personNo + ";"
		);
		
		return ! ers.isEmpty();
	}
	
	
	
	
	
	/**
	 * Create a new Personnel entry in the DB.
	 * For the sake of convenience, 1234 is the default password.
	 * @return personNo
	 */
	public static Integer create()
	{
		HashSaltPair hsp = LoginCrypto.generateHashSaltPair( "1234" );
		
		EnhancedResultSet ers = Common.createWithUniqueLockingSelect(
			"Personnel",
			"LAST_INSERT_ID()",
			"DEFAULT",
			"'new person'",
			"0",
			"'?'",
			"" + Ranks.getNumber( "Mook" ),
			"0",
			"'new person <?>'",
			"'" + hsp.hash + "'",
			"'" + hsp.salt + "'"
		);
		
		return ers.getElemAs(0,BigInteger.class).intValue();
	}
	
	
	
	
	
	/**
	 * Delete a person.
	 * Note that this is UNLIKELY to succeed given how many dependencies on Personnel there are.
	 * @param personNo
	 * @return succeeded
	 */
	public static boolean delete( Integer personNo )
	{		
		int rowMods = Database.update( 
			"delete from Personnel " +
			"where PersonNo = " + personNo + ";"
		);
		
		return (rowMods == 1);
	}
	
	
	
	
	
	/**
	 * Checks if the person is used in any of the other tables.
	 * If so, deleting them will cause major problems.
	 * @return
	 */
	public static boolean isInSquadOrVehicleOrMessage( Integer personNo )
	{
		long count = Database.querySingle( Long.class,
			"select count(val)                       " +
			"from (                                  " +
			"    select commander as val from Squads " +
			"    union                               " +
			"    select pilot from Vehicles          " +
			"    union                               " +
			"    select sentBy from Messages         " +
			"    union                               " +
			"    select sentTo from Messages         " +
			") as ut                                 " +
			"where ut.val = " + personNo + ";"
		);
		
		return (count > 0L);
	}
	
	
	
	
	
	/**
	 * Find the personNo related to a loginName, if any.
	 * @param loginName
	 * @return personNo if a valid login, -1 otherwise.
	 */
	public static int getNumberFromLogin( String loginName )
	{
		Integer[] numbers = Database.queryInts(
			"select personNo   " +
			"from Personnel    " +
			"where loginName = '" + loginName + "';"
		);
		
		return Util.firstOrElse( numbers, -1 );
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
			
		return Util.firstOrElse( pairs, null );
	}
	
	
	
	
	
	/**
	 * Get a person's privilegeLevel.  This is determined by their rank.
	 * @param personNo
	 * @return level, or -1 if no such person exists.
	 */
	public static int getPrivilegeLevel( int personNo )
	{
		Integer[] numbers = Database.queryInts(
			"select r.privilegeLevel     " +
			"from Ranks     r,           " +
			"     Personnel p            " +
			"where p.rankNo   = r.rankNo " +
			"  and p.personNo = " + personNo + ";"
		);
		
		return Util.firstOrElse( numbers, -1 );
	}
	
	
	
	
	
	/**
	 * Change a person's login passphrase.
	 * @param personNo
	 * @param pass
	 * @return Whether the change was successful
	 */
	public static boolean setPass( int personNo, String pass )
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
	
	
		
	
	
	/**
	 * Get a person's name.
	 * Returns null if no such person exists.
	 * @param personNo
	 * @return name or null
	 */
	public static String getLoginName( Integer personNo )
	{
		EnhancedResultSet ers = Database.query(
			"select loginName " +
		    "from Personnel   " +
		    "where personNo = " + personNo + ";"
		);
		
		if (ers.isEmpty())
			 return null;
		else return ers.getElemAs( "loginName", String.class ); 
	}
	
	
	
	
	
	/**
	 * Get a person's number based on their login name.
	 * Returns null if no such person exists.
	 * @param name
	 * @return personNo or null
	 */
	public static Integer getNumber( String loginName )
	{
		EnhancedResultSet ers = Database.query(
			"select personNo  " +
		    "from Personnel   " +
		    "where loginName = '" + loginName + "';"
		);
		
		if (ers.isEmpty())
			 return null;
		else return ers.getElemAs( "personNo", Integer.class ); 
	}
	
}



























































