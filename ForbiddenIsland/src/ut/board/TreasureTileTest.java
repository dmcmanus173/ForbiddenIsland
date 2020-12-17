package ut.board;

import static org.junit.Assert.*;

import org.junit.Test;

import fi.board.Board;
import fi.board.TreasureTile;
import fi.cards.HelicopterLiftCard;
import fi.cards.SandbagCard;
import fi.enums.AdventurerEnum;
import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.game.GameOverObserver;
import fi.players.Player;

public class TreasureTileTest {
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		System.out.println("BeforeClass");
	}
	
	@org.junit.Before
	public void setUp() throws Exception {
		System.out.println("SetUp");
	}	
	
	@Test (expected = RuntimeException.class)
	public void notTreasureTileTest() {
		try
		{
			new TreasureTile(TileEnum.BREAKERS_BRIDGE);
		}
		catch(RuntimeException re)
		{
			String message = "The given tile name is not a Treasure Tile";
		    assertEquals(message, re.getMessage());
		    throw re;
		}
		fail("Created TreasureTile object for non-Treasure Tile name");
	}
	
	@Test
	public void treasureTypeTest() {
		TreasureTile treasureTile;
		
		treasureTile = new TreasureTile(TileEnum.TEMPLE_OF_THE_MOON);
		assertEquals("Didn't get the right Treasure", TreasureEnum.THE_EARTH_STONE, treasureTile.getTreasureType());
		treasureTile = new TreasureTile(TileEnum.TEMPLE_OF_THE_SUN);
		assertEquals("Didn't get the right Treasure", TreasureEnum.THE_EARTH_STONE, treasureTile.getTreasureType());
		
		treasureTile = new TreasureTile(TileEnum.WHISPERING_GARDEN);
		assertEquals("Didn't get the right Treasure", TreasureEnum.THE_STATUE_OF_WIND, treasureTile.getTreasureType());
		treasureTile = new TreasureTile(TileEnum.HOWLING_GARDEN);
		assertEquals("Didn't get the right Treasure", TreasureEnum.THE_STATUE_OF_WIND, treasureTile.getTreasureType());
		
		treasureTile = new TreasureTile(TileEnum.CAVE_OF_EMBERS);
		assertEquals("Didn't get the right Treasure", TreasureEnum.THE_CRYSTAL_OF_FIRE, treasureTile.getTreasureType());
		treasureTile = new TreasureTile(TileEnum.CAVE_OF_SHADOWS);
		assertEquals("Didn't get the right Treasure", TreasureEnum.THE_CRYSTAL_OF_FIRE, treasureTile.getTreasureType());
		
		treasureTile = new TreasureTile(TileEnum.CORAL_PALACE);
		assertEquals("Didn't get the right Treasure", TreasureEnum.THE_OCEANS_CHALICE, treasureTile.getTreasureType());
		treasureTile = new TreasureTile(TileEnum.TIDAL_PALACE);
		assertEquals("Didn't get the right Treasure", TreasureEnum.THE_OCEANS_CHALICE, treasureTile.getTreasureType());
	}
	
	public void treasureFloodFunction(TreasureTile treasureTile1, TreasureTile treasureTile2) {
		GameOverObserver gameOverObserver = GameOverObserver.getInstance();
		
		assertEquals("Flood status should be Not Flooded", FloodStatusEnum.NOT_FLOODED, treasureTile1.getFloodStatus());
		assertEquals("Expect 0 Tile to be Sunk for this treasure", 0, gameOverObserver.getNumSunkTreasures(treasureTile1.getTreasureType()));
		treasureTile1.flood();
		assertEquals("Flood status should be Flooded", FloodStatusEnum.FLOODED, treasureTile1.getFloodStatus());
		assertEquals("Expect 0 Tile to be Sunk for this treasure", 0, gameOverObserver.getNumSunkTreasures(treasureTile1.getTreasureType()));
		treasureTile1.flood();
		assertEquals("Flood status should be Sunken", FloodStatusEnum.SUNKEN, treasureTile1.getFloodStatus());
		assertEquals("Expect 1 Tile to be Sunk for this treasure", 1, gameOverObserver.getNumSunkTreasures(treasureTile1.getTreasureType()));
		
		assertEquals("Flood status should be Not Flooded", FloodStatusEnum.NOT_FLOODED, treasureTile2.getFloodStatus());
		assertEquals("Expect 1 Tile to be Sunk for this treasure", 1, gameOverObserver.getNumSunkTreasures(treasureTile2.getTreasureType()));
		treasureTile2.flood();
		assertEquals("Flood status should be Flooded", FloodStatusEnum.FLOODED, treasureTile2.getFloodStatus());
		assertEquals("Expect 1 Tile to be Sunk for this treasure", 1, gameOverObserver.getNumSunkTreasures(treasureTile2.getTreasureType()));
		treasureTile2.flood();
		assertEquals("Flood status should be Sunken", FloodStatusEnum.SUNKEN, treasureTile2.getFloodStatus());
		assertEquals("Expect 2 Tile to be Sunk for this treasure", 2, gameOverObserver.getNumSunkTreasures(treasureTile2.getTreasureType()));
	}
	
	@Test
	public void treasureFloodTest() {
		TreasureTile treasureTile1;
		TreasureTile treasureTile2;
		
		treasureTile1 = new TreasureTile(TileEnum.TEMPLE_OF_THE_MOON);
		treasureTile2 = new TreasureTile(TileEnum.TEMPLE_OF_THE_SUN);
		treasureFloodFunction(treasureTile1, treasureTile2);

		treasureTile1 = new TreasureTile(TileEnum.WHISPERING_GARDEN);
		treasureTile2 = new TreasureTile(TileEnum.HOWLING_GARDEN);
		treasureFloodFunction(treasureTile1, treasureTile2);
		
		treasureTile1 = new TreasureTile(TileEnum.CAVE_OF_EMBERS);
		treasureTile2 = new TreasureTile(TileEnum.CAVE_OF_SHADOWS);
		treasureFloodFunction(treasureTile1, treasureTile2);
		
		treasureTile1 = new TreasureTile(TileEnum.CORAL_PALACE);
		treasureTile2 = new TreasureTile(TileEnum.TIDAL_PALACE);
		treasureFloodFunction(treasureTile1, treasureTile2);
	}
	
	@org.junit.After
	public void tearDown() {
		System.out.println("Tear down");
	}
	
	@org.junit.AfterClass
	public static void AfterClass() {
		System.out.println("After Class");
	}

}
