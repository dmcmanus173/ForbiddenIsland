package TreasureCards;

import enums.TreasureCardEnum;

/**
 * Abstract Class for AbstractTreaureCard in a Forbidden Island Treasure Deck.
 * This class should be extended by SandbagCard, TreasureCard, WaterRiseCard, and HelicopterLiftCard.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */
public abstract class AbstractTreasureCard {

	//===========================================================
	// Variable Setup
	//===========================================================
	private TreasureCardEnum cardType;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Abstract constructor for AbstractTreasureCard object.
	 * @param cardType TreasureCardEnum cardType for type of 
	 * treasure deck card.
	 */
	public AbstractTreasureCard(TreasureCardEnum cardType) {
		this.cardType = cardType;
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * getCardType function, to return the cardType
	 * @return the TreasureCardEnum type object cardType.
	 */
	public TreasureCardEnum getCardType() {
		return cardType;
	}
	
	/**
	 * toString method to return details of the class.
	 * @return String type object containing cardType as String.
	 */
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append("Card: " + cardType.toString());
		return temp.toString();
	}
	
}
