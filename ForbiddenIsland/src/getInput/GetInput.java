package getInput;

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
    	//Scanner aUser = new Scanner(System.in);
    	
    	Boolean validInput = false;
		int option = 0;
		String userString;
		while( !validInput ) {
			userString = user.nextLine();
			try {
				option = Integer.parseInt(userString);
			} catch (NumberFormatException e) {
			} catch (NoSuchElementException e) {
				System.out.println();
			} finally {
				if ((option >= minOption) && (option <= maxOption))
					validInput = true;
				else
					System.out.println("Incorrect input.");
			}
		}
		//aUser.close();
    	//endInput();
    	return option;
    }
    
	/**
	 * aString gets a String from the console.
	 * @param Scanner user will read user input from Console
	 */
	//TODO don't accept nothing.
    public String aString() {
    	String aString;
    	aString = user.nextLine();
    	//System.out.println(aString);
    	return aString;
    }
    
    public void endInput() {
    	user.close();
    }
    
}
