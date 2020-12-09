package fi.enums;

/**
 * Class holding the Adventurer types in the Forbidden Island Game.
 * @author Demi Oke & Daniel McManus
 * @date    31/10/2020
 * @version 0.1
 *
 */

public enum AdventurerEnum {
	
	NAVIGATOR  ("Navigator", TileEnum.GOLDEN_GATE),
	DIVER      ("Diver",     TileEnum.IRON_GATE),
	MESSENGER  ("Messenger", TileEnum.SILVER_GATE),
	EXPLORER   ("Explorer",  TileEnum.COPPER_GATE),
	PILOT      ("Pilot",     TileEnum.FOOLS_LANDING),
	ENGINEER   ("Engineer",  TileEnum.BRONZE_GATE);
	
	private final String type;
	private final TileEnum startingTileName;
	
	/**
     * constructor for Adventurer taking in the type associated 
     * with the Adventurer that will be printed on the board.
     * @param str String which will be set to the type of the adventurer.
     */
	private AdventurerEnum(String str, TileEnum startingTileName) {
		type = str; 
		this.startingTileName = startingTileName;
    }
	
	/**
     * gets type relating to type of adventurer
     * @return the name.
     */
	@Override
	public String toString() {
		return this.type;
	}
	
	/**
     * gets starting tile of the adventurer 
     * when the game begins
     * @return the Tile name of the starting tile.
     */
	public TileEnum getStartingTileName() {
		return this.startingTileName;
	}

}