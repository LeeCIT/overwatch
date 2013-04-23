


package overwatch.db;

import java.util.ArrayList;

import overwatch.core.Gui;
import overwatch.gui.NameRefPairList;





/**
 * 
 * @author John Murphy
 * @author Lee Coakley
 * @version 4
 * 
 * TODO comment your public functions
 */





public class Squads
{
	
	/**
	 * Create a new squad
	 * @return squadNo
	 */
	public static Integer create()
	{
		return Common.createWithUniqueLockingAutoInc(
			"Squads",
			"DEFAULT",
			"'new Squad <?>'",
			"null"
		);
	}
	
	
	
	
	
	public static boolean exists( Integer squadNo ) {
		return Database.queryExists( "Squads", "squadNo", squadNo.toString() );
	}
	
	
	
		
	
	public static NameRefPairList<Integer> getTroops( int squadNo )
	{
		EnhancedResultSet ers = Database.query(
			"SELECT  p.personNo AS personNum,  " +
			"        p.name     AS personName  " +
			"FROM Personnel   p,               " +
			"     Squads      s,               " +
			"     SquadTroops st               " +
			"WHERE s .squadNo  = " + squadNo + " " +
			"  AND s .squadNo  = st.squadNo    " +
			"  AND st.personNo = p.personNo;   "
		);
		
		if ( ! ers.isEmpty()) {
			Integer[] keys  = ers.getColumnAs( "personNum",  Integer[].class  );
			String [] names = ers.getColumnAs( "personName", String [].class  );
			return new NameRefPairList<Integer>( keys, names );
		}
		
		return new NameRefPairList<Integer>();
	}
	
	
	
	
	
	public static NameRefPairList<Integer> getVehicles( int squadNo )
	{
		EnhancedResultSet ers = Database.query(
			"SELECT v.name      AS vehicleName, " +
			"       v.vehicleNo AS vehicleNum   " +
			"FROM Squads        s,              " +
			"     SquadVehicles sv,             " +
			"     Vehicles      v               " +
			"WHERE s  .squadNo  = " + squadNo + " " +
			"  AND s  .squadNo  = sv.squadNo    " +
			"  AND sv.vehicleNo = v .vehicleNo; "
		);
		
		if ( ! ers.isEmpty()) {
			Integer[] keys  = ers.getColumnAs( "vehicleNum",  Integer[].class  );
			String [] names = ers.getColumnAs( "vehicleName", String [].class  );
			return new NameRefPairList<Integer>( keys, names );
		}
		
		return new NameRefPairList<Integer>();
	}
	
	
	
	
	
	public static NameRefPairList<Integer> getSupplies( int squadNo )
	{
		EnhancedResultSet ers = Database.query(
			"SELECT s.supplyNo AS supNo,          " +
			"       s.name     AS supName         " +
			"FROM Squads        sq,               " +
			"	  SquadSupplies sqs,              " +
			"     Supplies      s                 " +
			"WHERE sq .squadNo  = " + squadNo + " " + 
			"  AND sq .squadNo  = sqs.squadNo     " + 
			"  AND sqs.supplyNo = s  .supplyNo;   "
		);
		
		if ( ! ers.isEmpty()) {
			Integer[] keys  = ers.getColumnAs( "supNo",   Integer[].class  );
			String [] names = ers.getColumnAs( "supName", String [].class  );
			return new NameRefPairList<Integer>( keys, names );
		}
		
		return new NameRefPairList<Integer>();
	}
	
	
	
	
	
	public static String getAllVehiclesNotInSquads()
	{
		EnhancedResultSet ers = Database.query(
			"SELECT vehicleNo "		+
			"FROM Vehicles "	+
			"WHERE vehicleNo NOT IN "  +
			"( 		SELECT vehicleNo " +
			"		FROM SquadVehicles"
		);
		
		return ers.getElemAs("name", String.class);
	}
	
	
	
	
	
	/**
	 * Gets all the troops who are not in a squad
	 * @return
	 */
	public static NameRefPairList<Integer> getTroopsNotInSquads()
	{
		EnhancedResultSet ers = Database.query(
			"SELECT p.personNo, p.loginName " +
			"FROM Personnel p, Ranks r      " +
			"wHERE p.rankNo = r.rankNo      " +
			"AND r.name = 'Trooper'         " +
			"AND personNo NOT IN (          " +
			"    SELECT personNo            " +		
			"    FROM SquadTroops           " +
			");                             "	 
		);
		
		if ( ! ers.isEmpty()) {
			Integer[] keys  = ers.getColumnAs( "personNo",  Integer[].class  );
			String [] names = ers.getColumnAs( "loginName", String [].class  );
			return new NameRefPairList<Integer>( keys, names );
		}
		
		return new NameRefPairList<Integer>();
	}
	
	
	
	
	/**
	 * Get commanders not assignd to squads
	 */
	public static NameRefPairList<Integer> getCommandersNotInsquads()
	{
		EnhancedResultSet ers = Database.query(
			"SELECT p.personNo, p.loginName " +
			"FROM Personnel p, Ranks r      " +
			"wHERE p.rankNo = r.rankNo      " +
			"AND r.name = 'Commander';      "	 
		);
		
		if ( ! ers.isEmpty()) {
			Integer[] keys  = ers.getColumnAs( "personNo",  Integer[].class  );
			String [] names = ers.getColumnAs( "loginName", String [].class  );
			return new NameRefPairList<Integer>( keys, names );
		}
		
		return new NameRefPairList<Integer>();
	}
	
	
	
	
	
	public static boolean saveBasicSquadInfo( String squadName, Integer commanderNo, Integer squadNo )
	{		
		EnhancedPreparedStatement eps = new EnhancedPreparedStatement(
			"UPDATE Squads                   " +
			"SET name 	   = <<squadName>>,  " +
			"    commander = <<commanderNo>> " +
			"WHERE squadNo = <<squadNo>>;"
		);
		
		try {
			eps.set( "squadName",   squadName   );
			eps.set( "commanderNo", commanderNo );
			eps.set( "squadNo",     squadNo     );
			return (0 != eps.update());
		}
		finally {
			eps.close();
		}
	}
	
	
	
	
	
	/**
	 * Save squad assignments
	 * @param squadNo
	 * @param troops
	 * @param vehicles
	 * @param supplies
	 */
	public static void saveSquadAssignments( Integer squadNo, ArrayList<Integer> troops, ArrayList<Integer> vehicles, ArrayList<Integer> supplies )
	{		
		for (Integer trooper: troops) {
			Database.update(	
				"INSERT into SquadTroops VALUES( " + squadNo + ", " + trooper + " );"
			);
		}
		
		for (Integer vehicle: vehicles) {
			Database.update( "INSERT into SquadVehicles VALUES(" + squadNo + ", " + vehicle + "); "
			);
		}
		
		for (Integer supply: supplies) {
			Database.update(
				"INSERT into SquadSupplies VALUES(" + squadNo + ", " + supply + ");" 
			);
		}
	}
	
	
	
	
	
	/**
	 * Delete the squad selected
	 */
	public static int deleteSquad( Integer squadNo )
	{
		int mods = Database.update(
				"DELETE          " +
				"FROM Squads     " +
				"WHERE squadNo = " + squadNo + ";"
		);
		
		//Removes everything from the subpanel
		removeSquadAssignments(squadNo);
		
		return mods;
	}
	
	
	
	
	
	/**
	 * Remove everything from the squad
	 */
	public static void removeSquadAssignments( Integer squadNo )
	{
		Database.update(
				"DELETE FROM SquadTroops WHERE squadNo = " + squadNo + " ;"
		);
		
		//Delete the vehicles in the squad
		Database.update(
				"DELETE FROM SquadVehicles WHERE squadNo = " + squadNo + " ;"
		);
		
		// Delete the supplies
		Database.update(
				"DELETE FROM SquadSupplies WHERE squadNo = " + squadNo + " ;"
		);													
	}
		
		
		
	

}
