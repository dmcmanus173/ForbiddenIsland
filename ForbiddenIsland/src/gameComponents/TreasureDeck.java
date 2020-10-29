package gameComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import enums.TreasureEnum;

/**
 * Class for a TreasureDeck in Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */
public class TreasureDeck {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private Queue<AbstractTreasureCard> treasureDeck = new LinkedList<AbstractTreasureCard>();
	private Queue<AbstractTreasureCard> usedTreasureCards = new LinkedList<AbstractTreasureCard>();
	private int cardsIssued; // Tracker for number of cards handed to players //TODO @Demi should we make this a list, Retains more info?
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int NUM_TREASURE_CARDS   = 5;
	private final int NUM_SANDBAG_CARDS    = 2;
	private final int NUM_WATERRISE_CARDS  = 3;
	private final int NUM_HELICOPTER_CARDS = 3;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for TreasureDeck object.
	 */
	public TreasureDeck() {		
		cardsIssued = 0;
		TreasureCard crystalFireCard   = new TreasureCard(TreasureEnum.THE_CRYSTAL_OF_FIRE);
		TreasureCard earthStoneCard    = new TreasureCard(TreasureEnum.THE_EARTH_STONE    );
		TreasureCard oceansChaliceCard = new TreasureCard(TreasureEnum.THE_OCEANS_CHALICE );
		TreasureCard statueWindCard    = new TreasureCard(TreasureEnum.THE_STATUE_OF_WIND );
		
		SandbagCard sandbagCard = new SandbagCard();
		
		WaterRiseCard waterRiseCard = new WaterRiseCard();
		
		HelicopterLiftCard helicopterCard = new HelicopterLiftCard();
		
		for (int i = 0; i<NUM_TREASURE_CARDS; i++) {
			treasureDeck.add(crystalFireCard);
			treasureDeck.add(earthStoneCard);
			treasureDeck.add(oceansChaliceCard);
			treasureDeck.add(statueWindCard);
		}
		
		for (int i = 0; i<NUM_SANDBAG_CARDS; i++)
			treasureDeck.add(sandbagCard);
			
		for (int i = 0; i<NUM_WATERRISE_CARDS; i++)
			treasureDeck.add(waterRiseCard);	
		
		for (int i = 0; i<NUM_HELICOPTER_CARDS; i++)
			treasureDeck.add(helicopterCard);
		
		shuffleDeck();	
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * shuffleDeck method will shuffle the contents of treasureDeck
	 */
	public void shuffleDeck() {
		ArrayList<AbstractTreasureCard> tempList = new ArrayList<>();
		while( !treasureDeck.isEmpty() )
			tempList.add(treasureDeck.remove());
		Collections.shuffle(tempList);
		treasureDeck.addAll(tempList);
		
		//TODO Remove Debug code
		for(AbstractTreasureCard card : tempList)
			System.out.println("Debug: " + card.toString());
	}
	
	/**
	 * addUsedCardsToDeck method will add cards in usedTreasureCards
	 * queue back to treasureCards. 
	 * treasureDeck called shuffleDeck every time this method is called.
	 */
	private void addUsedCardsBack() {
		while( !usedTreasureCards.isEmpty() )
			treasureDeck.add( usedTreasureCards.remove() );
		shuffleDeck();
	}
	
	/**
	 * getNextCard method will return the next card from treasureDeck
	 * @return AbstractTreasureCard from Queue treasureDeck
	 */
	public AbstractTreasureCard getNextCard() {
		if( treasureDeck.isEmpty() )
			addUsedCardsBack();
		cardsIssued += 1;
		return treasureDeck.remove();
	}

	/**
	 * returnUsedCard method returns used AbstractTreasureCards back
	 *  to the TreasureDeck class.
	 * These AbstractTreasureCards can be added back to the deck later as required.
	 */
	public void returnUsedCard(AbstractTreasureCard usedCard) {
		usedTreasureCards.add(usedCard);
		cardsIssued -= 1;
	}
	
	/**
	 * toString returns number of cards in treasureDeck, number of cards in usedTreasureCards
	 * and number of cards currently issued out in the game.
	 * @return String containing variable information for the class.
	 */
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append("There is " + treasureDeck.size() + " cards in the Treasure Deck.");
		temp.append("\nThere is " + usedTreasureCards.size() + " cards in the used Treasure Card pile.");
		temp.append("\nThere is " + cardsIssued + " cards in posession of the players.");
		temp.append("\n");
		return temp.toString();
	}
	
}
