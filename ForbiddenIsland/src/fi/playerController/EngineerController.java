package fi.playerController;

import fi.board.Board;
import fi.players.Player;

public class EngineerController extends PlayerController {

	public EngineerController(Player player) {
		super(player);
	}
	
	/**
	 * handleShoreUp is the handle to be called as a player action. It will call doAShoreUp, to shore up a tile, if possible.
	 * If the player is an Engineer, they can shoreUp 2 tiles for one action. This function will facilitate this.
	 */
	@Override
	protected void handleShoreUp() {
		Boolean didShoreUp = doAShoreUp();
		if(!Board.getInstance().getTilesToShoreUpAround(player.getLocation()).isEmpty()) {
			if(playerView.engineerShoreUpOption())
				doAShoreUp();
		}
		if(didShoreUp)
			decreaseRemainingActions();
	}
	
}
