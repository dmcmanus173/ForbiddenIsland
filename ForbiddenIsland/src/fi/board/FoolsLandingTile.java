package fi.board;

import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.game.GameOverObserver;
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
	
	@Override
	public FloodStatusEnum flood() {
		if (super.flood() == FloodStatusEnum.SUNKEN) {
			GameOverObserver.getInstance().FoolsLandingTileDidSink();
		}
		return FloodStatusEnum.SUNKEN;
	}
	

}

