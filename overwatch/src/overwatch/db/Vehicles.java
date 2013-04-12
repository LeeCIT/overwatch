


package overwatch.db;





/**
 * Database <-> Vehicle interactions
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class Vehicles
{

	/**
	 * Create a new vehicle.
	 * Locks the table!
	 * @return vehicleNo
	 */
	public static Integer create()
	{
		return Common.createWithUniqueLockingAutoInc(
			"Vehicles",
			"DEFAULT",
			"'new vehicle <?>'",
			"NULL"
		);
	}
	
	
	
	
	
	/**
	 * Check if a vehicle exists.
	 * @param personNo
	 * @return exists
	 */
	public static boolean exists( Integer vehicleNo )
	{
		EnhancedResultSet ers = Database.query(
			"select vehicleNo  " +
		    "from Vehicles     " +
		    "where vehicleNo = " + vehicleNo + ";"
		);
		
		return ! ers.isEmpty();
	}
	
	
	
	
	
	/**
	 * Delete a vehicle.
	 * @param vehicleNo
	 */
	public static void delete( Integer vehicleNo )
	{		
		Database.update( 
			"delete from Vehicles " +
			"where vehicleNo =    " + vehicleNo + ";"
		);
	}
	
}






















