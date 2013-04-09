


package overwatch.db;





/**
 * Database <-> Supply interactions
 * 
 * @author  Lee Coakley
 * @author  John Murphy
 * @version 2
 */





public class Supplies
{
	/**
	 * Check if supply exists.
	 * @param number
	 * @return Exists
	 */
	public static boolean exists( Integer supplyNo )
	{
		Integer[] number = Database.queryInts(
			"SELECT supplyNo " +
			"from Supplies   " +
			"where supplyNo = " + supplyNo + " " +
			"limit 1;"
		);
		
		return (number.length != 0);
	}
	
	
	
	
	
	/**
	 * Adds a new supply to the database
	 * @return The max ID
	 */
	public static int create()
	{
		Database.update(
				"insert into Supplies " +
				"values( default, 'new Supply', 0 );"
				);
		
		EnhancedResultSet ers = Database.query("select max(supplyNo)" +
			"from Supplies;");
		
		return (Integer) ers.getElem(0);
		
		
	}
}
