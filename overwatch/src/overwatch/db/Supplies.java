


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
	 * Checks the database for the amount of supplies there are already and returns a new supply number
	 * @return
	 */
	public static int newSupplyId()
	{
		EnhancedResultSet ers = Database.query(
			"SELECT supplyNo " +
			"FROM Supplies;");
		
		return ers.getRowCount() + 1;
		
	}
}
