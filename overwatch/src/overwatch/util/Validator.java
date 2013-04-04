


package overwatch.util;

import java.math.BigDecimal;





/**
 * Checks various inputs for validity.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class Validator
{
	
	/**
	 * Check if a string can be converted to an integer.
	 * @param str
	 * @return boolean
	 */
	public static boolean isInt( String str )
	{
		try {
			Integer.parseInt( str );
			return true;
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}
	
	
	
	
	
	/**
	 * Check if a string can be converted to an integer.
	 * @param str
	 * @return boolean
	 */
	public static boolean isPositiveInt( String str )
	{
		try {
			int x = Integer.parseInt( str );
			return x >= 0;
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}
	
	
	
	
	
	/**
	 * Check if a string can be converted to a BigDecimal.
	 * @param str
	 * @return
	 */
	public static boolean isPositiveBigDecimal( String str )
	{
		try {
			BigDecimal bd = new BigDecimal( str );
			
			return (bd.signum() != -1);
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}
	
	
	
	
	
	/**
	 * Check if a string's size is between two sizes.
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isLengthRange( String str, int min, int max )
	{
		int len = str.length();
		
		return len >= min 
			&& len <= max;
	}
}



























