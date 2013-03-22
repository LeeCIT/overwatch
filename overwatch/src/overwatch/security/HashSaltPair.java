package overwatch.security;





/**
 * Immutable storage for LoginCrypto output.
 *  
 * @author  Lee Coakley
 * @since   15/Mar/2013
 * @version 2
 * Good work 
 * Demo upload
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
