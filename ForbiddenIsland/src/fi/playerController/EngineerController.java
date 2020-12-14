package fi.playerController;

import java.util.ArrayList;

import fi.board.Board;
import fi.enums.TileEnum;
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
		Board board = Board.getInstance();
		ArrayList<TileEnum> tilesPlayerCanShoreUp = board.getTilesToShoreUpAround(player.getLocation());
		
		Boolean didShoreUp = doAShoreUp(tilesPlayerCanShoreUp);
		tilesPlayerCanShoreUp = board.getTilesToShoreUpAround(player.getLocation());
		if(!tilesPlayerCanShoreUp.isEmpty()) {
			if(playerView.engineerShoreUpOption())
				doAShoreUp(tilesPlayerCanShoreUp);
		}
		if(didShoreUp)
			decreaseRemainingActions();
	}
	
}
