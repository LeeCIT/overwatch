


package overwatch.db;





public class Messages
{

	/**
	 * Create a new message.
	 * @param subject
	 * @param body
	 * @param sentBy
	 * @param sentTo
	 * @return messageNo
	 */
	public static Integer create( String subject, String body, Integer sentBy, Integer sentTo )
	{
		EnhancedPreparedStatement eps = new EnhancedPreparedStatement(
		  	"insert into Messages " +
		  	"values( default,     " +
		  	"		 default,     " +
		  	"		 <<subject>>, " +
		  	"		 <<body>>,    " +
		  	"		 <<sentBy>>,  " +
		  	"		 <<sentTo>>   " +
		  	");"
		);
		
		try {
			eps.set( "subject", subject );
			eps.set( "body",    body    );
			eps.set( "sentBy",  sentBy  );
			eps.set( "sentTo",  sentTo  );
			
			eps.update();
			
			return eps.getGeneratedKey();
		}
		finally {
			eps.close();
		}
	}
	
}
