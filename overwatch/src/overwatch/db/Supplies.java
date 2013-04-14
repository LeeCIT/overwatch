


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
		return Common.createWithUniqueLockingAutoInc(
			"Supplies",
			"DEFAULT",
			"'new Supply <?>'",
			"0"
		);
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
	
	
	
	
	
	
}
