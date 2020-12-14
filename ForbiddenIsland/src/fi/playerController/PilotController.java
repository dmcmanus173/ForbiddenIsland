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
	 * handleSunk moves this player from their current tile because it is sunk.
	 */
	@Override
	public Boolean handlePlayerSunk() {
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		
		tilesPlayerCanMoveTo.addAll(board.getUnsunkenTiles()); 
		
		return moveFromSunk(tilesPlayerCanMoveTo);
		
	}
	
}
