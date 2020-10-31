package enums;

/**
 * Class holding the tile names in the Forbidden Island Game.
 * @author Demi Oke & Daniel McManus
 * @date    31/10/2020
 * @version 0.1
 *
 */

public enum TileEnum {
	FOOLS_LANDING        ("Fools Landing", "FL"),
	
	TEMPLE_OF_THE_MOON   ("Temple of the Moon", "TM"),
	TEMPLE_OF_THE_SUN    ("Temple of the Sun", "TS"),
	WHISPERING_GARDEN    ("Whispering Garden", "WG"),
	HOWLING_GARDEN       ("Howling Garden", "HG"),
	CAVE_OF_EMBERS       ("Cave of Embers", "CE"),
	CAVE_OF_SHADOWS      ("Cave of Shadows", "CS"),
	CORAL_PALACE         ("Coral Palace", "CP"),
	TIDAL_PALACE         ("Tidal Palace", "TP"),
	
	BRONZE_GATE          ("Bronze Gate", "BG"),
	SILVER_GATE          ("Silver Gate", "SG"),
	COPPER_GATE          ("Copper Gate", "CG"),
	GOLDEN_GATE          ("Golden Gate", "GG"),
	IRON_GATE            ("Iron Gate", "IG"),
	TWILIGHT_HOLLOW      ("Twilight Hollow", "TH"),
	LOST_LAGOON          ("Lost Lagoon", "LL"),
	CRIMSON_FOREST       ("Crimson Forest", "CF"),
	WATCHTOWER           ("Watchtower", "WT"),
	MISTY_MARSH          ("Misty Marsh", "MM"),
	CLIFFS_OF_ABANDON    ("Cliffs of Abandon", "CA"),
	BREAKERS_BRIDGE      ("Breakers Brisge", "BB"),
	OBSERVATORY          ("Observatory", "OB"),
	PHANTOM_ROCK         ("Phantom Rock", "PR"),
	DUNES_OF_DECEPTION   ("Dunes of Deception", "DD");

	
	private final String name;
	private final String abbr;
	
	
	/**
     * constructor for Tiles taking in the name associated 
     * with the tile that will be printed on the board.
     * @param str String which will be set to the name of the tile
     * @param abbr String which will be set to abbreviation of the name of the tile
     */
	private TileEnum(String str, String abbr) {
		name = str;
		this.abbr = abbr;
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
	
	
	
	/*
	// Class-level test
	public static void main(String[] args) {
		
		for (TileEnum tile : TileEnum.values()) { 
		    System.out.println(tile); 
		}
		
	}
	*/



}
