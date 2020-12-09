package fi.game;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class GetInput {
	//===========================================================
	// Variable Setup
	//===========================================================
	private static GetInput getInput;
	Scanner user;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of TreasureDeck
     * @return treasureDeck. singleton TreasureDeck object.
     */
    public static GetInput getInstance(){
        if(getInput == null)
            getInput = new GetInput();
        return getInput;
    }
    
	//===========================================================
    // Constructor
    //===========================================================
    /*
     * Constructor for GetInput
     */
    private GetInput() {
    	user = new Scanner(System.in);
    }
    
	/**
	 * anInteger
	 * @param Integer minOption, the smallest valid number to be input.
	 * @param Integer maxOption, the largest valid number to be input.
	 * @return integer value of option picked
	 */
    public Integer anInteger(int minOption, int maxOption) {
    	Boolean validInput = false;
		int option = 0;
		String userString;
		while( !validInput ) {
			userString = user.nextLine();
			try {
				option = Integer.parseInt(userString);
			}
			catch (NumberFormatException e) {}
			catch (NoSuchElementException e) {}
			finally {
				if ((option >= minOption) && (option <= maxOption))
					validInput = true;
				else
					System.out.println("Incorrect input.");
			}
		}
    	return option;
    }
    
	/**
	 * aString gets a String from the console.
	 * @param Scanner user will read user input from Console
	 */
    public String aString() {
    	String aString = "";
    	while( aString.length() == 0)
    		aString = user.nextLine();
    	
    	return aString;
    }
    
    /**
     * Function to close the Scanner user in this class.
     * Should only be called at the end of the game otherwise the game will break!
     */
    public void endInput() {
    	user.close();
    }
    
}
