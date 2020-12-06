package fi.enums;


/**
* Class holding names of other types of cards in the
* Forbidden Island Game.
* @author Demi Oke & Daniel McManus
* @date    31/10/2020
* @version 0.1
*
*/
public enum SingleUseGameCardEnum {
	
	SANDBAG         ("Sandbag Card"        ),
	HELICOPTER_LIFT ("Helicopter Lift Card"),
	WATER_RISE      ("Water Rise Card"     );
		
	private final String cardType;	
	
	/**
     * constructor for TreasureCardEnum
     * @param str String which will be set to the name of the type of treasure card
     */
	private SingleUseGameCardEnum(String str) {
		cardType = str; 
    }
		
	/**
     * returns the string cardType of the TreasureCardEnum
     * @return the cardType.
     */
	@Override
	public String toString() {
		return this.cardType;
	}

}
