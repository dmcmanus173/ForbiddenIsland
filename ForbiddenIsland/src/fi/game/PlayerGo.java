package fi.game;

import java.util.ArrayList;

import fi.board.Board;
import fi.board.FoolsLandingTile;
import fi.board.TreasureTile;
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
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MAX_ACTIONS_PER_GO = 3;
	private final int MIN_ACTION_NUMBER = 1;
	private final int MAX_ACTION_NUMBER = 9;
	
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
		drawCardsFromTreasureDeckToStart();
	}
	
	/**
	 * drawCardsFromTreasureDeckToStart method will add the cards needed for a player to begin the game.
	 */
	private void drawCardsFromTreasureDeckToStart() {
		TreasureDeck treasureDeck = TreasureDeck.getInstance();
		ArrayList<Card> treasureCards = treasureDeck.drawCardsForStartOfGame();
		
		System.out.println("Drawing " + treasureCards.size() + " cards from treasure deck for "+player.getName()+".");
		treasureCards.forEach((card) -> {
			System.out.println(player.getName()+" just drew a card: "+card.toString());
			putCardInPlayersHand(card);
		});
		System.out.println();
	}
	
	/**
	 * doRound is a full round done by a player.
	 * @return
	 */
	public boolean doRound() {
		doActions();
		if(!gameOver) 
			drawCardsFromTreasureDeck();
		if(!gameOver) 
			drawCardsFromFloodDeck();
		if(gameOver)
			System.out.println("Game Over!");
		return gameOver;
	}
	
	/**
	 * decreaseRemainingActions removes a remaining action from the number of player actions.
	 */
	private void decreaseRemainingActions() {
		remainingActions -= 1;
		if(remainingActions == 0) {
			goHasEnded = true;
		}
	}
	
	//===========================================================
	// Actions during a Player Go.
	//===========================================================
	
	/**
	 * doActions will get the player to do their 3 actions as part of their go.
	 */
	private void doActions() {
		goHasEnded = false;
		int actionNumber;
		System.out.println("It is "+player.getName()+"'s turn!");
		 
		while(!goHasEnded) {
			showActions();
			actionNumber = GetInput.getInstance().anInteger(MIN_ACTION_NUMBER, MAX_ACTION_NUMBER);
			handleAction(actionNumber);
			if(gameOver) {
				goHasEnded = true;
			}
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
		System.out.println(" 9. End Go."                                   );
		System.out.println("Choose a number for what you would like to do.");
	}
	
	private void handleAction(int actionNumber) {
		switch(actionNumber) {
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
			endGo();
			break;
		default:
			throw new RuntimeException("Attempting to select a number outside of range.");	
		}
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

	/**
	 * Function to manage a player being able to give away treasure cards.
	 */
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
	
   /**
    * handleCaptureTreasure will facilitate a player claiming a treasure.
    */
	private void handleCaptureTreasure() {
		if(player.canCollectTreasure()) {
			TreasureEnum collectedTreasure = player.collectTreasure();
			System.out.println("Congratulations. " + player.getName() + " has just collected " + collectedTreasure.toString() + ".");
			decreaseRemainingActions();
			if(TreasureManager.getInstance().didClaimAllTreasures()) {
				System.out.println("All treasures have now been collected. Everyone should make their way to Fool's Landing to escape with a Helicopter Lift Card!");
			} else {
				System.out.println("Only "+ TreasureManager.getInstance().getNumOfRemainingTreasuresToCollect() + " more treasures to collect before everyone can escape the island.");
			}
		} 
		else 
			System.out.println(player.getName() + " can not collect a treasure.");
		
	}
	
	/**
	 * handleHelicopterLift allows a player to use any players helicopter lift card during a go.
	 */
	private void handleHelicopterLift() {
		TileEnum tileToMoveTo;
		ArrayList<TileEnum> tilesPlayersCanMoveTo = new ArrayList<TileEnum>();
		
		// Get use of HelicopterLiftCard
		Player playerWithHelicopterLift = null;
		if(player.hasHelicopterLiftCard())
			playerWithHelicopterLift = player;
		else {
			ArrayList<Player> otherPlayers = Players.getInstance().getPlayersExcept(player);
			for(Player otherPlayer : otherPlayers) {
				if(otherPlayer.hasHelicopterLiftCard()) {
					playerWithHelicopterLift= otherPlayer;
					break;
				}
			}
		}
		if(playerWithHelicopterLift == null)
			System.out.println("None of the players have a HelicopterLift Card for "+player.getName()+" to use.");
		
		// Gotten use of a HelicopterLift Card
		else {
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
			} else if(playersDidWin(playersToMove)) {
				System.out.println("Congradulations! You have succsesfully escaped the Island with all of the Treasures. You have won the game!!!");
				gameOver = true;
				return;
			} else {
				tilesPlayersCanMoveTo = Board.getInstance().getUnsunkenTilesToMove();
				tileToMoveTo = selectTileFromList(tilesPlayersCanMoveTo);
				for(Player player : playersToMove)
					player.move(tileToMoveTo);
				playerWithHelicopterLift.didUseHelicopterLiftCard();
			}
		}
	}
	
	/**
	 * playersDidWin is called only when players use they're helicopter lift card.
	 * The game is won if all players are lifted up from Fools Island Tile 
	 * and all treasures are collected.
	 * @param ArrayList<Player> indicating the players being moved with the helicopter lift.
	 * @return boolean indicating if the players met the aditional criteria to win.
	 */
	private boolean playersDidWin(ArrayList<Player> players) {
		int numPlayersInGame = Players.getInstance().getNumPlayers();
		
		if(players.size() != numPlayersInGame) {
			return false;
		}
		
		if(!TreasureManager.getInstance().didClaimAllTreasures()) {
			return false;
		}
		
		for(Player player: players) {
			if(player.getLocation() != TileEnum.FOOLS_LANDING) {
				return false;
			}
		}
		
		return true;
		
	}
	
	/**
	 * showTreasureCards will show the TreasureCards a player has in their Hand.
	 */
	private void showTreasureCards() {
		System.out.println(player.cardsToString());
	}

	/**
	 * handleDiscardTreasureCard asks the player which card they would like to remove from their Hand and then removes it.
	 */
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
	 * sets goHasEnded to true.
	 */
	private void endGo() {
		goHasEnded = true;
	}
	
	//===========================================================
	// Mandatory Player Action Functions
	//===========================================================

	/**
	 * drawCardsFromTreasureDeck method will draw the cards for a player and act accordingly.
	 * It will increase waterMeter level if required, or add a card to the player hand.
	 */
	private void drawCardsFromTreasureDeck() {
		TreasureDeck treasureDeck = TreasureDeck.getInstance();
		WaterMeter waterMeter = WaterMeter.getInstance();
		ArrayList<Card> treasureCards = treasureDeck.drawCards();
		
		System.out.println("Drawing " + treasureCards.size() + " cards from treasure deck for "+player.getName()+".");
	
		treasureCards.forEach((card) -> {
			System.out.println(player.getName()+" just drew a card: "+card.toString());
			if(card instanceof WaterRiseCard) {
				waterMeter.increaseWaterMeter();
				int currentLevel = waterMeter.getCurrentLevel();
				if(currentLevel == waterMeter.getGameOverLevel())
					gameOver = true;
				else
					System.out.println("The water meter has been increased to " + currentLevel + ". All players will now have to take " + currentLevel + " FloodCards at the end of their go!");
				treasureDeck.discardCard(card);
			} else {
				putCardInPlayersHand(card);
			}
		});
		System.out.println();
	}
	
	/**
	 * drawCardsFromFloodDeck is the last act a player does at the end of their go.
	 * It will change tiles flood status from cards taken from FloodDeck.
	 * It will move players off of sunk tiles and call gameOver if a gameOver condition is met.
	 */
	private void drawCardsFromFloodDeck() {
		ArrayList<TileEnum> tilesToFlood = FloodDeck.getInstance().getTilesToFlood(false);
		Board board = Board.getInstance();
		// check with treasure manager if treasuresAreAvailableToCollect
		tilesToFlood.forEach((tileEnum) -> {
			if(!board.getTileWithName(tileEnum).isSunken()) {
				board.floodTile(tileEnum);
				System.out.println(tileEnum.toString()+" is now "+board.getTileWithName(tileEnum).getFloodStatus()+"!");
			}
			if(board.getTileWithName(tileEnum).isSunken()) {
				actOnSunkTile(tileEnum);
				if(gameOver)
					return;
			}
	
		});
		System.out.println();
	}
	
	/**
	 * Check for lose conditions when a tile has sunk.
	 * @param tileEnum, name of the sunken tile.
	 */
	private void actOnSunkTile(TileEnum tileEnum) {
		Board board = Board.getInstance();
		
		if( !TreasureManager.getInstance().treasuresAreAvailableToCollect() ) {
			System.out.println("An uncollected Treasure has sunk!");
			gameOver = true; 
		}
		else if(board.getTileWithName(tileEnum) instanceof FoolsLandingTile) {
			gameOver = true;
		}
		else {	
			board.getPlayersFromTile(tileEnum).forEach(sunkPlayer -> {
				if(!moveFromSunk(sunkPlayer)) {
					gameOver = true;
					return;
				}
			});
		}
	}
	
	/**
	 * moveFromSunk moves a player sunkPlayer from the sunk tile that they are on.
	 * @param sunkPlayer, a player that is on a sunk tile.
	 * @return true if the player is able to move. Else false.
	 */
	private Boolean moveFromSunk(Player sunkPlayer) {
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		
		if(sunkPlayer.getRole() == AdventurerEnum.PILOT)
			tilesPlayerCanMoveTo.addAll(board.getUnsunkenTilesToMove()); 
		else if(sunkPlayer.getRole() == AdventurerEnum.EXPLORER)
			tilesPlayerCanMoveTo.addAll(board.getTilesAroundTile(sunkPlayer.getLocation(), false));
		else if(sunkPlayer.getRole() == AdventurerEnum.DIVER)
			tilesPlayerCanMoveTo.addAll(Board.getInstance().getNearestTilesToTile(sunkPlayer.getLocation()));
		else
			tilesPlayerCanMoveTo.addAll(board.getTilesAroundTile(sunkPlayer.getLocation(), false));
		
		if(tilesPlayerCanMoveTo.isEmpty()) {
			System.out.println("There is nowhere for "+sunkPlayer.getName()+" to move to!");
			return false;
		}
		
		System.out.println(sunkPlayer.getName()+" must move as they're on a sunken Tile!");
		showMap();
		System.out.println(sunkPlayer.getName()+" is on "+sunkPlayer.getLocation().toString());
		chosenTile = selectTileFromList(tilesPlayerCanMoveTo);
		sunkPlayer.move(chosenTile);
		System.out.println(sunkPlayer.getName()+" has moved to "+sunkPlayer.getLocation());
		return true;
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

	/**
	 * putCardInPlayersHand method will add a card to a player hand. It will remove a card if their hand is full.
	 * @param card, a card to add to the player hand.
	 */
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
	
}
