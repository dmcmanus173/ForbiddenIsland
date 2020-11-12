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
	private static TreasureDeck treasureDeck;
	private Queue<AbstractTreasureCard> cardDeck = new LinkedList<AbstractTreasureCard>();
	private ArrayList<AbstractTreasureCard> usedCards = new ArrayList<AbstractTreasureCard>();
	private ArrayList<AbstractTreasureCard> cardsIssued = new ArrayList<AbstractTreasureCard>();
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int NUM_TREASURE_CARDS   = 5;
	private final int NUM_SANDBAG_CARDS    = 2;
	private final int NUM_WATERRISE_CARDS  = 3;
	private final int NUM_HELICOPTER_CARDS = 3;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of TreasureDeck
     * @return treasureDeck. singleton TreasureDeck object.
     */
    public static TreasureDeck getInstance(){
        if(treasureDeck == null){
            treasureDeck = new TreasureDeck();
        }
        return treasureDeck;
    }
    
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for TreasureDeck object.
	 */
	public TreasureDeck() {		
		TreasureCard crystalFireCard   = new TreasureCard(TreasureEnum.THE_CRYSTAL_OF_FIRE);
		TreasureCard earthStoneCard    = new TreasureCard(TreasureEnum.THE_EARTH_STONE    );
		TreasureCard oceansChaliceCard = new TreasureCard(TreasureEnum.THE_OCEANS_CHALICE );
		TreasureCard statueWindCard    = new TreasureCard(TreasureEnum.THE_STATUE_OF_WIND );
		
		SandbagCard sandbagCard = new SandbagCard();
		
		WaterRiseCard waterRiseCard = new WaterRiseCard();
		
		HelicopterLiftCard helicopterCard = new HelicopterLiftCard();
		
		for (int i = 0; i<NUM_TREASURE_CARDS; i++) {
			cardDeck.add(crystalFireCard);
			cardDeck.add(earthStoneCard);
			cardDeck.add(oceansChaliceCard);
			cardDeck.add(statueWindCard);
		}
		
		for (int i = 0; i<NUM_SANDBAG_CARDS; i++)
			cardDeck.add(sandbagCard);
			
		for (int i = 0; i<NUM_WATERRISE_CARDS; i++)
			cardDeck.add(waterRiseCard);	
		
		for (int i = 0; i<NUM_HELICOPTER_CARDS; i++)
			cardDeck.add(helicopterCard);
		
		shuffleDeck();	
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * shuffleDeck method will shuffle the contents of cardDeck.
	 * Will add used cards back into main pile.
	 */
	public void shuffleDeck() {
		while( !cardDeck.isEmpty() )
			usedCards.add(cardDeck.remove());
		
		Collections.shuffle(usedCards);
		
		cardDeck.addAll(usedCards);
		while( !usedCards.isEmpty() )
			usedCards.remove(0);
	}
	
	/**
	 * addUsedCardsToDeck method will add cards in usedCards
	 * queue back to treasureCards. 
	 * cardDeck called shuffleDeck every time this method is called.
	 */
	private void addUsedCardsBack() {
		shuffleDeck();
	}
	
	/**
	 * getNextCard method will return the next card from cardDeck
	 * @return AbstractTreasureCard from Queue cardDeck
	 */
	public AbstractTreasureCard getNextCard() {
		if( cardDeck.isEmpty() )
			addUsedCardsBack();
		AbstractTreasureCard aCard = cardDeck.remove();
		cardsIssued.add(aCard);
		return aCard;
	}

	/**
	 * returnUsedCard method returns used AbstractTreasureCards back
	 *  to the TreasureDeck class.
	 * These AbstractTreasureCards can be added back to the deck later as required.
	 */
	public void returnUsedCard(AbstractTreasureCard usedCard) {
		usedCards.add(usedCard);
		cardsIssued.remove(usedCard);
	}
	
	/**
	 * toString returns number of cards in cardDeck, number of cards in usedCards
	 * and number of cards currently issued out in the game.
	 * @return String containing variable information for the class.
	 */
	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append("There is " + cardDeck.size() + " cards in the Treasure Deck.");
		temp.append("\nThere is " + usedCards.size() + " cards in the used Treasure Card pile.");
		temp.append("\nThere is " + cardsIssued.size() + " cards in posession of the players.");
		temp.append("\n");
		return temp.toString();
	}
	
}
