package ut.playerController;

import static org.junit.Assert.*;

import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

import fi.board.Board;
import fi.cards.HelicopterLiftCard;
import fi.cards.SandbagCard;
import fi.enums.AdventurerEnum;
import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.playerController.NavigatorController;
import fi.players.Player;

public class PlayerControllerTest {

	private static String playerName;
	private static AdventurerEnum playerRole;
	private Player player;
	private NavigatorController playerController; // Navigator is most vanilla player
	private Board board;
		
	@org.junit.BeforeClass
	public static void BeforeClass() {
		playerName = "Daniel";
		playerRole = AdventurerEnum.ENGINEER;
		System.out.println("BeforeClass");
	}
	
	@org.junit.Before
	public void setUp() throws Exception {
		player = new Player(playerName, playerRole);
		
		PrintStream out = System.out;
		// Suppress output from creating new playerController in next line
		System.setOut(new PrintStream(OutputStream.nullOutputStream()));
		playerController = new NavigatorController(player);
		// allow print to console again in next line
		System.setOut(out);
		player.discardCard(player.getCardsInPlayersHand().get(0));
		player.discardCard(player.getCardsInPlayersHand().get(0));
		board = Board.getInstance();
		System.out.println("SetUp");
	}
	
	public void floodBoard() {
		for(TileEnum tileEnum : TileEnum.values()) {
			board.floodTile(tileEnum);
		}
	}
	

	@Test
	public void handleMoveWhenCanMoveTest() {
		// Handle when player can move
		TileEnum newLocation = board.getTilesAroundTile(player.getLocation(), true).get(0);
		playerController.handleMoveUT();
		
		assertEquals("Player location not new location.", newLocation, player.getLocation());
		assertEquals("Board can't find player at new location.", player, board.getPlayersFromTile(newLocation).get(0));
	}
	
	@Test
	public void handleMoveWhenCanNotMoveTest() {
		// Handle if player cant move
		floodBoard();
		floodBoard();
		TileEnum location = player.getLocation();
		playerController.handleMoveUT();
		assertEquals("Player somehow moved.", location, player.getLocation());
		assertEquals("Board doesn't see player at location.", player, board.getPlayersFromTile(location).get(0));
	}
	
	@Test
	public void handleMoveTest() {
		handleMoveWhenCanMoveTest();
		handleMoveWhenCanNotMoveTest();
	}
	
	@Test
	public void handleShoreUpTest() {
		// No tiles to shore up
		assertEquals("Remaining actions should be 3.", 3, playerController.getRemainingActions());
		assertEquals("There are flooded tiles.", 0, board.getAllFloodedTiles().size());
		playerController.handleShoreUpUT();
		assertEquals("There are flooded tiles.", 0, board.getAllFloodedTiles().size());
		assertEquals("Remaining actions should be 3.", 3, playerController.getRemainingActions());
		
		// Successful sandbag,
		floodBoard();
		assertEquals("Incorrect number of flooded tiles", 24, board.getAllFloodedTiles().size());
		playerController.handleShoreUpUT();
		assertEquals("Tile is still flooded", 23, board.getAllFloodedTiles().size());
		assertEquals("Remaining actions should be 2.", 2, playerController.getRemainingActions());
	}
	
	@Test
	public void handleSandbagTest() {
		assertEquals("Player has a sandbag card", Boolean.FALSE, player.hasSandBagCard());
		assertEquals("There are flooded tiles.", 0, board.getAllFloodedTiles().size());
		playerController.handleSandBagUT();
		assertEquals("Player has a sandbag card", Boolean.FALSE, player.hasSandBagCard());
		assertEquals("There are flooded tiles.", 0, board.getAllFloodedTiles().size());

		// Player has sandbag card, no tiles flooded
		player.collectTreasureCard(new SandbagCard());
		playerController.handleSandBagUT();
		assertEquals("Player hasn't got a sandbag card", Boolean.TRUE, player.hasSandBagCard());
		assertEquals("There are flooded tiles.", 0, board.getAllFloodedTiles().size());

		// Player has no sandbag card, tiles to shore up
		player.didUseSandBagCard();
		assertEquals("Player has got a sandbag card", Boolean.FALSE, player.hasSandBagCard());
		floodBoard();
		playerController.handleSandBagUT();
		assertEquals("Player hasn't got a sandbag card", Boolean.FALSE, player.hasSandBagCard());
		assertEquals("Tile is still flooded", 24, board.getAllFloodedTiles().size());

		// Successful sandbag, card + tiles
		player.collectTreasureCard(new SandbagCard());
		playerController.handleSandBagUT();
		assertEquals("Player still has a sandbag card", Boolean.FALSE, player.hasSandBagCard());
		assertEquals("Tile is still flooded", 23, board.getAllFloodedTiles().size());
	}
	@Test
	public void handleDiscardTreasureCardTest() {
		assertEquals("There is a card in the players hand.", Boolean.TRUE, player.getCardsInPlayersHand().isEmpty());
		playerController.handleDiscardTreasureCardUT();
		assertEquals("There is a card in the players hand.", Boolean.TRUE, player.getCardsInPlayersHand().isEmpty());
		player.collectTreasureCard(new SandbagCard());
		assertEquals("There should be 1 card in the players hand.", 1, player.getCardsInPlayersHand().size());
		playerController.handleDiscardTreasureCardUT();
		assertEquals("There is a card in the players hand.", Boolean.TRUE, player.getCardsInPlayersHand().isEmpty());
	}
	
	@Test
	public void handlePlayerSunkCanMoveTest() {
		Boolean didMove;
		TileEnum location = player.getLocation();
		board.floodTile(location);
		board.floodTile(location);
		
		didMove = playerController.handlePlayerSunkUT();
		assertEquals("Player didn't move.", Boolean.TRUE, didMove);
		assertNotEquals("Player location didn't change.", location, player.getLocation());
		assertNotEquals("Player is on a sunk tile", FloodStatusEnum.SUNKEN, board.getTileWithName(player.getLocation()).getFloodStatus());
	}
	
	@Test
	public void handlePlayerSunkCanNotMoveTest() {
		Boolean didMove;
		TileEnum location = player.getLocation();
		
		floodBoard();
		floodBoard();
		
		didMove = playerController.handlePlayerSunkUT();
		
		assertEquals("Player moved.", Boolean.FALSE, didMove);
		assertEquals("Player location didn't change.", location, player.getLocation());
		assertEquals("Player is not on a sunk tile", FloodStatusEnum.SUNKEN, board.getTileWithName(player.getLocation()).getFloodStatus());
		
	}
	
	@Test
	public void handleHelicopterLiftCanMoveTest() {
		
		player.move(TileEnum.DUNES_OF_DECEPTION);
		TileEnum location = player.getLocation();
		// Player can move - Helicopter Lift
		player.collectTreasureCard(new HelicopterLiftCard());
		playerController.handleHelicopterLiftUT();
		assertNotEquals("Player location didn't change.", location, player.getLocation());
		assertNotEquals("Player is on a sunk tile", FloodStatusEnum.SUNKEN, board.getTileWithName(player.getLocation()).getFloodStatus());
			
	}
	
	@Test
	public void handleHelicopterLiftCanNotMoveTest() {
		player.move(TileEnum.CRIMSON_FOREST);
		TileEnum location = player.getLocation();
		// Player can't move - no Helicopter Lift
		playerController.handleHelicopterLiftUT();
		assertEquals("Player location didn't change.", location, player.getLocation());
		assertNotEquals("Player is on a sunk tile", FloodStatusEnum.SUNKEN, board.getTileWithName(player.getLocation()).getFloodStatus());
		
		floodBoard();
		floodBoard();
		player.collectTreasureCard(new HelicopterLiftCard());
		playerController.handleHelicopterLiftUT();
		assertEquals("Player location didn't change.", location, player.getLocation());
		assertEquals("Player is on a sunk tile", FloodStatusEnum.SUNKEN, board.getTileWithName(player.getLocation()).getFloodStatus());
		
	}
	
	@Test
	public void turnHasEnded() {
		assertEquals("Didn't expect turn to end. 0 Goes taken.", Boolean.FALSE, playerController.getTurnHasEnded());
		handleMoveWhenCanMoveTest();
		assertEquals("Didn't expect turn to end. 1 Goes taken.", Boolean.FALSE, playerController.getTurnHasEnded());
		handleMoveWhenCanMoveTest();
		assertEquals("Didn't expect turn to end. 2 Goes taken.", Boolean.FALSE, playerController.getTurnHasEnded());
		handleMoveWhenCanMoveTest();
		assertEquals("Didn't expect turn to end. 3 Goes taken.", Boolean.TRUE, playerController.getTurnHasEnded());
	}
	
	@org.junit.After
	public void tearDown() {
		playerController = null;
		player = null;
		board.destroy();
		System.out.println("Tear down");
	}
	
	@org.junit.AfterClass
	public static void AfterClass() {
		playerName = null;
		playerRole = null;
		System.out.println("After Class");
	}


}
