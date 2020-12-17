package ut.cards;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fi.cards.Card;
import fi.cards.WaterRiseCard;
import fi.cards.TreasureDeck;

public class TreasureDeckTest {

	private static int NUM_CARDS_TO_START;
	private static int NUM_CARDS_TO_DRAW;
	private static int NUM_CARDS_IN_DECK;
	
	private TreasureDeck treasureDeck;
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		
		NUM_CARDS_TO_START = 2;
		NUM_CARDS_TO_DRAW  = 2;
		NUM_CARDS_IN_DECK  = 28;
		System.out.println("BeforeClass");
	}
	
	@org.junit.Before
	public void setUp() throws Exception {
		treasureDeck = TreasureDeck.getInstance();
		System.out.println("SetUp");
	}
	
	@Test
	public void numTreasureCardsForGameStartTest() {
		ArrayList<Card> cards = treasureDeck.drawCardsForStartOfGame();
		assertEquals("Got unexpected number of Treasure Cards at start of game.", NUM_CARDS_TO_START, cards.size());
	}
	
	@Test
	public void numTreasureCardsDuringGameTest() {
		ArrayList<Card> cards = treasureDeck.drawCards();
		assertEquals("Got unexpected number of Treasure Cards during game.", NUM_CARDS_TO_DRAW, cards.size());
	}
	
	@Test
	public void noWaterRiseCardForGameStartTest() {
		ArrayList<Card> cards = new ArrayList<>();
		for(int i = 0; i<NUM_CARDS_IN_DECK/2; i++) {
			cards = treasureDeck.drawCardsForStartOfGame();
			assertFalse("Got a WaterRiseCard at GameStart", cards.get(0) instanceof WaterRiseCard);
			assertFalse("Got a WaterRiseCard at GameStart", cards.get(1) instanceof WaterRiseCard);
			treasureDeck.discardCard(cards.get(0));
			treasureDeck.discardCard(cards.get(1));
		}
	}
	
	public void drawAllCards() {
		for(int i=0; i<NUM_CARDS_IN_DECK/2; i++) {
			treasureDeck.drawCards();
		}
	}
	
	@Test
	public void discardCardTest() {
		ArrayList<Card> cards = new ArrayList<>();
		cards = treasureDeck.drawCards();
		treasureDeck.discardCard(cards.get(0));
		treasureDeck.discardCard(cards.get(1));
		Card testCard = cards.get(0);
		drawAllCards();
		treasureDeck.discardCard(testCard);
		treasureDeck.discardCard(testCard);
		for(int i = 0; i<NUM_CARDS_IN_DECK; i++) {
			cards = treasureDeck.drawCards();
			for(int j = 0; j < NUM_CARDS_TO_DRAW; j++) {
				assertEquals("Didn't get the discarded card back.", testCard, cards.get(j));
				treasureDeck.discardCard(testCard);
			}
		}
	}
	
	@Test (expected = RuntimeException.class)
	public void overDrawCardsTest() {
		try
		{
			drawAllCards();
			treasureDeck.drawCards();	
		}
		catch(RuntimeException re)
		{
			String message = "Both card deck and discard pile are empty in Treasure Deck Sorter.";
		    assertEquals(message, re.getMessage());
		    throw re;
		}
		fail("Didn't get Runtime exception for drawing more cards than in deck!");
	}
	
	@org.junit.After
	public void tearDown() {
		treasureDeck.destroy();
		System.out.println("Tear down");
	}

	@org.junit.AfterClass
	public static void AfterClass() {
		System.out.println("After Class");
	}

}
