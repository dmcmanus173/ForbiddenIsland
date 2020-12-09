package fi.players;

import java.util.ArrayList;

import fi.board.Board;
import fi.board.TreasureTile;
import fi.cards.Card;
import fi.cards.Hand;
import fi.enums.AdventurerEnum;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.treasures.TreasureManager;

public class Player {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private String name;
	private AdventurerEnum role;
	private TileEnum location;
	private Hand playerHand;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Player object.
	 * @param String name, the name of the player.
	 * @param AdventurerEnum role, role of the player.
	 * @param ArrayList<AbstractTreasureCard> treasureCards, the treasureCards that the player will begin with.
	 */
	public Player(String name, AdventurerEnum role) {
		this.name = name;
		this.role = role;
		this.playerHand = new Hand();
		this.location = role.getStartingTileName();
	}
	
	//===========================================================
	// Functions for Board Interactions
	//===========================================================
	/**
	 * move player to destination
	 * @param destination, where the player is to move to.
	 */
	public void move(TileEnum destination) {
		Board.getInstance().movePlayer(this, destination);
		location = destination;
	}
	
	/**
	 * shoreUp method is used to shoreUp tileEnum tileToShoreUp
	 * @param tileToShoreUp
	 */
	public void shoreUp(TileEnum tileToShoreUp) {
		Board.getInstance().shoreUpTile(tileToShoreUp);
	}
	
	//===========================================================
	// Functions for Treasure Cards Interactions
	//===========================================================
	/**
	 * collectTreasureCard method is used to let a player receive a treasure Card.
	 * @param Card to add to players hand.
	 */
	public void collectTreasureCard(Card card) {
		playerHand.addCard(card);
	}
	
	public void giveTreasureCard(Player otherPlayer, Card card) {
		if(otherPlayer.handIsFull())
			throw new RuntimeException("Attemting to give card to player but player's hand is full.");
		else {
			playerHand.removeCard(card); // removes card from players hand before giving it to other player.
			otherPlayer.collectTreasureCard(card);
		}
	}
	
	/**
	 * discardCard method will remove a treasure card from player's hand.
	 * @param Card to be discarded.
	 */
	public void discardCard(Card card) {
		playerHand.discardCard(card);
	}
	
	public boolean handIsFull() {
		return playerHand.handIsFull();
	}
	
	public boolean hasSandBagCard() {
		return playerHand.containsSandbagCard();
	}
	
	public boolean hasHelicopterLiftCard() {
		return playerHand.containsHelicopterLiftCard();
	}
	
	public void didUseSandBagCard() {
		playerHand.removeSandbagCard();
	}
	
	public void didUseHelicopterLiftCard() {
		playerHand.removeHelicopterLiftCard();
	}
	
	public String cardsToString() {
		StringBuilder handString = new StringBuilder("");
		handString.append(name + " has the following cards in hand: \n" + playerHand.toString());
		return handString.toString();
	}
	
	//===========================================================
	// Functions for Treasure Interactions
	//===========================================================
	
	public boolean canCollectTreasure() {
		return TreasureManager.getInstance().canCollectTreasure(playerHand, location);
	}
	
	public TreasureEnum collectTreasure() {
		if(location.hasTreasure()) {
			 TreasureTile treasureTile = (TreasureTile) Board.getInstance().getTileWithName(location);
			 TreasureEnum treasureType = treasureTile.getTreasureType();
			 TreasureManager.getInstance().claimTreasure(playerHand, treasureType);
			 return treasureType;
		 } 
		else
			 throw new RuntimeException("Player is attempting to collect a treasure on a tile that is not the treasure tile.");
	}

	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * getName method returns the players chosen name as a String.
	 * @return String name, the players chosen name at the start of game.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * getRole method will return the role of the player.
	 * @return AdventurerEnum role, the role of the player.
	 */
	public AdventurerEnum getRole() {
		return role;
	}
	
	/**
	 * getLocation method will return the tile that the player is currently located.
	 * @return Tile location, the location the player is at.
	 */
	public TileEnum getLocation() {
		return location;
	}
	
	public ArrayList<Card> getCardsInPlayersHand() {
		return playerHand.getCards();
	}
	
	/**
	 * toString method will return player info as a String.
	 * @return String info related to player.
	 */
	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append("Player Name: "+name);
		temp.append("\nRole: "+role.toString());
		temp.append("\nLocation: "+location.toString());
		return temp.toString();
	}
		

}