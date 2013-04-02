


package overwatch.gui;

import java.util.ArrayList;





public class NameRefPairList<T> extends ArrayList<NameRefPair<T>>
{
	public void addPair( T ref, String name ) {
		this.add( new NameRefPair<T>( ref, name ));
	}
}
