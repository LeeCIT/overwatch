


package overwatch.db;

import java.util.ArrayList;
import overwatch.gui.NameRefPair;





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
	
	
	
	
	
	/**
	 * Get orders sent to this person
	 * @param personNo
	 * @return
	 */
	public static ArrayList<NameRefPair<Integer>> getOrdersAndSubjectsSentTo( Integer personNo ) {
		return getOrdersAndSubjects( "sentTo", personNo );
	}
	
	
	
	
	
	/**
	 * Get orders sent by this person
	 * @param personNo
	 * @return
	 */
	public static ArrayList<NameRefPair<Integer>> getOrdersAndSubjectsSentBy( Integer personNo ) {
		return getOrdersAndSubjects( "sentBy", personNo );
	}
	
	
	
	
	
	/**
	 * Get message contents for a given order.
	 * @param orderNo
	 * @return EnhancedResultSet
	 */
	public static EnhancedResultSet getMessageContents( Integer orderNo )
	{
		return Database.query(
			"select sentDate,                " +
			"       subject,                 " +
			"       body,                    " +
			"       sentBy,                  " +
			"       sentTo                   " +
			"                                " +
			"from Orders   o,                " +
			"     Messages m                 " +
			"                                " +
			"where o.messageNo = m.messageNo " +
			"  and o.orderNo   = " + orderNo  + ";"
		);
	}
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	private static ArrayList<NameRefPair<Integer>> getOrdersAndSubjects( String sentByOrTo, Integer personNo )
	{
		EnhancedResultSet ers = Database.query(
			"select orderNo, subject           " +
			"                                  " +
			"from Orders   o,                  " +
			"     Messages m                   " +
			"                                  " +
			"where o.messageNo = m.messageNo   " +
			"  and m." + sentByOrTo + " = " + personNo +
			"                                  " +
			"order by o.isRead, o.isDone desc; "
		);
		
		return ers.getNameRefPairArrayList( "orderNo", Integer[].class, "subject" );
	}
	
}




























