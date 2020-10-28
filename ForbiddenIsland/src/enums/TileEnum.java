package enums;

import java.util.Optional;
/**
 * Class holding the tile names in the Forbidden Island Game.
 * @author Demi Oke
 * @version 1.0
 *
 */

public enum TileEnum {
	FOOLS_LANDING        ("Fools Landing"),
	
	TEMPLE_OF_THE_MOON   ("Temple of the Moon"),
	TEMPLE_OF_THE_SUN    ("Temple of the Sun"),
	WHISPERING_GARDEN    ("Whispering Garden"),
	HOWLING_GARDEN       ("Howling Garden"),
	CAVE_OF_EMBERS       ("Cave of Embers"),
	CAVE_OF_SHADOWS      ("Cave of Shadows"),
	CORAL_PALACE         ("Coral Palace"),
	TIDAL_PALACE         ("Tidal Palace"),
	
	BRONZE_GATE          ("Bronze Gate"),
	SILVER_GATE          ("Silver Gate"),
	COPPER_GATE          ("Copper Gate"),
	GOLDEN_GATE          ("Golden Gate"),
	IRON_GATE            ("Iron Gate"),
	TWILIGHT_HOLLOW      ("Twilight Hollow"),
	LOST_LAGOON          ("Lost Lagoon"),
	CRIMSON_FOREST       ("Crimson Forest"),
	WATCHTOWER           ("Watchtower"),
	MISTY_MARSH          ("Misty Marsh"),
	CLIFFS_OF_ABANDON    ("Cliffs of Abandon"),
	BREAKERS_BRIDGE      ("Breakers Brisge"),
	OBSERVATORY          ("Observatory"),
	PHANTOM_ROCK         ("Phantom Rock"),
	DUNES_OF_DECEPTION   ("Dunes of Deception");

	
	private final String name;
	
	
	/**
     * constructor for Tiles taking in the name associated 
     * with the tile that will be printed on the board.
     * @param str String which will be set to the name of the tile
     */
	private TileEnum(String str) {
		name = str; 
    }
	
	
	/**
    * gets name relating to tile
    * @return the name.
    */
	@Override
	public String toString() {
		return this.name;
	}
	
	/*
	// Class-level test
	public static void main(String[] args) {
		
		for (TileEnum tile : TileEnum.values()) { 
		    System.out.println(tile); 
		}
		
	}
	*/



}
