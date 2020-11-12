package getInput;

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
    }
    
	/**
	 * anInteger
	 * @param Integer minOption, the smallest valid number to be input.
	 * @param Integer maxOption, the largest valid number to be input.
	 * @return integer value of option picked
	 */
    public Integer anInteger(int minOption, int maxOption) {
    	startInput();
    	
    	Boolean validInput = false;
		int option = 0;
		while( !validInput ) {
			String userString = user.nextLine();
			try {
				option = Integer.parseInt(userString);
			} catch (NumberFormatException e) {
				return option;
			}
			
			if ((option >= minOption) && (option <= maxOption)) {
				validInput = true;
				user.close();
			}
			else
				System.out.println("Incorrect input");
		}
    	endInput();
    	return option;
    }
    
	/**
	 * aString gets a String from the console.
	 * @param Scanner user will read user input from Console
	 */
	//TODO don't accept nothing.
    public String aString() {
    	startInput();
    	
    	String aString = user.nextLine();
    	endInput();
    	return aString;
    }
    
    private void startInput() {
    	user = new Scanner(System.in);
    }
    
    private void endInput() {
    	user.close();
    }
}
