package gameComponents;

import java.util.ArrayList;

import board.Tile;
import enums.AdventurerEnum;

public class Player {
	
	/**
	 * Class which will manage players actions in the Forbidden Island game
	 * @author Daniel McManus
	 * @version 0.1
	 *
	 */

	private String name;
	private AdventurerEnum role;
	private Tile location;
	private ArrayList<AbstractTreasureCard> treasureCards = new ArrayList<>();
	
	private int maxTreasureCards = 5;
	
	public Player(String name, AdventurerEnum role, Tile location, ArrayList<AbstractTreasureCard> treasureCards) {
		
		this.name = name;
		this.role = role;
		this.location = location;
		this.treasureCards.addAll(treasureCards);
		
	}
	
	public Tile getLocation() {
		
		return location;
		
	}
	
	//TODO Change type to TreasureCard, ensure removing of card is done. 
	public void removeTreasureCard() {
		
		System.out.println(treasureCardsToString());
		
	}
	
	public String treasureCardsToString() {
		
		StringBuilder temp = new StringBuilder("");
		for(AbstractTreasureCard card : treasureCards)
			temp.append("Index: " + card.toString() + "\n"); //TODO add index number in ArrayList
		return temp.toString();
		
	}
	
	public void assignTreasureCard(TreasureCard card) {
		
		treasureCards.add(card);
		if( treasureCards.size() == maxTreasureCards+1) {
			removeTreasureCard();
		}
		// Possibly return null, if no card to remove...
	}
	
	public TreasureCard giveTreasureCard(Player otherPlayer, TreasureCard card) {
		
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
	
	public void move () {
		
		//TODO Add content
		// location = new location
		
	}
	
	public void shoreUp(Tile islandTile) {
		
		//TODO Must resolve to shore any tile adjacent to player
		SandbagCard sandbag = new SandbagCard(); //TODO see if this works, might change to iterative search
		if( treasureCards.contains(sandbag) ) {
			if( islandTile.shoreUp() ) treasureCards.remove(sandbag);
		}
		else
			System.out.println(name + " does not have a Sandbag card.");

		
	}
	
	
	
	
	
	
	
}
