package gameComponents;

import enums.TreasureEnum;
import enums.TreasureCardEnum;

/**
 * Class for TreasureCard in a Forbidden Island Treasure Deck.
 * This class is extended from AbstractTreasureCard.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */
public class TreasureCard extends AbstractTreasureCard {

	//===========================================================
	// Variable Setup
	//===========================================================
	private TreasureEnum treasureType;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for TreasureCard object.
	 * @param treasureType TreasureEnum treasureType for TreasureCard
	 */
	public TreasureCard(TreasureEnum treasureType) {
		super(TreasureCardEnum.TREASURE);
		this.treasureType = treasureType;
	}
	
	//===========================================================
	// Public Getters
	//===========================================================
	/**
	 * getTreasureType function, to return the treasureType
	 * @return the TreasureEnum type object treasureType.
	 */
	public TreasureEnum getTreasureType() {
		return treasureType;
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
		temp.append("; Treasure: " + treasureType.toString());
		return temp.toString();
	}
	
}
