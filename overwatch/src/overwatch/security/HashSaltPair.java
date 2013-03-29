


package overwatch.security;





/**
 * Immutable storage for LoginCrypto output.
 *  
 * @author  Lee Coakley
 * @version 2
 */





public class HashSaltPair
{
	public final String hash;
	public final String salt;
	
	
	
	
	
	public HashSaltPair( String hash, String salt )
	{
		this.hash = hash;
		this.salt = salt;
	}
}
