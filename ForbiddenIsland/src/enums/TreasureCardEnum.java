package enums;

public enum TreasureCardEnum {
	/**
	 * Enumerator class containing the types of Treasure Cards that the treasure deck contains
	 * @author Daniel McManus
	 * @version 0.1
	 *
	 */
	
	TREASURE        ("Treasure Card"       ),
	SANDBAG         ("Sandbag Card"        ),
	HELICOPTER_LIFT ("Helicopter Lift Card"),
	WATER_RISE      ("Water Rise Card"     );
		
	private final String name;
				
	/**
     * constructor for Treasures taking in the name associated 
     * with the treasure that will be printed on the board.
     * @param str String which will be set to the name of the treasure
     */
	private TreasureCardEnum(String str) {
		name = str; 
    }
		
	/**
     * gets name relating to treasure
     * @return the name.
     */
	@Override
	public String toString() {
		return this.name;
	}
		
	
}
