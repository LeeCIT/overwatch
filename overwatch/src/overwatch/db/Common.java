


package overwatch.db;

import overwatch.util.Util;





/**
 * Functions which are applicable to multiple types of data.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class Common
{
	public static String randomNamePart() {
		return "[" + Util.randomAlphaString( 12 ) + "]";
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args ) {
		for (int i=0; i++<64;)
			System.out.println( randomNamePart() );
	}
}
