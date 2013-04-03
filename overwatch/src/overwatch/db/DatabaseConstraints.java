


package overwatch.db;

import overwatch.util.Validator;





/**
 * Sanity-check data for entry into the database.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class DatabaseConstraints
{
	public static final int maxLengthName = 128;
	public static final int maxLengthSex  = 1;
	
	
	
	
	
	/**
	 * Check if a name is valid.
	 * Applies to names, logins, ranks, subjects, squadnames, etc
	 * @param str
	 * @return validity
	 */
	public static boolean isValidName( String str ) {
		return Validator.isLengthRange( str, 1, maxLengthName );
	}
	
	
	
	
	
	/**
	 * Check if sex is valid (just one character)
	 * @param str
	 * @return validity
	 */
	public static boolean isValidSex( String str ) {
		return Validator.isLengthRange( str, 1, maxLengthSex );
	}
	
	
	
	
	
	/**
	 * Check if a rank exists with the given name.
	 * Note: Ranks are case sensitive and start with a capital letter.
	 * @param rankName
	 * @return exists
	 */
	public static boolean rankExists( String rankName )
	{
		Integer[] ranks = Database.queryInts(
			"select rankNo " +
			"from Ranks " +
			"where name = '" + rankName + "' " +
			"limit 1;"
		);
		
		return (ranks.length != 0);
	}
}



























































