package setup;

import java.util.ArrayList;
import java.util.Collections;

import enums.AdventurerEnum;
import gameManager.GetInput;
import player.Players;

/**
 * Class for PlayerSetup in Forbidden Island game.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    17/11/2020
 * @version 0.2
 */
public class PlayerSetup {

	//===========================================================
	// Variable Setup
	//===========================================================
	private int numPlayers;
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MIN_PLAYERS = 2;
    private final int MAX_PLAYERS = 4;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor to PlayerSetup. Handles creation of all players.
	 */
	public PlayerSetup() {
		getNumPlayers();
		createPlayers();
		
		System.out.println("\n" + Players.getInstance().toString() );
	}

	/**
	 * getNumPlayers instance will get the number of players wanting to play.
	 */
	private void getNumPlayers() {
		System.out.println("How many people are playing? (must be between "+MIN_PLAYERS+" and "+MAX_PLAYERS+")");
		numPlayers = GetInput.getInstance().anInteger(MIN_PLAYERS, MAX_PLAYERS);
	}
	
	/**
	 * getRandomisedRoles instance will pick a random player for each role.
	 * There is always a pilot chosen.
	 * @return ArrayList<AdventurerEnum> roles for the players.
	 */
	private ArrayList<AdventurerEnum> getRandomisedRoles() {
		ArrayList<AdventurerEnum> allRoles = new ArrayList<AdventurerEnum>();
		ArrayList<AdventurerEnum> randomRoles = new ArrayList<AdventurerEnum>();
		
		allRoles.add(AdventurerEnum.DIVER);
		allRoles.add(AdventurerEnum.ENGINEER);
		allRoles.add(AdventurerEnum.EXPLORER);
		allRoles.add(AdventurerEnum.MESSENGER);
		allRoles.add(AdventurerEnum.NAVIGATOR);
		Collections.shuffle(allRoles);		   // Shuffle roles so not always picking same roles to be added
		
		randomRoles.add(AdventurerEnum.PILOT); // Always need a pilot
		for(int i=1; i<numPlayers; i++)
			randomRoles.add( allRoles.remove(0) );
		Collections.shuffle(randomRoles);	   // Shuffle again so that Pilot isn't always the first player 
		
		return randomRoles;
	}
	
	/**
	 * Create player for each person wanting to play.
	 */
	private void createPlayers() {
		//Randomize roles
		ArrayList<AdventurerEnum> randomisedRoles = getRandomisedRoles();
		for(int i=0; i<numPlayers; i++) 
			createOnePlayer(randomisedRoles.get(i), i+1);
	}
	
	/**
	 * createOnePlayer will create a single player, and add it to the list of players.
	 * @param AdventurerEnum role, the role of the player to be created.
	 * @param int i, player number.
	 */
	private void createOnePlayer(AdventurerEnum role, int i) {
		String playerName;
		playerName = getPlayerName(i);
		
		Players.getInstance().addPlayer(playerName, role);
	}
	
	/**
	 * getPlayerName gets the players name from the console.
	 * @param int i, player number.
	 */
	private String getPlayerName(int i) {
		System.out.println("\nEnter Player "+i+"'s Name:");
		return GetInput.getInstance().aString();
	}
	
	
}
