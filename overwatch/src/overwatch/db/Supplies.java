


package overwatch.db;





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
}
