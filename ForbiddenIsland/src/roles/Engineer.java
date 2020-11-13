package roles;

import java.util.ArrayList;

import board.Board;
import board.Tile;
import enums.AdventurerEnum;
import enums.TileEnum;

/**
 * Class for a Player Role Engineer in Forbidden Island
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
public class Engineer extends Role {

	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Engineer
	 */
	public Engineer() {
		super(AdventurerEnum.ENGINEER);
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
		// Engineer - Bronze Gate
		TileEnum tileName = TileEnum.BRONZE_GATE;
		Tile location = null;
		
		if ( !tileName.equals(null) ) {
			ArrayList<Tile> tiles = Board.getInstance().getIslandTiles();
			for(Tile tile : tiles) {
				if( tile.getTileName().equals(tileName) )
					location = tile;
			}
		}
		return location;
	}
	
	
}
