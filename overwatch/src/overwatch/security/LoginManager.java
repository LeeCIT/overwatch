


package overwatch.security;

import overwatch.db.DbConnection;
import overwatch.db.UserInfoFetcher;
import overwatch.util.Console;





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
	private final int NONE = -1;
	
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
		int     personNo     = db.mapLoginToPerson( inputUser );
		boolean personExists = (personNo > 0);
		
		if (personExists) {
			System.out.println( "Good login name" );
			HashSaltPair hsp = db.getHashSaltPair( personNo );
			
			if (LoginCrypto.isPassValid( inputPass, hsp )) {
				System.out.println( "Good password" );
				currentUser  = personNo;
				currentLevel = db.getPrivilegeLevel( currentUser );
				return true;
			}
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
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{
		LoginManager lm = new LoginManager();
		
		
		lm.doLogin( Console.getString( "Enter login: " ), Console.getString( "Enter pass: " ) );
		
		if (lm.hasCurrentUser()) {
			System.out.println( "logged in as #" + lm.getCurrentUser() );
		} else {
			System.out.println( "Invalid login details!" );
		}
	}
	
}











































