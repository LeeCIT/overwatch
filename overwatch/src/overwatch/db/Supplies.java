


package overwatch.db;

import overwatch.gui.NameRefPairList;





/**
 * Database <-> Supply interactions
 * 
 * @author  Lee Coakley
 * @author  John Murphy
 * @version 2
 */





public class Supplies
{
	
	/**
	 * Create a new supply
	 * @return supplyNo
	 */
	public static Integer create()
	{
		EnhancedPreparedStatement eps = new EnhancedPreparedStatement(
		  	"insert into Supplies " +
		  	"values( default,     " +
		  	"		 <<name>>,    " +
		  	"		 0            " +
		  	");"
		);
		
		try {
			eps.set( "name", "supply " + Common.randomNamePart() );
			eps.update();			
			return eps.getGeneratedKey();
		}
		finally {
			eps.close();
		}
	}
	
	
	
	
	
	/**
	 * Check if supply exists.
	 * @param number
	 * @return Exists
	 */
	public static boolean exists( Integer supplyNo )
	{
		Integer[] number = Database.queryInts(
			"SELECT supplyNo " +
			"from Supplies   " +
			"where supplyNo = " + supplyNo + " " +
			"limit 1;"
		);
		
		return (number.length != 0);
	}
	
	
	
	
	
	/**
	 * Delete a supply
	 * @param supplyNo
	 */
	public static void delete( Integer supplyNo )
	{
		Database.update(
			"DELETE           " +
			"FROM Supplies    " +
			"WHERE supplyNo = " + supplyNo + ";"
		);
	}
	
	
	
	
	
	/**
	 * Gets the name of the supply
	 * @return The name of the supply
	 */
	public static String getSupplyName(int supplyNo)
	{
		EnhancedResultSet ers = Database.query(
				"SELECT name "		+
				"FROM Supplies " 	+
				"WHERE supplyNo = " + supplyNo + " ;"
		);
		
		if (ers.isEmpty())
			 return null;
		else return ers.getElemAs( "name", String.class ); 
	}
	
	
	
	

	/**
	 * Gets all the supplies for the picker
	 * @return the supplies
	 */
	public static NameRefPairList<Integer> getAllSupplys()
	{
		EnhancedResultSet ers = Database.query(
				"SELECT supplyNo, name " +
				"FROM Supplies "
			);
		
		if ( ! ers.isEmpty()) {
			Integer[] keys  = ers.getColumnAs( "supplyNo",  Integer[].class  );
			String [] names = ers.getColumnAs( "name", String [].class  );
			return new NameRefPairList<Integer>( keys, names );
		}
		
		return new NameRefPairList<Integer>();
	}





	public static boolean save( Integer supplyNo, String supplyName, Integer supplyCount )
	{
		EnhancedPreparedStatement eps = new EnhancedPreparedStatement(
		  	"update Supplies          " +
		  	"set name  = <<name>>,    " +
		  	"	 count = <<count>>    " +
		  	"where supplyNo = <<num>>;"
		);
		
		try {
			eps.set( "num",   supplyNo    );
			eps.set( "name",  supplyName  );
			eps.set( "count", supplyCount );
			return (0 != eps.update());
		}
		finally {
			eps.close();
		}
	}
	
}




















































