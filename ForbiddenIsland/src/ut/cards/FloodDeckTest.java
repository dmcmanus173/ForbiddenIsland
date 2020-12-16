package ut.cards;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fi.cards.FloodDeck;
import fi.enums.TileEnum;
import fi.watermeter.WaterMeter;

public class FloodDeckTest {
	
	private static int MAX_WATER_LEVEL;
	private static int MIN_WATER_LEVEL;
	private static int NUM_TILES_TO_FLOOD_AT_START;
	private static WaterMeter waterMeter;
	private FloodDeck floodDeck;
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		waterMeter = WaterMeter.getInstance();
		MIN_WATER_LEVEL = waterMeter.getMinLevel();
		MAX_WATER_LEVEL = waterMeter.getMaxLevel();
		NUM_TILES_TO_FLOOD_AT_START = 6;
		
		System.out.println("BeforeClass");
	}
	
	@org.junit.Before
	public void setUp() throws Exception {
		floodDeck = FloodDeck.getInstance();
		System.out.println("SetUp");
	}
	
	@Test
	public void tilesForGameStartTest() {
		ArrayList<TileEnum> cards = floodDeck.getTilesToFlood(Boolean.TRUE);
		assertEquals("Got unexpected number of tiles to flood at start of game.", NUM_TILES_TO_FLOOD_AT_START, cards.size());
	}
	
	public void tilesToFloodDuringGameTest(int waterLevel) {
		ArrayList<TileEnum> cards = floodDeck.getTilesToFlood(Boolean.FALSE);
		assertEquals("Got unexpected number of tiles to flood during game.", waterLevel, cards.size());
	}
	
	@Test
	public void tilesToFloodDuringGameVaryingWaterLevelTest() {
		int waterLevel;
		for(waterLevel = MIN_WATER_LEVEL; waterLevel <= MAX_WATER_LEVEL; waterLevel++) {
			waterMeter.setCurrentLevel(waterLevel);
			tilesToFloodDuringGameTest(waterLevel);
		}
		
	}	
	
	@org.junit.After
	public void tearDown() {
		floodDeck.destroy();
		System.out.println("Tear down");
	}

	@org.junit.AfterClass
	public static void AfterClass() {
		MAX_WATER_LEVEL = 0;
		MIN_WATER_LEVEL = 0;
		NUM_TILES_TO_FLOOD_AT_START = 0;
		waterMeter = null;
		System.out.println("After Class");
	}

}
