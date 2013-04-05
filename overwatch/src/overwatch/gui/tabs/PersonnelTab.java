


package overwatch.gui.tabs;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import overwatch.db.Database;
import overwatch.gui.CheckedFieldValidator;
import overwatch.gui.GenericPanelButtoned;
import overwatch.gui.LabelFieldEllipsisTriplet;
import overwatch.gui.LabelFieldPair;





/**
 * Implements the Personnel tab for the main interface.
 * The search panel here relates by personNo.
 * 
 * @author  John Murphy
 * @author  Lee Coakley
 * @version 2
 */





public class PersonnelTab extends GenericPanelButtoned<Integer>
{
	public final LabelFieldPair            number;
	public final LabelFieldPair            name;
	public final LabelFieldPair            age;
	public final LabelFieldPair            sex;
	public final LabelFieldPair            salary;
	public final LabelFieldEllipsisTriplet rank;
	public final JButton 				   login;
	
	
	
	
	
	public PersonnelTab()
	{
		super( "Personnel" );
		
		number  = addLabelledField( "Number:" );
		name    = addLabelledField( "Name:"   );
		age     = addLabelledField( "Age:"    );
		sex     = addLabelledField( "Sex:"    );
		salary  = addLabelledField( "Salary:" );
		rank    = addLabelledFieldWithEllipsis( "Rank:"   );
		login   = addToMain( new JButton("Login details..."), "skip 1, alignx right" );
		
		number.field.setEditable( false );
	};
	
	
	
	
	
	// Field validators
	public void addNameValidator  ( CheckedFieldValidator v ) {  name  .field.addValidator( v );  }
	public void addAgeValidator   ( CheckedFieldValidator v ) {  age   .field.addValidator( v );  }
	public void addSexValidator   ( CheckedFieldValidator v ) {  sex   .field.addValidator( v );  }
	public void addSalaryValidator( CheckedFieldValidator v ) {  salary.field.addValidator( v );  }
	public void addRankValidator  ( CheckedFieldValidator v ) {  rank  .field.addValidator( v );  }

	// Buttons
	public void addChangeLoginListener( ActionListener e ) { login.addActionListener(e); }

}













































