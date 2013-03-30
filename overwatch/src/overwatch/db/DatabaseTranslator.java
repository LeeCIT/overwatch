


package overwatch.db;

import overwatch.security.HashSaltPair;





/**
 * Translates database results into more immediately useful representations.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class DatabaseTranslator
{
	
	public static HashSaltPair[] translateHashSaltPairs( EnhancedResultSet ers )
	{
		HashSaltPair[] array = new HashSaltPair[ ers.getRowCount() ];
		
		String[] hashes = ers.getColumnAs( "loginHash", String[].class );
		String[] salts  = ers.getColumnAs( "loginSalt", String[].class );
		
		for (int i=0; i<array.length; i++) {
			array[i] = new HashSaltPair( hashes[i], salts[i] );
		}
		
		return array;
	}
	
}
