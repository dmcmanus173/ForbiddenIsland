/**
 * 
 */
package ut.board;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import fi.board.Board;
import fi.board.Tile;
import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;

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

}
