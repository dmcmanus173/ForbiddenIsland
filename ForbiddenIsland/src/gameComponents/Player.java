package gameComponents;

import java.util.ArrayList;

import board.Tile;
import enums.AdventurerEnum;

/**
 * Class for the Player in Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */
public class Player {

	//===========================================================
	// Variable Setup
	//===========================================================
	private String name;
	private AdventurerEnum role;
	private Tile location;
	private ArrayList<AbstractTreasureCard> treasureCards = new ArrayList<>();
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MAX_TREASURE_CARDS = 5;
	private final int NUM_INITIAL_CARDS = 2;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Player object.
	 * @param String name, the name of the player.
	 * @param AdventurerEnum role, role of the player.
	 * @param Tile location, tile the Player will begin the game.
	 * @param ArrayList<AbstractTreasureCard> treasureCards, the treasureCards that the player will begin with.
	 */
	public Player(String name, AdventurerEnum role) {
		this.name = name;
		this.role = role;
		this.location = null; //TODO start location in player
		for(int i=0; i<NUM_INITIAL_CARDS; i++)
			treasureCards.add(TreasureDeck.getInstance().getNextCard());
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * getLocation method will return the tile that the player is currently located.
	 * @return Tile location, the location the player is at.
	 */
	public Tile getLocation() {
		return location;
	}
	
	/**
	 * removeTreasureCard method will remove a TreasureCard from player inventory.
	 */
	public void removeTreasureCard() {
		//TODO Change type to TreasureCard, ensure removing of card is done. 
		System.out.println(treasureCardsToString());
	}
	
	/**
	 * treasureCardsToString method returns the treasureCards that the player has
	 * in string format.
	 * @return String containing the cards that the player has in possession.
	 */
	public String treasureCardsToString() {
		StringBuilder temp = new StringBuilder("");
		for(AbstractTreasureCard card : treasureCards)
			temp.append("Index: " + treasureCards.indexOf(card) + " - " + card.toString() + "\n"); //TODO add index number in ArrayList
		return temp.toString();
	}
	
	/**
	 * assignTreasureCard method will add card to player inventory.
	 * If player has too many cards, will require removal of one.
	 * @param AbstractTreasureCard card, the card to add to inventory. 
	 */
	public void assignTreasureCard(AbstractTreasureCard card) {
		treasureCards.add(card);
		if( treasureCards.size() == MAX_TREASURE_CARDS+1) {
			removeTreasureCard();
		}
		//TODO Possibly return null, if no card to remove...
	}
	
	/**
	 * giveTreasureCard will facilitate giving a treasureCard from the inventory
	 * of AbstractTreasureCards to a different player.
	 * @param Player otherPlayer, the player to give a card to.
	 * @param AbstractTreasureCard, the card to give away.
	 * @return if can give the card, return AbstractTreasureCard, else return null.
	 */
	public AbstractTreasureCard giveTreasureCard(Player otherPlayer, AbstractTreasureCard card) {
		if( (location == otherPlayer.getLocation() )||( role == AdventurerEnum.MESSENGER) ) {
			if(treasureCards.contains(card)) {
				treasureCards.remove(card);
				return card;
			}
			else System.out.println(name + " doesn't have the card " + card.getCardType().toString() );
				return null;
		}
		System.out.println("These players can't trade cards, they are not near each other.");
		return null; // No card to give, return NULL
	}
	
	/**
	 * move method will move a player to a different tile.
	 */
	public void move () {
		//TODO Add content
		// location = new location
	}
	
	/**
	 * shoreUp will allow a player to shore-up a flooded island if it is flooded, and the player contains a sandbag card.
	 * @param Tile islandTile to shore-up.
	 */
	public void shoreUp(Tile islandTile) {
		//TODO Must resolve to shore any tile adjacent to player
		SandbagCard sandbag = new SandbagCard(); //TODO see if this works, might change to iterative search
		if( treasureCards.contains(sandbag) ) {
			if( islandTile.shoreUp() ) treasureCards.remove(sandbag);
		}
		else
			System.out.println(name + " does not have a Sandbag card.");
	}
	
	/**
	 * cardsToString method will return cards a player has.
	 * @return String info related to player.
	 */
	public String cardsToString() {
		StringBuilder temp = new StringBuilder("");
		temp.append(name + " has the following cards:");
		for(AbstractTreasureCard card : treasureCards)
			temp.append("\n" + card.toString());
		return temp.toString();
	}
	
	/**
	 * toString method will return player info as a String.
	 * @return String info related to player.
	 */
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append("Player Name: "+name);
		temp.append("\nRole: "+role.toString());
//		temp.append("\nLocation: "+location.getTileName().toString());
		temp.append("\n"+cardsToString());
		return temp.toString();
	}
}
