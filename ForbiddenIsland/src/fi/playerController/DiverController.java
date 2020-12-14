package fi.playerController;

import java.util.ArrayList;

import fi.board.Board;
import fi.enums.TileEnum;
import fi.players.Player;

public class DiverController extends PlayerController {

	public DiverController(Player player) {
		super(player);
	}
	
	@Override
	private Boolean moveFromSunk(Player sunkPlayer) {
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		
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
