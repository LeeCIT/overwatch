


package overwatch.gui;

import java.util.ArrayList;





/**
 * Nicer version of ArrayList<NameRefPair<T>>.
 * 
 * @author  Lee Coakley
 * @version 2
 */





public class NameRefPairList<T> extends ArrayList<NameRefPair<T>>
{
	
	public NameRefPairList()
	{
		super();
	}
	
	
	
	
	
	public NameRefPairList( T[] refs, String[] names )
	{
		if (refs.length != names.length)
			throw new RuntimeException( "Array lengths do not match." );
		
		for (int i=0; i<refs.length; i++) {
			addPair( refs[i], names[i] );
		}
	}
	
	
	
	
	
	public void addPair( T ref, String name ) {
		this.add( new NameRefPair<T>( ref, name ));
	}
	
}
