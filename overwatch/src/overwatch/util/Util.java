


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
	
}
