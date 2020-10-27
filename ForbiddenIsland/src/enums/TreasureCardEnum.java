package enums;

/**
 * Class holding the enumerated types of cards in the treasure deck in the game Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */
public enum TreasureCardEnum {
	TREASURE        ("Treasure Card"       ),
	SANDBAG         ("Sandbag Card"        ),
	HELICOPTER_LIFT ("Helicopter Lift Card"),
	WATER_RISE      ("Water Rise Card"     );
		
	private final String cardType;	
	
	/**
     * constructor for TreasureCardEnum
     * @param str String which will be set to the name of the type of treasure card
     */
	private TreasureCardEnum(String str) {
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
