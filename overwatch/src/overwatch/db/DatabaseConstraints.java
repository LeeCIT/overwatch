


package overwatch.db;

import overwatch.util.Validator;





/**
 * Sanity-check data for entry into the database.
 * 
 * @author  Lee Coakley
 * @author 	John Murphy
 * @version 2
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
	
	
	/**
	 * Check if the type is valid
	 * Applies to supplies tab and vehicles tab
	 * @param Type
	 * @return Exists
	 */
	public static boolean isValidType(String str)
	{
		return Validator.isLengthRange(str, 1, maxLengthName);
	}
	
	
	
	/**
	 * Checks the amount passed in to ensure it will go into the database
	 * @param Amount
	 * @return Valid or invalid
	 */
	public static boolean isValidAmount(String amount)
	{
		return Validator.isPositiveInt(amount);
	}
	
	
	/**
	 * Checks if the id number is still valid
	 * Applicable to all tabs with a uneditable
	 * @param Num
	 * @return Exists
	 */
	public static boolean numberExists(String num)
	{
		Integer[] number = Database.queryInts(
				"SELECT  supplyNo " +
				"from Supplies " +
				"where supplyNo = '" + num + "' " +
				"limit 1;"
			);
			
			return (number.length != 0);
	}
	
	
	/**
	 * Checks if the personnel still exists
	 * @param name
	 * @return Exists
	 */
	public static boolean personnelExists(String name)
	{
		Integer[] personnelName = Database.queryInts(
				"SELECT  name " +
				"from Personnel " +
				"where name = '" + name + "' " +
				"limit 1;"
			);
		
			return (personnelName.length != 0);
	}
	
}



























































