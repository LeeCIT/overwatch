


package overwatch.core;

import overwatch.db.DatabaseConstraints;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.PersonnelTab;
import overwatch.util.Validator;





public class PersonnelLogic
{
	
	public static void setupValidators( PersonnelTab tab )
	{
		tab.addNameValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.isValidName( text );
			}
		});
		
		
		
		tab.addAgeValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return Validator.isPositiveInt( text );
			}
		});
		
		
		
		tab.addSexValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.isValidSex( text );
			}
		});
		
		
		
		tab.addSalaryValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return Validator.isPositiveInt( text );
			}
		});
		
		
		
		tab.addRankValidator( new CheckedFieldValidator() {
			public boolean check( String text ) {
				return DatabaseConstraints.rankExists( text );
			}
		});
	}
	
}
