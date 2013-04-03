


package overwatch.gui;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import overwatch.db.Database;





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
	private LabelFieldPair            number;
	private LabelFieldPair            name;
	private LabelFieldPair            age;
	private LabelFieldPair            sex;
	private LabelFieldPair            salary;
	private LabelFieldEllipsisTriplet rank;
	private JButton 				  login;
	
	
	
	
	
	public PersonnelTab()
	{
		super( "Personnel", "Details" );
		
		setupComponents();
	};
	
	
	
	
	
	// Field validators
	public void addNameValidator  ( CheckedFieldValidator v ) {  name  .field.addValidator( v );  }
	public void addAgeValidator   ( CheckedFieldValidator v ) {  age   .field.addValidator( v );  }
	public void addSexValidator   ( CheckedFieldValidator v ) {  sex   .field.addValidator( v );  }
	public void addSalaryValidator( CheckedFieldValidator v ) {  salary.field.addValidator( v );  }
	public void addRankValidator  ( CheckedFieldValidator v ) {  rank  .field.addValidator( v );  }

	// Buttons
	public void addChangeLoginListener( ActionListener e ) { login.addActionListener(e); }
	
	

	
		
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private void setupComponents()
	{
		number  = addLabelledField( "Number:" );
		name    = addLabelledField( "Name:"   );
		age     = addLabelledField( "Age:"    );
		sex     = addLabelledField( "Sex:"    );
		salary  = addLabelledField( "Salary:" );
		rank    = addLabelledFieldWithEllipsis( "Rank:"   );
		login   = addToMain( new JButton("Login details..."), "skip 1, alignx right" );
		
		number.field.setEditable( false );
	}

}













































