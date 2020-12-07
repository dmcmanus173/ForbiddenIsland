package fi.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fi.board.Board;
import fi.board.Tile;
import fi.board.TreasureTile;
import fi.cards.Card;
import fi.cards.FloodDeck;
import fi.cards.HelicopterLiftCard;
import fi.cards.TreasureDeck;
import fi.cards.WaterRiseCard;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.players.Player;
import fi.players.Players;
import fi.treasures.TreasureManager;
import fi.watermeter.WaterMeter;
import gameManager.GetInput;


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
			int chosenCardIndex;
			Card cardToDiscard;
			
			player.collectTreasureCard(card);
			System.out.println(player.getName()+ "'s hand is now full. Select a card to discard from hand.");
			chosenCardIndex = selectCardFromList(player.getCardsInPlayersHand());
			cardToDiscard = player.getCardsInPlayersHand().get(chosenCardIndex);
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
		int chosenTileIndex;
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		tilesPlayerCanMoveTo.addAll( Board.getInstance().getTilesAroundTile(player.getLocation(), true) );
		if(tilesPlayerCanMoveTo.isEmpty()) {
			System.out.println(player.getName()+" is unable to move.");
			// TODO: AUTOMATIC GAME OVER -> NOTIFY GAME MANAGER.
			
			return;
		}
		chosenTileIndex = selectTileFromList(tilesPlayerCanMoveTo);
		player.move(tilesPlayerCanMoveTo.get(chosenTileIndex));
		System.out.println(player.getName()+" has moved to "+player.getLocation());
		decreaseRemainingActions();
	}

	private void handleShoreUp() {
		int chosenTileIndex;
		ArrayList<TileEnum> tilesPlayerCanShoreUp = new ArrayList<TileEnum>();
		tilesPlayerCanShoreUp.addAll( Board.getInstance().getTilesToShoreUpAround(player.getLocation()));
		if(tilesPlayerCanShoreUp.isEmpty()) {
			System.out.println("There are no flooded tiles adjacent to " + player.getName() + "'s current location that can be shored up.");
			return;
		}
		System.out.println("pick a tile to shore up.");
		chosenTileIndex = selectTileFromList(tilesPlayerCanShoreUp);
		Board.getInstance().shoreUpTile(tilesPlayerCanShoreUp.get(chosenTileIndex));
		
		if(player.getRole() == AdventurerEnum.ENGINEER) {
			tilesPlayerCanShoreUp.remove(chosenTileIndex);
			if(tilesPlayerCanShoreUp.isEmpty()) {
				System.out.println(player.getName() + "is an Engineer and can shore two tiles for the cost of 1. But there are no more unflooded adjacent tiles.");
				return;
			}
			System.out.println(player.getName() + "is an Engineer and can shore two tiles for the cost of 1. Would you like to?");
			System.out.println("would you like to shore up another tile?");
			System.out.println("1. Yes.");
			System.out.println("2. No.");
			if( GetInput.getInstance().anInteger(1, 2)==1 ) {
				chosenTileIndex = selectTileFromList(tilesPlayerCanShoreUp);
				Board.getInstance().shoreUpTile(tilesPlayerCanShoreUp.get(chosenTileIndex));
			}
				
		}
		
		decreaseRemainingActions();
	}

	private void handleGiveTreasureCard() {
		int chosenPlayerIndex;
		int chosenCardIndex;
		Player playerToGiveCardTo;
		Card cardToGive;
		ArrayList<Player> possiblePlayers = new ArrayList<Player>();
		ArrayList<Player> playersThatCanAcceptACard = new ArrayList<Player>();
		
		if(player.getCardsInPlayersHand().isEmpty()) {
			System.out.println( player.getName() + " does not have any cards to give");
			return;
		}
		
		if(player.getRole() == AdventurerEnum.MESSENGER) {
			System.out.println(player.getName() + "is a Messenger and can give a card to anyone other player in the game.");
			possiblePlayers = Players.getInstance().getPlayersExcept(player);
			
		} else {
			// TODO: MIGHT HAVE TO CONVER PLAYERSONTILE IN TILE CLASS TO ARRAYLIST AND NOT SET
			possiblePlayers.addAll(Board.getInstance().getOtherPlayersOnTile(player));
//			ArrayList<Player> targetList = new ArrayList<>(possiblePlayers);
		}
		
		if(possiblePlayers.isEmpty()) {
			// This Could only happen if the player is not a MESSENGER
			System.out.println("There are no other players on the same tile as " + player.getName() + "to give a treasure card to.");
			return;
		}
		
		possiblePlayers.forEach((possiblePlayer) -> {
			if(!possiblePlayer.handIsFull()) {
				playersThatCanAcceptACard.add(possiblePlayer);
			}
		});
		
		if(playersThatCanAcceptACard.isEmpty()) {
			// This Could only happen if the player is not a MESSENGER
			System.out.println("Everyone's hand is full. cannot give card.");
			return;
		}
		
		
		chosenPlayerIndex = selectPlayerFromList(playersThatCanAcceptACard);
		playerToGiveCardTo = playersThatCanAcceptACard.get(chosenPlayerIndex);
		chosenCardIndex = selectCardFromList(player.getCardsInPlayersHand());
		cardToGive = player.getCardsInPlayersHand().get(chosenCardIndex);
		
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
		int indexOdTileToMoveTo;
		ArrayList<TileEnum> tilesPlayersCanMoveTo = new ArrayList<TileEnum>();
		TileEnum tileToMoveTo;
		
		if(player.hasHelicopterLiftCard()) {
			System.out.println("Selecting players to move...");
			ArrayList<Player> playersToMove = new ArrayList<>();
			for (Player player : Players.getInstance().getPlayers()) {
				System.out.println("Would you like to move "+player.getName()+" with Helicopter Lift?");
				System.out.println("1. Yes.");
				System.out.println("2. No.");
				if( GetInput.getInstance().anInteger(1, 2) == 1 )
					playersToMove.add(player);
			}
			if(playersToMove.isEmpty()) {
				System.out.println("Didn't select any players to move. Won't use the Helicopter Lift Card.");
			} else {
				tilesPlayersCanMoveTo = Board.getInstance().getUnsunkenTilesToMove();
				indexOdTileToMoveTo = selectTileFromList(tilesPlayersCanMoveTo);
				tileToMoveTo = tilesPlayersCanMoveTo.get(indexOdTileToMoveTo);
				for(Player player : playersToMove) {
					player.move(tileToMoveTo);
				}
			}
		} else {
			System.out.println(player.getName() + " does not have a helicopter lift card to use.");
		}
	}

	private void handleSandbag() {
		
		if(!player.hasSandBagCard()) {
			System.out.println(player.getName() + " does not have a sanbag card to use.");
		}
		
		int chosenTileIndex;
		ArrayList<TileEnum> tilesPlayerCanShoreUp = new ArrayList<TileEnum>();
		tilesPlayerCanShoreUp.addAll( Board.getInstance().getAllFloodedTiles());
		if(tilesPlayerCanShoreUp.isEmpty()) {
			System.out.println(" There are no flooded tiles on the board!");
			return;
		}
		chosenTileIndex = selectTileFromList(tilesPlayerCanShoreUp);
		Board.getInstance().shoreUpTile(tilesPlayerCanShoreUp.get(chosenTileIndex));
	}
	
	private void showTreasureCards() {
		System.out.println(player.cardsToString());
	}

	private void handleDiscardTreasureCard() {
		int chosenCardIndex;
		Card cardToDiscard;
		ArrayList<Card> cardInHand = player.getCardsInPlayersHand();
		
		if(player.getCardsInPlayersHand().isEmpty()) {
			System.out.println( player.getName() + " does not have any cards to discard.");
			return;
		}
		
		chosenCardIndex = selectCardFromList(player.getCardsInPlayersHand());
		cardToDiscard = player.getCardsInPlayersHand().get(chosenCardIndex);
		player.discardCard(cardToDiscard);
		
	}
	
	private void showClaimedTreasures() {
		System.out.println( TreasureManager.getInstance().toString());
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
	
	
	
	
	
	
	private TileEnum selectTiles(ArrayList<TileEnum> tiles) {
		System.out.println("Choose one of the following tiles:");
		for (int i=0; i<tiles.size(); i++) {
			System.out.println("Tile "+(i+1)+": "+tiles.get(i).toString());
		}
		TileEnum chosenTile = tiles.get(  GetInput.getInstance().anInteger(1, tiles.size())-1 );
		return chosenTile;
	}
	
	
	
	private int selectTileFromList(ArrayList<TileEnum> listOfObjects) {
		for (int i=0; i<listOfObjects.size(); i++) {
			System.out.println("Tile "+(i)+": "+listOfObjects.get(i).toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(0, listOfObjects.size()-1);
		return chosenNumber;
	}
	
	private int selectPlayerFromList(ArrayList<Player> listOfObjects) {
		for (int i=0; i<listOfObjects.size(); i++) {
			System.out.println("Tile "+(i)+": "+listOfObjects.get(i).toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(0, listOfObjects.size()-1);
		return chosenNumber;
	}
	
	private int selectCardFromList(ArrayList<Card> listOfObjects) {
		for (int i=0; i<listOfObjects.size(); i++) {
			System.out.println("Tile "+(i)+": "+listOfObjects.get(i).toString());
		}
		int chosenNumber = GetInput.getInstance().anInteger(0, listOfObjects.size()-1);
		return chosenNumber;
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
