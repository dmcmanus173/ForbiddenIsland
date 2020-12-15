package ut.board;

import static org.junit.Assert.*;

import org.junit.Test;

import fi.board.Tile;
import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;

public class TileTest {
	private static TileEnum tileName;
	private Tile tile;
	
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		tileName = TileEnum.BREAKERS_BRIDGE;
		System.out.println("BeforeClass");
	}
	
	@org.junit.Before
	public void setUp() throws Exception {
		tile = new Tile(tileName);
		System.out.println("SetUp");
	}
	

	@Test
	public void testFlood() {
		assertEquals("not flood", FloodStatusEnum.NOT_FLOODED, tile.getFloodStatus());
		
		tile.flood();
		assertEquals("flood", FloodStatusEnum.FLOODED, tile.getFloodStatus());
		
		tile.flood();
		assertEquals("sunk", FloodStatusEnum.SUNKEN, tile.getFloodStatus());
		
	}
	
	
	@Test (expected = RuntimeException.class)
	public void myTestMethod()
	{
	   try
	   {
		   System.out.println(tile.getFloodStatus());
		   tile.flood();
		   System.out.println(tile.getFloodStatus());
		   tile.flood();
		   System.out.println(tile.getFloodStatus());
		   tile.flood();
		   System.out.println(tile.getFloodStatus());
		   
	   }
	   catch(RuntimeException re)
	   {
	      String message = "Attempting to flood sunken tile.";
	      assertEquals(message, re.getMessage());
	      throw re;
	    }
	    fail("Tile Flood Null exception did not throw!");
	  }
	
	
	@org.junit.After
	public void tearDown() {
		tile = null;
		System.out.println("Tear down");
	}
	
	@org.junit.AfterClass
	public static void AfterClass() {
		tileName = TileEnum.BRONZE_GATE;
		System.out.println("After Class");
	}

}
