package fi.playerController;

import java.util.ArrayList;

import fi.board.Board;
import fi.cards.Card;
import fi.cards.FloodDeck;
import fi.cards.TreasureDeck;
import fi.cards.WaterRiseCard;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.gameView.GameView;
import fi.gameView.PlayerView;
import fi.players.Player;
import fi.players.Players;
import fi.treasures.TreasureManager;
import fi.watermeter.WaterMeter;

public abstract class PlayerController {
	//===========================================================
	// Variable Setup
	//===========================================================
	protected Player player;
	protected PlayerView playerView;
	protected GameView gameView;
	protected int remainingActions;
	protected boolean turnHasEnded;
	protected boolean gameOver;
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	protected final int MAX_ACTIONS_PER_GO = 3;
	protected final int MIN_ACTION_NUMBER  = 0;
	protected final int MAX_ACTION_NUMBER  = 9;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for PlayerGo object.
	 */
	public PlayerController(Player player) {
		this.player = player;
		this.remainingActions = MAX_ACTIONS_PER_GO;
		playerView = new PlayerView(player);
		gameView = GameView.getInstance();
		drawCardsFromTreasureDeckToStart();
	}
	
	public void doActions() {
		turnHasEnded = false;
		while(!turnHasEnded) {
			getAction();
			if(gameOver) {
				turnHasEnded = true;
			}
		}
	}
	
	/**
	 * getAction method calls the playerView to get the next action to be done.
	 */
	public void getAction() {
		int option = playerView.getAction(remainingActions);
		if(option < MIN_ACTION_NUMBER || option > MAX_ACTION_NUMBER)
			throw new RuntimeException("Invalid option number received");
		handleAction(option);
	}
	
	/**
	 * handleAction picks the correct action based on actionNumber
	 * @param actionNumber
	 */
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
			playerView.showTreasureCards();
			break;
		case 7:
			handleDiscardTreasureCard();
			break;
		case 8:
			gameView.showClaimedTreasures();
			break;
		case 9:
			handleSandBag();
			break;
		case 0:
			endGo();
			break;
		default:
			throw new RuntimeException("Attempting to select a number outside of range.");	
		}
	}
	
	/**
	 * drawCardsFromTreasureDeckToStart method will add the cards needed for a player to begin the game.
	 */
	private void drawCardsFromTreasureDeckToStart() {
		TreasureDeck treasureDeck = TreasureDeck.getInstance();
		ArrayList<Card> treasureCards = treasureDeck.drawCardsForStartOfGame();
		
		playerView.printNumberDrawnCards(treasureCards.size());
		treasureCards.forEach((card) -> {
			playerView.printPlayerCard(card);
			putCardInPlayersHand(card);
		});
		gameView.printNewLine(); //TODO use a gameView to format game.
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
			gameView.gameOver();
		return gameOver;
	}
	
	/**
	 * decreaseRemainingActions removes a remaining action from the number of player actions.
	 */
	protected void decreaseRemainingActions() {
		remainingActions -= 1;
		if(remainingActions == 0) {
			turnHasEnded = true;
		}
	}
	
	//===========================================================
	// Actions during a Player Go.
	//===========================================================
	
	/**
	 * handleMove will manage movement of player during game.
	 */
	private void handleMove() {
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		tilesPlayerCanMoveTo.addAll(board.getTilesAroundTile(player.getLocation(), true));
		if(tilesPlayerCanMoveTo.isEmpty()) {
			playerView.playerCantMove();
			return;
		}
		chosenTile = playerView.selectTileFromList(tilesPlayerCanMoveTo);
		player.move(chosenTile);
		playerView.playerHasMoved();
		decreaseRemainingActions();
	}
	
	/**
	 * handleSandBag method is a function to be called which will allow
	 * any player to use their sandbag card at any time.
	 */
	private void handleSandBag() {
		ArrayList<Player> allPlayers = Players.getInstance().getPlayers();
		ArrayList<Player> playersWithSandbag = new ArrayList<Player>();
		Player playerUsingSandbag;
		Board board = Board.getInstance();
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanShoreUp = new ArrayList<TileEnum>();
		
		for(Player aPlayer : allPlayers) {
			if(aPlayer.hasSandBagCard()) {
				playersWithSandbag.add(aPlayer);
				break;
			}
		}	
		if(playersWithSandbag.isEmpty()) { 
			playerView.noSandbagCards();
			return;
		}
		
		playerUsingSandbag = playerView.selectPlayerWithSandbag(playersWithSandbag);
		
		tilesPlayerCanShoreUp.addAll(board.getAllFloodedTiles());
		if(tilesPlayerCanShoreUp.isEmpty()) { 
			gameView.noFloodedTiles();
			return;
		}
		
		chosenTile = playerView.selectTileFromList(tilesPlayerCanShoreUp);
		playerUsingSandbag.shoreUp(chosenTile);
		playerUsingSandbag.didUseSandBagCard();
		gameView.changeFloodStatus(chosenTile);
	}
	
	/**
	 * doAShoreUp method is a function to be called by handleShoreUp.
	 * It will facilitate the shoring-up of one tile near player.
	 * @return Boolean true if it has shoredUp a tile. Else false.
	 */
	protected Boolean doAShoreUp() {
		Board board = Board.getInstance();
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanShoreUp = new ArrayList<TileEnum>();
		tilesPlayerCanShoreUp.addAll(board.getTilesToShoreUpAround(player.getLocation()));
		if(tilesPlayerCanShoreUp.isEmpty()) {
			gameView.noFloodedTiles();
			return false;
		}
		chosenTile = playerView.selectTileFromList(tilesPlayerCanShoreUp);
		player.shoreUp(chosenTile);
		
		gameView.changeFloodStatus(chosenTile);
		return true;
	}
	//TODO use doAShoreUp with argument tile list, player, call from sandbag and shoreUp9

	/**
	 * handleShoreUp is the handle to be called as a player action. It will call doAShoreUp, to shore up a tile, if possible.
	 * If the player is an Engineer, they can shoreUp 2 tiles for one action. This function will facilitate this.
	 */
	protected void handleShoreUp() {
		Boolean didShoreUp = doAShoreUp();
		if(didShoreUp)
			decreaseRemainingActions();
	}

	/**
	 * Function to manage a player being able to give away treasure cards.
	 */
	protected void handleGiveTreasureCard() {
		Player playerToGiveCardTo;
		Card cardToGive;
		ArrayList<Player> possiblePlayers = new ArrayList<Player>();           // Players that can be sent a card
		ArrayList<Player> playersThatCanAcceptACard = new ArrayList<Player>(); // Players that can receive a card (Hand is not full)
		
		if(player.getCardsInPlayersHand().isEmpty()) {
			playerView.playerHasNoCards();
			return;
		}
		
		// Get possiblePlayers depending on role
		if(player.getRole() == AdventurerEnum.MESSENGER) {
			playerView.playerIsMessengerGiveCards();
			possiblePlayers = Players.getInstance().getPlayersExcept(player);	
		} 
		else
			possiblePlayers.addAll(Board.getInstance().getOtherPlayersOnTile(player));
		
		if(possiblePlayers.isEmpty()) {
			// This Could only happen if the player is not a MESSENGER
			playerView.playersNotOnSameTile();
			return;
		}
		
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
	
   /**
    * handleCaptureTreasure will facilitate a player claiming a treasure.
    */
	private void handleCaptureTreasure() {
		if(player.canCollectTreasure()) {
			TreasureEnum collectedTreasure = player.collectTreasure();
			playerView.playerCollectedTreasure(collectedTreasure);
			decreaseRemainingActions();
			if(TreasureManager.getInstance().didClaimAllTreasures())
				gameView.claimedAllTreasures();
			else
				gameView.getRemainingNumberTreasures();
		} 
		else 
			playerView.cantCollectTreasure();
		
	}
	
	/**
	 * handleHelicopterLift allows a player to use any players helicopter lift card during a go.
	 */
	private void handleHelicopterLift() {
		TileEnum tileToMoveTo;
		ArrayList<TileEnum> tilesPlayersCanMoveTo = new ArrayList<TileEnum>();
		Players players = Players.getInstance();
		
		// Get use of HelicopterLiftCard
		Player playerWithHelicopterLift = null;
		if(player.hasHelicopterLiftCard())
			playerWithHelicopterLift = player;
		else {
			ArrayList<Player> otherPlayers = players.getPlayersExcept(player);
			for(Player otherPlayer : otherPlayers) {
				if(otherPlayer.hasHelicopterLiftCard()) {
					playerWithHelicopterLift= otherPlayer;
					break;
				}
			}
		}
		if(playerWithHelicopterLift == null)
			playerView.noHelicopterLiftCards();
		
		// Gotten use of a HelicopterLift Card
		else {
			playerView.selectPlayerToMove();
			ArrayList<Player> playersToMove = new ArrayList<>();
			for (Player player : players.getPlayers()) {
				if(playerView.selectThisPlayerToMove(player))
					playersToMove.add(player);
			}
			if(playersToMove.isEmpty()) {
				playerView.noPlayersToHelicopterLift();
			} else if(playersDidWin(playersToMove)) {
				gameView.gameWon();
				gameOver = true;
				return;
			} else {
				tilesPlayersCanMoveTo = Board.getInstance().getUnsunkenTiles();
				tileToMoveTo = playerView.selectTileFromList(tilesPlayersCanMoveTo);
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
	 * @return boolean indicating if the players met the additional criteria to win.
	 */
	private boolean playersDidWin(ArrayList<Player> players) {
		int numPlayersInGame = Players.getInstance().getNumPlayers();
		
		if(players.size() != numPlayersInGame || !TreasureManager.getInstance().didClaimAllTreasures())
			return false;
		
		for(Player player: players) {
			if(player.getLocation() != TileEnum.FOOLS_LANDING)
				return false;
		}
		
		return true;
	}

	/**
	 * handleDiscardTreasureCard asks the player which card they would like to remove from their Hand and then removes it.
	 */
	private void handleDiscardTreasureCard() {
		Card cardToDiscard;
		ArrayList<Card> cardInHand = player.getCardsInPlayersHand();
		
		if(player.getCardsInPlayersHand().isEmpty()) {
			playerView.playerHasNoCards();
			return;
		}
		
		cardToDiscard = playerView.selectCardFromList(cardInHand);
		player.discardCard(cardToDiscard);
		
	}
	
	/**
	 * sets turnHasEnded to true.
	 */
	private void endGo() {
		turnHasEnded = true;
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
		
		playerView.printNumberDrawnCards(treasureCards.size());
	
		treasureCards.forEach((card) -> {
			playerView.printPlayerCard(card);
			if(card instanceof WaterRiseCard) {
				waterMeter.increaseWaterMeter();
				int currentLevel = waterMeter.getCurrentLevel();
				if(currentLevel == waterMeter.getGameOverLevel())
					gameOver = true;
				else
					gameView.increasedWaterMeter();
				treasureDeck.discardCard(card);
			} else {
				putCardInPlayersHand(card);
			}
		});
		gameView.printNewLine();
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
		tilesToFlood.forEach((tileEnum) -> { //TODO check order
			if(!board.getTileWithName(tileEnum).isSunken()) {
				board.floodTile(tileEnum);
				gameView.changeFloodStatus(tileEnum);
			}
			if(board.getTileWithName(tileEnum).isSunken()) {
				actOnSunkTile(tileEnum);
				if(gameOver)
					return;
			}
		});
		gameView.printNewLine();
	}
	
	/**
	 * Check for lose conditions when a tile has sunk.
	 * @param tileEnum, name of the sunken tile.
	 */
	private void actOnSunkTile(TileEnum tileEnum) {
		Board board = Board.getInstance();
		if( !TreasureManager.getInstance().treasuresAreAvailableToCollect() ) {
			gameView.treasureHasSunk();
			gameOver = true; 
		}
		else if(tileEnum == TileEnum.FOOLS_LANDING) {
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
	protected Boolean moveFromSunk(Player sunkPlayer) {
		TileEnum chosenTile;
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		
		if(sunkPlayer.getRole() == AdventurerEnum.PILOT)
			tilesPlayerCanMoveTo.addAll(board.getUnsunkenTiles()); 
		else if(sunkPlayer.getRole() == AdventurerEnum.EXPLORER)
			tilesPlayerCanMoveTo.addAll(board.getTilesAroundTile(sunkPlayer.getLocation(), false));
		else if(sunkPlayer.getRole() == AdventurerEnum.DIVER)
			tilesPlayerCanMoveTo.addAll(Board.getInstance().getNearestTilesToTile(sunkPlayer.getLocation()));
		else
			tilesPlayerCanMoveTo.addAll(board.getTilesAroundTile(sunkPlayer.getLocation(), false));
		
		if(tilesPlayerCanMoveTo.isEmpty()) {
			playerView.playerCantMove();
			return false;
		}
		
		chosenTile = playerView.tileToMoveFromSunk(sunkPlayer, tilesPlayerCanMoveTo);
		sunkPlayer.move(chosenTile);
		playerView.playerHasMovedFromSunk(sunkPlayer);
		return true;
	}
	
	//===========================================================
	// Other functions
	//===========================================================
	/**
	 * putCardInPlayersHand method will add a card to a player hand. It will remove a card if their hand is full.
	 * @param card, a card to add to the player hand.
	 */
	private void putCardInPlayersHand(Card card) {
		if(player.handIsFull()) {
			Card cardToDiscard;
			player.collectTreasureCard(card);
			cardToDiscard = playerView.selectCardToDiscard();
			player.discardCard(cardToDiscard);
		} 
		else
			player.collectTreasureCard(card);
	}
}
