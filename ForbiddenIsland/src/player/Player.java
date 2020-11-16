package player;

import java.util.ArrayList;

import board.Board;
import board.Tile;
import board.TreasureTile;
import enums.AdventurerEnum;
import enums.FloodStatusEnum;
import enums.TreasureCardEnum;
import enums.TreasureEnum;
import gameComponents.AbstractTreasureCard;
import gameComponents.HelicopterLiftCard;
import gameComponents.SandbagCard;
import gameComponents.TreasureCard;
import gameComponents.TreasureDeck;
import gameComponents.WaterMeter;
import getInput.GetInput;
import roles.Diver;
import roles.Engineer;
import roles.Explorer;
import roles.Messenger;
import roles.Navigator;
import roles.Pilot;
import roles.Role;

/**
 * Class for the Player in Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
public class Player {

	//===========================================================
	// Variable Setup
	//===========================================================
	private String name;
	private Role role;
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
		
		if(role == AdventurerEnum.PILOT)
			this.role = new Pilot();
		else if(role == AdventurerEnum.DIVER)
			this.role = new Diver();
		else if(role == AdventurerEnum.NAVIGATOR)
			this.role = new Navigator();
		else if(role == AdventurerEnum.MESSENGER)
			this.role = new Messenger();
		else if(role == AdventurerEnum.ENGINEER)
			this.role = new Engineer();
		else if(role == AdventurerEnum.EXPLORER)
			this.role = new Explorer();
		else 
			System.out.println("Error: Couldn't find role");

		location = this.role.startPosition(); 
		location.addPlayerToTile(this);
		
		// Adding NUM_INITIAL_CARDS to TreasueCard collection. If get WaterRiseCard, put back and try again.
		AbstractTreasureCard temp;
		for(int i=0; i<NUM_INITIAL_CARDS; i++) {
			temp = TreasureDeck.getInstance().getNextCard();
			while(temp.getCardType() == TreasureCardEnum.WATER_RISE ) {
				TreasureDeck.getInstance().returnUsedCard(temp);
				temp = TreasureDeck.getInstance().getNextCard();
			}
			receiveCard(temp);
		}
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
	 * getRole method will return the role of the player.
	 * @return AdventurerEnum role, the role of the player.
	 */
	public AdventurerEnum getRole() {
		return role.getRole();
	}
	
	/**
	 * getNumTreasureCards gets the number of treasure cards the player has in possession.
	 * @return integer numTreasureCards, the number of treasure cards a player has.
	 */
	public int getNumTreasureCards() {
		return numTreasureCards;
	}
	
	/**
	 * getName method returns the players chosen name as a String.
	 * @return String name, the players chosen name at the start of game.
	 */
	public String getName() {
		return name;
	}

	/**
	 * removeTreasureCard method will remove a TreasureCard from player inventory.
	 */
	public void removeTreasureCard() {
		System.out.println("Choose the card number for the card to remove: ");
		printCardsHeld();
		AbstractTreasureCard removedCard = treasureCards.get(GetInput.getInstance().anInteger(1, numTreasureCards)-1);
		removeTreasureCard( removedCard );
	}
	
	/**
	 * removeTreasureCard method will remove a TreasureCard from player inventory.
	 * @param AbstractTreasureCard aCard to be removed.
	 */
	public void removeTreasureCard(AbstractTreasureCard aCard) {
		treasureCards.remove(aCard);
		TreasureDeck.getInstance().returnUsedCard(aCard);
		numTreasureCards -= 1;
	}
	
	/**
	 * treasureCardsToString method returns the treasureCards that the player has
	 * in string format.
	 * @return String containing the cards that the player has in possession.
	 */
	public String treasureCardsToString() {
		StringBuilder temp = new StringBuilder("");
		for(int i=0; i<numTreasureCards; i++)
			temp.append( (i+1) + ". " + treasureCards.get(i).toString() + "\n");
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
		else {
			receiveCard(aCard);
			System.out.println(aCard.toString());
		}
		
		if( numTreasureCards == MAX_TREASURE_CARDS+1) {
			System.out.println("You have exceeded the max number of cards you can carry.");
			removeTreasureCard();
		}
	}
	
	/**
	 * claimTreasure method will allow a player to claim a treasure if they:
	 * 1. Have the correct number of cards.
	 * 2. Are on a treasureTile.
	 * 3. The treasure has not been claimed already.
	 * @return Boolean variable for if a treasure has been claimed.
	 */
	public Boolean claimTreasure() {
		if( location.getClass() == TreasureTile.class ) {
			TreasureTile treasureTile = (TreasureTile) location;
			TreasureEnum treasureType = treasureTile.getTreasureType();
			AbstractTreasureCard treasureCard = null;
			for( AbstractTreasureCard aTreasureCard : TreasureDeck.getInstance().getDifferentTreasureCards() )
				if( ((TreasureCard) aTreasureCard).getTreasureType() == treasureType) treasureCard = aTreasureCard;
			int countTreasureCards = 0;
			for(AbstractTreasureCard aTreasureCard : treasureCards) {
				if(treasureCard == aTreasureCard) 
						countTreasureCards += 1;
			}
			if(countTreasureCards > 4) {
				for(int i=0; i<4; i++)
					removeTreasureCard(treasureCard);
				Rucksack.getInstance().isTreasureClaimed(treasureType);
				return true;
			}
			else {
				System.out.println("Not enough treasure cards to claim the "+treasureType.toString());
				return false;
			}
		}
		else {
			System.out.println("Not on a treasure tile. Can't claim a treasure.");
			return false;
		}
	}
	
	/**
	 * giveTreasureCard will facilitate giving a treasureCard from the inventory
	 * of AbstractTreasureCards to a different player.
	 * @return Boolean variable for if can give a card away.
	 */
	public Boolean giveTreasureCard() {
		ArrayList<Player> potentialPlayers = new ArrayList<>();
		potentialPlayers.addAll( role.getPlayersForGiveTreasureCard(this, location, MAX_TREASURE_CARDS) );
		if(numTreasureCards == 0) {
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
			Player chosenPlayer = potentialPlayers.get( GetInput.getInstance().anInteger(1,potentialPlayers.size())-1 );
			System.out.println("Choose the card to give.");
		    printCardsHeld();
			AbstractTreasureCard chosenCard = treasureCards.get( GetInput.getInstance().anInteger(1,numTreasureCards)-1 );
			chosenPlayer.receiveCard(chosenCard);
			removeTreasureCard(chosenCard);
			return true;
		}
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
	 * TilesForIfOnSunkTile method is used to getTiles that player can move to if on a sunken tile.
	 * Diver can move to the nearest tile
	 * Pilot can move to any tile
	 * Explorer can also move to diagonal tile
	 * Rest of players can move to opposite tile
	 * @return ArrayList<Tile>, a tile containing tiles that player can move to
	 */
	private ArrayList<Tile> TilesForIfOnSunkTile() {
		ArrayList<Tile> potentialTiles = new ArrayList<>();
		potentialTiles.addAll( role.getTilesForIfOnSunk(location) );
		
		return potentialTiles;		
	}
	
	/**
	 * Removes a player from a tile, then places them on a new tile.
	 * @param newLocation, the location to move the player to.
	 */
	private void changeLocation(Tile newLocation) {
		location.removePlayerFromTile(this);
		location = newLocation;
		location.addPlayerToTile(this);
	}
	
	/*
	 * UseHelicopterLift method will use a HelicopterLift card if there is one in the inventory,
	 * Otherwise won't be able to move.
	 * @return Boolean true if was able to move using card, else returns false if do not have card.
	 */
	private void UseHelicopterLift() {
		removeTreasureCard(TreasureDeck.getInstance().aHelicopterLift());
			
		ArrayList<Tile> potentialTiles = new ArrayList<>();
		potentialTiles.addAll( Board.getInstance().getUnsunkenTiles() );
		Tile chosenTile = selectOptionTiles(potentialTiles);
		changeLocation(chosenTile);
	}
	
	/**
	 * move method to be called if player must move.
	 * If called when player on sunk tile, it will get a list of tiles to move to with respect to said rule.
	 * If called when player is not on sunk tile, i.e. during a typical go, player can move to opposite tile.
	 * @return Boolean, return false if no tile to move to, else true if player has successfully moved.
	 */
	public Boolean move () {
		ArrayList<Tile> potentialTiles = new ArrayList<Tile>();
		if(location.isSunken())
			potentialTiles.addAll( TilesForIfOnSunkTile() );
		// If typical user move...
		else {
			if( hasHelicopterLiftCard() ) {
				System.out.println("Would you like to use your Helicopter Lift card?" );
				System.out.println("1. Yes.");
				System.out.println("2. No.");
				if( GetInput.getInstance().anInteger(1, 2) == 1 ) {
					UseHelicopterLift();
					return true;
				}
			}
			// Otherwise a regular move...
			potentialTiles.addAll( Board.getInstance().getTilesAroundTile(location, true) );
		}
		if(potentialTiles.isEmpty()) {
			System.out.println(getName()+" is unable to move.");
			return false;
		}
		Tile chosenTile = selectOptionTiles(potentialTiles);
		changeLocation(chosenTile);
		return true;
	}
	
	/**
	 * selectOptionTiles method prints tiles from an ArrayList<Tile>, which is passed as parameter.
	 * It then requires user to interact via console in choosing a tile.
	 * It returns the chosen tile.
	 * @param optionTiles, ArrayList of tiles to be offered as options.
	 * @return Tile, the tile chosen.
	 */
	private Tile selectOptionTiles(ArrayList <Tile> optionTiles) {
		System.out.println("Choose one of the following tiles:");
		for (int i=0; i<optionTiles.size(); i++) {
			System.out.println("Tile "+(i+1)+": "+optionTiles.get(i).getTileName());
		}
		Tile chosenTile = optionTiles.get(  GetInput.getInstance().anInteger(1, optionTiles.size())-1 );
		return chosenTile;
	}
	
	/**
	 * shoreUp will allow a player to shore-up a flooded island if it is flooded, and the player contains a sandbag card.
	 * @param Tile islandTile to shore-up.
	 */
	public Boolean shoreUp() {
		if( treasureCards.contains( TreasureDeck.getInstance().aSandbag() ) ) {
			ArrayList<Tile> potentialTiles = new ArrayList<>();
			System.out.println("DEBUG: added tiles");//TODO remove line
			for(Tile tile : Board.getInstance().getTilesAroundTile(location, true)) {
				if( tile.getFloodStatus() == FloodStatusEnum.FLOODED ) 
					potentialTiles.add(tile);
			}
			System.out.println("Finished loop");//TODO remove line
			if ( !potentialTiles.isEmpty() ) {
				Tile chosenTile = selectOptionTiles(potentialTiles);
				chosenTile.shoreUp();
				removeTreasureCard( TreasureDeck.getInstance().aSandbag() );
				return true;
			}
			else System.out.println("There are no local Tiles to shoreUp.");
		}
		else
			System.out.println(name + " does not have a Sandbag card.");
		return false;
	}
	
	
	/**
    * hasHelicopterLiftCard method checks if a player has a helicopterLift card.
    * Also checks to see if a winning condition is met.
    * @return boolean determining if player has helicopterLift card..
    */
	public boolean hasHelicopterLiftCard() {
		return treasureCards.contains(TreasureDeck.getInstance().aHelicopterLift());
	}
	
	/**\\
	 * toString method will return player info as a String.
	 * @return String info related to player.
	 */
	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append("Player Name: "+name);
		temp.append("\nRole: "+role.toString());
		temp.append("\nLocation: "+location.getTileName().toString());
		temp.append("\n"+treasureCardsToString());
		return temp.toString();
	}
	

}

