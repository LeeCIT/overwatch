


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
		return "[" + Util.randomAlphaString( 8 ) + "]";
	}
}
