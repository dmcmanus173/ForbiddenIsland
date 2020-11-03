package setup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import enums.AdventurerEnum;
import player.Players;

/**
 * Class for PlayerSetup in Forbidden Island game.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    01/11/2020
 * @version 0.1
 */
public class PlayerSetup {

	//===========================================================
	// Variable Setup
	//===========================================================
	private int numPlayers;
	private Boolean validNumPlayers;
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int minPlayers = 2;
    private final int maxPlayers = 4;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor to PlayerSetup. Handles creation of all players.
	 */
	public PlayerSetup(Scanner user) {
		validNumPlayers = false;
		getNumPlayers(user);
		createPlayers(user);
		
		//TODO
		System.out.println();
		System.out.println( Players.getInstance().toString() );
	}

	/**
	 * getNumPlayers instance will get the number of players wanting to play.
	 * @param Scanner user will read user input from Console
	 */
	private void getNumPlayers(Scanner user) {
		String userString;
		while (!validNumPlayers) {
			System.out.println("How many people are playing? (must be between "+minPlayers+" and "+maxPlayers+")");
			userString = user.nextLine();
			numPlayers = setNumPlayers(userString);
		}
	}
	
	/**
	 * setNumPlayers will read String provided for an integer.
	 * @param String userString will return the number read from userString
	 */
	private int setNumPlayers(String userString) {
		int numOfPlayers = 0;
		try {
			numOfPlayers = Integer.parseInt(userString);
		} catch (NumberFormatException e) {
			return numOfPlayers;
		}

		if ((numOfPlayers >= minPlayers) && (numOfPlayers <= maxPlayers))
			validNumPlayers = true;
		else
			System.out.println("Incorrect input");
		return numOfPlayers;
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
		Collections.shuffle(allRoles);
		
		randomRoles.add(AdventurerEnum.PILOT); // Always need a pilot
		for(int i=1; i<numPlayers; i++)
			randomRoles.add( allRoles.remove(0) );
		Collections.shuffle(randomRoles);
		
		return randomRoles;
	}
	
	/**
	 * Create player for each person wanting to play.
	 * @param Scanner user will read user input from Console
	 */
	private void createPlayers(Scanner user) {
		//Randomize roles
		ArrayList<AdventurerEnum> randomisedRoles = getRandomisedRoles();
		for(int i=0; i<numPlayers; i++) 
			createOnePlayer(user, randomisedRoles.get(i), i+1);
	}
	
	/**
	 * createOnePlayer will create a single player, and add it to the list of players.
	 * @param Scanner user will read user input from Console
	 * @param AdventurerEnum role, the role of the player to be created.
	 * @param int i, player number.
	 */
	private void createOnePlayer(Scanner user, AdventurerEnum role, int i) {
		String playerName;
		playerName = getPlayerName(user, i);
		
		Players.getInstance().addPlayer(playerName, role);
		//System.out.println("Created Player " + i + ".\n");
		//System.out.println( Players.getInstance().getPlayer(i).toString() );
	}
	
	/**
	 * getPlayerName gets the players name from the console.
	 * @param Scanner user will read user input from Console
	 * @param int i, player number.
	 */
	//TODO don't accept nothing.
	private String getPlayerName(Scanner user, int i) {
		String name;
		System.out.println("\nEnter Player "+i+"'s Name:");
		name = user.nextLine();
		return name;
	}
	
	
}
