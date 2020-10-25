package board;

import enums.FloodStatusEnum;
import enums.TileEnum;

public abstract class Tile {
	
	//===========================================================
    // Variable Setup
    //===========================================================
	protected TileEnum tileName;
	protected FloodStatusEnum floodStatus;
	
	
	//===========================================================
    // Constructor
    //===========================================================
    /**
     * Constructor for a tile object.
     * @param tileName     Name of Tile
     * @param floodStatus  The status of Tile i.e. flooded, not flooded or sunken
     */
	public Tile(TileEnum tileName, FloodStatusEnum floodStatus) {
		this.tileName = tileName;
		this.floodStatus = floodStatus;
	}
	
	
	//===========================================================
    // Public Getters
    //===========================================================
    /**
     * getter to return the name the Tile
     * @return the name of the Tile.
     */
	public TileEnum getTileName() {
		return this.tileName;
	}
	
	/**
    * getter to return the flood status Tile
    * @return the flood status of the Tile.
    */
	public FloodStatusEnum getFloodStatus() {
		return this.floodStatus;
	}

}
