package ut.board;

import static org.junit.Assert.*;

import org.junit.Test;

import fi.board.Tile;
import fi.enums.AdventurerEnum;
import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.players.Player;

public class TileTest {
	private static TileEnum tileName;
	private static Player player1;
	private static Player player2;
	private Tile tile;
	
	
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		tileName = TileEnum.BREAKERS_BRIDGE;
		player1 = new Player("player1", AdventurerEnum.DIVER);
		player2 = new Player("player1", AdventurerEnum.ENGINEER);
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
	
	@Test
	public void testShoreUpNotFloodedTileTest() {
		assertEquals("tile should not be flooded at initialisation.", FloodStatusEnum.NOT_FLOODED, tile.getFloodStatus());
		
		tile.shoreUp();
		assertEquals("A tile that is not flooded should not change the flood status if it is shored up.", FloodStatusEnum.NOT_FLOODED, tile.getFloodStatus());
	}
	
	@Test
	public void testShoreUpFloodedTileTest() {
		assertEquals("tile should not be flooded at initialisation.", FloodStatusEnum.NOT_FLOODED, tile.getFloodStatus());
		
		tile.flood();
		assertEquals("tile should not be flooded.", FloodStatusEnum.FLOODED, tile.getFloodStatus());
		
		tile.shoreUp();
		assertEquals("tile should not be flooded after shoring up.", FloodStatusEnum.NOT_FLOODED, tile.getFloodStatus());
	}
	
	@Test
	public void testShoreUpSunkenTileTest() {
		assertEquals("tile should not be flooded at initialisation.", FloodStatusEnum.NOT_FLOODED, tile.getFloodStatus());
		
		tile.flood();
		assertEquals("tile should not be flooded.", FloodStatusEnum.FLOODED, tile.getFloodStatus());
		tile.flood();
		assertEquals("tile should not be sunken after flooding twice.", FloodStatusEnum.SUNKEN, tile.getFloodStatus());
		
		tile.shoreUp();
		assertEquals("a sunken tile should still be sunken even when shored up.", FloodStatusEnum.SUNKEN, tile.getFloodStatus());
	}
	
	@Test
	public void addingAndRemovingPlayersFromTileTest() {
		assertTrue("There should be no players on tile at initialisation.", tile.getPlayersOnTile().isEmpty());
		
		tile.addPlayerToTile(player1);
		assertTrue("Player 1 should be added to the tile", tile.getPlayersOnTile().contains(player1));
		assertEquals("There should now be only 1 player on the tile.", 1, tile.getPlayersOnTile().size());
		
		tile.addPlayerToTile(player2);
		assertTrue("Player 2 should be added to the tile", tile.getPlayersOnTile().contains(player2));
		assertTrue("Player 1 should be still be on the tile", tile.getPlayersOnTile().contains(player1));
		assertEquals("There should now be only 2 player on the tile.", 2, tile.getPlayersOnTile().size());
		
		tile.removePlayerFromTile(player1);
		assertTrue("Player 1 should be removed from the tile", !tile.getPlayersOnTile().contains(player1));
		assertTrue("Player 2 was not removed from the tile so player to should still be on the tile.", tile.getPlayersOnTile().contains(player2));
		assertEquals("There should now be only 1 player on the tile.", 1, tile.getPlayersOnTile().size());
		
		tile.removePlayerFromTile(player2);
		assertTrue("Player 2 should be removed from the tile", !tile.getPlayersOnTile().contains(player2));
		assertTrue("There should be no players on tile if all players have been removed.", tile.getPlayersOnTile().isEmpty());
	}
	
	
	@Test (expected = RuntimeException.class)
	public void addingPlayerThatIsAlreadyOnTileToTileTest() {
		assertTrue("There should be no players on tile at initialisation.", tile.getPlayersOnTile().isEmpty());
		
		try
		   {
			tile.addPlayerToTile(player1);
			tile.addPlayerToTile(player1);   
		   }
		   catch(RuntimeException re)
		   {
		      String message = "Cannot add player to a tile they already exist on.";
		      assertEquals(message, re.getMessage());
		      throw re;
		    }
		    fail("Tile addPlayerToTile Null exception did not throw!");
		
	}
	
	
	@Test (expected = RuntimeException.class)
	public void removingPlayerThatIsNotOnTileToTileTest() {
		assertTrue("There should be no players on tile at initialisation.", tile.getPlayersOnTile().isEmpty());
		
		try
		   {
			tile.removePlayerFromTile(player1); 
		   }
		   catch(RuntimeException re)
		   {
		      String message = "Cannot remove player from tile. Player does not exist on tile";
		      assertEquals(message, re.getMessage());
		      throw re;
		    }
		    fail("Tile addPlayerToTile Null exception did not throw!");
		
	}
	

	
	
	@Test (expected = RuntimeException.class)
	public void floodTileTest()
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
		player1 = null;
		player2 = null;
		System.out.println("After Class");
	}

}
