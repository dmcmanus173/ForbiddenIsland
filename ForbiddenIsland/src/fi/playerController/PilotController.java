package fi.playerController;

import java.util.ArrayList;

import fi.board.Board;
import fi.enums.TileEnum;
import fi.players.Player;

public class PilotController extends PlayerController {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for PilotController object.
	 */
	public PilotController(Player player) {
		super(player);
	}
	
	/**
	 * moveFromSunk moves a sunk Player (Pilot) from the sunk tile that they are on.
	 * @param sunkPlayer (Pilot), a player that is on a sunk tile.
	 * @return true if the player is able to move. Else false.
	 */
	@Override
	private Boolean moveFromSunk(Player sunkPlayer) {
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		
		tilesPlayerCanMoveTo.addAll(board.getUnsunkenTiles()); //TODO: PERHAPS THIS IS THE ONLY LINE THAT NEEDS TO BE OVERRIDEN?
		
		if(tilesPlayerCanMoveTo.isEmpty()) {
			playerView.playerCantMove();
			return false;
		}
		
		chosenTile = playerView.tileToMoveFromSunk(sunkPlayer, tilesPlayerCanMoveTo);
		sunkPlayer.move(chosenTile);
		playerView.playerHasMovedFromSunk(sunkPlayer);
		return true;
	}
	
}
