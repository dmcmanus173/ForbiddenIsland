package ut.gameOver;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fi.board.TreasureTile;
import fi.cards.Hand;
import fi.cards.TreasureCard;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.game.GameOverObserver;
import fi.treasures.TreasureManager;

public class GameOverTest {
	GameOverObserver gameOverObserver;
	TreasureManager treasureManager;

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
	
	public Hand createHandWith(TreasureEnum treasureType, int numCards) {
		 Hand hand = new Hand();
		 for(int i = 0; i < numCards; i++) {
			 hand.addCard(new TreasureCard(treasureType));
		 }
		return hand;
	}

}
