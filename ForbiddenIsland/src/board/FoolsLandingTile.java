package board;

import enums.FloodStatusEnum;
import enums.TileEnum;
import gameManager.GameManager;
import gameManager.TreasureManager;
import player.Player;
import player.Players;
import treasureCards.TreasureDeck;

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
		
		int totalNumOfPlayers = Players.getInstance().getNumPlayers();
		if(playersOnTile.size() ==  totalNumOfPlayers && TreasureManager.getInstance().didClaimAllTreasures() && TreasureDeck.getInstance().checkIfPlayersHaveHelicopterLift())
			GameManager.getInstance().gameWon();		
	}
	
	
	/**
    * flood method floods a tile if it is shored up and sinks a tile if it is flooded.
    */
	@Override
	public void flood() {
		if(floodStatus == FloodStatusEnum.FLOODED) {
			GameManager.getInstance().gameOver();
			System.out.println("Fools' Landing has sunk!");
		} else {
			super.flood();
		}
	}

}
