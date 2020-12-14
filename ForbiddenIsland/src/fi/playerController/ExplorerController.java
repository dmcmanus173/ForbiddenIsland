package fi.playerController;

import java.util.ArrayList;

import fi.board.Board;
import fi.enums.TileEnum;
import fi.players.Player;

public class ExplorerController extends PlayerController {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for ExplorerController object.
	 */
	public ExplorerController(Player player) {
		super(player);
	}
	
	/**
	 * handleSunk moves this player from their current tile because it is sunk.
	 */
	@Override
	public Boolean handlePlayerSunk() {
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		
		tilesPlayerCanMoveTo.addAll(board.getTilesAroundTile(player.getLocation(), false));
		
		return moveFromSunk(tilesPlayerCanMoveTo);
		
	}
}
