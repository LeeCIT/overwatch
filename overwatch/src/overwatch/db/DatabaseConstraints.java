


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
	 * @return
	 */
	public static boolean isValidName( String str ) {
		return Validator.isLengthRange( str, 1, maxLengthName );
	}
	
	
	
	
	
	public static boolean isValidSex( String str ) {
		return Validator.isLengthRange( str, 1, maxLengthSex );
	}
}
