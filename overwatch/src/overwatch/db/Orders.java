


package overwatch.db;





public class Orders
{
	
	/**
	 * Create a new order and an associated message.
	 * The returned integer is guaranteed to be the orderNo associated.
	 * WARNING: This function locks the Message table, then the Orders table, but not both.  Be aware of deadlocks.
	 * @param subject
	 * @param body
	 * @param sentBy
	 * @param sentTo
	 * @return orderNo
	 */
	public static Integer create( String subject, String body, Integer sentBy, Integer sentTo )
	{
		return Common.createWithUniqueLockingAutoInc(
			"Orders",
			"default",
			Messages.create( subject, body, sentBy, sentTo ).toString(),
			"false",
			"false"
		);
	}
}
