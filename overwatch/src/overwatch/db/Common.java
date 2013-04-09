


package overwatch.db;

import overwatch.util.Util;





/**
 * Functions which are applicable to multiple types of data.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class Common
{
	public static String randomNamePart() {
		return "[" + Util.randomAlphaString( 8 ) + "]";
	}
	
	
	
	
	
	/**
	 * Unique creator for ranks, personnel etc
	 * The table must use autoIncrement values.
	 * @param table Table to apply the insert to
	 * @param namePrefix What to prepend the unique name with, eg "vehicle"
	 * @param values Values you would put in the SQL query values() part.  The string "<?>" will be replaced with a random unique string.
	 */
	public static void createWithUnique( String table, String...values )
	{
		String pre   = "insert into " + table + " ";
		String vals  = "values(" + Util.concatWithCommas(values) + ");";
		String query = pre + vals;
		
		for (;;) {
			try {
				Database.update(  query.replaceAll("\\<\\?\\>",randomNamePart())  );
				return;
			}
			catch (DatabaseException ex) {
				ex.printStackTrace();
				System.exit( 1 );
			}
		}
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args ) {
		for (int i=0; i++<64;)
			System.out.println( randomNamePart() );
	}
}
