/**
 * 
 */
package ut.board;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import fi.board.Board;
import fi.board.Tile;
import fi.enums.AdventurerEnum;
import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.players.Player;

/**
 * @author demioke
 *
 */
public class BoardTest {
	private static TileEnum tileName;
	private Board board;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		System.out.println("BeforeClass");
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("After Class");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		board = Board.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		board.destroy();
	}
	
	@Test
	public void boardInitialisationTest() {
		Tile tile;
		for(TileEnum tileName: TileEnum.values()) {
			tile = board.getTileWithName(tileName);
			assertTrue("Tile with name " + tileName +"is on board", tile != null);
			assertTrue("Tile with name " + tileName +"is on board", tile.getFloodStatus() == FloodStatusEnum.NOT_FLOODED);	
		}
		
	}
	
	
	@Test
	public void getTilesAroundTest() {
		
		//TODO: TEST SUGGEST THAT WE MIGN NEED TO REFACTOR METHOD - split into diagonal and adjacent.
		// This is based on the positional layout of the board as printed for clarity.
		TileEnum nameOfTileWith2AdjacentAnd3DiagonalTiles = board.getIslandTiles().get(2).getTileName();
		TileEnum nameOfTileWith4AdjacentAnd3DiagonalTiles = board.getIslandTiles().get(4).getTileName();
		ArrayList<TileEnum> tilesAroundTile;
		TileEnum nameOfTileToSink;
		System.out.println(board.toString());
		
		
		// Making sure that the correct number of adjacent unsunken tiles is returned
		tilesAroundTile = board.getTilesAroundTile(nameOfTileWith2AdjacentAnd3DiagonalTiles, true);
		assertEquals("This tile should have only 2 unsunken adjacent tiles.", 2, tilesAroundTile.size());
		
		tilesAroundTile = board.getTilesAroundTile(nameOfTileWith4AdjacentAnd3DiagonalTiles, true);
		assertEquals("This tile should have only 2 unsunken adjacent tiles.", 4, tilesAroundTile.size());
		
		// Making sure that the correct number of  unsunken tiles (including diagonal) is returned
		tilesAroundTile = board.getTilesAroundTile(nameOfTileWith2AdjacentAnd3DiagonalTiles, false);
		assertEquals("This tile should have only 5 unsunken total tiles (including diagonal) around it.", 5, tilesAroundTile.size());
		
		tilesAroundTile = board.getTilesAroundTile(nameOfTileWith4AdjacentAnd3DiagonalTiles, false);
		assertEquals("This tile should have only 7 unsunken tiles (including diagonal) around it.", 7, tilesAroundTile.size());
		
		// Making sure that only unsunken tiles are returned
		nameOfTileToSink = board.getTilesAroundTile(nameOfTileWith4AdjacentAnd3DiagonalTiles, true).get(0);
		board.floodTile(nameOfTileToSink);
		board.floodTile(nameOfTileToSink);
		
		tilesAroundTile = board.getTilesAroundTile(nameOfTileWith4AdjacentAnd3DiagonalTiles, true);
		assertEquals("This tile should now have only 3 unsunken adjacent tiles around it.", 3, tilesAroundTile.size());

	}
	
	
	
	@Test
	public void getTilesToShoreUpAroundTest() {
		TileEnum nameOfTileWithFourAdjacentTiles = board.getIslandTiles().get(9).getTileName();
		ArrayList<TileEnum> tilesToShoreUp;
		ArrayList<TileEnum> tilesAroundTile;
		
		tilesToShoreUp = board.getTilesToShoreUpAround(nameOfTileWithFourAdjacentTiles);
		assertTrue("There should be no tiles to shore up once the board is created.", tilesToShoreUp.isEmpty());
	
		for(TileEnum tileName: board.getTilesAroundTile(nameOfTileWithFourAdjacentTiles, true)) {
			//flood all the tiles around a given tile
			board.floodTile(tileName);
		}
		tilesToShoreUp = board.getTilesToShoreUpAround(nameOfTileWithFourAdjacentTiles);
		assertEquals("After sinking a all 4 adjacent tiles around such a tile, getTilesToShoreUpAround method should return 4 tiles", 4, tilesToShoreUp.size());

	}
	
	@Test
	public void getNearestTileTest() {
		TileEnum nameOfFirstTileLaidOnBoard = board.getIslandTiles().get(0).getTileName();
		TileEnum nameOfLastastTileLaidOnBoard = board.getIslandTiles().get(23).getTileName();
		ArrayList<TileEnum> nearestTiles;
		
		for(Tile tile: board.getIslandTiles()) {
			// sink every tile except the first AND last tiles laid on the board
			if(tile.getTileName() != nameOfFirstTileLaidOnBoard & tile.getTileName() != nameOfLastastTileLaidOnBoard) {
				tile.flood();
				tile.flood();
			}
		}
		
		nearestTiles = board.getNearestTilesToTile(nameOfFirstTileLaidOnBoard);
		
		assertTrue("There should only be one unsunken tile in this case.", nearestTiles.size() == 1);
		assertTrue("The nearest tile should be the only unsunken tile.", nearestTiles.get(0) == nameOfLastastTileLaidOnBoard);
	}
	
	@Test
	public void movingPlayerOnBoardTest() {		
		Player player;
		TileEnum nameOfPlayerStartingTile;
		Tile tileWithPlayer;
		
		player = new Player("Demi", AdventurerEnum.DIVER);
		nameOfPlayerStartingTile = AdventurerEnum.DIVER.getStartingTileName();
		
		assertEquals("Didn't get correct number of players from tile", 1, board.getPlayersFromTile(nameOfPlayerStartingTile).size());
		for(TileEnum tileEnum : TileEnum.values()) {
			board.movePlayer(player, tileEnum);
			player.setLocation(tileEnum);
			assertEquals("Didn't get correct number of players from tile", 1, board.getPlayersFromTile(tileEnum).size());
			assertEquals("Didn't get correct players from tile", player, board.getPlayersFromTile(tileEnum).get(0));
		}

	} 
	
	//===========================================================
    // Flood Tile test
    //===========================================================
	@Test
	public void floodTileTest() {
		for(TileEnum tileEnum : TileEnum.values()) {
			// FLOOD ALL TILES
			assert(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.NOT_FLOODED);
			board.floodTile(tileEnum);
			assertEquals("Tile didn't flood.", FloodStatusEnum.FLOODED, board.getTileWithName(tileEnum).getFloodStatus());
		}
	}
	
	@Test
	public void sinkTileTest() {
		floodTileTest();
		for(TileEnum tileEnum : TileEnum.values()) {
			// FLOOD ALL TILES
			assert(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.FLOODED);
			board.floodTile(tileEnum);
			assertEquals("Tile didn't sink.", FloodStatusEnum.SUNKEN, board.getTileWithName(tileEnum).getFloodStatus());
		}
	}
	
	//===========================================================
    // get sunken/unsunken/flooded tiles test
    //===========================================================
	@Test
	public void getUnsunkenTilesTest() {
		ArrayList<TileEnum> tileEnums = board.getUnsunkenTiles();
		assertEquals("Tile lists are exepcted to be same size.", TileEnum.values().length, tileEnums.size());
		for(TileEnum tileEnum : TileEnum.values()) {
			assertEquals("Couldn't find expected tile.", Boolean.TRUE, tileEnums.contains(tileEnum));
		}
	}
	
	@Test
	public void getFloodedTilesTest() {
		floodTileTest();
		ArrayList<TileEnum> tileEnums = board.getAllFloodedTiles();
		assertEquals("Tile lists are exepcted to be same size.", TileEnum.values().length, tileEnums.size());
		for(TileEnum tileEnum : TileEnum.values()) {
			assertEquals("Couldn't find expected tile.", Boolean.TRUE, tileEnums.contains(tileEnum));
		}
	}

	@Test
	public void getSunkenTilesTest() {
		sinkTileTest();
		ArrayList<TileEnum> tileEnums = board.getUnsunkenTiles();
		assertEquals("Tile lists are exepcted to be same size.", 0, tileEnums.size());
		for(TileEnum tileEnum : TileEnum.values()) {
			assertEquals("Couldn't find expected tile.", Boolean.FALSE, tileEnums.contains(tileEnum));
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
			board.shoreUpTile(tileEnum);
			assertEquals("Tile Status changed.", FloodStatusEnum.NOT_FLOODED, board.getTileWithName(tileEnum).getFloodStatus());
		}
	}
	
	@Test
	public void shoreUpFloodedTileTest() {
		floodTileTest();
		for(TileEnum tileEnum : TileEnum.values()) {
			board.shoreUpTile(tileEnum);
			assertEquals("Tile didn't shoreUp.", FloodStatusEnum.NOT_FLOODED, board.getTileWithName(tileEnum).getFloodStatus());
		}
	}
	
	@Test
	public void shoreUpSunkenTileTest() {
		sinkTileTest();
		for(TileEnum tileEnum : TileEnum.values()) {
			board.shoreUpTile(tileEnum);
			assertEquals("Tile did shoreUp.", FloodStatusEnum.SUNKEN, board.getTileWithName(tileEnum).getFloodStatus());
		}
	}
	
	@Test
	public void getTilesToShoreUpAroundTileTest() {
		for(TileEnum tileEnum : TileEnum.values()) {
			assertEquals("Could find tiles to shore-up.", Boolean.TRUE, board.getTilesToShoreUpAround(tileEnum).isEmpty());
		}
		floodTileTest();
		for(TileEnum tileEnum : TileEnum.values()) {
			assertEquals("Couldn't find tiles to shore-up.", Boolean.FALSE, board.getTilesToShoreUpAround(tileEnum).isEmpty());
		}
	}
	
	//===========================================================
	// Testing Get other Players on Tile
	//===========================================================
	@Test
    public void getOtherPlayersOnTileTest() {
    	Player player1 = new Player("Demi", AdventurerEnum.DIVER);
    	Player player2 = new Player("Daniel", AdventurerEnum.ENGINEER);
    	
    	assertEquals("Didn't expect any other players on tile.", 0, board.getOtherPlayersOnTile(player1).size());
    	assertEquals("Didn't expect any other players on tile.", 0, board.getOtherPlayersOnTile(player2).size());
    	
    	Player player3 = new Player("McManus", AdventurerEnum.ENGINEER);
    	assertEquals("Expected one other players on tile.", 1, board.getOtherPlayersOnTile(player2).size());
    	
    	assertEquals("Expected player3 on tile.", Boolean.TRUE, board.getOtherPlayersOnTile(player2).contains(player3));
    	assertEquals("Expected player2 on tile.", Boolean.TRUE, board.getOtherPlayersOnTile(player2).contains(player3));
    }
}
