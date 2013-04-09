


package overwatch.util;





public class Util
{
	
	/**
	 * Return the first element of an array if it's not empty, otherwise return null.
	 * @param array
	 * @param ifNone
	 * @return element/null
	 */
	public static <T> T firstOrElse( T[] array, T ifNone ) {
		return (array.length != 0)  ?  array[0]  :  ifNone;
	}
	
	
	
	
	
	public static String randomAlphaString( int length )
	{
		char[] chars   = new char[ length ];
		char   lowest  = 'A';
		char   highest = 'Z';
		
		for (int i=0; i<length; i++) {
			chars[i] = (char) randomIntRange( lowest, highest );
		}
		
		return new String( chars );
	}
	
	
	
	
	
	public static int randomIntRange( int lowest, int highest ) {
		int delta = highest - lowest;
		return lowest + (int) (Math.random() * ++delta);
	}
	
	
	
	
	
	public static String concatWithCommas( String[] array ) {
		StringBuilder bld  = new StringBuilder();
		int           last = array.length - 1;
		
		for (int i=0; i<=last; i++) {
			bld.append(
				array[i] + ((i!=last) ? "," : "")
			);
		}
		
		return bld.toString();
	}
}
