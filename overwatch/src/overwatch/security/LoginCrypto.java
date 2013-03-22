


package overwatch.security;

import java.security.MessageDigest;
import java.security.SecureRandom;





/**
 * Provides easy-to-use passphrase cryptography functions.
 * Passphrases should never be stored in a database.  Instead a salted hash of the passphrase
 * should be stored along with its salt, for later comparison by the same hashing scheme.
 * This ensures that user passphrases are unreadable even if the database is broken into.  
 *  
 * @author  Lee Coakley
 * @since   15/Mar/2013
 * @version 2
 */





public class LoginCrypto
{
	private static final String HASH_ALGORITHM  = "SHA-256";
	private static final int    HASH_ITERATIONS = 1 << 18;
	private static final int    SALT_BYTES 		= 8;
	private static final String STRING_ENCODING = "UTF-8";
	
	
	
	
	
	/**
	 * Generates a salted hash for storage in the database.
	 * Later used to evaluate passphrase correctness.
	 * The input passphrase itself MUST NOT BE STORED ANYWHERE.
	 * @param inputPass Passphrase string from user input.
	 * @return Immutable hash/salt pair to store in database. 
	 */
	public static HashSaltPair generateHashSaltPair( String inputPass )
	{	
		byte[] pass = stringBytes( inputPass );
		byte[] salt = generateSalt();
		byte[] hash = iterativeHash( pass, salt );
		
		String saltHex = bytesToHex( salt );
		String hashHex = bytesToHex( hash );
		
		return new HashSaltPair( hashHex, saltHex );
	}
	
	
	
	
	
	/**
	 * Given the stored values, check if a user-input passphrase produces the same result.
	 * @param inputPass Passphrase string from user input.
	 * @param storedHash Hex-encoded passphrase hash.
	 * @param storedSalt Hex-encoded salt for given hash.
	 * @return True if matches, false otherwise.
	 */
	public static boolean isPassValid( String inputPass, String storedHash, String storedSalt )
	{	
		byte[] pass = stringBytes( inputPass );
		byte[] salt = hexToBytes( storedSalt );
		byte[] hash = iterativeHash( pass, salt );
		
		String expectedHash = bytesToHex( hash );
		return expectedHash.equals( storedHash );
	}
	
	
	
	
	
	/**
	 * Feeds the hash back into itself repeatedly.
	 * Should the database be compromised, this makes brute-forcing the hashed passphrases
	 * much more computationally expensive.  It is intentionally very slow.
	 * @param pass Byte representation of user-input passphrase.
	 * @param salt Random bytes for combination with the passphrase.
	 * @return Byte array 32 bytes in length.
	 */
	private static byte[] iterativeHash( byte[] pass, byte[] salt )
	{
		byte[] accumulator = hash( pass, salt );
		
		for (int i=0; i<HASH_ITERATIONS; i++) {
			accumulator = hash( accumulator, salt );
		}
		
		return accumulator;
	}
	
	
	
	
	
	/**
	 * Get SHA-256 message digest for a given passphrase and salt combination.
	 * The result is always the same for the same inputs.
	 * This isn't the optimal choice of algorithm, but will suffice for this project.
	 * In reality you would use Bcrypt or PCKDF2.
	 * @param pass Byte representation of user-input passphrase.
	 * @param salt Random bytes for combination with the passphrase.
	 * @return Byte array 32 bytes in length.
	 */
	private static byte[] hash( byte[] pass, byte[] salt )
	{
		try {
			MessageDigest algo = MessageDigest.getInstance( HASH_ALGORITHM );
	 		
			byte[] saltedPass = concatBytes( pass, salt );
			return algo.digest( saltedPass );
		}
		catch (Exception ex) {
			throw new RuntimeException( ex ); 
		}
	}
	
	
	
	
	
	/**
	 * Generate cryptographically secure random data for combination with user passphrase input.
	 * Using a random per-passphrase salt prevents brute-force attacks from revealing more than
	 * one passphrase at a time.  If not used, each passphrase would have a higher probability of
	 * being cracked as the same passphrase would produce the same hash - with a salt, this is no
	 * longer feasible.
	 * @return random fixed-lenght byte array.
	 */
	private static byte[] generateSalt()
	{
		SecureRandom secRand = new SecureRandom();
		byte[]		 salt    = new byte[ SALT_BYTES ];
		
		secRand.nextBytes( salt );
		
		return salt;
	}
	
	
	
	
	
	/**
	 * Get raw byte representation of a string in UTF-8.
	 * @param str Input string
	 * @return Byte array.
	 */
	private static byte[] stringBytes( String str ) {
		try {
			return str.getBytes( STRING_ENCODING );
		}
		catch (Exception ex) {
			throw new RuntimeException( ex ); 
		}
	}
	
	
	
	
	
	/**
	 * Convert byte array to hexidecimal string representation.
	 * @param bytes Byte array
	 * @return hexidecimal representation.  Each character encodes 4 bits.
	 */
	private static String bytesToHex( byte[] bytes )
	{
		String out = "";
		
		for (byte by: bytes) {
			int    masked = 0xFF  &  by; // Upcast and remove sign-extended sign bit
			String hexStr = Integer.toHexString( masked );
			
			if (masked <= 0x0F) 
				hexStr = "0" + hexStr;
			
			out += hexStr;
		}
		
		return out;
	}
	
	
	
	
	
	/**
	 * Convert hexidecimal representation back to byte array. 
	 * @param hex String of hexidecimal characters.  (00 to FF).
	 * @return Byte array
	 */
	private static byte[] hexToBytes( String hex )
	{
		byte[] out = new byte[ hex.length() / 2 ];
		
		for (int i=0; i<out.length; i++) {
			int    pos    = i * 2;
			String digits = hex.substring( pos, pos + 2 );
			out[i] = (byte) Integer.parseInt( digits, 16 );
		}
		
		return out;
	}
	
	
	
	
	
	/**
	 * Concatenate two arrays of bytes.
	 * @param first
	 * @param second
	 * @return Concatenated array.
	 */
	private static byte[] concatBytes( byte[] first, byte[] second )
	{
		byte[] out = new byte[ first.length + second.length ];
		
		for (int i=0; i<out.length; i++) {			
			if (i < first.length) 
				 { out[i] = first [ i ];                }
			else { out[i] = second[ i - first.length ];	}
		}
		
		return out;
	}
}




























