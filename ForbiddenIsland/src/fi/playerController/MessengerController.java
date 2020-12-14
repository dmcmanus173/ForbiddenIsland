package fi.playerController;

import java.util.ArrayList;

import fi.board.Board;
import fi.cards.Card;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;
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
	 * Function to manage a player (Messenger) being able to give away treasure cards.
	 */
	@Override
	protected void handleGiveTreasureCard() {
		Player playerToGiveCardTo;
		Card cardToGive;
		ArrayList<Player> possiblePlayers = new ArrayList<Player>();           // Players that can be sent a card
		ArrayList<Player> playersThatCanAcceptACard = new ArrayList<Player>(); // Players that can receive a card (Hand is not full)
		
		if(player.getCardsInPlayersHand().isEmpty()) {
			playerView.playerHasNoCards();
			return;
		}
		
		playerView.playerIsMessengerGiveCards();
		possiblePlayers = Players.getInstance().getPlayersExcept(player); //TODO: PERHAPS THIS IS THE ONLY LINE THAT NEEDS TO BE OVERRIDEN?
		
		
		// Getting players from possiblePlayers without full hand
		possiblePlayers.forEach((possiblePlayer) -> {
			if(!possiblePlayer.handIsFull()) {
				playersThatCanAcceptACard.add(possiblePlayer);
			}
		});
		if(playersThatCanAcceptACard.isEmpty()) {
			gameView.allHandsAreFull();
			return;
		}
		
		playerToGiveCardTo = playerView.selectPlayerFromList(playersThatCanAcceptACard);
		cardToGive = playerView.selectCardFromList(player.getCardsInPlayersHand());
		
		player.giveTreasureCard(playerToGiveCardTo, cardToGive);
		decreaseRemainingActions();
	}
}
