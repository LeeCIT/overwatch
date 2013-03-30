


package overwatch.util;

import java.util.Scanner;





/**
 * Provides basic console input functions with a variety of constraints.
 * These functions can't fail: they loop forever until input matching the constraints is provided.
 * @author  Lee Coakley
 * @version 4
 */





public class Console
{
	/**
	 * Safely get integer constrained by lower bound.
	 * @param prompt What to say.
	 * @param lowest Lower bound.
	 * @return Integer <= lower bound.
	 */
	public static int getIntLowerBound( String prompt, int lowest )
	{
	    for (;;) { // Forever
	        System.out.print( prompt );
	        
	        int in = getInt( "" );  // Already have a prompt, so none here.
	        
	        if (in >= lowest) {
	            return in;
	        } else {
	        	System.out.println( "[Error] Number must be at least " + lowest + "." );
	        }
	    }
	}
	
	
	
	
	
	/**
	 * Safely get integer constrained within an inclusive range.
	 * @param prompt What to say
	 * @param lowest The lower bound
	 * @return Guaranteed clamped integer
	 */
	public static int getIntRange( String prompt, int lowest, int highest )
	{
	    for (;;) { // Forever
	        System.out.print( prompt );
	        
	        int in = getInt( "" );  // Already have a prompt, so none here.
	        
	        if (in >= lowest && in <= highest) {
	            return in;
	        } else {
	        	System.out.println( "[Error] Number must be between " + lowest + " and " + highest + " inclusive." );
	        }
	    }
	}
	
	
	
	
	
    /**
     * Safely gets integer user input.
     * @param prompt What to ask.
     * @return A value guaranteed to be a valid integer.
	 */
	public static int getInt( String prompt )
	{
		Scanner kbi = new Scanner(System.in);
		
		// What are we up to here?
		System.out.print( prompt );
		
		// While we haven't got anything good...
		while (!kbi.hasNextInt()) {
			// Bad input.  Show error, eat newline and repeat.
			System.out.print( "[Error] Not an integer.\n" + prompt );
			kbi.next();
		}
		
		// Must be an int now.
		return kbi.nextInt();
	}
	
	
	
	
	
	/**
	 * Safely get string user input, with a constraint on the minimum length.
	 * @param prompt The prompt to display when asking for input.
	 * @param len The minimum allowed string length.
	 * @return The string the user entered.
 	*/
	public static String getStringMinLength( String prompt, int len )
	{
		for(;;) { // Stay in the loop forever until a valid input is given.
			String str = getString( prompt );
			
			if (str.length() < len) {
				System.out.println( "[Error] String length must be at least " + len + " characters."  );
			} else {
				return str; // Good input; time to vacate the premises.
			}
		}
	}
	
		
	
	
	
	/**
	 * Safely get string user input.
	 * @param prompt The prompt to display when asking for input.
	 * @return The string the user entered, guaranteed to be valid.
	 */
	public static String getString( String prompt )
	{
		Scanner kbi = new Scanner( System.in ); // Instance a scanner
		System.out.print( prompt ); // What are we up to here?
		
		while (!kbi.hasNextLine()) { // Not sure if this can actually happen, but you never know.
			// Bad bad bad.  Show error, eat newline and repeat.
			System.out.print( "[Error] How did you do that?  Try again: " );
			kbi.next();
		}
		
		// String is now guaranteed to be valid.
		return kbi.nextLine();
	}
	
	
	
	
	
	/**
     * Safely get double input with inclusive lower limit.
     * @param prompt The message to show when getting this input.
     * @param lowest Lowest number allowed.
     * @return A value guaranteed to be a valid integer >= 'lowest'.
	 */
	public static double getDoubleLowerBound( String prompt, double lowest )
	{
	    for (;;) { // Forever
	        System.out.print( prompt );
	        
	        double in = getDouble( "" );  // Already have a prompt, so none here.
	        
	        if (in >= lowest) {
	            return in;
	        } else {
	        	System.out.println( "[Error] Number must be greater than or equal to " + lowest );
	        }
	    }
	}
	
	
	
	
	
	/**
     * Safely get double user input.
     * @param prompt What to ask.
     * @return A value guaranteed to be a valid double.
	 */
	public static double getDouble( String prompt )
	{
		Scanner kbi = new Scanner(System.in);
		
		// What are we up to here?
		System.out.print( prompt );
		
		// While we haven't got anything good...
		while (!kbi.hasNextDouble()) {
			// Bad input.  Show error, eat newline and repeat.
			System.out.print( "[Error] Not a numeric value.  Try again: " );
			kbi.nextLine();
		}
		
		// Must be an int now.
		return kbi.nextDouble();
	}
	
	
	
	
	
	/**
	 * Ask a question and append "(Y/N)" as is usual in CLIs.
	 * @param question What to ask.
	 * @return True or false, signifying whether the answered yes or no.
	 */
	public static boolean getBoolYesNo( String question )
	{		
		System.out.print( question + " (Y/N): " );
		
		for (;;) {
			String  input   = getString( "" );
			boolean saidYes = ( input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes") );
			boolean saidNo  = ( input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")  );
			
			if (saidYes || saidNo) {
				return saidYes ? true : false;
			} else {
				System.out.print( "[Error] Type Y or N: " );
			}
		}
	}
	
	
	
	
	
	/**
	 * Show a message and wait for user to press enter before continuing execution.
	 * @param message What to say
	 */
	public static void pause( String message )
	{
		Scanner kbi = new Scanner(System.in);
		
		if (message.length() > 0) {
			System.out.print( "\n" + message + "\n\n" );
		} else {
			System.out.println();
		}
		
		System.out.print( "Press Enter to continue..." );
		kbi.nextLine();
		System.out.print( "\n\n" );
	}
	
	
	
	
	
	public static void pause()
	{
		Console.pause("");
	}
}






















