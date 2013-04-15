


package overwatch.gui;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;




/**
 * Loads the nice icons I made.
 * 
 * @author  Lee Coakley
 * @version 1
 */




public class IconLoader
{
	private static ArrayList<Image> icons;
	
	
	
	
	
	/**
	 * Get icons for use with JFrame.setIconImages.
	 * Loads only once.
	 * @return ArrayList<Image>
	 */
	public static ArrayList<Image> getIcons()
	{
		if (icons == null) {
			icons = new ArrayList<Image>();
			icons.add( new ImageIcon( "images/ico16px.png" ).getImage() );
			icons.add( new ImageIcon( "images/ico64px.png" ).getImage() );
		}
		
		return icons;
	}
	
}
