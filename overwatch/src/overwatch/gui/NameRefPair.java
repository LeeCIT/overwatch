


package overwatch.gui;





/**
 * Pairs a reference with a name.
 * Intended for GUI containers which use toString() to decide what will be shown.
 * Often you don't want the default toString() to be what is displayed.
 * 
 * @author Lee Coakley
 */





public class NameRefPair
{
	private String name;
	private Object ref;
	
	
	
	
	
	public NameRefPair( Object ref, String displayedName )
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
	
	
	
	
	
	public Object getRef() {
		return ref;
	}

	public void setRef( Object ref ) {
		this.ref = ref;
	}
	
	
	
	
	
	public String toString() {
		return getName();
	}
}
