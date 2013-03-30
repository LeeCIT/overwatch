


package overwatch.db;

import overwatch.security.HashSaltPair;





public class DatabaseTranslator
{
	public static HashSaltPair[] translateHashSaltPairs( EnhancedResultSet ers )
	{
		HashSaltPair[] array = new HashSaltPair[ ers.getRowCount() ];
		
		String[] hashes = (String[]) ers.getColumn( "loginHash" );
		String[] salts  = (String[]) ers.getColumn( "loginSalt" );
		
		for (int i=0; i<array.length; i++) {
			array[i] = new HashSaltPair( hashes[i], salts[i] );
		}
		
		return array;
	}
}
