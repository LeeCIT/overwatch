


package overwatch.db;





public class Messages
{

	/**
	 * Create a new message.
	 * The returned integer is guaranteed to be the messageNo for the given message.
	 * @param subject
	 * @param body
	 * @param sentBy
	 * @param sentTo
	 * @return
	 */
	public static Integer create( String subject, String body, Integer sentBy, Integer sentTo )
	{
		return Common.createWithUniqueLockingAutoInc(
			"Messages",
			"default",
			"default",
			"'" + subject + "'",
			"'" + body    + "'",
			""  + sentBy,
			""  + sentTo
		);
	}
	
}
