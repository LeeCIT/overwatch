package overwatch.db;

import java.util.ArrayList;

import overwatch.gui.NameRefPair;
import overwatch.gui.NameRefPairList;



/**
 * 
 * @author John Murphy
 * @author Lee Coakley
 * @version 2
 */



public class Squads
{
	
	public static  <T> NameRefPairList<T> getTroops(int squadNo, Class<? extends T[]> keyType)
	{
		EnhancedResultSet ers = Database.query(
			"SELECT  p.personNo AS personNum, p.name AS personName " +
			"FROM Personnel p, Squads s, SquadTroops st " 			 +
			"WHERE s.squadNo = " + squadNo + " "					 +
			"AND s.squadNo = st.squadNo "							 +
			"AND st.personNo = p.personNo;"
		);
			
		T[]      keys  = ers.getColumnAs( "personNum",  keyType        );
		String[] names = ers.getColumnAs( "personName", String[].class );
		
		return new NameRefPairList<T>( keys, names );
	}
	
	
	
	
	public static <T> NameRefPairList<T> getVehicles(int squadNo, Class<? extends T[]> keyType)
	{
		EnhancedResultSet ers = Database.query(
				"SELECT v.type AS vehicleType, v.vehicleNo AS vehicleNum " 	+
				"FROM Squads s, SquadVehicles sv, Vehicles v " 	+
				"WHERE s.squadNo = " + squadNo + " " +
				"AND s.squadNo = sv.squadNo "	+
				"AND sv.vehicleNo = v.vehicleNo;"				
		);
		
		
		T[]      keys  = ers.getColumnAs( "vehicleNum",   keyType        );
		String[] names = ers.getColumnAs( "vehicleType", String[].class );
		
		return new NameRefPairList<T>( keys, names );		
	}
	
	
	
	public static <T> NameRefPairList<T> getSupplies(int squadNo, Class<? extends T[]> keyType)
	{
		EnhancedResultSet ers = Database.query(
				"SELECT supplies.type AS supType, supplies.supplyNo AS supNo " +
				"FROM Squads s, SquadSupplies ss, Supplies supplies " +
				"WHERE s.squadNo = " + squadNo + " " + 
				"AND s.squadNo = ss.squadNo " + 
				"AND ss.supplyNo = supplies.supplyNo;"
		);
		
		
		T[]      keys  = ers.getColumnAs( "supNo",   keyType        );
		String[] names = ers.getColumnAs( "supType", String[].class );
		
		return new NameRefPairList<T>( keys, names );	
	}
	
	
	
	
	public static Integer create()
	{
		Common.createWithUnique( "Squads", "DEFAULT", "'new Squad <?>'");
		
		return Database.querySingle( Integer.class,
			"select max(squadNo)" +
			"from Squads;"
		);
	}
	 
	
	
	
	

}
