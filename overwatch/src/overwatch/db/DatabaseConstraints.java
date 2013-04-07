


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
	 * Check if salary is valid.
	 * Format is: 1-9 digits . 1-2 digits 
	 * @param str
	 * @return
	 */
	public static boolean isValidSalary( String str ) {
		return str.matches( "\\d{1,9}(\\.\\d{1,2})?" );
	}
	
}



























































