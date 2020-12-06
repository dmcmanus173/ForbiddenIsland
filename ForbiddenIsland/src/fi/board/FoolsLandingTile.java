package fi.board;

import fi.enums.TileEnum;
import fi.players.Player;

/**
 * Class for the Fools' Landing tile in the Forbidden Island Game.
 * @author Demi Oke and Daniel McManus
 * @date 17/11/2020
 * @version 0.1
 */
public class FoolsLandingTile extends Tile {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * FoolsLandingTile constructor. 
	 * Sets up the tile FoolsLanding
	 */
	public FoolsLandingTile(TileEnum tileName) {
		super(tileName);
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
    * addPlayerToTile method adds a player to the set of players on tile.
    * Also checks to see if a winning condition is met.
    * @param Player player that is to be added to tile.
    */
	@Override
	public void addPlayerToTile(Player player) {
		super.addPlayerToTile(player);
		//loop throgh players and check if they have a helicopter lift card. If they do notify game manager			
	}
	

}

