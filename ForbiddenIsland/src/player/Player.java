package player;

import java.util.ArrayList;

import fi.board.Board;
import fi.board.Tile;
import fi.enums.AdventurerEnum;
import fi.enums.FloodStatusEnum;
import fi.roles.Diver;
import fi.roles.Engineer;
import fi.roles.Explorer;
import fi.roles.Messenger;
import fi.roles.Navigator;
import fi.roles.Pilot;
import fi.roles.Role;
import gameManager.GetInput;
import fi.cards.Card;
import fi.cards.HelicopterLiftCard;
import fi.cards.SandbagCard;
import fi.cards.TreasureDeck;

/**
 * Class for the Player in Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
abstract class Player {

	//===========================================================
	// Variable Setup
	//===========================================================
	private String name;
	private Role role;
	private Tile location;
	protected ArrayList<Card> treasureCards = new ArrayList<>();
	private int numTreasureCards;
	
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
		   	throw new RuntimeException("Can't find role in Player Constructor.");

		location = this.role.startPosition(); 
		Board.getInstance().setUpPlayerOnBoard(this);
		
		// Adding NUM_INITIAL_CARDS to TreasueCard collection. If get WaterRiseCard, put back and try again.
		ArrayList<Card> cards = TreasureDeck.getInstance().drawCardsForStartOfGame();
		for(Card aCard : cards) {
			receiveCard(aCard);
		}
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
	 * @return role, the role of the player.
	 */
	public Role getRole() {
		return role;
	}
	
	/**
	 * getLocation method will return the tile that the player is currently located.
	 * @return Tile location, the location the player is at.
	 */
	public Tile getLocation() {
		return location;
	}
	
	/**
	 * getNumTreasureCards gets the number of treasure cards the player has in possession.
	 * @return integer numTreasureCards, the number of treasure cards a player has.
	 */
	public int getNumTreasureCards() {
		return numTreasureCards;
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
		temp.append("\n"+treasureCardsToString());
		return temp.toString();
	}
	
	/**
	 * receiveCard method is used to let a player receive a TreasureCard.
	 * @param card, an AbstractTreasureCard to receive.
	 */
	protected void receiveCard(Card card) {
		treasureCards.add(card);
		numTreasureCards += 1;
	}
	
	/**
	 * removeTreasureCard method will remove a TreasureCard from player inventory.
	 * @param AbstractTreasureCard aCard to be removed.
	 */
	protected void removeTreasureCard(Card cardToRemove) {
		treasureCards.remove(cardToRemove);
		TreasureDeck.getInstance().discardCard(cardToRemove);
		numTreasureCards -= 1;
	}
	
	/**
	 * removeTreasureCard method will remove a TreasureCard from player inventory.
	 * @param position of Treasure Card to remove
	 */
	protected void removeTreasureCard(int position) {
		// Position on screen 1-numTreasureCards, in Array it is 0-(numTreasureCards-1), thus position-1
		Card aCard = treasureCards.get(position-1);
		treasureCards.remove(aCard);
		TreasureDeck.getInstance().discardCard(aCard);
		numTreasureCards -= 1;
	}
	
	/**
	 * treasureCardsToString method returns the treasureCards that the player has
	 * in string format.
	 * @return String containing the cards that the player has in possession.
	 */
	protected String treasureCardsToString() {
		StringBuilder temp = new StringBuilder("");
		for(int i=0; i<numTreasureCards; i++)
			temp.append( (i+1) + ". " + treasureCards.get(i).toString() + "\n");
		return temp.toString();
	}
	
	/**
	 * sendCard method sends a particular card to a player.
	 * @param AbstractTreasureCard card to send
	 * @param Player aPlayer to receive card
	 */
	protected void sendCard(Card aCard, Player aPlayer) {
		aPlayer.receiveCard(aCard);
		treasureCards.remove(aCard);
		numTreasureCards -= 1;
	}
	
	/**
	 * Removes a player from a tile, then places them on a new tile.
	 * @param newLocation, the location to move the player to.
	 */
	protected void changeLocation(Tile newLocation) {
		Board.getInstance().movePlayer((PlayerView) this, newLocation.getTileName());
		location = newLocation;
	}
	
	/**
	    * hasHelicopterLiftCard method checks if a player has a helicopterLift card.
	    * Also checks to see if a winning condition is met.
	    * @return boolean determining if player has helicopterLift card..
	*/
	protected boolean hasHelicopterLiftCard() {
		return hasCardOfClassType(HelicopterLiftCard.class);
	}
	
	
	protected boolean hasCardOfClassType(@SuppressWarnings("rawtypes") Class classType) {
		for(Card aCard : treasureCards) {
			if( aCard.getClass() == classType )
				return true;
		}
		return false;
	}
	
	protected void removeCardOfClassType(@SuppressWarnings("rawtypes") Class classType) {
		Card toRemove = null;
		for(Card aCard : treasureCards) {
			if( aCard.getClass() == classType ) {
				toRemove = aCard;
				break;
			}
		}
		if(toRemove != null)
			removeTreasureCard(toRemove);
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
	 * moveFromSunk method to be called if player must move.
	 * If called when player is not on sunk tile, i.e. during a typical go, player can move to opposite tile.
	 * @return Boolean, return false if no tile to move to, else true if player has successfully moved.
	 */
	public Boolean moveFromSunk () {
		ArrayList<Tile> potentialTiles = new ArrayList<Tile>();
		potentialTiles.addAll( TilesForIfOnSunkTile() );
		if(potentialTiles.isEmpty()) {
			System.out.println(getName()+" is unable to move.");
			return false;
		}
		System.out.println(getName()+" is drowning! "+getName()+" must move to a new tile!");
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
	protected Tile selectOptionTiles(ArrayList <Tile> optionTiles) {
		System.out.println("Choose one of the following tiles:");
		for (int i=0; i<optionTiles.size(); i++) {
			System.out.println("Tile "+(i+1)+": "+optionTiles.get(i).getTileName());
		}
		Tile chosenTile = optionTiles.get(  GetInput.getInstance().anInteger(1, optionTiles.size())-1 );
		return chosenTile;
	}
	
	/**
	 * shoreUpContent method will shoreUp a tile in the area if possible.
	 * @return Boolean true if a tile has been shored Up.
	 */
	public Boolean shoreUpContent() {
		if(treasureCards.contains(hasCardOfClassType(SandbagCard.class) )) {
			ArrayList<Tile> potentialTiles = new ArrayList<>();
			for(Tile tile : Board.getInstance().getTilesAroundTile(location, true)) {
				if( tile.getFloodStatus() == FloodStatusEnum.FLOODED ) 
					potentialTiles.add(tile);
			}
			if ( !potentialTiles.isEmpty() ) {
				Tile chosenTile = selectOptionTiles(potentialTiles);
				chosenTile.shoreUp();
				removeCardOfClassType(SandbagCard.class);
				return true;
			}
			else System.out.println("There are no local Tiles to shoreUp.");
		}
		else
			System.out.println(name + " does not have a Sandbag card.");
		return false;
	}	

}

