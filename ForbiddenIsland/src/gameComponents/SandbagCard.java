package gameComponents;

import enums.TreasureCardEnum;

/**
 * Class for SandbagCard in a Forbidden Island Treasure Deck.
 * This class is extended from AbstractTreasureCard.
 * Singleton method used as only need one reference to this class.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    15/11/2020
 * @version 0.1
 */
public class SandbagCard extends AbstractTreasureCard {

	//===========================================================
	// Variable Setup
	//===========================================================
	private static SandbagCard sandbag; 
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of Sandbag card
     * @return sandbadCard. singleton SandbagCard object.
     */
	public static SandbagCard getInstance() {
		if(sandbag == null)
			sandbag = new SandbagCard();
		return sandbag;
	}
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for SandbagCard object.
	 */
	private SandbagCard() {
		super(TreasureCardEnum.SANDBAG);
	}
	
}
