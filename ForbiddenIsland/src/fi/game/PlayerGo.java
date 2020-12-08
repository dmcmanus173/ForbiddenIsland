package fi.game;

import java.util.ArrayList;

import fi.board.Board;
import fi.cards.Card;
import fi.cards.FloodDeck;
import fi.cards.TreasureDeck;
import fi.cards.WaterRiseCard;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.players.Player;
import fi.players.Players;
import fi.treasures.TreasureManager;
import fi.watermeter.WaterMeter;


public class PlayerGo {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private Player player;
	private int remainingActions;
	private boolean goHasEnded;
	private boolean gameOver;
	
	private ArrayList<TileEnum> floodedTilesAtEndOfGo;
	private ArrayList<TileEnum> sunkenTilesAtEndOfGo;
	private ArrayList<Player> sunkenPlayersAtEndOfGo;
	
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MAX_ACTIONS_PER_GO = 3;
	private final int MIN_ACTION_NUMBER = 0;
	private final int MAX_ACTION_NUMBER = 10;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for PlayerGo object.
	 */
	public PlayerGo(Player player) {
		this.player = player;
		this.remainingActions = MAX_ACTIONS_PER_GO;
		this.goHasEnded = false;
		floodedTilesAtEndOfGo = new ArrayList<TileEnum>();
		sunkenTilesAtEndOfGo = new ArrayList<TileEnum>();
		sunkenPlayersAtEndOfGo = new ArrayList<Player>();
	}
	
	
	private void decreaseRemainingActions() {
		remainingActions -= 1;
		if(remainingActions == 0) {
			goHasEnded = true;
		}
	}
	
	
	public boolean doRound() {
		doGo();
		drawCardsFromTreasureDeck();
		gameOver = drawCardsFromFloodDeck();
		if(gameOver)
			System.out.println("Game Over!");
		return gameOver;
	}
	
	
	private void drawCardsFromTreasureDeck() {
		TreasureDeck treasureDeck = TreasureDeck.getInstance();
		ArrayList<Card> treasureCards = treasureDeck.drawCards();
		
		
		ArrayList<Card> treasureCardsPlayerCanAddToHand = new ArrayList<Card>();
		System.out.println("Drawing " + treasureCards.size() + " cards from treasure deck for "+player.getName()+".");
	
		treasureCards.forEach((card) -> {
			System.out.println(player.getName()+" just drew a card: "+card.toString());
			if(card instanceof WaterRiseCard) {
				WaterMeter.getInstance().increaseWaterMeter();
				int currentLevel = WaterMeter.getInstance().getCurrentLevel();
				System.out.println("The water meter has been increased to " + currentLevel + ".");
				System.out.println("All players will now have to take " + currentLevel + "FloodCards at the end of their go!");
				treasureDeck.discardCard(card);
			} else {
				putCardInPlayersHand(card);
			}
		});
	}
	
	public void drawCardsFromTreasureDeckToStart() {
		TreasureDeck treasureDeck = TreasureDeck.getInstance();
		ArrayList<Card> treasureCards = treasureDeck.drawCards();
		
		System.out.println("Drawing " + treasureCards.size() + " cards from treasure deck for "+player.getName()+".");
	
		treasureCards.forEach((card) -> {
			if(card instanceof WaterRiseCard) {
				treasureDeck.discardCard(card);
			} else {
				System.out.println(player.getName()+" just drew a card: "+card.toString());
				putCardInPlayersHand(card);
			}
		});
	}
	
	private void putCardInPlayersHand(Card card) {
		if(player.handIsFull()) {
			Card cardToDiscard;
			player.collectTreasureCard(card);
			System.out.println(player.getName()+ "'s hand is now full.\nSelect a card to discard from hand:");
			cardToDiscard = selectCardFromList(player.getCardsInPlayersHand());
			player.discardCard(cardToDiscard);
		} 
		else
			player.collectTreasureCard(card);
	}
	
	
	private boolean drawCardsFromFloodDeck() {
		ArrayList<TileEnum> tilesToFlood = FloodDeck.getInstance().getTilesToFlood(false);
		Board board = Board.getInstance();
		// check with treasure manager if treasuresAreAvailableToCollect
		tilesToFlood.forEach((tileEnum) -> {
			board.floodTile(tileEnum);
			sunkenPlayersAtEndOfGo.addAll( board.getPlayersFromTile(tileEnum) );
			//TODO move sunken players
			
			
		});
		return false;
	}
	
	
	private void doGo() {
		goHasEnded = false;
		int actionNumber;
		System.out.println("It is "+player.getName()+"'s turn!");
		 
		while(!goHasEnded) {
			showActions();
			actionNumber = GetInput.getInstance().anInteger(MIN_ACTION_NUMBER, MAX_ACTION_NUMBER);
			handleAction(actionNumber);
		}
		
	}
	
	private void showActions() {
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
		System.out.println(" 9. Print the map."                            );
		System.out.println("10. Print player location."                    );
		System.out.println(" 0. End Go."                                   );
		System.out.println("Choose a number for what you would like to do.");
	}
	
	private void handleAction(int actionNumber) {
		switch(actionNumber) {
		case 0:
			endGo();
			break;
		case 1:
			handleMove();
			break;
		case 2:
			handleShoreUp();
			break;
		case 3:
			handleGiveTreasureCard();
			break;
		case 4:
			handleCaptureTreasure();
			break;
		case 5:
			handleHelicopterLift();
			break;
		case 6:
			showTreasureCards();
			break;
		case 7:
			handleDiscardTreasureCard();
			break;
		case 8:
			showClaimedTreasures();
			break;
		case 9:
			showMap();
			break;
		case 10:
			showLocation();
			break;
		default:
			throw new RuntimeException("Attempting to select a number outside of range.");	
		}
	}
	
	private void endGo() {
		goHasEnded = true;
	}
	
	private void handleMove() {
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		tilesPlayerCanMoveTo.addAll(board.getTilesAroundTile(player.getLocation(), true));
		if(tilesPlayerCanMoveTo.isEmpty()) {
			System.out.println(player.getName()+" is unable to move.");
			return;
		}
		chosenTile = selectTileFromList(tilesPlayerCanMoveTo);
		player.move(chosenTile);
		System.out.println(player.getName()+" has moved to "+player.getLocation());
		decreaseRemainingActions();
	}
	
	/**
	 * doAShoreUp method is a function to be called by handleShoreUp.
	 * It will facilitate the shoring-up of one tile near player.
	 * @return Boolean true if it has shoredUp a tile. Else false.
	 */
	private Boolean doAShoreUp() {
		Player playerWithSandbag = null;
		if(player.hasSandBagCard()) {
			playerWithSandbag = player;
		}
		else {
			ArrayList<Player> otherPlayers = Players.getInstance().getPlayersExcept(player);
			for(Player otherPlayer : otherPlayers) {
				if(otherPlayer.hasSandBagCard()) {
					playerWithSandbag = otherPlayer;
					break;
				}
			}
		}
		if(playerWithSandbag == null) {
			System.out.println("None of the players have a SandBag Card for "+player.getName()+" to use.");
			return false;
		}
	
		Board board = Board.getInstance();
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanShoreUp = new ArrayList<TileEnum>();
		tilesPlayerCanShoreUp.addAll(board.getTilesToShoreUpAround(player.getLocation()));
		if(tilesPlayerCanShoreUp.isEmpty()) {
			System.out.println(" There are no flooded tiles on the board!");
			return false;
		}
		chosenTile = selectTileFromList(tilesPlayerCanShoreUp);
		playerWithSandbag.shoreUp(chosenTile);
		System.out.println(chosenTile.toString()+" is now "+board.getTileWithName(chosenTile).getFloodStatus().toString()+"!");
		return true;
	}

	/**
	 * handleShoreUp is the handle to be called as a player action. It will call doAShoreUp, to shore up a tile, if possible.
	 * If the player is an Engineer, they can shoreUp 2 tiles for one action. This function will facilitate this.
	 */
	private void handleShoreUp() {
		Boolean didShoreUp = doAShoreUp();
		if(player.getRole() == AdventurerEnum.ENGINEER && didShoreUp) {
			System.out.println(player.getName() + " is an Engineer and can shoreUp two tiles for the cost of 1 action.");
			System.out.println("would you like to shore up another tile?");
			System.out.println("1. Yes.");
			System.out.println("2. No. ");
			if(GetInput.getInstance().anInteger(1, 2) == 1) {
				doAShoreUp();
			}
		}
		if(didShoreUp)
			decreaseRemainingActions();
	}

	private void handleGiveTreasureCard() {
		Player playerToGiveCardTo;
		Card cardToGive;
		ArrayList<Player> possiblePlayers = new ArrayList<Player>();           // Players that can be sent a card
		ArrayList<Player> playersThatCanAcceptACard = new ArrayList<Player>(); // Players that can receive a card (Hand is not full)
		
		if(player.getCardsInPlayersHand().isEmpty()) {
			System.out.println( player.getName() + " does not have any cards to give");
			return;
		}
		
		if(player.getRole() == AdventurerEnum.MESSENGER) {
			System.out.println(player.getName() + " is a Messenger and can give a card to anyone in the game.");
			possiblePlayers = Players.getInstance().getPlayersExcept(player);
			
		} else {
			possiblePlayers.addAll(Board.getInstance().getOtherPlayersOnTile(player));
		}
		
		if(possiblePlayers.isEmpty()) {
			// This Could only happen if the player is not a MESSENGER
			System.out.println("There are no players on the same tile as " + player.getName() + ".");
			return;
		}
		
		possiblePlayers.forEach((possiblePlayer) -> {
			if(!possiblePlayer.handIsFull()) {
				playersThatCanAcceptACard.add(possiblePlayer);
			}
		});
		if(playersThatCanAcceptACard.isEmpty()) {
			System.out.println("Everyone's hand is full. cannot give card.");
			return;
		}
		
		playerToGiveCardTo = selectPlayerFromList(playersThatCanAcceptACard);
		cardToGive = selectCardFromList(player.getCardsInPlayersHand());
		
		player.giveTreasureCard(playerToGiveCardTo, cardToGive);
		
		decreaseRemainingActions();
		
	}
	
	private void handleCaptureTreasure() {
		if(player.canCollectTreasure()) {
			TreasureEnum collectedTreasure = player.collectTreasure();
			System.out.println("Congradulations. " + player.getName() + "has just collected the " + collectedTreasure.toString() + " treasure.");
			decreaseRemainingActions();
			if(TreasureManager.getInstance().didClaimAllTreasures()) {
				System.out.println("All treasures have now been collected. Everyone should make thir way to the Fool's Landing Tile and escape with a Helicopter Lift Card!");
			} else {
				System.out.println("Only "+ TreasureManager.getInstance().getNumOfRemainingTreasuresToCollect() + " more treasures to collect before everyone can escape the island.");
			}
		} else {
			// TODO: May need to inform player why not.
			System.out.println(player.getName() + " can not collect a treasure.");
		}
		
	}
	
	private void handleHelicopterLift() {
		TileEnum tileToMoveTo;
		ArrayList<TileEnum> tilesPlayersCanMoveTo = new ArrayList<TileEnum>();
		//TODO for any player.
		if(player.hasHelicopterLiftCard()) {
			System.out.println("Selecting players to move...");
			ArrayList<Player> playersToMove = new ArrayList<>();
			for (Player player : Players.getInstance().getPlayers()) {
				System.out.println("Would you like to move "+player.getName()+" with Helicopter Lift?");
				System.out.println("1. Yes.");
				System.out.println("2. No.");
				if( GetInput.getInstance().anInteger(1,2) == 1 )
					playersToMove.add(player);
			}
			if(playersToMove.isEmpty()) {
				System.out.println("Didn't select any players to move. Won't use the Helicopter Lift Card.");
			} else {
				tilesPlayersCanMoveTo = Board.getInstance().getUnsunkenTilesToMove();
				tileToMoveTo = selectTileFromList(tilesPlayersCanMoveTo);
				for(Player player : playersToMove) {
					player.move(tileToMoveTo);
				}
			}
		} else {
			System.out.println(player.getName() + " does not have a helicopter lift card to use.");
		}
	}
	
	private void showTreasureCards() {
		System.out.println(player.cardsToString());
	}

	private void handleDiscardTreasureCard() {
		Card cardToDiscard;
		ArrayList<Card> cardInHand = player.getCardsInPlayersHand();
		
		if(player.getCardsInPlayersHand().isEmpty()) {
			System.out.println( player.getName() + " does not have any cards to discard.");
			return;
		}
		
		cardToDiscard = selectCardFromList(cardInHand);
		player.discardCard(cardToDiscard);
		
	}
	
	/**
	 * showClaimedTreasures method will show what/number of treasures been claimed so far.
	 */
	private void showClaimedTreasures() {
		TreasureManager treasureManager = TreasureManager.getInstance();
		System.out.println(treasureManager.toString());
	}

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
	
	
	//===========================================================
	// selectObjectFromList functions
	//===========================================================
	
	private TileEnum selectTileFromList(ArrayList<TileEnum> listOfTileEnums) {
		System.out.println("Choose a Tile Number:");
		for (int i=0; i<listOfTileEnums.size(); i++) {
			System.out.println(i+". "+listOfTileEnums.get(i).toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(0, listOfTileEnums.size()-1);
		TileEnum chosenTile = listOfTileEnums.get(chosenNumber);
		return chosenTile;
	}
	
	private Player selectPlayerFromList(ArrayList<Player> listOfPlayers) {
		System.out.println("Choose a player Number:");
		for (int i=0; i<listOfPlayers.size(); i++) {
			System.out.println(i+". "+listOfPlayers.get(i).getName().toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(0, listOfPlayers.size()-1);
		Player chosenPlayer = listOfPlayers.get(chosenNumber);
		return chosenPlayer;
	}
	
	private Card selectCardFromList(ArrayList<Card> listOfCards) {
		System.out.println("Choose a Card Number:");
		for (int i=0; i<listOfCards.size(); i++) {
			System.out.println(i+". "+listOfCards.get(i).toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(0, listOfCards.size()-1);
		Card chosenCard = listOfCards.get(chosenNumber);
		return chosenCard;
	}
	

	
	
/*	
	public static void main(String[] args) {
		boolean gameOver;
		Player myPlayer = new Player("Demi", AdventurerEnum.ENGINEER);
		ArrayList<Player> myPlayers = new ArrayList<Player>();
		myPlayers.add(myPlayer);
		//Board.getInstance().setUpPlayerOnBoard(myPlayers);
		PlayerGo myPlayerGo = new PlayerGo(myPlayer);
		gameOver = myPlayerGo.doRound();
		
	}
*/

}
