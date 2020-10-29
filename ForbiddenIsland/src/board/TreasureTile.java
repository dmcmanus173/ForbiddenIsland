package board;

import enums.TileEnum;
import enums.TreasureEnum;


/**
 * Class for TreasureTile in a Forbidden Island Game.
 * This class is extended from Tile.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */

public class TreasureTile extends Tile {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private TreasureEnum treasureType;
	
	
	//===========================================================
    // Constructor
    //===========================================================
    /**
     * Constructor for a tile object.
     * @param tileName      Name of Tile
     * @param floodStatus   The status of Tile i.e. flooded, not flooded or sunken
     * @param treasureType  TreasureEnum treasureType for TreasureTile
     */
	public TreasureTile(TileEnum tileName, TreasureEnum treasureType) {
		super(tileName);
		this.treasureType = treasureType;
	}
	
	
	//===========================================================
	// Public Getters
	//===========================================================
	/**
	 * getTreasureType function, to return the treasureType
	 * @return the TreasureEnum type object treasureType.
	 */
	public TreasureEnum getTreasureType() {
		return treasureType;
	}

}
