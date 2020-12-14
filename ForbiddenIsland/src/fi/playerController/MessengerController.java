package fi.playerController;

import java.util.ArrayList;

import fi.players.Player;
import fi.players.Players;

public class MessengerController extends PlayerController {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for MessengerController object.
	 */
	public MessengerController(Player player) {
		super(player);
	}
	
	/**
	 * Function to manage a player being able to give away treasure cards.
	 */
	@Override
	protected void handleGiveTreasureCard() {
		ArrayList<Player> possiblePlayers = new ArrayList<Player>();           // Players that can be sent a card
		
		possiblePlayers.addAll(Players.getInstance().getPlayersExcept(player));
		if(possiblePlayers.isEmpty()) {
			playerView.playersNotOnSameTile();
			return;
		}
		
		toGiveTreasureCard(possiblePlayers);
	}
}
