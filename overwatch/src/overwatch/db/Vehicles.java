


package overwatch.db;

import overwatch.gui.NameRefPairList;





/**
 * Database <-> Vehicle interactions
 * 
 * @author  Lee Coakley
 * @author  John Murphy
 * @version 4
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
	 * Update an existing vehicle's info.
	 * @param vehicleNo
	 * @param name
	 * @param pilotNo
	 * @return boolean whether it succeeded
	 */
	public static boolean save( Integer vehicleNo, String name, Integer pilotNo )
	{
		EnhancedPreparedStatement eps = new EnhancedPreparedStatement(
		  	"update Vehicles           " +
		  	"set name  = <<name>>,     " +
		  	"	 pilot = <<pilot>>     " +
		  	"where vehicleNo = <<num>>;"
		);
		
		try {
			eps.set( "num",   vehicleNo );
			eps.set( "name",  name      );
			eps.set( "pilot", pilotNo   );
			return (0 != eps.update());
		}
		finally {
			eps.close();
		}
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
	
	
	
	
	
	/**
	 * Get vehicle type
	 * @return The vehicle type
	 */
	public static String getVehicleType(int vehicleNo)
	{
		EnhancedResultSet ers = Database.query(
			"SELECT name " 			+
			"FROM Vehicles "		+
			"WHERE vehicleNo = " 	+ vehicleNo
		);
		
		
		if (ers.isEmpty())
			 return null;
		else return ers.getElemAs( "name", String.class ); 
	}
	
	
	
	
	
	// TODO, probably better placed in Squads, as it's relevant only to that class.
	public static NameRefPairList<Integer> getAllVehiclesNotInSquads()
	{
		EnhancedResultSet ers = Database.query(
			"SELECT vehicleNo, name "	+
			"FROM Vehicles "			+
			"WHERE vehicleNo NOT IN "  	+
			"( 		SELECT vehicleNo " 	+
			"		FROM SquadVehicles);"
		);
		
		if ( ! ers.isEmpty()) {
			Integer[] keys  = ers.getColumnAs( "vehicleNo",  Integer[].class  );
			String [] names = ers.getColumnAs( "name", String [].class  );
			return new NameRefPairList<Integer>( keys, names );
		}
		
		return new NameRefPairList<Integer>();
	}
	
	
	
	
	
	/**
	 * Get vehicleNo, name, pilot from Vehicles.
	 * @param vehicleNo
	 * @return EnhancedResultSet
	 */
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
	
	
	
	
	
	/**
	 * Get the name of a vehicle's pilot.
	 * If there is none the result will be null.
	 * @param vehicleNo
	 * @return String
	 */
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
	
	
	
	
	
	/**
	 * Find all personnel who are not assigned to a vehicle.
	 * @return Integer[]
	 */
	public static Integer[] getPersonnelNotAssignedToVehicles()
	{
		return Database.queryInts(
			"SELECT personNo             " +
			"FROM Personnel              " +
			"WHERE personNo NOT IN (     " +
			"	SELECT pilot             " +
			"	FROM Vehicles            " +
			"    WHERE pilot IS NOT NULL " +
			");                          "
		);
	}
	
}






















