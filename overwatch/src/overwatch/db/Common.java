


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
	 * @param table Table to apply the insert to
	 * @param namePrefix What to prepend the unique name with, eg "vehicle"
	 * @param values Values you would put in the SQL query values() part.  The string "<?>" will be replaced with a random unique string.
	 * @throws DatabaseException Thrown if it fails.
	 */
	public static void createWithUnique( String table, String...values )
	{
		int maxRetries = 5;
		
		String pre   = "insert into " + table + " ";
		String vals  = "values(" + Util.concatWithCommas(values) + ");";
		String query = pre + vals;
		
		for (int r=0; r<maxRetries; r++) {
			try {
				Database.update(  query.replaceAll("\\<\\?\\>",randomNamePart())  );
				return;
			}
			catch (DatabaseException ex) {
				ex.printStackTrace();
				System.out.println( "<debug> createWithUnique failed, retrying" ); // TODO remove debug
			}
		}
		
		throw new DatabaseException( "createWithUnique failed after " + maxRetries + " attempts." );		
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args ) {
		for (int i=0; i++<64;)
			System.out.println( randomNamePart() );
	}
}
