package otherComponents;

import fi.enums.TreasureEnum;

/**
 * Class for a Treasure in Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */
public class Treasure {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private TreasureEnum treasure;
	private boolean claimed;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Treasure object.
	 * @param treasure TreasureEnum treasure for type of 
	 * treasure (e.g. The Earth Stone).
	 */
	public Treasure(TreasureEnum treasure) {
		this.treasure = treasure;
		claimed = false;
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * claimTreasure function
	 * Changes treasure claimed variable to Boolean value true.
	 */
	public void claimTreasure() {
		System.out.println(treasure.toString() + " has been claimed!");
		claimed = true;
	}
	
	/**
	 * getType method returns the type of treasure it is.
	 * @return TreasureEnum treasureType
	 */
	public TreasureEnum getType() {
		return treasure;
	}	
	
	/**
	 * isClaimed function
	 * @return Boolean variable claimed, indicating whether a 
	 * treasure has been claimed.
	 */
	public boolean isClaimed() {
		return claimed;
	}
	
	/**
	 * toString method to return details of the class.
	 * @return String type object containing values of class variables.
	 */
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append("Treasure: " + treasure.toString() );
		temp.append("\nClaimed: " + claimed);
		return temp.toString();
	}
	
}
