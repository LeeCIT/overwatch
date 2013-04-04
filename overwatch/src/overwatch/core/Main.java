


package overwatch.core;





public class Main
{
	public static void main( String[] args )
	{
		Gui.setNativeStyle();
		
		Gui gui = new Gui();
		
		Controller.attachLogicControllers( gui );
	}
}
