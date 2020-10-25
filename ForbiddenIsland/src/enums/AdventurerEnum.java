package enums;

/**
 * Class holding the Adventurer types in the Forbidden Island Game.
 * @author Demi Oke
 * @version 1.0
 *
 */

public enum AdventurerEnum {
	
	NAVIGATOR  ("Navigator"),
	DRIVER     ("Driver"),
	MESSENGER  ("Messenger"),
	EXPLORER   ("Explorer"),
	PILOT      ("Pilot"),
	ENGINEER   ("Engineer");
	
	private final String type;
	
	/**
     * constructor for Adventurer taking in the type associated 
     * with the Adventurer that will be printed on the board.
     * @param str String which will be set to the type of the adventurer.
     */
	private AdventurerEnum(String str) {
		type = str; 
    }
	
	
	/**
     * gets type relating to type of adventurer
     * @return the name.
     */
	@Override
	public String toString() {
		return this.type;
	}
	
	
	/*
	// Class-level test
	public static void main(String[] args) {
		
		for (AdventurerEnum adventurer : AdventurerEnum.values()) { 
		    System.out.println(adventurer); 
		}
		
	}
	*/

}
