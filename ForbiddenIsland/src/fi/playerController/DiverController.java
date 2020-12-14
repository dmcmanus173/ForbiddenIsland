package fi.playerController;

import java.util.ArrayList;

import fi.board.Board;
import fi.enums.TileEnum;
import fi.players.Player;

public class DiverController extends PlayerController {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for DiverController object.
	 */
	public DiverController(Player player) {
		super(player);
	}
	
	/**
	 * moveFromSunk moves a sunk Player (Diver) from the sunk tile that they are on.
	 * @param sunkPlayer (Diver), a player that is on a sunk tile.
	 * @return true if the player is able to move. Else false.
	 */
	@Override
	protected Boolean moveFromSunk(Player sunkPlayer) {
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		
		tilesPlayerCanMoveTo.addAll(Board.getInstance().getNearestTilesToTile(sunkPlayer.getLocation())); //TODO: PERHAPS THIS IS THE ONLY LINE THAT NEEDS TO BE OVERRIDEN?
		
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
