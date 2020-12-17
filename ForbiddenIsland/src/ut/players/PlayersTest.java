package ut.players;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fi.enums.AdventurerEnum;
import fi.players.Player;
import fi.players.Players;

public class PlayersTest {

	private static String playerName1;
	private static AdventurerEnum playerRole1;
	private static String playerName2;
	private static AdventurerEnum playerRole2;
	private static String playerName3;
	private static AdventurerEnum playerRole3;
	;
	private Players players;
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		playerName1 = "Daniel";
		playerRole1 = AdventurerEnum.DIVER;
		playerName2 = "Demi";
		playerRole2 = AdventurerEnum.ENGINEER;
		playerName3 = "Hayley";
		playerRole3 = AdventurerEnum.EXPLORER;
		System.out.println("BeforeClass");
	}
	
	@org.junit.Before
	public void setUp() throws Exception {
		players = Players.getInstance();
		System.out.println("SetUp");
	}
	
	@Test
	public void initialTest() {
		assertEquals("Expected NumPlayers to be 0.", 0, players.getNumPlayers());
		assertEquals("Expected no players in players list.", 0, players.getPlayers().size());
	}
	
	@Test
	public void addPlayersTest() {
		players.addPlayer(playerName1, playerRole1);
		assertEquals("Expected NumPlayers to be 1.", 1, players.getNumPlayers());
		assertEquals("Expected 1 players in players list.", 1, players.getPlayers().size());
		assertEquals("Didn't get expected playerName", playerName1, players.getPlayers().get(0).getName());
		assertEquals("Didn't get expected playerRole", playerRole1, players.getPlayers().get(0).getRole());
		
		players.addPlayer(playerName2, playerRole2);
		assertEquals("Expected NumPlayers to be 2.", 2, players.getNumPlayers());
		assertEquals("Expected 2 players in players list.", 2, players.getPlayers().size());
		assertEquals("Didn't get expected playerName", playerName1, players.getPlayers().get(0).getName());
		assertEquals("Didn't get expected playerRole", playerRole1, players.getPlayers().get(0).getRole());
		assertEquals("Didn't get expected playerName", playerName2, players.getPlayers().get(1).getName());
		assertEquals("Didn't get expected playerRole", playerRole2, players.getPlayers().get(1).getRole());
		
		players.addPlayer(playerName3, playerRole3);
		assertEquals("Expected NumPlayers to be 3.", 3, players.getNumPlayers());
		assertEquals("Expected 3 players in players list.", 3, players.getPlayers().size());
		assertEquals("Didn't get expected playerName", playerName1, players.getPlayers().get(0).getName());
		assertEquals("Didn't get expected playerRole", playerRole1, players.getPlayers().get(0).getRole());
		assertEquals("Didn't get expected playerName", playerName2, players.getPlayers().get(1).getName());
		assertEquals("Didn't get expected playerRole", playerRole2, players.getPlayers().get(1).getRole());
		assertEquals("Didn't get expected playerName", playerName3, players.getPlayers().get(2).getName());
		assertEquals("Didn't get expected playerRole", playerRole3, players.getPlayers().get(2).getRole());
	}
	
	@Test
	public void getPlayersExceptTest() {
		addPlayersTest();
		ArrayList<Player> playerList = new ArrayList<>();
		playerList.addAll(players.getPlayers());
		
		ArrayList<Player> playersExcept = new ArrayList<>();
		
		playersExcept = players.getPlayersExcept(playerList.get(0));
		assertEquals("Expected NumPlayers to be 2.", 2, playersExcept.size());
		assertEquals("Didn't get expected playerName", playerName2, playersExcept.get(0).getName());
		assertEquals("Didn't get expected playerRole", playerRole2, playersExcept.get(0).getRole());
		assertEquals("Didn't get expected playerName", playerName3, playersExcept.get(1).getName());
		assertEquals("Didn't get expected playerRole", playerRole3, playersExcept.get(1).getRole());
		
		playersExcept = players.getPlayersExcept(playerList.get(1));
		assertEquals("Expected NumPlayers to be 2.", 2, playersExcept.size());
		assertEquals("Didn't get expected playerName", playerName1, playersExcept.get(0).getName());
		assertEquals("Didn't get expected playerRole", playerRole1, playersExcept.get(0).getRole());
		assertEquals("Didn't get expected playerName", playerName3, playersExcept.get(1).getName());
		assertEquals("Didn't get expected playerRole", playerRole3, playersExcept.get(1).getRole());
		
		playersExcept = players.getPlayersExcept(playerList.get(2));
		assertEquals("Expected NumPlayers to be 2.", 2, playersExcept.size());
		assertEquals("Didn't get expected playerName", playerName1, playersExcept.get(0).getName());
		assertEquals("Didn't get expected playerRole", playerRole1, playersExcept.get(0).getRole());
		assertEquals("Didn't get expected playerName", playerName2, playersExcept.get(1).getName());
		assertEquals("Didn't get expected playerRole", playerRole2, playersExcept.get(1).getRole());
	}

	@Test
	public void removePlayersTest() {
		addPlayersTest();
		players.removeAllPlayers();
		initialTest();
	}
	
	@org.junit.After
	public void tearDown() {
		players.destroy();
		System.out.println("Tear down");
	}
	
	@org.junit.AfterClass
	public static void AfterClass() {
		playerName1 = null;
		playerRole1 = null;
		playerName2 = null;
		playerRole2 = null;
		playerName3 = null;
		playerRole3 = null;
		System.out.println("After Class");
	}

}
