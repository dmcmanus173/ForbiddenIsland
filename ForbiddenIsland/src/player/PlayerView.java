package player;

import java.util.ArrayList;

import fi.board.Board;
import board.FloodDeck;
import fi.board.Tile;
import fi.board.TreasureTile;
import fi.enums.AdventurerEnum;
import gameManager.GetInput;
import fi.watermeter.WaterMeter;
import fi.cards.Card;
import fi.cards.HelicopterLiftCard;
import fi.cards.SandbagCard;
import fi.cards.TreasureDeck;
import fi.cards.WaterRiseCard;

public class PlayerView extends Player {
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MAX_TREASURE_CARDS = 5;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Player object.
	 * @param String name, the name of the player.
	 * @param AdventurerEnum role, role of the player.
	 * @param ArrayList<AbstractTreasureCard> treasureCards, the treasureCards that the player will begin with.
	 */
	public PlayerView(String name, AdventurerEnum role) {
		super(name, role);
	}
	
	/**
	 * move method to be called if player must move.
	 * If called when player is not on sunk tile, i.e. during a typical go, player can move to opposite tile.
	 * @return Boolean, return false if no tile to move to, else true if player has successfully moved.
	 */
	public Boolean moveDuringGo () {
		ArrayList<Tile> potentialTiles = new ArrayList<Tile>();
		potentialTiles.addAll( Board.getInstance().getTilesAroundTile(getLocation(), true) );
		if(potentialTiles.isEmpty()) {
			System.out.println(getName()+" is unable to move.");
			return false;
		}
		Tile chosenTile = selectOptionTiles(potentialTiles);
		changeLocation(chosenTile);
		return true;
	}
	
	/**
	 * claimTreasure method will allow a player to claim a treasure if they:
	 * 1. Have the correct number of cards.
	 * 2. Are on a treasureTile.
	 * 3. The treasure has not been claimed already.
	 * @return Boolean variable for if a treasure has been claimed.
	 */
	public Boolean claimTreasure() {
		int numCardsForTreasure = 4;
		Tile location = getLocation();
		if( location instanceof TreasureTile ) {
			if( ((TreasureTile) location).collectTreasure(treasureCards) ) {
				Card cardToRemove = TreasureDeck.getInstance().getTreasureCardReference( ((TreasureTile) location).getTreasureType() );
				for(int i=0; i<numCardsForTreasure; i++)
					removeTreasureCard(cardToRemove);
				return true;
			}
			else
				return false;
		
		}
		else {
			System.out.println("Not on a treasure tile. Can't claim a treasure.");
			return false;
		}
	}
	
	/**
	 * shoreUpWrapper is the face to the shoreUpContent function. This is to be called in the GameManager.
	 * It will interact with the player role to ensure shoreUpContent has been called the correct number of times.
	 */
	public Boolean shoreUpWrapper() {
		if( getRole().shoreUp(this) )
			return true;
		return false; // if can't shore up a tile, return false.
	}
	
	/*
	 * useHelicopterLift method will use a HelicopterLift card if there is one in the inventory.
	 */
	public void useHelicopterLift() {
		System.out.println("Using "+getName()+"'s HelicopterLift card.");
		ArrayList<Player> playersToMove = new ArrayList<>();
		for (Player player : Players.getInstance().getPlayers()) {
			System.out.println("Would you like to move "+player.getName()+" with Helicopter Lift?");
			System.out.println("1. Yes.");
			System.out.println("2. No.");
			if( GetInput.getInstance().anInteger(1, 2) == 1 )
				playersToMove.add(player);
		}
		if(playersToMove.isEmpty())
			System.out.println("Didn't select any players to move. Won't use the Helicopter Lift Card.");
		else {
			removeCardOfClassType(HelicopterLiftCard.class);
			ArrayList<Tile> potentialTiles = new ArrayList<>();
			potentialTiles.addAll( Board.getInstance().getUnsunkenTiles() );
			Tile chosenTile = selectOptionTiles(potentialTiles);
			for(Player player : playersToMove) {
				player.changeLocation(chosenTile);
			}
		}
	}
	
	/**
	 * getTreasureCards method will take a required number of card from the TreasureDeck.
	 * If the TreasureCard is a Water Rise card, it will be used immediately. 
	 */
	private void getTreasureCards() {
		TreasureDeck treasureDeck = TreasureDeck.getInstance();
		ArrayList<Card> gottenCards = treasureDeck.drawCards();
		for(Card aCard : gottenCards) {
			if( aCard instanceof WaterRiseCard ) {
				WaterMeter.getInstance().increaseWaterMeter();
				treasureDeck.discardCard(aCard);
			}
			else {
				System.out.println("You have drawn the following card: ");
				System.out.println(aCard.toString()						);
				System.out.println("Do you wish to:"					);
				System.out.println("1. Keep it."						);
				System.out.println("2. Give it to another player."		);
				System.out.println("3. Put it back."				    );
				int option = GetInput.getInstance().anInteger(1, 3);
				if( option == 1 )
					receiveCard(aCard);
				else if( option == 2 ) {
					if( !giveTreasureCard() ) {
						System.out.println("Do you wish to:"			);
						System.out.println("1. Keep it."				);
						System.out.println("2. Put it back."		    );
						option = GetInput.getInstance().anInteger(1, 3);
						if( option == 1 )
							receiveCard(aCard);
						else
							treasureDeck.discardCard(aCard);
					}	
				}
				else
					treasureDeck.discardCard(aCard);
			}
			if( getNumTreasureCards() == MAX_TREASURE_CARDS+1) {
				System.out.println("You have exceeded the max number of cards you can carry.");
				removeATreasureCard();
			}
		}
	}
	
	/**
	 * giveTreasureCard will facilitate giving a treasureCard from the inventory
	 * of AbstractTreasureCards to a different player.
	 * @return Boolean variable for if can give a card away.
	 */
	public Boolean giveTreasureCard() {
		GetInput getInput = GetInput.getInstance();
		ArrayList<Player> potentialPlayers = new ArrayList<>();
		potentialPlayers.addAll( getRole().getPlayersForGiveTreasureCard(this, getLocation(), MAX_TREASURE_CARDS) );
		if(getNumTreasureCards() == 0) {
			System.out.println("There are no Treasure Cards to give.");
			return false;
		}
		else if(potentialPlayers.isEmpty()) {
			System.out.println("There are no players to give cards to.");
			return false;
		}
		else {
			// Choose Player
			System.out.println("Choose a player to give a card to.");
		    for(int i=0; i<potentialPlayers.size(); i++)
		    	System.out.println((i+1 )+ ": " + potentialPlayers.get(i).getName() );
			Player chosenPlayer = potentialPlayers.get( getInput.anInteger(1,potentialPlayers.size())-1 );
			// Choose card to give
			System.out.println("Choose the card to give.");
		    printCardsHeld();
			Card chosenCard = treasureCards.get( getInput.anInteger(1,getNumTreasureCards())-1 );
			// Give card away
			sendCard(chosenCard, chosenPlayer);
			return true;
		}
	}
	
	/**
	 * removeATreasureCard method will poll player to remove a TreasureCard from inventory.
	 */
	public void removeATreasureCard() {
		System.out.println("Choose the card number for the card to remove: ");
		printCardsHeld();
		int option = GetInput.getInstance().anInteger(1, getNumTreasureCards()) ;
		removeTreasureCard( option );
	}
	
	/**
	 * printCardsHeld method will print to the console the cards that a player has in their possession.
	 */
	public void printCardsHeld() {
		if( getNumTreasureCards() == 0 )
			System.out.println(getName() + " has no cards.");
		else
			System.out.println(treasureCardsToString());
	}
	
	/**
	 * endOfGo method is the actions a player takes after taking their 3 turns during a go.
	 * It includes: taking 2 TreasureCards
	 * 				drawing Flood Cards
	 */
	public void endOfGo() {
    	System.out.println(getName()+" is drawing two cards from the TreasureDeck:");
    	getTreasureCards();
    	
    	System.out.println("\n"+getName()+" is drawing flood cards.");
    	FloodDeck.getInstance().drawFloodCards();
	}

}
