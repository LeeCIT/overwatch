


package overwatch.security;

import overwatch.db.UserInfo;





/**
 * Manages the user login process.
 * 
 * TODO: What happens if some berk deletes a user that's logged in?
 *       Need some periodic checking somewhere to keep things valid with multiple users.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class LoginManager
{
	private static final int NONE = -1;
	
	private static int currentUser;
	private static int currentLevel;
	
	
	
	
	
	public static boolean hasCurrentUser() {
		return (currentUser != NONE);
	}
	
	
	
	
	
	public static int getCurrentUser() {
		checkUser();
		return currentUser;
	}
	
	
	
	
	
	public static int getCurrentPrivilegeLevel() {
		checkUser();		
		return currentLevel;
	}
	
	
	
	
	
	public static boolean doLogin( String inputUser, String inputPass )
	{
		int     personNo     = UserInfo.mapLoginToPerson( inputUser );
		boolean personExists = (personNo > 0);
		
		if (personExists) {
			HashSaltPair hsp = UserInfo.getHashSaltPair( personNo );
			
			if (hsp != null)
			if (LoginCrypto.isPassValid( inputPass, hsp )) {
				currentUser  = personNo;
				currentLevel = UserInfo.getPrivilegeLevel( personNo );
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private static void checkUser()
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
			
		int personNo = UserInfo.mapLoginToPerson( "testGuy" );
		UserInfo.setUserPass( personNo, "1234" );
		
		
		
		boolean loginSuccess = LoginManager.doLogin(
			overwatch.util.Console.getString( "Enter login: " ),
			overwatch.util.Console.getString( "Enter pass:  " )
		);
		
		
		
		if (loginSuccess) {
			System.out.println( "logged in as #" + LoginManager.getCurrentUser() );
		} else {
			System.out.println( "Invalid login details!" );
		}
	}
	
}











































