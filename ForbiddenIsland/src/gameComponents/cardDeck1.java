package gameComponents;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public interface cardDeck1 {

	Queue<Card> deckCards = new LinkedList<>();
	ArrayList<Card> usedCards = new ArrayList<Card>();
	
	public void addUsedCardsBack();
	
	public void shuffleDeck();
	
	public Card getCard();
	
}
