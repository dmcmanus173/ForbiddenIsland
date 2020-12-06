package fi.roles;

import java.util.ArrayList;

import fi.board.Board;
import fi.board.Tile;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;

/**
 * Class for a Player Role Diver in Forbidden Island
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
public class Diver extends Role{

	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Diver
	 */
	public Diver() {
		super(AdventurerEnum.DIVER);
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * startPosition method will return the tile that a player is to start on.
	 * @return Tile startPosition, where the player for a given role should start the game.
	 */
	@Override
	public Tile startPosition() {
		// Diver - Iron Gate
		TileEnum tileName = TileEnum.IRON_GATE;
		Tile location = Board.getInstance().getTileWithName(tileName);
		if(location == null)
    		throw new RuntimeException("startPosition() in Diver can not find correct tile Iron Gate.");
		
		return location;
	}
	
	/**
	 * getTilesForIfOnSunk method gets the tiles that a player can move to if they're on a sunken tile.
	 * @param location, the Tile location the player is  on.
	 */
	@Override
	public ArrayList<Tile> getTilesForIfOnSunk(Tile location) {
		ArrayList<Tile> potentialTiles = new ArrayList<>();
		potentialTiles.addAll( Board.getInstance().getNearestTilesToTile(location) );
		return potentialTiles;
	}
	
	
	
}
