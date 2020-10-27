package board;

import enums.FloodStatusEnum;
import enums.TileEnum;

/**
 * Abstract class for tiles on the board in the Forbidden Island Game.
 * @author Demi Oke
 * @version 1.0
 *
 */

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
	
	public Boolean shoreUp() {
		
		if(floodStatus == FloodStatusEnum.FLOODED) {
			floodStatus = FloodStatusEnum.NOT_FLOODED;
			return true;
		}
		else if(floodStatus == FloodStatusEnum.SUNKEN)
			System.out.println("This tile can't be sandbagged, it is already sunk.");
		else
			System.out.println("This tile doesn't need to be sandbagged, it isn't flooded.");
		return false;
		
	}
	
	public void flood() {
		if(floodStatus == FloodStatusEnum.NOT_FLOODED) {
			floodStatus = FloodStatusEnum.FLOODED;
			System.out.println("Island Tile is now flooded!");
		}
		else if(floodStatus == FloodStatusEnum.FLOODED) {
			floodStatus = FloodStatusEnum.SUNKEN;
			System.out.println("Island Tile is now sunk!");
		}
		
	}
	
	/*
	public String toString() {
		
		StringBuilder temp = new StringBuilder("");
		temp.append("Tile Name: " + tileName);
		temp.append("\nFlooded: " + floodStatus.toString());
		return temp.toString();
		
	}
	public Boolean compareTo(Tile otherTile) {
		
		if(this.toString() == otherTile.toString())
			return true;
		else return false;
		
	}
	*/

}
