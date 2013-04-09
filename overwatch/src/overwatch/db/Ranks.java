


package overwatch.db;





/**
 * Database <-> Rank interactions
 * 
 * @author  Lee Coakley
 * @author  John Murphy
 * @version 2
 */





public class Ranks
{
	
	/**
	 * Check if a rank exists.
	 * @param rankNo
	 * @return exists
	 */
	public static boolean exists( Integer rankNo )
	{
		EnhancedResultSet ers = Database.query(
			"select rankNo  " +
		    "from Ranks     " +
		    "where rankNo = " + rankNo + ";"
		);
		
		return ! ers.isEmpty();
	}
	
	
	
	
	
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
	
	
	
	
	
	/**
	 * Get a person's number based on their name.
	 * Returns null if no such person exists.
	 * @param name
	 * @return rankNo or null
	 */
	public static Integer getNumber( String name )
	{
		EnhancedResultSet ers = Database.query(
			"select rankNo  " +
		    "from Ranks     " +
		    "where name = '" + name + "';"
		);
		
		if (ers.isEmpty())
			 return null;
		else return ers.getElemAs( "rankNo", Integer.class ); 
	}
	
	
	
	/**
	 * Creates a new Rank
	 * @return The Max id
	 */
	public static Integer create()
	{
		Database.update(
				"insert into Ranks " +
				"values( default, 'new Rank', 0 );"
				);
		
		EnhancedResultSet ers = Database.query("select max(rankNo)" +
			"from Ranks;");
		
		return (Integer) ers.getElem(0);
	}
}














