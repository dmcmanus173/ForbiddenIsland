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

//	@Test
//	public void floodStatusOfTilesOnBoardTest() {
//		assertTrue("There are no flooded tiles when the board is created.", board.getAllFloodedTiles().isEmpty());
//		assertTrue("There are no sunken tiles when the board is created.", board.getUnsunkenTiles().size() == 24);
//	}
	
	@Test
	public void boardInitialisationTest() {
//		Tile tile;
		for(TileEnum tileName: TileEnum.values()) {
//			tile = board.getTileWithName(tileName);
//			assertTrue("Tile with name " + tileName +"is on board", tile != null);
//			assertTrue("Tile with name " + tileName +"is on board", tile != null);	
			assertTrue("Tile with name " + tileName +"is on board and is not flooded", board.getTileWithName(tileName).getFloodStatus() == FloodStatusEnum.NOT_FLOODED);

		}
		
	}

}
