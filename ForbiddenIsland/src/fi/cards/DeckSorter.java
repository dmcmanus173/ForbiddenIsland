package fi.cards;

import java.util.Stack;
import java.util.Collections;



/**
 * Class for a DeckSorter to manage card decks and discard piles
 * in the Forbidden Island Game.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
public class DeckSorter {
	//===========================================================
	// Variable Setup
	//===========================================================
	private String name;
	private Stack<Card> cardDeck;
	private Stack<Card> discardPile;

	//===========================================================
  	// Constructor
  	//===========================================================
  	/**
  	 * Constructor for DeckSorter object.
  	 */
	public DeckSorter(String name, Stack<Card> cards) {
		this.name = name;
		this.cardDeck = cards;
		this.discardPile = new Stack<Card>();
		shuffleCardDeck();
	}
	
	/**
    * drawCard method draws a card from the Deck sorter. If the 
    * main deck runs out of cards, the deck is reset using the 
    * resetCardDeck method.
    * @return Card the card that is drawn from the deck
    */
	public Card drawCard() {
		if(!cardDeck.isEmpty()) {
			return cardDeck.pop();
		} else if(!discardPile.isEmpty()) {
			resetCardDeck();
			return cardDeck.pop();
		}
		throw new RuntimeException("Both card deck and discard pile are empty in " + name + ".");
	}
	
	/**
    * discardCard method returns a card to the discard pile.
    * @param Card the card that is to be discarded.
    */
	public void discardCard(Card card) {
		discardPile.push(card);
	}
	
	/**
    * in the event that the cards in the main deck run out, 
    * the resetCardDeck method adds the cards in the discard 
    * pile to the main deck and calls the shuffleCardDeck
    * method to shuffle the deck.
    */
	private void resetCardDeck() {
		for (int i=0; i<discardPile.size(); i++) {
			cardDeck.push(discardPile.pop());
		}
		shuffleCardDeck();	
	}
	
	/**
    * shuffles the main card deck.
    */
	public void shuffleCardDeck() {
		Collections.shuffle(cardDeck);
	}
	
}