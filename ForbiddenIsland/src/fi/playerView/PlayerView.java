package fi.playerView;

import java.util.ArrayList;

import fi.cards.Card;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.game.GetInput;
import fi.gameView.GameView;
import fi.players.Player;

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
		GameView.getInstance().showMap();
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
	public TileEnum selectTileFromList(ArrayList<TileEnum> listOfTileEnums) {
		System.out.println("Choose a Tile Number:");
		for (int i=0; i<listOfTileEnums.size(); i++) {
			System.out.println((i+1)+". "+listOfTileEnums.get(i).toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(1, listOfTileEnums.size())-1;
		TileEnum chosenTile = listOfTileEnums.get(chosenNumber);
		return chosenTile;
	}
	
	public Player selectPlayerFromList(ArrayList<Player> listOfPlayers) {
		System.out.println("Choose a player Number:");
		for (int i=0; i<listOfPlayers.size(); i++) {
			System.out.println((i+1)+". "+listOfPlayers.get(i).getName().toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(1, listOfPlayers.size())-1;
		Player chosenPlayer = listOfPlayers.get(chosenNumber);
		return chosenPlayer;
	}

	public Card selectCardFromList(ArrayList<Card> listOfCards) {
		System.out.println("Choose a Card Number:");
		for (int i=0; i<listOfCards.size(); i++) {
			System.out.println((i+1)+". "+listOfCards.get(i).toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(1, listOfCards.size())-1;
		Card chosenCard = listOfCards.get(chosenNumber);
		return chosenCard;
	}
	
	
	//===========================================================
	// Treasure Card functions
	//===========================================================
	public void printNumberDrawnCards(int numberCards) {
		System.out.println("Drawing " + numberCards + " cards from treasure deck for "+player.getName()+".");
	}
	
	public void printPlayerCard(Card aCard) {
		System.out.println(player.getName()+" just drew a card: "+aCard.toString());
	}
	
	/**
	 * showTreasureCards will show the TreasureCards a player has in their Hand.
	 */
	public void showTreasureCards() {
		System.out.println(player.cardsToString());
	}
	
	public Player selectPlayerWithSandbag(ArrayList<Player> players) {
		System.out.println("Select a player that wants to use their Sandbag Card:");
		Player aPlayer = selectPlayerFromList(players);
		System.out.println(aPlayer.getName()+" is using their Sandbag card.");
		return aPlayer;
	}
	
	public void playerHasNoCards() {
		System.out.println( player.getName() + " does not have any cards to give");
	}
	
	public Card selectCardToDiscard() {
		Card cardToDiscard;
		System.out.println(player.getName()+ "'s hand is now full.\nSelect a card to discard from hand:");
		cardToDiscard = selectCardFromList(player.getCardsInPlayersHand());
		return cardToDiscard;
	}
	
	public void playerIsMessengerGiveCards() {
		System.out.println(player.getName() + " is a Messenger and can give a card to anyone in the game.");
	}
	
	public void noSandbagCards() {
		System.out.println("None of the players have a SandBag Card to use.");
	}
	
	public void noHelicopterLiftCards() {
		System.out.println("None of the players have a HelicopterLift Card to use.");
	}
	
	//===========================================================
	// Player Movement functions
	//===========================================================
	public void showLocation() {
		System.out.println(player.getName() + " is on " + player.getLocation());
	}
	
	public void playerHasMoved() {
		System.out.println(player.getName()+" has moved to "+player.getLocation());
	}
	
	public void playerCantMove() {
		System.out.println(player.getName()+" is unable to move.");
	}
	
	public void selectPlayerToMove() {
		System.out.println("Selecting players to move...");
	}
	
	public Boolean selectThisPlayerToMove(Player aPlayer) {
		System.out.println("Would you like to move "+aPlayer.getName()+" with Helicopter Lift?");
		System.out.println("1. Yes.");
		System.out.println("2. No.");
		if( GetInput.getInstance().anInteger(1,2) == 1 )
			return true;
		return false;
	}
	
	public void playersNotOnSameTile() {
		System.out.println("There are no players on the same tile as " + player.getName() + ".");
	}

	//===========================================================
	// ShoreUp
	//===========================================================
	public Boolean engineerShoreUpOption() {
		System.out.println(player.getName() + " is an Engineer and can shoreUp two tiles for the cost of 1 action.");
		System.out.println("would you like to shore up another tile?");
		System.out.println("1. Yes.");
		System.out.println("2. No. ");
		if(GetInput.getInstance().anInteger(1, 2) == 1) {
			return true;
		}
		return false;
	}
	
	//===========================================================
	// Helicopter Lift
	//===========================================================
	public Player choosePlayerWithHelicopterLift(ArrayList<Player> players) {
		Player chosenPlayer;
		System.out.println("Choose a player who wants to use their HelicopterLift.");
		chosenPlayer = selectPlayerFromList(players);
		return chosenPlayer;
	}
	
	public void noPlayersToHelicopterLift() {
		System.out.println("Didn't select any players to move. Won't use the Helicopter Lift Card.");
	}
	
	public void noTilesToMoveTo() {
		System.out.println("There are no tiles to move to.");
	}
	
	//===========================================================
	// Collect a Treasure
	//===========================================================
	public void playerCollectedTreasure(TreasureEnum aTreasure) {
		System.out.println("Congratulations. " + player.getName() + " has just collected " + aTreasure.toString() + ".");
	}
	
	public void cantCollectTreasure() {
		System.out.println(player.getName() + " can not collect a treasure.");
	}
	

	
	//===========================================================
	// Sunk Player
	//===========================================================
	public TileEnum tileToMoveFromSunk(ArrayList<TileEnum> tilesPlayerCanMoveTo) {
		TileEnum chosenTile;
		System.out.println(player.getName()+" must move as they're on a sunken Tile!");
		GameView.getInstance().showMap();
		System.out.println(player.getName()+" is on "+player.getLocation().toString());
		chosenTile = selectTileFromList(tilesPlayerCanMoveTo);
		return chosenTile;
	}
	
}
