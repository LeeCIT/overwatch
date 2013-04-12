


package overwatch.db;





/**
 * DatabaseException thrown when an update would result in an integrity violation.
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class DatabaseIntegrityException extends RuntimeException
{

	public DatabaseIntegrityException() {
		super();
	}
	
	public DatabaseIntegrityException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super( message, cause, enableSuppression, writableStackTrace );
	}

	public DatabaseIntegrityException( String message, Throwable cause ) {
		super( message, cause );
	}

	public DatabaseIntegrityException( String message ) {
		super( message );
	}

	public DatabaseIntegrityException( Throwable cause ) {
		super( cause );
	}
	
}
