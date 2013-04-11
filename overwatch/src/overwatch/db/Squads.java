


package overwatch.db;

import java.util.ArrayList;

import overwatch.gui.NameRefPair;
import overwatch.gui.NameRefPairList;





/**
 * 
 * @author John Murphy
 * @author Lee Coakley
 * @version 2
 * 
 * TODO comment your public functions
 */





public class Squads
{
	
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
		
		Integer[] keys  = ers.getColumnAs( "personNum",  Integer[].class  );
		String [] names = ers.getColumnAs( "personName", String [].class  );
		
		return new NameRefPairList<Integer>( keys, names );
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
		
		Integer[] keys  = ers.getColumnAs( "vehicleNum",  Integer[].class  );
		String [] names = ers.getColumnAs( "vehicleName", String [].class  );
		
		return new NameRefPairList<Integer>( keys, names );
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
		
		Integer[] keys  = ers.getColumnAs( "supNo",   Integer[].class );
		String [] names = ers.getColumnAs( "supName", String [].class );
		
		return new NameRefPairList<Integer>( keys, names );
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
