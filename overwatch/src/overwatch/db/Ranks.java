


package overwatch.db;





/**
 * Database <-> Rank interactions
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class Ranks
{
	
	/**
	 * Get a rank's name.
	 * Returns null if no such rank exists.
	 * @param rankNo
	 * @return name or null
	 */
	public static String getName( Integer rankNo )
	{
		EnhancedResultSet ers = Database.query(
			"select name    " +
		    "from Ranks     " +
		    "where rankNo = " + rankNo + ";"
		);
		
		if (ers.isEmpty())
			 return null;
		else return ers.getElemAs( "name", String.class ); 
	}
	
}
