package overwatch.db;

import java.util.ArrayList;

import overwatch.gui.NameRefPair;
import overwatch.gui.NameRefPairList;



/**
 * 
 * @author john
 * @version 1
 */



public class Squads
{
	
	public static  <T> NameRefPairList<T> getTroops(int squadNo, Class<? extends T[]> keyType)
	{
		EnhancedResultSet ers = Database.query(
			"SELECT  p.personNo AS personNum, p.name AS personName " +
			"FROM Personnel p, Squads s, SquadTroops st " +
			"WHERE s.squadNo = st.squadNo " +
			"AND st.personNo = p.personNo;"
		);
			
		T[]      keys  = ers.getColumnAs( "personNum",  keyType        );
		String[] names = ers.getColumnAs( "personName", String[].class );
		
		return new NameRefPairList<T>( keys, names );
	}

}
