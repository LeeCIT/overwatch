


package overwatch.db;

import java.sql.Connection;
import java.util.ArrayList;

import overwatch.core.Gui;
import overwatch.gui.NameRefPair;
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
	
	
	
	
	
	public static void save( Integer squadNo, String squadName, Integer commanderNo,
							Integer[] troops, Integer[] vehicles, Integer[] supplies )
	{
		Connection conn = Database.getConnection();
		
		try {
			Database.lock( conn,
				"Squads",
				"SquadTroops",
				"SquadVehicles",
				"SquadSupplies"
			);
			
			try {
				int rowMods = 0;
				
				EnhancedPreparedStatement eps = new EnhancedPreparedStatement( conn,
					"UPDATE Squads                   " +
					"SET name 	   = <<squadName>>,  " +
					"    commander = <<commanderNo>> " +
					"WHERE squadNo = <<squadNo>>;"
				);
				
				try {
					eps.set( "squadName",   squadName   );
					eps.set( "commanderNo", commanderNo );
					eps.set( "squadNo",     squadNo     );
					rowMods = eps.update();
				}
				finally {
					eps.close();
				}
				
				if (rowMods == 0)
					throw new DatabaseException( "Squad #" + squadNo + " no longer exists." );
				
				// Clear assignments
				Database.update( conn, "DELETE FROM SquadTroops   WHERE squadNo = " + squadNo + " ;" );
				Database.update( conn, "DELETE FROM SquadVehicles WHERE squadNo = " + squadNo + " ;" );			
				Database.update( conn, "DELETE FROM SquadSupplies WHERE squadNo = " + squadNo + " ;" );
				
				// Insert new assignments
				for (Integer trooper: troops)   Database.update( conn, "INSERT into SquadTroops   VALUES(" + squadNo + ", " + trooper + ");" );
				for (Integer vehicle: vehicles) Database.update( conn, "INSERT into SquadVehicles VALUES(" + squadNo + ", " + vehicle + ");" );				
				for (Integer supply:  supplies) Database.update( conn, "INSERT into SquadSupplies VALUES(" + squadNo + ", " + supply  + ");" );
			}
			finally {
				Database.unlock( conn );
			}
		}
		finally {
			Database.returnConnection( conn );
		}
	}
	
	
	
	
	
	/**
	 * Delete the squad selected
	 */
	public static int delete( Integer squadNo )
	{
		int mods = Database.update(
				"DELETE          " +
				"FROM Squads     " +
				"WHERE squadNo = " + squadNo + ";"
		);
		
		//Removes everything from the subpanel
		deleteAssignments(squadNo);
		
		return mods;
	}
	
	
	
	
	
	/**
	 * Remove everything from the squad
	 */
	public static void deleteAssignments( Integer squadNo )
	{
		Database.update(
			"DELETE FROM SquadTroops WHERE squadNo = " + squadNo + " ;"
		);
		
		Database.update(
			"DELETE FROM SquadVehicles WHERE squadNo = " + squadNo + " ;"
		);
		
		Database.update(
			"DELETE FROM SquadSupplies WHERE squadNo = " + squadNo + " ;"
		);
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
	 * Get commanders not assigned to squads
	 */
	public static ArrayList<NameRefPair<Integer>> getCommandersNotInsquads()
	{
		String query = 
			" SELECT p.personNo,                  " +
			"        p.loginName                  " +
			" FROM Personnel p,                   " +
			"      Ranks r                        " +
			" WHERE p.rankNo = r.rankNo           " +
			"   and r.name   = 'Commander'        " +
			"   and p.personNo NOT IN (           " +
			"         SELECT Commander            " +
			"         FROM Squads                 " +
			"         WHERE Commander IS NOT NULL " +
			"     );                              ";
		
		return Database.queryKeyNamePairsGeneral( query, "personNo", "loginName", Integer[].class );
	}

	
}
