package fi.enums;

/**
 * Class holding the tile names in the Forbidden Island Game.
 * @author Demi Oke & Daniel McManus
 * @date    31/10/2020
 * @version 0.1
 *
 */

public enum TileEnum {
	FOOLS_LANDING        ("Fools' Landing",     "FL", false),
	
	TEMPLE_OF_THE_MOON   ("Temple of the Moon", "TM", true),
	TEMPLE_OF_THE_SUN    ("Temple of the Sun",  "TS", true),
	WHISPERING_GARDEN    ("Whispering Garden",  "WG", true),
	HOWLING_GARDEN       ("Howling Garden",     "HG", true),
	CAVE_OF_EMBERS       ("Cave of Embers",     "CE", true),
	CAVE_OF_SHADOWS      ("Cave of Shadows",    "CS", true),
	CORAL_PALACE         ("Coral Palace",       "CP", true),
	TIDAL_PALACE         ("Tidal Palace",       "TP", true),
	
	BRONZE_GATE          ("Bronze Gate",        "BG", false),
	SILVER_GATE          ("Silver Gate",        "SG", false),
	COPPER_GATE          ("Copper Gate",        "CG", false),
	GOLDEN_GATE          ("Golden Gate",        "GG", false),
	IRON_GATE            ("Iron Gate",          "IG", false),
	TWILIGHT_HOLLOW      ("Twilight Hollow",    "TH", false),
	LOST_LAGOON          ("Lost Lagoon",        "LL", false),
	CRIMSON_FOREST       ("Crimson Forest",     "CF", false),
	WATCHTOWER           ("Watchtower",         "WT", false),
	MISTY_MARSH          ("Misty Marsh",        "MM", false),
	CLIFFS_OF_ABANDON    ("Cliffs of Abandon",  "CA", false),
	BREAKERS_BRIDGE      ("Breakers Bridge",    "BB", false),
	OBSERVATORY          ("Observatory",        "OB", false),
	PHANTOM_ROCK         ("Phantom Rock",       "PR", false),
	DUNES_OF_DECEPTION   ("Dunes of Deception", "DD", false);

	
	private final String name;
	private final String abbr;
	private final boolean hasTreasure;
	
	
	/**
     * constructor for Tiles taking in the name associated 
     * with the tile that will be printed on the board.
     * @param str String which will be set to the name of the tile
     * @param abbr String which will be set to abbreviation of the name of the tile
     */
	private TileEnum(String str, String abbr, boolean hasTreasure) {
		this.name = str;
		this.abbr = abbr;
		this.hasTreasure = hasTreasure;
    }
	
	
	/**
    * gets name relating to tile
    * @return the name.
    */
	@Override
	public String toString() {
		return this.name;
	}
	
	
	/**
    * gets name relating to tile
    * @return the name.
    */
	public String getAbbr() {
		return this.abbr;
	}
	
	/**
    * gets name relating to tile
    * @return the name.
    */
	public boolean hasTreasure() {
		return this.hasTreasure;
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
