package fi.cards;

import fi.enums.SingleUseGameCardEnum;

/**
* Class for a SandbagCard in the Forbidden Island Game.
* This card is extends Card Abstract Class.
* 
* @author  Demi Oke and Daniel McManus
* @date    27/10/2020
* @version 0.1
*/

public class SandbagCard extends Card {
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for SandbagCard object.
	 */
	public SandbagCard() {
		super(SingleUseGameCardEnum.SANDBAG);
	}
	
}

