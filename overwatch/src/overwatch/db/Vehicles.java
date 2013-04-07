


package overwatch.db;





public class Vehicles
{
	
	/**
	 * Check if a vehicle exists.
	 * @param personNo
	 * @return name or null
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
	
}
