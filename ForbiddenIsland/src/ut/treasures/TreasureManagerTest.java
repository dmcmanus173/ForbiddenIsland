package ut.treasures;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import fi.board.Tile;
import fi.board.TreasureTile;
import fi.cards.Hand;
import fi.cards.TreasureCard;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.treasures.TreasureManager;

public class TreasureManagerTest {
	private static TreasureEnum oceansChalice;
	private static TreasureEnum statueOfWind;
	private static TreasureEnum earthStone;
	private static TreasureEnum crystalOfFire;
	private TreasureManager treasureManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		oceansChalice = TreasureEnum.THE_OCEANS_CHALICE;
		statueOfWind  = TreasureEnum.THE_STATUE_OF_WIND;
		earthStone    = TreasureEnum.THE_EARTH_STONE;
		crystalOfFire = TreasureEnum.THE_CRYSTAL_OF_FIRE;
		System.out.println("BeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		oceansChalice = null;
		statueOfWind  = null;
		earthStone    = null;
		crystalOfFire = null;
		System.out.println("After Class");
	}

	@Before
	public void setUp() throws Exception {
		treasureManager = TreasureManager.getInstance();
		System.out.println("SetUp");
	}

	@After
	public void tearDown() throws Exception {
		treasureManager.destroy();
		System.out.println("Tear down");
	}

	@Test
	public void noClaimedTreasuresAtStartTest() {
		for(TreasureEnum treasureType: TreasureEnum.values()) {
			assertTrue("There should only be no collected treasures once treasure manager is declared.", treasureManager.didClaimTreasure(treasureType) == false);
		}
	}
	
	@Test
	public void canClaimTreasureTest() {
		ArrayList<TreasureTile> treasureTiles = new ArrayList<TreasureTile>();
		ArrayList<Tile> noramlTiles = new ArrayList<Tile>();
		int numOfTreasureCardsOfGivenTreasureType;
		Hand hand;
		
		for(TileEnum tileName: TileEnum.values()) {
			if(tileName.hasTreasure())
				treasureTiles.add(new TreasureTile(tileName));
			else
				noramlTiles.add(new Tile(tileName));
		}
		
		for(TreasureTile treasureTile: treasureTiles) {
			numOfTreasureCardsOfGivenTreasureType = 4;
			hand = createHandWith(treasureTile.getTreasureType(), numOfTreasureCardsOfGivenTreasureType);
			assertEquals("The required conditions are present to collect a treasure.", Boolean.TRUE, treasureManager.canCollectTreasure(hand, treasureTile.getTileName()));
			
			numOfTreasureCardsOfGivenTreasureType = 3;
			hand = createHandWith(treasureTile.getTreasureType(), numOfTreasureCardsOfGivenTreasureType);
			assertEquals("The re are not enough treasure cards to collect a treasure.", Boolean.FALSE, treasureManager.canCollectTreasure(hand, treasureTile.getTileName()));
		}
		
		
		for(Tile tile: noramlTiles) {
			numOfTreasureCardsOfGivenTreasureType = 4;
			hand = createHandWith(TreasureEnum.THE_STATUE_OF_WIND, numOfTreasureCardsOfGivenTreasureType);
			assertEquals("There are enough treasure cards but can not collect any treasure on a normal tile. ", Boolean.FALSE, treasureManager.canCollectTreasure(hand, tile.getTileName()));
		}
	}
	
	@Test
	public void claimedTreasuresWithEnoughTreasureCardsTest() {
		//TODO: WILL WE NEED TO ADD SAFEGUARD FOR WHEN TREASURE IS BEING CLAIMED BUT HASNT CALLED CANClaim method before hand. if not might change the name of the test.
		int numOfTreasureCardsOfGivenTreasureType = 4;
		for(TreasureEnum treasureType: TreasureEnum.values()) {
			Hand hand = createHandWith(treasureType, numOfTreasureCardsOfGivenTreasureType);
			treasureManager.claimTreasure(hand, treasureType);
			assertEquals("The treasure should be claimed once hand has enough treasure cards.", Boolean.TRUE, treasureManager.didClaimTreasure(treasureType));
			assertEquals("TreasureManager shouold remove the treasure cards from hand.", Boolean.TRUE, hand.getCardsForTreasure(treasureType).isEmpty());
		}
		
		assertEquals("If All Ttreasures have been claimed Treasure manager should state this.", Boolean.TRUE, treasureManager.didClaimAllTreasures());
		
	}
	
	
	
	public Hand createHandWith(TreasureEnum treasureType, int numCards) {
		 Hand hand = new Hand();
		 for(int i = 0; i < numCards; i++) {
			 hand.addCard(new TreasureCard(treasureType));
		 }
		return hand;
	}
	
	
	// TODO: CURRENTLY NO LOGIC TO PREVENT CREATING A NORMAL TILE WITH A TREASURE TILE ENUM?
	
	
	
	

}
