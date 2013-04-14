


package overwatch.db;

import overwatch.gui.NameRefPairList;





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
	
}






















