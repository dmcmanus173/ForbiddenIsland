package roles;

import java.util.ArrayList;

import board.Board;
import board.Tile;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;

/**
 * Class for a Player Role Explorer in Forbidden Island
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
public class Explorer extends Role {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Explorer
	 */
	public Explorer() {
		super(AdventurerEnum.EXPLORER);
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * startPosition method will return the tile that a player is to start on.
	 * @return Tile startPosition, where the player for a given role should start the game.
	 */
	public Tile startPosition() {
		// Explorer - Copper Gate
		TileEnum tileName = TileEnum.COPPER_GATE;
		Tile location = null;
		
		ArrayList<Tile> tiles = Board.getInstance().getIslandTiles();
		for(Tile tile : tiles) {
			if( tile.getTileName().equals(tileName) )
				location = tile;
		}
		
		if(location == null)
    		throw new RuntimeException("startPosition() in Explorer can not find correct tile Copper Gate.");
		
		return location;
	}
	
	/**
	 * getTilesForIfOnSunk method gets the tiles that a player can move to if they're on a sunken tile.
	 * This method allows the player to move to the tile that is beside the one the player is orthogonal and diagonal to it.
	 * @param location, the Tile location the player is  on.
	 */
	@Override
	public ArrayList<Tile> getTilesForIfOnSunk(Tile location) {
		ArrayList<Tile> potentialTiles = new ArrayList<>();
		potentialTiles.addAll( Board.getInstance().getTilesAroundTile(location, false) );
		return potentialTiles;
	}

}
