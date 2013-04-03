


package overwatch.gui;





/**
 * For automatically validating checked fields.
 * You can have multiple validators on a field.
 * 
 * 
 * @author  Lee Coakley
 * @version 1
 * @see     CheckedField
 */





public interface CheckedFieldValidator
{
	public boolean check( String text );
}
