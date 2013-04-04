


package overwatch.util;





public class ArrayGeneric
{
	
	/**
	 * Join two arrays together.
	 * @param first
	 * @param second
	 * @return first + second
	 */
	public static <T> T[] concat( T[] first, T[] second, Class<? extends T[]> type )
	{
		T[] con = (T[]) new Object[ first.length + second.length ];
		
		for (int i=0; i<con.length; i++) {
			if (i < first.length)
				con[i] = first[i];
			else
				con[i] = second[i - first.length];
		}
		
		return con;
	}
	
}
