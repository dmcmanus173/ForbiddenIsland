package fi.cards;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


import fi.enums.TreasureEnum;

/**
 * Class for a Hand to handle the logic required for the
 * cards in a players hand.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */

public class Hand {
	//===========================================================
	// Variable Setup
	//===========================================================
	
	ArrayList<Card> allCards;
	Map<TreasureEnum, ArrayList<TreasureCard>> treasureCards;
	
	// TODO: Might consider changing these to stacks??
	ArrayList<SandbagCard> sandbagCards;
	ArrayList<HelicopterLiftCard> helicopterLiftCards;
	
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	
	private final int MAX_CARDS_IN_HAND = 5;
	
	
	
	//===========================================================
  	// Constructor
  	//===========================================================
  	/**
  	 * Constructor for Hand object.
  	 */
	public Hand() {
		allCards = new ArrayList<Card>();
		sandbagCards = new ArrayList<SandbagCard>(); 
		helicopterLiftCards = new ArrayList<HelicopterLiftCard>();
		treasureCards = new HashMap<TreasureEnum, ArrayList<TreasureCard>>(); 
		
		for (TreasureEnum treasureType : TreasureEnum.values()) { 
			treasureCards.put(treasureType, new ArrayList<TreasureCard>());
  		}
	}
	
	
	/**
     * addCard method adds a card to the players hand. Must not be a flood Card
     * @param Card to be handed to hand.
     */
	public boolean addCard(Card card) {
		
		if(card instanceof SandbagCard) {
			sandbagCards.add((SandbagCard) card);
		} else if (card instanceof HelicopterLiftCard) {
			helicopterLiftCards.add((HelicopterLiftCard)card);
		} else if (card instanceof TreasureCard) {
			TreasureCard treasureCard = (TreasureCard) card;
			TreasureEnum treasureType = (TreasureEnum) treasureCard.getName();
			treasureCards.get(treasureType).add(treasureCard);
		} else {
			throw new RuntimeException("Attempting to add invalid card type to players hand.");
		}
		
		allCards.add(card);
		
		return handIsFull();
	}
	
	
	/**
     * addCard method removes a card to the players hand
     * and adds it to the treasure discard pile.
     * @param Card to be discarded.
     */
	public void discardCard(Card card) {
		removeCard(card);
		TreasureDeck.getInstance().discardCard(card);
	}
	
	/**
     * removeCard removes a card to the players hand. 
     * @param Card to be discarded.
     */
	public void removeCard(Card card) {
		
		if(card instanceof SandbagCard) {
			sandbagCards.remove((SandbagCard) card);
		} else if (card instanceof HelicopterLiftCard) {
			helicopterLiftCards.remove((HelicopterLiftCard)card);
		} else if (card instanceof TreasureCard) {
			TreasureCard treasureCard = (TreasureCard) card;
			TreasureEnum treasureType = (TreasureEnum) treasureCard.getName();
			treasureCards.get(treasureType).remove(treasureCard);
		} else {
			throw new RuntimeException("Attempting to remove card that is not in players hand.");
		}
		
		allCards.remove(card);

	}
	
	
	//===========================================================
	// Getters
	//===========================================================
	
	/**
     * getCards method returns all the cards in the hand.
     * @return ArrayList<Card> containing cards in players hand.
     */
	public ArrayList<Card> getCards() {
		return allCards;
		
	}
	
	/**
     * getCards method returns all treasure cards (of a given
     * treasure) in a players hand
     * @return ArrayList<Card> containing cards in players hand.
     */
	// TODO: might consider just returning the number.
	public ArrayList<TreasureCard> getCardsForTreasure(TreasureEnum treasureType) {
		return treasureCards.get(treasureType);
		
	}
	
	
	//===========================================================
	// Boolean Checkers
	//===========================================================
	
	/**
     * handIsFull method checks if the hand has reached the
     * maximum number of cards it can contain.
     * @return boolean determining if the hand is full.
     */
	public boolean handIsFull() {
		return allCards.size() >= MAX_CARDS_IN_HAND;
	}
	
	/**
     * handIsFull method checks if there is a SandbagCard 
     * in the hand.
     * @return boolean determining if SandbagCard is present.
     */
	public boolean containsSandbagCard() {
		return !sandbagCards.isEmpty();
	}
	
	/**
     * handIsFull method checks if there is a HelicopterLiftCard 
     * in the hand.
     * @return boolean determining if HelicopterLiftCard is present.
     */
	public boolean containsHelicopterLiftCard() {
		return !helicopterLiftCards.isEmpty();
	}
	
	/**
     * didUseHelicopterLiftCard method removes and discards a HelicopterLiftCard
     * that was just used by the player.
     */
	public void removeHelicopterLiftCard() {
		if (!helicopterLiftCards.isEmpty()) {
			HelicopterLiftCard usedCard = helicopterLiftCards.get(0);
			removeCard(usedCard);
			discardCard(usedCard);
		} else {
			throw new RuntimeException("Attempting to use A HelicopterLiftCard that the player does not have.");
		}
	}
	
	/**
     * didUseSandbagCard method removes and discards a SandbagCard
     * that was just used by the player.
     */
	public void removeSandbagCard() {
		if (!sandbagCards.isEmpty()) {
			SandbagCard usedCard = sandbagCards.get(0);
			removeCard(usedCard);
			discardCard(usedCard);
		} else {
			throw new RuntimeException("Attempting to use A SandBagCard that the player does not have.");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder handString = new StringBuilder("");
		for(Card card: allCards) {
			handString.append(card.toString() + ", ");
		}
		return handString.toString();
	}

}
