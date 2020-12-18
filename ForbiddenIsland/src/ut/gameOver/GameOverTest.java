package ut.gameOver;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fi.board.FoolsLandingTile;
import fi.board.Tile;
import fi.board.TreasureTile;
import fi.cards.Hand;
import fi.cards.TreasureCard;
import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.game.GameOverObserver;
import fi.treasures.TreasureManager;
import fi.watermeter.WaterMeter;

public class GameOverTest {
	private GameOverObserver gameOverObserver;
	private TreasureManager treasureManager;
	private WaterMeter waterMeter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		gameOverObserver = GameOverObserver.getInstance();
		treasureManager = TreasureManager.getInstance();
		waterMeter = WaterMeter.getInstance();
		System.out.println("SetUp");
	}

	@After
	public void tearDown() throws Exception {
		gameOverObserver.destroy();
		treasureManager.destroy();
		System.out.println("Tear down");
	}

	@Test
	public void sunkenTreasureTilesWithUnclaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.HOWLING_GARDEN);
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.WHISPERING_GARDEN);
		
		//TODO: MAKE MESSAGIS MORE SPECIFIC
		
		assertEquals("No action has caused the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		windTreasureTile1.flood();
		assertEquals("Flooding a treasure tile has caused the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		windTreasureTile1.flood();
		assertEquals("Sinking a single treasure tile has caused the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		
		windTreasureTile2.flood();
		assertEquals("Flooding a treasure tile has caused the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		windTreasureTile2.flood();
		assertEquals("Sinking both treasure tiles associated with a given treasure that has not been claimed did not end the game.", Boolean.TRUE, gameOverObserver.isGameOver());
	
	}
	
	@Test
	public void sunkenTreasureTilesWithClaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.HOWLING_GARDEN); 
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.WHISPERING_GARDEN);
		Hand hand = createHandWith(windTreasureTile1.getTreasureType(), 4);
		
		assertEquals("No action has caused the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		windTreasureTile1.flood();
		assertEquals("Flooding a treasure tile has caused the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		windTreasureTile1.flood();
		assertEquals("Sinking a single treasure tile has caused the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		
		treasureManager.claimTreasure(hand, windTreasureTile1.getTreasureType());
		
		windTreasureTile2.flood();
		assertEquals("Flooding a treasure tile has caused the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		windTreasureTile2.flood();
		assertEquals("Sinking both treasure tiles associated with a given treasure that has been claimed caused the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
	
	}
	
	//=======================================================================
    // Testing game over when WaterMeter reaches maximum level
    //=======================================================================
	@Test
	public void waterMeterMaxTestTest() {
		while(waterMeter.getCurrentLevel() < waterMeter.getGameOverLevel()) {
			waterMeter.increaseWaterMeter();
			if(waterMeter.getCurrentLevel() == waterMeter.getGameOverLevel()) {
				assertEquals("The game should end if the water level has reached it's maximum", Boolean.TRUE, gameOverObserver.isGameOver());
			} else {
				assertEquals("The game should not end if the water level has not reached it's maximum", Boolean.FALSE, gameOverObserver.isGameOver());
			}
		}
	}
	
	//=======================================================================
    // Testing game over when Fools Landing Tile sinks 
    //=======================================================================
	@Test
	public void sunkenFoolsLandingTilesTest() {
		FoolsLandingTile foolsLandingTile = new FoolsLandingTile(TileEnum.FOOLS_LANDING);
		assertEquals("Flood status should be Not Flooded", FloodStatusEnum.NOT_FLOODED, foolsLandingTile.getFloodStatus());
		
		foolsLandingTile.flood();
		assertEquals("Flood status should be Flooded", FloodStatusEnum.FLOODED, foolsLandingTile.getFloodStatus());
		assertEquals("Flooding Fools Island should not cause the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		foolsLandingTile.flood();
		assertEquals("Flood status should be Flooded", FloodStatusEnum.SUNKEN, foolsLandingTile.getFloodStatus());
		assertEquals("Sinking Fools Island should cause the game to end.", Boolean.TRUE, gameOverObserver.isGameOver());
	} 
	
	//=======================================================================
    // Testing game over when treasure is unclaimed and treasure tiles sink
    //=======================================================================
	@Test
	public void sunkenWindTreasureTilesWithoutClaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.HOWLING_GARDEN);
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.WHISPERING_GARDEN);
		treasureTileSinkWithoutClaimedTreasureFunction(windTreasureTile1, windTreasureTile2);
	}
	
	@Test
	public void sunkenFireTreasureTilesWithoutClaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.CAVE_OF_EMBERS);
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.CAVE_OF_SHADOWS);
		treasureTileSinkWithoutClaimedTreasureFunction(windTreasureTile1, windTreasureTile2);
	}
	
	@Test
	public void sunkenWaterTreasureTilesWithoutClaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.CORAL_PALACE);
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.TIDAL_PALACE);
		treasureTileSinkWithoutClaimedTreasureFunction(windTreasureTile1, windTreasureTile2);
	}
	
	@Test
	public void sunkenEarthTreasureTilesWithoutClaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.TEMPLE_OF_THE_MOON);
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.TEMPLE_OF_THE_SUN);
		treasureTileSinkWithoutClaimedTreasureFunction(windTreasureTile1, windTreasureTile2);
	}
	
	//=======================================================================
    // Testing game over when treasure is claimed and treasure tiles sink
    //=======================================================================
	@Test
	public void sunkenWindTreasureTilesWithClaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.HOWLING_GARDEN);
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.WHISPERING_GARDEN);
		treasureTileSinkWithClaimedTreasureFunction(windTreasureTile1, windTreasureTile2);
	}
	
	@Test
	public void sunkenFireTreasureTilesWithClaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.CAVE_OF_EMBERS);
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.CAVE_OF_SHADOWS);
		treasureTileSinkWithClaimedTreasureFunction(windTreasureTile1, windTreasureTile2);
	}
	
	@Test
	public void sunkenWaterTreasureTilesWithClaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.CORAL_PALACE);
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.TIDAL_PALACE);
		treasureTileSinkWithClaimedTreasureFunction(windTreasureTile1, windTreasureTile2);
	}
	
	@Test
	public void sunkenEarthTreasureTilesWithClaimedTreasureTest() {
		TreasureTile windTreasureTile1 = new TreasureTile(TileEnum.TEMPLE_OF_THE_MOON);
		TreasureTile windTreasureTile2 = new TreasureTile(TileEnum.TEMPLE_OF_THE_SUN);
		treasureTileSinkWithClaimedTreasureFunction(windTreasureTile1, windTreasureTile2);
	}
	
	
	//=======================================================================
    // Helper functions
    //=======================================================================
	public void treasureTileSinkWithoutClaimedTreasureFunction(TreasureTile treasureTile1, TreasureTile treasureTile2) {
		
		assertEquals("Flood status should be Not Flooded", FloodStatusEnum.NOT_FLOODED, treasureTile1.getFloodStatus());
		assertEquals("Expect 0 Tile to be Sunk for this treasure", 0, gameOverObserver.getNumSunkTreasures(treasureTile1.getTreasureType()));
		
		treasureTile1.flood();
		assertEquals("Flood status should be Flooded", FloodStatusEnum.FLOODED, treasureTile1.getFloodStatus());
		assertEquals("Expect 0 Tile to be Sunk for this treasure", 0, gameOverObserver.getNumSunkTreasures(treasureTile1.getTreasureType()));
		assertEquals("Flooding a treasure tile should not cause the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		treasureTile1.flood();
		assertEquals("Flood status should be Sunken", FloodStatusEnum.SUNKEN, treasureTile1.getFloodStatus());
		assertEquals("Expect 1 Tile to be Sunk for this treasure", 1, gameOverObserver.getNumSunkTreasures(treasureTile1.getTreasureType()));
		assertEquals("Sinking a single treasure tile should not cause the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		assertEquals("Flood status should be Not Flooded", FloodStatusEnum.NOT_FLOODED, treasureTile2.getFloodStatus());
		assertEquals("Expect 1 Tile to be Sunk for this treasure", 1, gameOverObserver.getNumSunkTreasures(treasureTile2.getTreasureType()));
		
		treasureTile2.flood();
		assertEquals("Flood status should be Flooded", FloodStatusEnum.FLOODED, treasureTile2.getFloodStatus());
		assertEquals("Expect 1 Tile to be Sunk for this treasure", 1, gameOverObserver.getNumSunkTreasures(treasureTile2.getTreasureType()));
		assertEquals("Flooding a treasure tile should not cause the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		treasureTile2.flood();
		assertEquals("Flood status should be Sunken", FloodStatusEnum.SUNKEN, treasureTile2.getFloodStatus());
		assertEquals("Expect 2 Tile to be Sunk for this treasure", 2, gameOverObserver.getNumSunkTreasures(treasureTile2.getTreasureType()));
		assertEquals("Sinking both treasure tiles associated with a given treasure that has not been claimed should end the game.", Boolean.TRUE, gameOverObserver.isGameOver());
	}
	
	public void treasureTileSinkWithClaimedTreasureFunction(TreasureTile treasureTile1, TreasureTile treasureTile2) {
		Hand hand = createHandWith(treasureTile1.getTreasureType(), 4);
		treasureManager.claimTreasure(hand, treasureTile1.getTreasureType());
		
		assertEquals("Flood status should be Not Flooded", FloodStatusEnum.NOT_FLOODED, treasureTile1.getFloodStatus());
		assertEquals("Expect 0 Tile to be Sunk for this treasure", 0, gameOverObserver.getNumSunkTreasures(treasureTile1.getTreasureType()));
		
		treasureTile1.flood();
		assertEquals("Flood status should be Flooded", FloodStatusEnum.FLOODED, treasureTile1.getFloodStatus());
		assertEquals("Expect 0 Tile to be Sunk for this treasure", 0, gameOverObserver.getNumSunkTreasures(treasureTile1.getTreasureType()));
		assertEquals("Flooding a treasure tile should not cause the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		treasureTile1.flood();
		assertEquals("Flood status should be Sunken", FloodStatusEnum.SUNKEN, treasureTile1.getFloodStatus());
		assertEquals("Expect 1 Tile to be Sunk for this treasure", 1, gameOverObserver.getNumSunkTreasures(treasureTile1.getTreasureType()));
		assertEquals("Sinking a single treasure tile should not cause the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		assertEquals("Flood status should be Not Flooded", FloodStatusEnum.NOT_FLOODED, treasureTile2.getFloodStatus());
		assertEquals("Expect 1 Tile to be Sunk for this treasure", 1, gameOverObserver.getNumSunkTreasures(treasureTile2.getTreasureType()));
		
		treasureTile2.flood();
		assertEquals("Flood status should be Flooded", FloodStatusEnum.FLOODED, treasureTile2.getFloodStatus());
		assertEquals("Expect 1 Tile to be Sunk for this treasure", 1, gameOverObserver.getNumSunkTreasures(treasureTile2.getTreasureType()));
		assertEquals("Flooding a treasure tile should not cause the game to end.", Boolean.FALSE, gameOverObserver.isGameOver());
		
		treasureTile2.flood();
		assertEquals("Flood status should be Sunken", FloodStatusEnum.SUNKEN, treasureTile2.getFloodStatus());
		assertEquals("Expect 2 Tile to be Sunk for this treasure", 2, gameOverObserver.getNumSunkTreasures(treasureTile2.getTreasureType()));
		assertEquals("Sinking both treasure tiles associated with a given treasure that has been claimed should not end the game.", Boolean.FALSE, gameOverObserver.isGameOver());
	}
	
	public Hand createHandWith(TreasureEnum treasureType, int numCards) {
		 Hand hand = new Hand();
		 for(int i = 0; i < numCards; i++) {
			 hand.addCard(new TreasureCard(treasureType));
		 }
		return hand;
	}

}
