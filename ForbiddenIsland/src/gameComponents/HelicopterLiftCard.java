package gameComponents;

import enums.TreasureCardEnum;

/**
 * Class for HelicopterLiftCard in a Forbidden Island Treasure Deck.
 * This class is extended from AbstractTreasureCard.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */
public class HelicopterLiftCard extends AbstractTreasureCard {

	//===========================================================
	// Variable Setup
	//===========================================================
	private static HelicopterLiftCard heliCard; 
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of HelicopterLiftCard
     * @return HelicopterCard. singleton HelicopterLiftCard object.
     */
	public static HelicopterLiftCard getInstance() {
		if(heliCard == null)
			heliCard = new HelicopterLiftCard();
		return heliCard;
	}
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for HelicopterLiftCard object.
	 */
	private HelicopterLiftCard() {
		super(TreasureCardEnum.HELICOPTER_LIFT);
	}
	
}
