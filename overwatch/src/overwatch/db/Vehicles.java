


package overwatch.db;





/**
 * Database <-> Vehicle interactions
 * 
 * @author  Lee Coakley
 * @version 3
 */





public class Vehicles
{

	/**
	 * Create a new vehicle.
	 * @return vehicleNo
	 */
	public static Integer create()
	{
		EnhancedPreparedStatement eps = new EnhancedPreparedStatement(
		  	"insert into Vehicles " +
		  	"values( default,     " +
		  	"		 <<name>>,    " +
		  	"		 null         " +
		  	");"
		);
		
		try {
			eps.set( "name", "vehicle " + Common.randomNamePart() );
			eps.update();			
			return eps.getGeneratedKey();
		}
		finally {
			eps.close();
		}
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
	
	
	
	
	
	public static EnhancedResultSet getInfo( Integer vehicleNo )
	{
		return Database.query(
			"SELECT vehicleNo, " +
			"       name,      " +
			"		pilot      " +
		    "FROM Vehicles     " +
		    "WHERE vehicleNo = " + vehicleNo + ";"
		);
	}
	
	
	
	
	
	public static String getPilotName( Integer vehicleNo )
	{
		return Database.querySingle( String.class,
			"select loginName            " +
			"from Personnel p,           " +
			"     Vehicles  v            " +
			"where p.personNo  = v.pilot " +
			"and   v.vehicleNo = " + vehicleNo + ";"
		);
	}
	
}






















