package fi.cards;

import fi.enums.TileEnum;

/**
* Class for a FloodCard in the Forbidden Island Game.
* This card is extends Card Abstract Class.
* 
* @author  Demi Oke and Daniel McManus
* @date    27/10/2020
* @version 0.1
*/

public class FloodCard extends Card{

	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for TreasureCard object.
	 * @param treasureType TreasureEnum treasureType for TreasureCard
	 */
	public FloodCard(TileEnum tileName) {
		super(tileName);
	}
	
	/**
	 * toString method to return details of the class.
	 * @return String type object containing TreasureCard variable 
	 * values for this class and its super class.
	 */
	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append(super.toString());
		temp.append(" - Flood Card");
		return temp.toString();
	}

}

