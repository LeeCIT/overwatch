


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
	
	
	
	
	
	/**
	 * Create a new vehicle.
	 * @return Integer vehicleNo
	 */
	public static Integer create()
	{	
		Integer vehicleNo = null;
		
		Database.lockWrite( "Vehicles" );
		
		try {
			 vehicleNo = Database.queryInt(
				"select max(vehicleNo)+1 " +
				"from Vehicles;"
			);
			
			Database.update( 
				"insert into Vehicles values (" +
				    vehicleNo + ", " +
					"'new vehicle #" + vehicleNo + "'," +
					"null" +
				");"
			);
		}
		finally {
			Database.unlock();
		}
		
		return vehicleNo;
	}
	
	
	
	
	
	public static void delete( Integer vehicleNo )
	{
		// TODO: check if vehicle is in use in a squad, and throw an exception or show a warning message if so
		// TODO: create Squads.usesVehicle( squadNo, vehicleNo )
		
		Database.update( 
			"delete from Vehicles " +
			"where vehicleNo =    " + vehicleNo + ";"
		);
	}
	
}
