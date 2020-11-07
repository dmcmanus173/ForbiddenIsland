package player;

import java.util.ArrayList;
import java.util.Scanner;

import board.Board;
import board.Tile;
import enums.AdventurerEnum;
import enums.TileEnum;
import enums.TreasureCardEnum;
import gameComponents.AbstractTreasureCard;
import gameComponents.SandbagCard;
import gameComponents.TreasureDeck;
import gameComponents.WaterMeter;

/**
 * Class for the Player in Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    04/11/2020
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
	private int numTreasureCards;
	
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
	 * @param ArrayList<AbstractTreasureCard> treasureCards, the treasureCards that the player will begin with.
	 */
	public Player(String name, AdventurerEnum role) {
		this.name = name;
		this.role = role;
		startPosition(); 
		
		// Adding NUM_INITIAL_CARDS to TreasueCard collection. If get WaterRiseCard, put back and try again.
		AbstractTreasureCard temp;
		for(int i=0; i<NUM_INITIAL_CARDS; i++) {
			temp = TreasureDeck.getInstance().getNextCard();
			while(temp.getCardType() == TreasureCardEnum.WATER_RISE ) {
				TreasureDeck.getInstance().returnUsedCard(temp);
				temp = TreasureDeck.getInstance().getNextCard();
			}
			treasureCards.add(temp);
		}
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * startPosition method will return the tile that a player is to start on.
	 * @return Tile startPosition, where the player for a given role should start the game.
	 */
	private void startPosition() {
		// Engineer - Bronze Gate
		// Explorer - Copper Gate
		// Diver - Iron Gate
		// Pilot - Fools' Landing
		// Messenger - Silver Gate
		// Navigator - Gold Gate
		TileEnum tileName;
		if (role == AdventurerEnum.ENGINEER)
			tileName = TileEnum.BRONZE_GATE;
		else if (role == AdventurerEnum.DIVER)
			tileName = TileEnum.IRON_GATE;
		else if (role == AdventurerEnum.EXPLORER)
			tileName = TileEnum.COPPER_GATE;
		else if (role == AdventurerEnum.PILOT)
			tileName = TileEnum.FOOLS_LANDING;
		else if (role == AdventurerEnum.MESSENGER)
			tileName = TileEnum.SILVER_GATE;
		else if (role == AdventurerEnum.NAVIGATOR)
			tileName = TileEnum.GOLDEN_GATE;
		else {
			System.out.println("Error. Can't find start position.");
			tileName = null;
			location = null;
		}
		
		if ( !tileName.equals(null) ) {
			ArrayList<Tile> tiles = Board.getInstance().getIslandTiles();
			for(Tile tile : tiles) {
				if( tile.getTileName().equals(tileName) )
					location = tile;
			}
		}
	}
	
	/**
	 * getLocation method will return the tile that the player is currently located.
	 * @return Tile location, the location the player is at.
	 */
	public Tile getLocation() {
		return location;
	}
	
	/**
	 * getRole method will return the role of the player.
	 * @return AdventurerEnum role, the role of the player.
	 */
	public AdventurerEnum getRole() {
		return role;
	}

	/**
	 * removeTreasureCard method will remove a TreasureCard from player inventory.
	 */
	public void removeTreasureCard() {
		System.out.println("Choose the card number to remove card: ");
		printCardsHeld();
		AbstractTreasureCard removedCard = treasureCards.remove(getOptionNumber(1, numTreasureCards)-1);
		TreasureDeck.getInstance().returnUsedCard(removedCard);
	}
	
	/**
	 * getOptionNumber
	 * @param userString
	 * @return integer value of option picked
	 */
	private int getOptionNumber(int minOption, int maxOption) {
		Scanner user = new Scanner(System.in);
		Boolean validInput = false;
		int option = 0;
		while( !validInput ) {
			String userString = user.nextLine();
			try {
				option = Integer.parseInt(userString);
			} catch (NumberFormatException e) {
				return option;
			}
			
			if ((option >= minOption) && (option <= maxOption)) {
				validInput = true;
				user.close();
			}
			else
				System.out.println("Incorrect input");
		}
		return option;
	}
	
	/**
	 * treasureCardsToString method returns the treasureCards that the player has
	 * in string format.
	 * @return String containing the cards that the player has in possession.
	 */
	public String treasureCardsToString() {
		StringBuilder temp = new StringBuilder("");
		for(int i=0; i<numTreasureCards; i++)
			temp.append( (i+1) + ". " + treasureCards.get(i).toString() );
		return temp.toString();
	}
	
	/**
	 * printCardsHeld method will print to the console the cards that a player has in their possession.
	 */
	public void printCardsHeld() {
		if( numTreasureCards == 0 )
			System.out.println(name + " has no cards.");
		else
			System.out.println(treasureCardsToString());
	}
	
	/**
	 * getTreasureCard method will take a card from the TreasureDeck.
	 * If the TreasureCard is a Water Rise card, it will be used immediately. 
	 */
	public void getTreasureCard() {
		AbstractTreasureCard aCard = TreasureDeck.getInstance().getNextCard();
		if( aCard.getCardType() == TreasureCardEnum.WATER_RISE ) {
			WaterMeter.getInstance().increaseWaterMeter();
			TreasureDeck.getInstance().returnUsedCard(aCard);
		}
		else if( numTreasureCards == MAX_TREASURE_CARDS) {
			treasureCards.add(aCard);
			System.out.println("You have exceeded the max number of cards you can carry.");
			System.out.println("You can either Remove a card (Option 1), or Give a card (Option 2).");
			System.out.println("Pick an option: (1 or 2)");
			int option = getOptionNumber(1,2);
			if( option == 1)
				removeTreasureCard();
			else {
//				System.out.println("Debug: giveTreasureCard if possible");
				if (giveTreasureCard() ) {
					numTreasureCards += 1; // giveTreasureCard decrements numTreasureCards, must balance.
				}					
				else
					removeTreasureCard();
			}
		}
		else {
			receiveCard(aCard);
		}
	}
	
	/**
	 * getNumTreasureCards gets the number of treasure cards the player has in possession.
	 * @return int numTreasureCards, the number of treasure cards a player has.
	 */
	public int getNumTreasureCards() {
		return numTreasureCards;
	}
	
	/**
	 * giveTreasureCard will facilitate giving a treasureCard from the inventory
	 * of AbstractTreasureCards to a different player.
	 * @param Player otherPlayer, the player to give a card to.
	 * @param AbstractTreasureCard, the card to give away.
	 * @return if can give the card, return AbstractTreasureCard, else return null.
	 */
	public Boolean giveTreasureCard() {
		ArrayList<Player> potentialPlayers = new ArrayList<Player>();
		if( role == AdventurerEnum.MESSENGER ) {
			System.out.println("This player is the Messenger. They can give a card to anyone.");
			ArrayList<Player> allPlayers = Players.getInstance().getPlayers();
			for(Player aPlayer : allPlayers) {
				if( (aPlayer.getNumTreasureCards() < MAX_TREASURE_CARDS) && (this != aPlayer))
					potentialPlayers.add(aPlayer);
			}
		}
		else {
			System.out.println("This player can only give cards to who is on their tile.");
			ArrayList<Player> tilePlayers = new ArrayList<>(location.getPlayersOnTile());
			for(Player aPlayer : tilePlayers) {
				if( (aPlayer.getNumTreasureCards() < MAX_TREASURE_CARDS) && (this != aPlayer))
					potentialPlayers.add(aPlayer);
			}
		}
		if(potentialPlayers.isEmpty()) {
			System.out.println("There are no players to give cards to.");
			return false;
		}
		else {
			// Choose Player
			System.out.println("Choose a player to give a card to.");
		    for(int i=0; i<potentialPlayers.size(); i++)
		    	System.out.println((i+1 )+ ": " + potentialPlayers.get(i).getName() );
			Player chosenPlayer = potentialPlayers.get( getOptionNumber(1,potentialPlayers.size())-1 );
			// Choose Card TODO: Print the cards.
			System.out.println("Choose the card to give.");
		    for(int i=0; i<numTreasureCards; i++)
		    	System.out.println((i+1) + ": " + treasureCards.get(i) );
			AbstractTreasureCard chosenCard = treasureCards.get( getOptionNumber(1,numTreasureCards)-1 );
			chosenPlayer.receiveCard(chosenCard);
			treasureCards.remove(chosenCard);
			numTreasureCards -= 1;
			return true;
		}
	}
	
	/**
	 * getName method returns the players chosen name as a String.
	 * @return String name, the players chosen name at the start of game.
	 */
	private String getName() {
		return name;
	}
	
	/**
	 * receiveCard method is used to let a player receive a TreasureCard.
	 * @param card, an AbstractTreasureCard to receive.
	 */
	private void receiveCard(AbstractTreasureCard card) {
		treasureCards.add(card);
		numTreasureCards += 1;
	}
	
	/**
	 * move method will move a player to a different tile.
	 */
	public void move () {
		//Board.getInstance().
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
	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append("Player Name: "+name);
		temp.append("\nRole: "+role.toString());
		temp.append("\nLocation: "+location.getTileName().toString());
		temp.append("\n"+cardsToString());
		return temp.toString();
	}
}
