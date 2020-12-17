package fi.playerController;

import java.util.ArrayList;

import fi.board.Board;
import fi.cards.Card;
import fi.cards.FloodDeck;
import fi.cards.TreasureDeck;
import fi.cards.WaterRiseCard;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.board.TreasureTile;
import fi.game.GameOverObserver;
import fi.gameView.GameView;
import fi.playerView.PlayerView;
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
	protected GameOverObserver gameOverObserver;
	
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
		gameOverObserver = GameOverObserver.getInstance();
		gameView = GameView.getInstance();
		playerView = new PlayerView(player);
		
		drawCardsFromTreasureDeckToStart();
	}
	
	private void doActions() {
		turnHasEnded = false;
		while(!turnHasEnded)
			getAction();
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
	 * doRound is a full round done by a player. 3 actions, draw from treasure deck, draw from flood deck.
	 * @return sunkenPlayers, players on tiles that have sunk during round.
	 */
	public ArrayList<Player> doRound() {
		ArrayList<Player> sunkenPlayers = new ArrayList<>();
		doActions();
		if(!gameOverObserver.isGameOver()) 
			drawCardsFromTreasureDeck();
		if(!gameOverObserver.isGameOver()) 
			sunkenPlayers = drawCardsFromFloodDeck();

		return sunkenPlayers;
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
		if(tilesPlayerCanMoveTo.isEmpty())
			playerView.playerCantMove();
		else {
			chosenTile = playerView.selectTileFromList(tilesPlayerCanMoveTo);
			player.move(chosenTile);
			playerView.playerHasMoved();
			decreaseRemainingActions();
		}
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
		ArrayList<TileEnum> tilesPlayerCanShoreUp = new ArrayList<TileEnum>();
		
		for(Player aPlayer : allPlayers) {
			if(aPlayer.hasSandBagCard()) {
				playersWithSandbag.add(aPlayer);
				break;
			}
		}	
		if(playersWithSandbag.isEmpty()) { 
			playerView.noSandbagCards();
		} else {
			playerUsingSandbag = playerView.selectPlayerWithSandbag(playersWithSandbag);
			tilesPlayerCanShoreUp.addAll(board.getAllFloodedTiles());
			doAShoreUp(tilesPlayerCanShoreUp);
			playerUsingSandbag.didUseSandBagCard();
		}
	}
	
	/**
	 * doAShoreUp method is a function to be called by handleShoreUp or handleSandbag.
	 * It will facilitate the shoring-up of one tile from the ArrayList of possible tiles provided when called by handleShoreUp and handleSandbag.
	 * @param ArrayList<TileEnum> list of tiles to shoreUp.
	 * @return Boolean true if it has shoredUp a tile. Else false.
	 */
	protected Boolean doAShoreUp(ArrayList<TileEnum> tilesPlayerCanShoreUp) {
		TileEnum chosenTile;

		if(tilesPlayerCanShoreUp.isEmpty()) {
			gameView.noFloodedTiles();
			return false;
		}
		chosenTile = playerView.selectTileFromList(tilesPlayerCanShoreUp);
		player.shoreUp(chosenTile);
		
		gameView.changeFloodStatus(chosenTile);
		return true;
	}

	/**
	 * handleShoreUp is the handle to be called as a player action. It will call doAShoreUp, to shore up a tile, if possible.
	 * If the player is an Engineer, they can shoreUp 2 tiles for one action. This function will facilitate this.
	 */
	protected void handleShoreUp() {
		Board board = Board.getInstance();
		ArrayList<TileEnum> tilesPlayerCanShoreUp = board.getTilesToShoreUpAround(player.getLocation());
		Boolean didShoreUp = doAShoreUp(tilesPlayerCanShoreUp);
		if(didShoreUp)
			decreaseRemainingActions();
	}

	/**
	 * Function to manage a player being able to give away treasure cards.
	 */
	protected void handleGiveTreasureCard() {
		ArrayList<Player> possiblePlayers = new ArrayList<Player>();           // Players that can be sent a card
		
		possiblePlayers.addAll(Board.getInstance().getOtherPlayersOnTile(player));
		if(possiblePlayers.isEmpty()) {
			playerView.playersNotOnSameTile();
		}
		else		
			toGiveTreasureCard(possiblePlayers);
	}
	
	/**
	 * Function to give a TreasureCard away, to a player from the list
	 * @param list of players that can give card to
	 */
	protected void toGiveTreasureCard(ArrayList<Player> possiblePlayers) {
		Player playerToGiveCardTo;
		Card cardToGive;
		ArrayList<Player> playersThatCanAcceptACard = new ArrayList<Player>(); // Players that can receive a card (Hand is not full)
		
		// Getting players from possiblePlayers without full hand
		possiblePlayers.forEach((possiblePlayer) -> {
			if(!possiblePlayer.handIsFull()) {
				playersThatCanAcceptACard.add(possiblePlayer);
			}
		});
		
		if(player.getCardsInPlayersHand().isEmpty()) {
			playerView.playerHasNoCards();
		}
		else if(playersThatCanAcceptACard.isEmpty()) {
			gameView.allHandsAreFull();
		}
		else {
			playerToGiveCardTo = playerView.selectPlayerFromList(playersThatCanAcceptACard);
			cardToGive = playerView.selectCardFromList(player.getCardsInPlayersHand());
			
			player.giveTreasureCard(playerToGiveCardTo, cardToGive);
			decreaseRemainingActions();
		}
		
	}
	
   /**
    * handleCaptureTreasure will facilitate a player claiming a treasure.
    */
	private void handleCaptureTreasure() {
		if(player.collectTreasure()) {
			TreasureEnum collectedTreasure = ((TreasureTile) Board.getInstance().getTileWithName(player.getLocation())).getTreasureType();
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
		Players players = Players.getInstance();
		ArrayList<Player> allPlayers = players.getPlayers();
		ArrayList<Player> playersWithHelicopterLift = new ArrayList<Player>();
		Player chosenPlayerWithHelicopterLift;
		
		ArrayList<TileEnum> tilesPlayersCanMoveTo = new ArrayList<TileEnum>();
		TileEnum tileToMoveTo;
		
		// Get players with helicopter lift
		for(Player aPlayer : allPlayers) {
			if(aPlayer.hasHelicopterLiftCard()) {
				playersWithHelicopterLift.add(aPlayer);
				break;
			}
		}
		// If no one has a helicopter lift
		if(playersWithHelicopterLift.isEmpty())
			playerView.noHelicopterLiftCards();
		
		// If someone has helicopter lift, check for win state
		else if(playersDidWin()) {
			gameOverObserver.playersWonGame();
			endGo();
			return;
		}
		
		// Gotten use of a HelicopterLift Card
		else {
			chosenPlayerWithHelicopterLift = playerView.choosePlayerWithHelicopterLift(playersWithHelicopterLift);
			
			playerView.selectPlayerToMove();
			ArrayList<Player> playersToMove = new ArrayList<>();
			for (Player player : allPlayers) {
				if(playerView.selectThisPlayerToMove(player))
					playersToMove.add(player);
			}
			if(playersToMove.isEmpty())
				playerView.noPlayersToHelicopterLift();
			else {
				tilesPlayersCanMoveTo = Board.getInstance().getUnsunkenTiles();
				tileToMoveTo = playerView.selectTileFromList(tilesPlayersCanMoveTo);
				for(Player player : playersToMove)
					player.move(tileToMoveTo);
				chosenPlayerWithHelicopterLift.didUseHelicopterLiftCard();
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
	private boolean playersDidWin() {
		Players players = Players.getInstance();
		ArrayList<Player> allPlayers = players.getPlayers(); 
		
		if(!TreasureManager.getInstance().didClaimAllTreasures())
			return false;	
		for(Player player: allPlayers) {
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
		
		if(player.getCardsInPlayersHand().isEmpty())
			playerView.playerHasNoCards();
		else {
			cardToDiscard = playerView.selectCardFromList(cardInHand);
			player.discardCard(cardToDiscard);
		}
	}
	
	/**
	 * sets turnHasEnded to true.
	 */
	private void endGo() {
		turnHasEnded = true;
	}
	
	//===========================================================
	// Draw Card Functions
	//===========================================================
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
		gameView.printNewLine(); 
	}
	
	/**
	 * drawCardsFromTreasureDeck method will draw the cards for a player and act accordingly.
	 * It will increase waterMeter level if required, or add a card to the player hand.
	 */
	private void drawCardsFromTreasureDeck() {
		TreasureDeck treasureDeck = TreasureDeck.getInstance();
		WaterMeter waterMeter = WaterMeter.getInstance();
		ArrayList<Card> treasureCards = treasureDeck.drawCards();
		
		playerView.printNumberDrawnCards(treasureCards.size());
	
		for(Card aCard : treasureCards) {
			playerView.printPlayerCard(aCard);
			if(aCard instanceof WaterRiseCard) {
				waterMeter.increaseWaterMeter();
				gameView.increasedWaterMeter();
				treasureDeck.discardCard(aCard);
			} 
			else
				putCardInPlayersHand(aCard);
		}
		gameView.printNewLine();
	}
	
	/**
	 * drawCardsFromFloodDeck is the last act a player does at the end of their go.
	 * It will change tiles flood status from cards taken from FloodDeck.
	 * It will move players off of sunk tiles and call gameOver if a gameOver condition is met.
	 * @return sunkPlayers, players that are now on sunk tiles.
	 */
	private ArrayList<Player> drawCardsFromFloodDeck() {
		ArrayList<TileEnum> tilesToFlood = FloodDeck.getInstance().getTilesToFlood(false);
		Board board = Board.getInstance();
		ArrayList<Player> sunkPlayers = new ArrayList<Player>();
		// check with treasure manager if treasuresAreAvailableToCollect
		for(TileEnum tile : tilesToFlood) {
			if(!board.getTileWithName(tile).isSunken()) {
				board.floodTile(tile);
				gameView.changeFloodStatus(tile);
			}
			if(board.getTileWithName(tile).isSunken()) {
				sunkPlayers = board.getPlayersFromTile(tile);
			}
		}
		gameView.printNewLine();
		return sunkPlayers;
	}
	
	//===========================================================
	// Move from Sunk Functions
	//===========================================================
	/**
	 * handleSunk moves this player from their current tile because it is sunk.
	 */
	public Boolean handlePlayerSunk() {
		ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
		Board board = Board.getInstance();
		
		tilesPlayerCanMoveTo.addAll(board.getTilesAroundTile(player.getLocation(), false));
		
		return moveFromSunk(tilesPlayerCanMoveTo);
	}
	
	
	/**
	 * moveFromSunk moves a player sunkPlayer from the sunk tile that they are on.
	 * @param sunkPlayer, a player that is on a sunk tile.
	 * @return true if the player is able to move. Else false.
	 */
	protected Boolean moveFromSunk(ArrayList<TileEnum> tilesPlayerCanMoveTo) {
		TileEnum chosenTile;
		
		if(tilesPlayerCanMoveTo.isEmpty()) {
			playerView.playerCantMove();
			return false;
		}
		
		chosenTile = playerView.tileToMoveFromSunk(tilesPlayerCanMoveTo);
		player.move(chosenTile);
		playerView.playerHasMoved(); 
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
