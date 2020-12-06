package fi.cards;

import fi.enums.SingleUseGameCardEnum;

/**
* Class for a WaterRiseCard in the Forbidden Island Game.
* This card is extends Card Abstract Class.
* 
* @author  Demi Oke and Daniel McManus
* @date    27/10/2020
* @version 0.1
*/

public class WaterRiseCard extends Card {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for WaterRiseCard object.
	 */
	public WaterRiseCard() {
		super(SingleUseGameCardEnum.WATER_RISE);
	}

}
