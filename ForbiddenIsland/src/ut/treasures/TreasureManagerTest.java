package ut.treasures;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fi.cards.Hand;
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
	
	

}
