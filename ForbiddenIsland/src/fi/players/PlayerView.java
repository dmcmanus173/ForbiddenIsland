package fi.players;

import java.util.ArrayList;

import fi.board.Board;
import fi.cards.Card;
import fi.cards.FloodDeck;
import fi.cards.TreasureDeck;
import fi.cards.WaterRiseCard;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.game.GetInput;
import fi.treasures.TreasureManager;
import fi.watermeter.WaterMeter;

/**
 * Class for GameTurnView of Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    09/12/2020
 * @version 0.1
 */
public class PlayerView {
	//===========================================================
	// Variable Setup
	//===========================================================
	private Player player;
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MIN_ACTION_NUMBER  = 0;
	private final int MAX_ACTION_NUMBER  = 9;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for PlayerGo object.
	 */
	public PlayerView(Player player) {
		this.player = player;
	}
		
	//===========================================================
	// Actions during a Player Go.
	//===========================================================
	/**
	 * doActions will get the player to do their 3 actions as part of their go.
	 */
	public int getAction(int remainingActions) {
		int actionNumber;
		System.out.println("It is "+player.getName()+"'s turn!");
		showActions(remainingActions);
		actionNumber = GetInput.getInstance().anInteger(MIN_ACTION_NUMBER, MAX_ACTION_NUMBER);
		return actionNumber;		
	}
	
	private void showActions(int remainingActions) {
		showMap();
		showLocation();
		System.out.println("You have "+remainingActions+" actions remaining. Would you like to: ");
		System.out.println(" 1. Move.                 (Costs 1 Action)"    );
		System.out.println(" 2. Shore Up.             (Costs 1 Action)"    );
		System.out.println(" 3. Give a Treasure Card. (Costs 1 Action)"    );
		System.out.println(" 4. Capture A Treasure.   (Costs 1 Action)"    );
		System.out.println(" 5. Use a Helicopter Lift Card."               );
		System.out.println(" 6. See what Treasure Cards you have."         );
		System.out.println(" 7. Remove a Treasure Card."                   );
		System.out.println(" 8. See what treasure's are claimed."          );
		System.out.println(" 9. Use a Sandbag Card."                       );
		System.out.println(" 0. End Go."                                   );
		System.out.println("Choose a number for what you would like to do.");
	}
	
	//===========================================================
	// selectObjectFromList functions
	//===========================================================
	
	private TileEnum selectTileFromList(ArrayList<TileEnum> listOfTileEnums) {
		System.out.println("Choose a Tile Number:");
		for (int i=0; i<listOfTileEnums.size(); i++) {
			System.out.println((i+1)+". "+listOfTileEnums.get(i).toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(1, listOfTileEnums.size())-1;
		TileEnum chosenTile = listOfTileEnums.get(chosenNumber);
		return chosenTile;
	}
	
	private Player selectPlayerFromList(ArrayList<Player> listOfPlayers) {
		System.out.println("Choose a player Number:");
		for (int i=0; i<listOfPlayers.size(); i++) {
			System.out.println((i+1)+". "+listOfPlayers.get(i).getName().toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(1, listOfPlayers.size())-1;
		Player chosenPlayer = listOfPlayers.get(chosenNumber);
		return chosenPlayer;
	}

	private Card selectCardFromList(ArrayList<Card> listOfCards) {
		System.out.println("Choose a Card Number:");
		for (int i=0; i<listOfCards.size(); i++) {
			System.out.println((i+1)+". "+listOfCards.get(i).toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(1, listOfCards.size())-1;
		Card chosenCard = listOfCards.get(chosenNumber);
		return chosenCard;
	}
	
	
	//===========================================================
	// Other functions
	//===========================================================
	/**
	 * showMap method will call the Board for a copy of the board map, then print it to the console.
	 */
	private void showMap() {
		Board board = Board.getInstance();
		System.out.println(board.toString());
	}
	
	private void showLocation() {
		System.out.println(player.getName() + " is on " + player.getLocation());
	}

	public void printNumberDrawnCards(int numberCards) {
		System.out.println("Drawing " + numberCards + " cards from treasure deck for "+player.getName()+".");
	}
	
	public void printPlayerCard(Card aCard) {
		System.out.println(player.getName()+" just drew a card: "+aCard.toString());
	}
	
	public void printNewLine() {
		System.out.println();
	}
	
	public void gameOver() {
		System.out.println("Game Over!");
	}
}
