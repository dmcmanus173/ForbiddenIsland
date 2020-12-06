package roles;

import java.util.ArrayList;

import board.Board;
import board.Tile;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;

/**
 * Class for a Player Role Pilot in Forbidden Island
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
public class Pilot extends Role{

	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Pilot
	 */
	public Pilot() {
		super(AdventurerEnum.PILOT);
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
		// Pilot - Fools' Landing
		TileEnum tileName = TileEnum.FOOLS_LANDING;
		Tile location = null;
		ArrayList<Tile> tiles = Board.getInstance().getIslandTiles();
		for(Tile tile : tiles) {
			if( tile.getTileName().equals(tileName) )
				location = tile;
		}
		if(location == null)
    		throw new RuntimeException("startPosition() in Pilot can not find correct tile Fools' Landing.");
		return location;
	}
	
	/**
	 * getTilesForIfOnSunk method gets the tiles that a player can move to if they're on a sunken tile.
	 * @param location, the Tile location the player is  on.
	 */
	@Override
	public ArrayList<Tile> getTilesForIfOnSunk(Tile location) {
		ArrayList<Tile> potentialTiles = new ArrayList<>();
		potentialTiles.addAll( Board.getInstance().getUnsunkenTiles() );
		return potentialTiles;
	}

}
