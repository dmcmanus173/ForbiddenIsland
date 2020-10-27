package gameComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import enums.TreasureEnum;

public class TreasureDeck {
	
	private Queue<AbstractTreasureCard> treasureDeck = new LinkedList<AbstractTreasureCard>();
	private Queue<AbstractTreasureCard> usedTreasureCards = new LinkedList<AbstractTreasureCard>();
	private int cardsIssued; // Tracker for number of cards handed to players //TODO @Demi should we make this a list, Retains more info?
	
	private int numTreasureCards   = 5;
	private int numSandbagCards    = 2;
	private int numWaterRiseCards  = 3;
	private int numHelicopterCards = 3;
	
	public TreasureDeck() {
		
		cardsIssued = 0;
		TreasureCard crystalFireCard   = new TreasureCard(TreasureEnum.THE_CRYSTAL_OF_FIRE);
		TreasureCard earthStoneCard    = new TreasureCard(TreasureEnum.THE_EARTH_STONE    );
		TreasureCard oceansChaliceCard = new TreasureCard(TreasureEnum.THE_OCEANS_CHALICE );
		TreasureCard statueWindCard    = new TreasureCard(TreasureEnum.THE_STATUE_OF_WIND );
		
		SandbagCard sandbagCard = new SandbagCard();
		
		WaterRiseCard waterRiseCard = new WaterRiseCard();
		
		HelicopterLiftCard helicopterCard = new HelicopterLiftCard();
		
		for (int i = 0; i<numTreasureCards; i++) {
			treasureDeck.add(crystalFireCard);
			treasureDeck.add(earthStoneCard);
			treasureDeck.add(oceansChaliceCard);
			treasureDeck.add(statueWindCard);
		}
		
		for (int i = 0; i<numSandbagCards; i++)
			treasureDeck.add(sandbagCard);
			
		for (int i = 0; i<numWaterRiseCards; i++)
			treasureDeck.add(waterRiseCard);	
		
		for (int i = 0; i<numHelicopterCards; i++)
			treasureDeck.add(helicopterCard);
		
		shuffleDeck();
		
	}
	
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
	
	public void addUsedCardsBack() {
		
		while( !usedTreasureCards.isEmpty() )
			treasureDeck.add( usedTreasureCards.remove() );
		shuffleDeck();
		
	}
	
	public AbstractTreasureCard getNextCard() {
		
		if( treasureDeck.isEmpty() )
			addUsedCardsBack();
		cardsIssued += 1;
		return treasureDeck.remove();
		
	}

	public void returnUsedCard(AbstractTreasureCard usedCard) {
		
		usedTreasureCards.add(usedCard);
		cardsIssued -= 1;
		
	}
	
	public String toString() {
		
		StringBuilder temp = new StringBuilder("");
		temp.append("There is " + treasureDeck.size() + " cards in the Treasure Deck.");
		temp.append("\nThere is " + usedTreasureCards.size() + " cards in the used Treasure Card pile.");
		temp.append("\nThere is " + cardsIssued + " cards in posession of the players.");
		temp.append("\n");
		return temp.toString();
		
	}
	
}
