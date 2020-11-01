package setup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import enums.AdventurerEnum;
import gameComponents.Player;

public class PlayerSetup {
	
	private final int minPlayers = 2;
    private final int maxPlayers = 4;
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private int numPlayers;
	private Boolean validNumPlayers;
	
	public PlayerSetup(Scanner user) {
		validNumPlayers = false;
		getNumPlayers(user);
		createPlayers(user);
	}

	
	/**
	 * Gets the number of Players who will play the game
	 * @param user The scanner the user which we read the users input from
	 * @return The number of Players to create
	 */
	private void getNumPlayers(Scanner user) {
		String userString;
		while (!validNumPlayers) {
			System.out.println("How many people are playing? (must be between "+minPlayers+" and "+maxPlayers+")");
			userString = user.nextLine();
			numPlayers = setNumPlayers(userString);
		}
	}
	
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
	
	private void createPlayers(Scanner user) {
		//Randomize roles
		ArrayList<AdventurerEnum> randomisedRoles = getRandomisedRoles();
		for(int i=0; i<numPlayers; i++) 
			createOnePlayer(user, randomisedRoles.get(i), i+1);
	}
	
	private void createOnePlayer(Scanner user, AdventurerEnum role, int i) {
		String playerName;
		playerName = getPlayerName(user, i);
		
		Player aPlayer = new Player(playerName, role);
		players.add(aPlayer);
		System.out.println("Created Player " + i + "\n");
		System.out.println( aPlayer.toString() );
	}
	
	private String getPlayerName(Scanner user, int i) {
		String name;
		System.out.println("\nPlayer "+i+" Name:");
		name = user.nextLine();
		return name;
	}
	
	
}
