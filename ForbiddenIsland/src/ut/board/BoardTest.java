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
