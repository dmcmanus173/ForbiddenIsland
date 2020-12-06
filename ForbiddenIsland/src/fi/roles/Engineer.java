package fi.roles;

import fi.board.Board;
import fi.board.Tile;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;
import gameManager.GetInput;
import player.PlayerView;

/**
 * Class for a Player Role Engineer in Forbidden Island
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    17/11/2020
 * @version 0.2
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
		Tile location = Board.getInstance().getTileWithName(tileName);
		
		if(location == null)
    		throw new RuntimeException("startPosition() in Engineer can not find correct tile Bronze Gate.");
		
		return location;
	}
	
	/**
	 * shoreUp method will call the shoreUpContent function with the correct options dependent on the role calling it.
	 * Engineer may shoreUp two islands.
	 * @return Boolean true if a tile has been shoredUp.
	 */
	@Override
	public Boolean shoreUp(PlayerView aPlayer) {
		Boolean didShoreUp;
		didShoreUp = aPlayer.shoreUpContent();
		if(didShoreUp) {
			System.out.println("The "+super.getRole().name()+" can shoreUp two tiles in one action.");
			System.out.println("Would you like to shoreUp another tile?");
			System.out.println("1. Yes.");
			System.out.println("2. No.");
			if( GetInput.getInstance().anInteger(1, 2)==1 )
				aPlayer.shoreUpContent();
		}
		return didShoreUp;
	}
}
