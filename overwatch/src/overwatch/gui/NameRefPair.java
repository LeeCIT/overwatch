


package overwatch.gui;





/**
 * Pairs a reference with a name.
 * Intended for GUI containers which use toString() to decide what will be shown.
 * Often you don't want the default toString() to be what is displayed.
 * 
 * @author Lee Coakley
 * @version 1
 */





public class NameRefPair<Type>
{
	private String name;
	private Type   ref;
	
	
	
	
	
	public NameRefPair( Type ref, String displayedName )
	{
		this.ref  = ref;
		this.name = displayedName;
	}

	
	
	
	
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
	
	
	
	
	public Type getRef() {
		return ref;
	}

	public void setRef( Type ref ) {
		this.ref = ref;
	}
	
	
	
	
	
	public String toString() {
		return getName();
	}
}
