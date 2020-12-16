package ut.cards;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fi.cards.Card;
import fi.cards.Hand;
import fi.cards.HelicopterLiftCard;
import fi.cards.SandbagCard;
import fi.cards.TreasureCard;
import fi.enums.TreasureEnum;

public class HandTest {

	private static HelicopterLiftCard helicopterLift;
	private static SandbagCard sandbag;
	private static TreasureEnum oceansChalice;
	private static TreasureEnum statueOfWind;
	private static TreasureEnum earthStone;
	private static TreasureEnum crystalOfFire;
	private Hand hand;
	
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		helicopterLift = new HelicopterLiftCard();
		sandbag = new SandbagCard();
		
		oceansChalice = TreasureEnum.THE_OCEANS_CHALICE;
		statueOfWind  = TreasureEnum.THE_STATUE_OF_WIND;
		earthStone    = TreasureEnum.THE_EARTH_STONE;
		crystalOfFire = TreasureEnum.THE_CRYSTAL_OF_FIRE;
		
		System.out.println("BeforeClass");
	}
	
	@org.junit.Before
	public void setUp() throws Exception {
		hand = new Hand();
		System.out.println("SetUp");
	}
	

	@Test
	public void noCardsTest() {
		assertEquals("Cards present.", hand.getCards().isEmpty(), Boolean.TRUE);
		
		assertEquals("There is a HelicopterLift Card.", Boolean.FALSE, hand.containsHelicopterLiftCard());
		assertEquals("There is a Sandbag Card.", Boolean.FALSE, hand.containsSandbagCard());
		
		assertEquals("There is a Oceans Chalice Treasure Card after adding Helicopter Lift",  Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_OCEANS_CHALICE).isEmpty() );
		assertEquals("There is a Statue Of Wind Treasure Card after adding Helicopter Lift",  Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_STATUE_OF_WIND).isEmpty() );
		assertEquals("There is a Earth Stone Treasure Card after adding Helicopter Lift",     Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_EARTH_STONE).isEmpty()    );
		assertEquals("There is a Crystal of Fire Treasure Card after adding Helicopter Lift", Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_CRYSTAL_OF_FIRE).isEmpty());	}
	
	
	@Test 
	public void testAddingRemovingHelicopterLift(){
		hand.addCard(helicopterLift);
		assertEquals("Expected 1 card available.", hand.getCards().size(), 1);
		Card card = hand.getCards().get(0);
		assertEquals("Card returned from getCards doesn't match card added.", helicopterLift, card);
		
		assertEquals("There is no HelicopterLift Card present after adding it.", Boolean.TRUE, hand.containsHelicopterLiftCard());
		assertEquals("There is a Sandbag Card after adding a Helicopter Lift.", Boolean.FALSE, hand.containsSandbagCard());
		
		assertEquals("There is a Oceans Chalice Treasure Card after adding Helicopter Lift",  Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_OCEANS_CHALICE).isEmpty() );
		assertEquals("There is a Statue Of Wind Treasure Card after adding Helicopter Lift",  Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_STATUE_OF_WIND).isEmpty() );
		assertEquals("There is a Earth Stone Treasure Card after adding Helicopter Lift",     Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_EARTH_STONE).isEmpty()    );
		assertEquals("There is a Crystal of Fire Treasure Card after adding Helicopter Lift", Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_CRYSTAL_OF_FIRE).isEmpty());
		
		hand.removeHelicopterLiftCard();
		noCardsTest();
	}
	
	@Test 
	public void testAddingRemovingSandbag(){
		hand.addCard(sandbag);
		assertEquals("Expected 1 card available.", hand.getCards().size(), 1);
		Card card = hand.getCards().get(0);
		assertEquals("Card returned from getCards doesn't match card added.", sandbag, card);
		
		assertEquals("There is a HelicopterLift Card present after adding Sandbag.", Boolean.FALSE, hand.containsHelicopterLiftCard());
		assertEquals("There is no Sandbag Card after adding it.", Boolean.TRUE, hand.containsSandbagCard());
		
		assertEquals("There is a Oceans Chalice Treasure Card after adding Helicopter Lift",  Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_OCEANS_CHALICE).isEmpty() );
		assertEquals("There is a Statue Of Wind Treasure Card after adding Helicopter Lift",  Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_STATUE_OF_WIND).isEmpty() );
		assertEquals("There is a Earth Stone Treasure Card after adding Helicopter Lift",     Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_EARTH_STONE).isEmpty()    );
		assertEquals("There is a Crystal of Fire Treasure Card after adding Helicopter Lift", Boolean.TRUE, hand.getCardsForTreasure(TreasureEnum.THE_CRYSTAL_OF_FIRE).isEmpty());		
		hand.removeSandbagCard();
		noCardsTest();
	}
	 
	public void testFunctionAddingRemovingTreasureCard(TreasureEnum aTreasureEnum){
		System.out.println("Running testFunctionAddingRemovingTreasureCard for "+aTreasureEnum.toString());
		TreasureCard treasureCard = new TreasureCard(aTreasureEnum);
		hand.addCard(treasureCard);
		assertEquals("Expected 1 card available.", hand.getCards().size(), 1);
		Card card = hand.getCards().get(0);
		assertEquals("Card returned from getCards doesn't match card added.", card, treasureCard);
		
		assertEquals("There is a HelicopterLift Card present after adding "+aTreasureEnum.toString()+" Card", hand.containsHelicopterLiftCard(), Boolean.FALSE);
		assertEquals("There is no Sandbag Card after adding "              +aTreasureEnum.toString()+" Card", hand.containsSandbagCard(),        Boolean.FALSE);
		
		if(aTreasureEnum == oceansChalice)
			assertEquals("There isn't 1 Oceans Chalice Treasure Card after adding just 1.",  hand.getCardsForTreasure(TreasureEnum.THE_OCEANS_CHALICE).size(),  1);
		else
			assertEquals("There is a Oceans Chalice Treasure Card after adding "+aTreasureEnum.toString()+" Card", hand.getCardsForTreasure(TreasureEnum.THE_OCEANS_CHALICE).isEmpty(),  Boolean.TRUE);
		if(aTreasureEnum == statueOfWind)
			assertEquals("There isn't 1 Statue of Wind Treasure Card after adding just 1.",  hand.getCardsForTreasure(TreasureEnum.THE_STATUE_OF_WIND).size(),  1);
		else
			assertEquals("There is a Statue Of Wind Treasure Card after adding "+aTreasureEnum.toString()+" Card",  hand.getCardsForTreasure(TreasureEnum.THE_STATUE_OF_WIND).isEmpty(),  Boolean.TRUE);
		if(aTreasureEnum == earthStone)
			assertEquals("There isn't 1 Earth Stone Treasure Card after adding just 1.",  hand.getCardsForTreasure(TreasureEnum.THE_EARTH_STONE).size(),  1);
		else
			assertEquals("There is a Earth Stone Treasure Card after adding "+aTreasureEnum.toString()+" Card",     hand.getCardsForTreasure(TreasureEnum.THE_EARTH_STONE).isEmpty(),     Boolean.TRUE);
		if(aTreasureEnum == crystalOfFire)
			assertEquals("There isn't 1 Crystal of Fire Treasure Card after adding just 1.",  hand.getCardsForTreasure(TreasureEnum.THE_CRYSTAL_OF_FIRE).size(),  1);
		else
			assertEquals("There is a Crystal of Fire Treasure Card after adding "+aTreasureEnum.toString()+" Card", hand.getCardsForTreasure(TreasureEnum.THE_CRYSTAL_OF_FIRE).isEmpty(), Boolean.TRUE);
		
		hand.removeCard(treasureCard);
		noCardsTest();
	}
	
	@Test
	public void testAddingRemovingATreasureCard(){
		testFunctionAddingRemovingTreasureCard(oceansChalice);
		testFunctionAddingRemovingTreasureCard(statueOfWind );
		testFunctionAddingRemovingTreasureCard(earthStone   );
		testFunctionAddingRemovingTreasureCard(crystalOfFire);
	}
	
	@Test (expected = RuntimeException.class)
	public void removingHelicopterLiftWhenHandEmpty() {
		   try
		   {
			   hand.removeHelicopterLiftCard();
		   }
		   catch(RuntimeException re)
		   {
		      String message = "Attempting to use A HelicopterLiftCard that the player does not have.";
		      assertEquals(message, re.getMessage());
		      throw re;
		    }
		    fail("Removing Helicopter Lift when No Card in Hand exception did not throw!");
	}
	
	@Test (expected = RuntimeException.class)
	public void removingSandbagWhenHandEmpty() {
		   try
		   {
			   hand.removeSandbagCard();
		   }
		   catch(RuntimeException re)
		   {
		      String message = "Attempting to use A SandBagCard that the player does not have.";
		      assertEquals(message, re.getMessage());
		      throw re;
		    }
		    fail("Removing SandbagCard when No Card in Hand exception did not throw!");
	}
	
	
	@Test (expected = RuntimeException.class)
	public void removingCardsWhenHandEmpty(){
		System.out.println(1);
		removeCardWhenHandEmpty(sandbag);
		System.out.println(2);
		removeCardWhenHandEmpty(helicopterLift);
		
		TreasureCard oceansChaliceCard = new TreasureCard(oceansChalice);
		TreasureCard statueOfWindCard = new TreasureCard(statueOfWind);
		TreasureCard earthStoneCard = new TreasureCard(earthStone);
		TreasureCard crystalOfFireCard = new TreasureCard(crystalOfFire);
		
		System.out.println(3);
		removeCardWhenHandEmpty(oceansChaliceCard);
		removeCardWhenHandEmpty(statueOfWindCard );
		removeCardWhenHandEmpty(earthStoneCard   );
		removeCardWhenHandEmpty(crystalOfFireCard);
		
	}
	
	public void removeCardWhenHandEmpty(Card aCard) {
		   try
		   {
			   hand.removeCard(aCard);
		   }
		   catch(RuntimeException re)
		   {
		      String message = "Attempting to remove card that is not in players hand.";
		      assertEquals(message, re.getMessage());
		      throw re;
		    }
		    fail("Removing a card when No Card in Hand exception did not throw!");
	}
	
	@Test
	public void containsSandbagTest(){
		hand.addCard(sandbag);
		assertEquals("Hand should contain a sandbag.", Boolean.TRUE, hand.containsSandbagCard());
	}
	
	@Test
	public void containsHelicopterLiftTest(){
		hand.addCard(helicopterLift);
		assertEquals("Hand should contain a Helicopter Lift.", Boolean.TRUE, hand.containsHelicopterLiftCard());
	}
	
	@Test
	public void handIsFullTest() {
		assertEquals("Hand is full after adding 1 card.", Boolean.TRUE, hand.addCard(sandbag));
		assertEquals("Hand is full after adding 2 card.", Boolean.TRUE, hand.addCard(sandbag));
		assertEquals("Hand is full after adding 3 card.", Boolean.TRUE, hand.addCard(sandbag));
		assertEquals("Hand is full after adding 4 card.", Boolean.TRUE, hand.addCard(sandbag));
		assertEquals("Hand is not full after adding 5 card.", Boolean.FALSE, hand.addCard(sandbag));
	}
	
	@org.junit.After
	public void tearDown() {
		hand = null;
		System.out.println("Tear down");
	}
	
	@org.junit.AfterClass
	public static void AfterClass() {
		helicopterLift = null;
		sandbag = null;
		
		oceansChalice = null;
		statueOfWind  = null;
		earthStone    = null;
		crystalOfFire = null;
		System.out.println("After Class");
	}

}
