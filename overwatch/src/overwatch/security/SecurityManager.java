


package overwatch.security;

import overwatch.db.Personnel;





/**
 * Handles security permissions.
 * These are all relative to the current user.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class SecurityManager
{
	
	public static boolean isGreaterOrEqualRankComparedTo( Integer personNo ) {	
		return current() > Personnel.getPrivilegeLevel( personNo );
	}
	
	
	
	
	
	private static int current() {
		return LoginManager.getSecurityLevel();
	}
}
