


package overwatch.security;

import overwatch.core.Gui;
import overwatch.core.Main;
import overwatch.db.Database;
import overwatch.db.Personnel;





/**
 * Manages the user login process.
 * 
 * @author  Lee Coakley
 * @version 3
 */





public class LoginManager
{
	private static final int               NONE = -1;
	private static       int               currentUser;
	private static       BackgroundMonitor monitor;
	
	
	
	
	
	/**
	 * Check whether there is someone logged in right now.
	 * @return boolean
	 */
	public static boolean hasUser() {
		return (currentUser != NONE);
	}
	
	
	
	
	
	/**
	 * Get the person currently logged in.
	 * @return personNo
	 */
	public static int getUser() {
		checkUser();
		return currentUser;
	}
	
	
	
	
	
	/**
	 * Get security level for the current user from the database.
	 * @return integer security level
	 */
	public static int getSecurityLevel() {
		checkUser();		
		return Personnel.getPrivilegeLevel( getUser() );
	}
	
	
	
	
	
	/**
	 * Logs in a user.  If this function succeeds, the current user is set and
	 * a background monitor is run.
	 * @param user
	 * @param pass
	 * @return whether it succeeded
	 */
	public static boolean doLogin( String user, String pass )
	{
		int     personNo     = Personnel.getNumberFromLogin( user );
		boolean personExists = (personNo > 0);
		
		if (personExists) {
			HashSaltPair hsp = Personnel.getHashSaltPair( personNo );
			
			if (hsp != null)
			if (LoginCrypto.isPassValid( pass, hsp )) {
				currentUser  = personNo;
				setupMonitor();
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
	
	public static void debugResetAllPasses( String resetTo ) {
		HashSaltPair hsp = LoginCrypto.generateHashSaltPair( resetTo );
		
		int rowsChanged = Database.update(
			"update Personnel  " +
			"set loginHash = '" + hsp.hash + "'," +
			"    loginSalt = '" + hsp.salt + "'"
		);
		
		System.out.println( "resetAllPasses: changed " + rowsChanged + " HSPs." );
	}
	
	
	
	
	




	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private static void setupMonitor()
	{
		if (monitor != null) {
			monitor.stop();
		}
		
		monitor = new BackgroundMonitor();
		
		
		// Existence
		monitor.addBackgroundCheck( new BackgroundCheck() {
			public void onCheck()
			{
				if ( ! Personnel.exists( LoginManager.getUser() )) {
					Gui.showErrorDialogue( "Account Deleted", "Your account has been deleted!" );
					Main.shutdown();
				}
			}
		});
	}
	
	
	
	
	
	private static void checkUser()
	{
		if ( ! hasUser()) {
			throw new RuntimeException( "No user logged in!" );
		}
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////
	
	public static void main( String[] args )
	{	
			
		int personNo = Personnel.getNumberFromLogin( "testGuy" );
		Personnel.setPass( personNo, "1234" );
		
		
		
		boolean loginSuccess = LoginManager.doLogin(
			overwatch.util.Console.getString( "Enter login: " ),
			overwatch.util.Console.getString( "Enter pass:  " )
		);
		
		
		
		if (loginSuccess) {
			System.out.println( "logged in as #" + LoginManager.getUser() );
		} else {
			System.out.println( "Invalid login details!" );
		}
	}
	
}











































