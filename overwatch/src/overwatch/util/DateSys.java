


package overwatch.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;





/**
 * Provides a simple means of manipulating dates.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class DateSys
{
	private static SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy H:m" );
	
	
	
	

	public static boolean inInclusiveRange( Date compare, Date start, Date end ) {
		return onOrAfter ( compare, start )
			&& onOrBefore( compare, end   );
	}
	
	
	
	
	
	public static boolean onOrBefore( Date compare, Date reference ) {
		return (compare.getTime() <= reference.getTime());
	}
	
	
	
	
	
	public static boolean onOrAfter( Date compare, Date reference ) {
		return (compare.getTime() >= reference.getTime());
	}
	
	
	
	
	
	public static String format( Date date ) {
		return dateFormat.format( date );
	}
	
	
	
	
	
	public static Date parse( String dateString ) throws ParseException {
		return dateFormat.parse( dateString );
	}
	
	
	
	
	
	public static boolean isDateValid( String dateString )
	{
		try {
			dateFormat.parse( dateString );
			return true; // Will not be reached if parse throws an exception
		}
		catch (ParseException ex) {
			return false;
		}
	}
}





























