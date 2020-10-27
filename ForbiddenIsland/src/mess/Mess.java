package mess;

import java.util.ArrayList;

import gameComponents.AbstractTreasureCard;
import gameComponents.TreasureDeck;
import gameComponents.WaterMeter;

public class Mess {

	/*
	// WaterMeter Class-level debug test
	public static void main(String[] args) {
		
		WaterMeter wm1 = new WaterMeter();
		System.out.println( wm1.toString() ); // "Water Meter: 1"
		wm1.IncreaseWaterMeter();
		System.out.println( wm1.toString() ); // "Water Meter: 2"
		
		WaterMeter wm2 = new WaterMeter(4);
		System.out.println( wm2.toString() ); // "Water Meter: 4"
		wm2.IncreaseWaterMeter();
		System.out.println( wm2.toString() ); // "Water Meter: 5"
		wm2.IncreaseWaterMeter();			  // "WaterMeter can't increase past maxLevel(=5). WaterMeter remaining at maxLevel."
		System.out.println( wm2.toString() ); // "Water Meter: 5"		
		
		WaterMeter wm3 = new WaterMeter(5);
		System.out.println( wm3.toString() ); // "Water Meter: 5"
		
		WaterMeter wm4 = new WaterMeter(6);   // "Level requested is greater than maxLevel(=5). Level set to maxLevel."
		System.out.println( wm4.toString() ); // "Water Meter: 5"	
	
	}
	*/
	
	// TreasureDeck Class-level debug test
	public static void main(String[] args) {
		
		TreasureDeck treasureDeck = new TreasureDeck();
		ArrayList <AbstractTreasureCard> cardList = new ArrayList<AbstractTreasureCard>();
		
		// Remove 4 cards from deck
		System.out.println(treasureDeck.toString() );
		cardList.add( treasureDeck.getNextCard() );
		cardList.add( treasureDeck.getNextCard() );	
		cardList.add( treasureDeck.getNextCard() );
		cardList.add( treasureDeck.getNextCard() );
		for(AbstractTreasureCard card : cardList)
			System.out.println( card.toString() );
		System.out.println(treasureDeck.toString() );
		
		// Add card to used pile
		treasureDeck.returnUsedCard( cardList.remove(0) );
		System.out.println(treasureDeck.toString());
		
		// Put used cards back into the deck
		treasureDeck.addUsedCardsBack();
		System.out.println(treasureDeck.toString());
		
	}
	
}
