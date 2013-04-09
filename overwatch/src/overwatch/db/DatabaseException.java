


package overwatch.db;





/**
 * DatabaseException thrown when queries/updates fail for any reason.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class DatabaseException extends RuntimeException
{

	public DatabaseException() {
		super();
	}
	
	public DatabaseException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super( message, cause, enableSuppression, writableStackTrace );
	}

	public DatabaseException( String message, Throwable cause ) {
		super( message, cause );
	}

	public DatabaseException( String message ) {
		super( message );
	}

	public DatabaseException( Throwable cause ) {
		super( cause );
	}
	
}
