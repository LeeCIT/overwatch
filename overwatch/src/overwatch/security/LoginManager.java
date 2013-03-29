


package overwatch.security;

import overwatch.db.Db;
import overwatch.db.UserInfoFetcher;





/**
 * Manages the user login process.
 * 
 * TODO: what happens if some berk deletes a user that's logged in?
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class LoginManager
{
	private final int NONE         = -1;
	
	private int currentUser;
	private int currentLevel;
	
	private UserInfoFetcher db;
	
	
	
	
	
	public LoginManager()
	{
		currentUser  = NONE;
		currentLevel = NONE;
		db           = new UserInfoFetcher();
	}
	
	
	
	
	
	public boolean hasCurrentUser() {
		return (currentUser != NONE);
	}
	
	
	
	
	
	public int getCurrentUser() {
		checkUser();
		return currentUser;
	}
	
	
	
	
	
	public int getCurrentPrivilegeLevel() {
		checkUser();		
		return currentLevel;
	}
	
	
	
	
	
	public boolean doLogin( String inputUser, String inputPass )
	{
		int          personNo = db.getPersonForLogin( inputUser );
		HashSaltPair hsp      = db.getHashSaltPair( personNo );
		
		boolean passValid = LoginCrypto.isPassValid( inputPass, hsp );
		
		if (passValid) {
			currentUser  = personNo;
			currentLevel = db.getPrivilegeLevel( currentUser );
			return true;
		}
		
		return false;
	}
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private void checkUser()
	{
		if ( ! hasCurrentUser()) {
			throw new RuntimeException( "No user logged in!" );
		}
	}
	
}
