package ut.cards;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import fi.cards.Card;
import fi.cards.DeckSorter;
import fi.cards.TreasureCard;
import fi.enums.TreasureEnum;

public class DeckSorterTest {

	private DeckSorter deckSorter;
	private static String deckName;
	private static Stack<Card> cards;
	
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		deckName = "deckSorter";
		cards = new Stack<Card>();
  		for (TreasureEnum treasureName : TreasureEnum.values()) { 
  			cards.add( new TreasureCard(treasureName) );
  		}
		System.out.println("BeforeClass");
	} 
	
	@org.junit.Before
	public void setUp() throws Exception {
		deckSorter = new DeckSorter(deckName, cards);
		System.out.println("SetUp");
	}
	
	
	public void drawACardTest(String assertMessage) {
		Card aCard = deckSorter.drawCard();
		assertEquals("The card drawn was not expected: "+assertMessage, Boolean.TRUE, cards.contains(aCard));	
	}
	
	@Test
	public void drawCardTest() {
		drawACardTest("");
	}

	@Test
	public void drawAllCardsTest() {
		for(int i=0; i<cards.size(); i++) {
			drawACardTest("drawing card "+i);
		}
	}
	
	@Test
	public void discardCardTest() {
		Card cardToDiscard = cards.get(0); 		   // Get a Card
		drawAllCardsTest();				   		   // Remove all cards from deck
		deckSorter.discardCard(cardToDiscard);	   // Discard a known card. Only card now in deck.
		System.out.println("Here");
		Card cardReturned = deckSorter.drawCard(); // Re add card to deck, then draw it.
		assertEquals("Card discarded is not the same as the one drawn.", cardToDiscard, cardReturned);	
	}
	
	@Test (expected = RuntimeException.class)
	public void overDrawCardsTest() {
		   try
		   {
			   drawAllCardsTest();
			   deckSorter.drawCard();
		   }
		   catch(RuntimeException re)
		   {
		      String message = "Both card deck and discard pile are empty in " + deckName + ".";
		      assertEquals(message, re.getMessage());
		      throw re;
		    }
		    fail("Didn't get Runtime exception for drawing more cards than in deck!");
	}
	
	
	@org.junit.After
	public void tearDown() {
		deckSorter = null;
		System.out.println("Tear down");
	}
	
	@org.junit.AfterClass
	public static void AfterClass() {
		cards = null;
		System.out.println("After Class");
	}
}
