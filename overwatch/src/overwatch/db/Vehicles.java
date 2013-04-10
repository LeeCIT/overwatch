


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
	
	
	
	
	
	public static Integer create()
	{
		Common.createWithUnique( "Vehicles", "DEFAULT", "'new vehicle <?>'", "NULL" );
		
		return Database.querySingle( Integer.class,
			"select max(vehicleNo)" +
			"from Vehicles;"
		);
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
