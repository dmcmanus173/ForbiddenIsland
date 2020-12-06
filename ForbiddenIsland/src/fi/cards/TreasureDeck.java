package fi.cards;

import java.util.Stack;
import java.util.ArrayList;


import fi.enums.TreasureEnum;


/**
 * Class for a TreasureDeck in the Forbidden Island Game.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */

public class TreasureDeck {
	
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static TreasureDeck treasureDeck;
	private DeckSorter treasureDeckSorter;
	
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int NUM_TREASURE_CARDS   = 5;
	private final int NUM_SANDBAG_CARDS    = 2;
	private final int NUM_WATERRISE_CARDS  = 3;
	private final int NUM_HELICOPTER_LIFT_CARDS = 3;
	
	private final int CARD_DRAW_RATE = 2;
	
	
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
  		Stack<Card> cards = new Stack<Card>();
  		for (TreasureEnum treasure : TreasureEnum.values()) { 
  			for (int i = 0; i<NUM_TREASURE_CARDS; i++) {
  				cards.add(  new TreasureCard(treasure) );
  			} 
  		}
  		for (int i = 0; i<NUM_SANDBAG_CARDS; i++)
  			cards.add(new SandbagCard());
  			
  		for (int i = 0; i<NUM_WATERRISE_CARDS; i++)
  			cards.add(new WaterRiseCard());	
  		
  		for (int i = 0; i<NUM_HELICOPTER_LIFT_CARDS; i++)
  			cards.add(new HelicopterLiftCard());
  		treasureDeckSorter = new DeckSorter("Treasure Deck Sorter", cards);
  	}
  	
  	
  	/**
     * drawCardsForStartOfGame method is used to get the cards for the
     * start of the game. It ensures that a water rise card is not drawn
     * by a player.
     * @return ArrayList<Card> containing the cards drawn for a single player.
     */
  	public ArrayList<Card> drawCardsForStartOfGame() {
  		ArrayList<Card> cardsForPlayer = new ArrayList<Card>();
  		Card drawnCard;
  		for (int i=0; i<CARD_DRAW_RATE; i++) {
			drawnCard = treasureDeckSorter.drawCard();
			while(drawnCard instanceof WaterRiseCard) {
				treasureDeckSorter.discardCard(drawnCard);
				treasureDeckSorter.shuffleCardDeck();
				drawnCard = treasureDeckSorter.drawCard();
			}
			cardsForPlayer.add(drawnCard);
		} 
  		return cardsForPlayer;
  	}
  	
  	
  	/**
     * drawCards method is used to get the cards drawn for a single player
     * during the game.
     * @return ArrayList<Card> containing the cards drawn for a single player.
     */
  	public ArrayList<Card> drawCards() {
  		ArrayList<Card> cardsForPlayer = new ArrayList<Card>();
  		Card drawnCard;
  		for (int i=0; i<CARD_DRAW_RATE; i++) {
			drawnCard = treasureDeckSorter.drawCard();
			cardsForPlayer.add(drawnCard);
		} 
  		return cardsForPlayer;
  	}
  	
  	
  	/**
     * discardCard method discards a given card by putting it into the discard 
     * pile
     * @param Card the card to be discarded.
     */
  	public void discardCard(Card card) {
  		treasureDeckSorter.discardCard(card);
  	}

}
