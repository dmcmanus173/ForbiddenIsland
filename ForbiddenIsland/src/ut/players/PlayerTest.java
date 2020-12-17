package ut.players;

import static org.junit.Assert.*;

import org.junit.Test;

import fi.board.Board;
import fi.cards.Card;
import fi.cards.HelicopterLiftCard;
import fi.cards.SandbagCard;
import fi.cards.TreasureCard;
import fi.enums.AdventurerEnum;
import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import fi.players.Player;

public class PlayerTest {

	private static String playerName;
	private static AdventurerEnum playerRole;
	private static String otherName;
	private static AdventurerEnum otherRole;
	
	private static HelicopterLiftCard helicopterLift;
	private static SandbagCard sandbag;
	private static TreasureEnum oceansChalice;
	private static TreasureEnum statueOfWind;
	private static TreasureEnum earthStone;
	private static TreasureEnum crystalOfFire;
	
	private static Board board;
	private Player player;
	private Player otherPlayer;
	
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		playerName = "Daniel";
		playerRole = AdventurerEnum.ENGINEER;
		otherName = "Demi";
		otherRole = AdventurerEnum.EXPLORER;
		
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
		player = new Player(playerName, playerRole);
		otherPlayer = new Player(otherName, otherRole);
		
		board = Board.getInstance();
		
		System.out.println("SetUp");
	}	

	public void playerInitialisationFunction(Player aPlayer, String aName, AdventurerEnum aRole) {
		assertEquals("Player name didn't match expected.", aName, aPlayer.getName());
		assertEquals("Player role didn't match expected.", aRole, aPlayer.getRole());
		assertEquals("Initial Player location is not expected.", aRole.getStartingTileName(), aPlayer.getLocation());
		//TODO test if player is on the board?
		assertEquals("Initial Player should have no cards.", Boolean.TRUE, aPlayer.getCardsInPlayersHand().isEmpty());
		assertEquals("Initial Player shouldn't have helicopter lift card", Boolean.FALSE, aPlayer.hasHelicopterLiftCard());
		assertEquals("Initial Player shouldn't have sandbag card", Boolean.FALSE, aPlayer.hasSandBagCard());
	}
	
	@Test
	public void playerInitialisationTest() {
		playerInitialisationFunction(player, playerName, playerRole);
		playerInitialisationFunction(otherPlayer, otherName, otherRole);
	}
	
	//===========================================================
	// Testing move
	//===========================================================
	@Test
	public void moveNotFloodedPlayerTest() {
		shoreUpNotFloodedTileTest();
		for(TileEnum tileEnum : TileEnum.values()) {
			player.move(tileEnum);
			assertEquals("Player location is not expected.", tileEnum, player.getLocation());
		}
	}
	
	@Test
	public void moveFloodedPlayerTest() {
		shoreUpFloodedTileTest();
		for(TileEnum tileEnum : TileEnum.values()) {
			// FLOOD ALL TILES
			if(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.NOT_FLOODED)
				board.floodTile(tileEnum);
			assert(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.FLOODED);
			
			player.move(tileEnum);
			assertEquals("Player location is not expected.", tileEnum, player.getLocation());
		}
	}
	
	@Test
	public void moveSunkPlayerTest() {
		shoreUpSunkenTileTest();
		for(TileEnum tileEnum : TileEnum.values()) {
			player.move(tileEnum);
			assertEquals("Player location is not expected.", tileEnum, player.getLocation());
		}
	}
	
	//===========================================================
	// Testing shoreUp
	//===========================================================
	@Test
	public void shoreUpNotFloodedTileTest() {
		for(TileEnum tileEnum : TileEnum.values()) {
			assert(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.NOT_FLOODED);

			// TEST IF SHOREUP ALL TILES
			player.shoreUp(tileEnum);
			assertEquals("Tile Status changed.", FloodStatusEnum.NOT_FLOODED, board.getTileWithName(tileEnum).getFloodStatus());
		}
	}
	
	@Test
	public void shoreUpFloodedTileTest() {
		for(TileEnum tileEnum : TileEnum.values()) {
			// FLOOD ALL TILES
			if(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.NOT_FLOODED)
				board.floodTile(tileEnum);
			assert(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.FLOODED);

			// TEST IF SHOREUP ALL TILES
			player.shoreUp(tileEnum);
			assertEquals("Tile didn't shoreUp.", FloodStatusEnum.NOT_FLOODED, board.getTileWithName(tileEnum).getFloodStatus());
		}
	}
	
	@Test
	public void shoreUpSunkenTileTest() {
		for(TileEnum tileEnum : TileEnum.values()) {
			// SINK ALL TILES
			if(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.NOT_FLOODED)
				board.floodTile(tileEnum);
			if(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.FLOODED)
				board.floodTile(tileEnum);			
			assert(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.SUNKEN);

			// TEST IF SHOREUP ALL TILES
			player.shoreUp(tileEnum);
			assertEquals("Tile did shoreUp.", FloodStatusEnum.SUNKEN, board.getTileWithName(tileEnum).getFloodStatus());
		}
	}
	
	//===========================================================
	// Testing Collect TreasureCard
	//===========================================================
	@Test 
	public void testCollectingDiscardHelicopterLift(){
		player.collectTreasureCard(helicopterLift);
		assertEquals("Expected 1 card available.", 1, player.getCardsInPlayersHand().size());
		Card card = player.getCardsInPlayersHand().get(0);
		assertEquals("Card returned from getCards doesn't match card added.", helicopterLift, card);
		
		assertEquals("There isn't a HelicopterLift Card present after adding it.", Boolean.TRUE, player.hasHelicopterLiftCard());
		assertEquals("There is a Sandbag Card after adding a Helicopter Lift.", Boolean.FALSE, player.hasSandBagCard());
		
		player.discardCard(helicopterLift);
		
		assertEquals("Expected no cards to be available.",0 , player.getCardsInPlayersHand().size());
		
		assertEquals("There is a HelicopterLift Card present. Expected none.", Boolean.FALSE, player.hasHelicopterLiftCard());
		assertEquals("There is a Sandbag Card present. Expected none."       , Boolean.FALSE, player.hasSandBagCard());
	}
	
	@Test 
	public void testAddingRemovingSandbag(){
		player.collectTreasureCard(sandbag);
		assertEquals("Expected 1 card available.", 1, player.getCardsInPlayersHand().size());
		Card card = player.getCardsInPlayersHand().get(0);
		assertEquals("Card returned from getCards doesn't match card added.", sandbag, card);
		
		assertEquals("There is a HelicopterLift Card present after a sandbag card.", Boolean.FALSE, player.hasHelicopterLiftCard());
		assertEquals("There isn't a Sandbag Card after adding one.", Boolean.TRUE, player.hasSandBagCard());
		
		player.discardCard(sandbag);
		
		assertEquals("Expected no cards to be available.",0 , player.getCardsInPlayersHand().size());
		
		assertEquals("There is a HelicopterLift Card present. Expected none.", Boolean.FALSE, player.hasHelicopterLiftCard());
		assertEquals("There is a Sandbag Card present. Expected none."       , Boolean.FALSE, player.hasSandBagCard());
	}
	 
	public void testFunctionAddingRemovingTreasureCard(TreasureEnum aTreasureEnum){
		System.out.println("Running testFunctionAddingRemovingTreasureCard for "+aTreasureEnum.toString());
		TreasureCard treasureCard = new TreasureCard(aTreasureEnum);
		
		player.collectTreasureCard(treasureCard);
		assertEquals("Expected 1 card available.", player.getCardsInPlayersHand().size(), 1);
		
		Card card = player.getCardsInPlayersHand().get(0);
		assertEquals("Card returned from getCards doesn't match card added.", card, treasureCard);
		
		assertEquals("There is a HelicopterLift Card present after adding "+aTreasureEnum.toString()+" Card", Boolean.FALSE, player.hasHelicopterLiftCard());
		assertEquals("There is a Sandbag Card after adding "               +aTreasureEnum.toString()+" Card", Boolean.FALSE, player.hasSandBagCard());
		
		player.discardCard(treasureCard);
		
		assertEquals("Expected no cards to be available.",0 , player.getCardsInPlayersHand().size());
		
		assertEquals("There is a HelicopterLift Card present. Expected none.", Boolean.FALSE, player.hasHelicopterLiftCard());
		assertEquals("There is a Sandbag Card present. Expected none."       , Boolean.FALSE, player.hasSandBagCard());
	}
	
	@Test
	public void testAddingRemovingATreasureCard(){
		testFunctionAddingRemovingTreasureCard(oceansChalice);
		testFunctionAddingRemovingTreasureCard(statueOfWind );
		testFunctionAddingRemovingTreasureCard(earthStone   );
		testFunctionAddingRemovingTreasureCard(crystalOfFire);
	}
	
	//===========================================================
	// Testing remove of cards that don't have
	//===========================================================
	@Test (expected = RuntimeException.class)
	public void removingHelicopterLiftWhenHandEmpty() {
		   try
		   {
			   player.didUseHelicopterLiftCard();
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
			   player.didUseSandBagCard();
		   }
		   catch(RuntimeException re)
		   {
		      String message = "Attempting to use A SandBagCard that the player does not have.";
		      assertEquals(message, re.getMessage());
		      throw re;
		    }
		    fail("Removing SandbagCard when No Card in Hand exception did not throw!");
	}
	
	public void removeCardWhenHandEmpty(Card aCard) {
		try
		{
			player.discardCard(aCard);
		}
		catch(RuntimeException re)
		{
			String message = "Attempting to remove a Treasure Card that the player does not have.";
		    assertEquals(message, re.getMessage());
		    throw re;
		}
		fail("Removing a card when No Card in Hand exception did not throw!");
	}
	
	@Test (expected = RuntimeException.class)
	public void removingCardsWhenHandEmpty(){
		TreasureCard oceansChaliceCard = new TreasureCard(oceansChalice);
		TreasureCard statueOfWindCard = new TreasureCard(statueOfWind);
		TreasureCard earthStoneCard = new TreasureCard(earthStone);
		TreasureCard crystalOfFireCard = new TreasureCard(crystalOfFire);
		
		removeCardWhenHandEmpty(oceansChaliceCard);
		removeCardWhenHandEmpty(statueOfWindCard );
		removeCardWhenHandEmpty(earthStoneCard   );
		removeCardWhenHandEmpty(crystalOfFireCard);
		
	}
	
	//===========================================================
	// Testing containsSpecialCard
	//===========================================================
	@Test
	public void containsSandbagTest(){
		assertEquals("Player should not contain a sandbag.", Boolean.FALSE, player.hasSandBagCard());
		player.collectTreasureCard(sandbag);
		assertEquals("Player should contain a sandbag.", Boolean.TRUE, player.hasSandBagCard());
	}
	
	@Test
	public void containsHelicopterLiftTest(){
		assertEquals("Player should not contain a Helicopter Lift.", Boolean.FALSE, player.hasHelicopterLiftCard());
		player.collectTreasureCard(helicopterLift);
		assertEquals("Player should contain a Helicopter Lift.", Boolean.TRUE, player.hasHelicopterLiftCard());
	}
	
	//===========================================================
	// Testing handIsFull
	//===========================================================
	@Test
	public void handIsFullTest() {
		player.collectTreasureCard(sandbag);
		assertEquals("Hand is full after adding 1 card.", Boolean.FALSE, player.handIsFull());
		player.collectTreasureCard(sandbag);
		assertEquals("Hand is full after adding 2 card.", Boolean.FALSE, player.handIsFull());
		player.collectTreasureCard(sandbag);
		assertEquals("Hand is full after adding 3 card.", Boolean.FALSE, player.handIsFull());
		player.collectTreasureCard(sandbag);
		assertEquals("Hand is full after adding 4 card.", Boolean.FALSE, player.handIsFull());
		player.collectTreasureCard(sandbag);
		assertEquals("Hand is full after adding 1 card.", Boolean.TRUE, player.handIsFull());
	}
	
	//===========================================================
	// Give treasure card test
	//===========================================================
	@Test
	public void giveTreasureCardsTest() {
		player.collectTreasureCard(sandbag);
		player.giveTreasureCard(otherPlayer, sandbag);
		assertEquals("Player still contains a card", 0, player.getCardsInPlayersHand().size());
		assertEquals("Other player didn't receive a card", 1, otherPlayer.getCardsInPlayersHand().size());
		assertEquals("Other player didn't receive correct card", sandbag, otherPlayer.getCardsInPlayersHand().get(0));
	}
	
	@Test (expected = RuntimeException.class)
	public void giveTreasureCardsToPlayerWithFullDeck() {
		for(int i=0; i<5; i++)
			otherPlayer.collectTreasureCard(sandbag);
		try
		{
			giveTreasureCardsTest();
		}
		catch(RuntimeException re)
		{
			String message = "Attempting to give card to player but player's hand is full.";
		    assertEquals(message, re.getMessage());
		    throw re;
		}
		fail("Removing a card when No Card in Hand exception did not throw!");
	}
	
	//===========================================================
	// Testing treasureCollection
	//===========================================================
	@Test
	public void canCollectTreasureNotEnoughCardsTest() {
		//TODO get this from Demi. Same as TreasureManager.
	}
	
	@Test
	public void canCollectTreasureNotOnTreasureTileTest() {
		//TODO get this from Demi. Same as TreasureManager.
	}
	
	@Test
	public void canCollectTreasureTest() {
		//TODO get this from Demi. Same as TreasureManager.
	}
	
	@Test
	public void collectTreasureTest() {
		//TODO
	}
	
	@org.junit.After
	public void tearDown() {
		player = null;
		otherPlayer = null;
		board.destroy();
		System.out.println("Tear down");
	}
	
	@org.junit.AfterClass
	public static void AfterClass() {
		playerName = null;
		playerRole = null;
		otherName = null;
		otherName = null;
		
		helicopterLift = null;
		sandbag = null;
		
		oceansChalice = null;
		statueOfWind  = null;
		earthStone    = null;
		crystalOfFire = null;
		
		System.out.println("After Class");
	}

}
