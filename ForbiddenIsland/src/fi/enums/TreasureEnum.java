package fi.enums;

/**
 * Class holding the Treasure names in the Forbidden Island Game.
 * @author Demi Oke & Daniel McManus
 * @date    31/10/2020
 * @version 0.1
 *
 */

public enum TreasureEnum {
	
	THE_EARTH_STONE      ("The Earth Stone"),
	THE_STATUE_OF_WIND   ("The Statue of Wind"),
	THE_CRYSTAL_OF_FIRE  ("The Crystal of Fire"),
	THE_OCEANS_CHALICE   ("The Ocean's Chalice");
	
	private final String name;
	
	/**
     * constructor for Treasures taking in the name associated 
     * with the treasure that will be printed on the board.
     * @param str String which will be set to the name of the treasure
     */
	private TreasureEnum(String str) {
		name = str; 
    }
	
	/**
     * gets name relating to treasure
     * @return the name.
     */
	@Override
	public String toString() {
		return this.name;
	}
	
	
	/*
	// Class-level test
	public static void main(String[] args) {
		
		for (TreasureEnum treasure : TreasureEnum.values()) { 
		    System.out.println(treasure); 
		}
		
	}
	*/
		
	
	

}