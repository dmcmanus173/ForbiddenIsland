package fi.players;

import java.util.ArrayList;

import fi.enums.AdventurerEnum;

/**
 * Class containing all Players in a game of Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    04/11/2020
 * @version 0.1
 */
public class Players {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static Players players;
	private ArrayList<Player> listOfPlayers;
	private int numPlayers;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of Players
     * @return Players. singleton Players object.
     */
    public static Players getInstance(){
        if(players == null)
            players = new Players();
        return players;
    }
    
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Players object.
	 * At this point, no players are added. numPlayers set to 0.
	 */
    private Players() {
    	numPlayers = 0;
    	listOfPlayers = new ArrayList<Player>();
    }
    
    /**
     * getNumPlayers method will return the number of players involved in the game.
     * @return Integer, the number of players.
     */
    public Integer getNumPlayers() {
    	return numPlayers;
    }
    
	/**
	 * addPlayers will create and add a new player to the listPlayers.
	 * @param String name, the name of the player.
	 * @param AdventurerEnum role, role of the player.
	 */
    public void addPlayer(String name, AdventurerEnum role) {
    	listOfPlayers.add(new Player(name, role));
    	numPlayers++;
    }
    
	
    
	/**
	 * removeAllPlayers will remove all players from the listPlayers
	 */
    public void removeAllPlayers() {
    	listOfPlayers.removeAll(listOfPlayers);
    	numPlayers = 0;
    }
    
    
    
	/**
	 * getPlayers will retrieve Player i.
	 * @param int i, i is the player number. Not the position of the player in listPlayer. i = pos+1.
	 */  
    public ArrayList<Player> getPlayers() {
    	return listOfPlayers;
    }
    
    
    public ArrayList<Player> getPlayersExcept(Player player) {
    	ArrayList<Player> otherPlayers = new ArrayList<Player>();
    	listOfPlayers.forEach((playerInList) -> {
    		if(playerInList != player) {
    			otherPlayers.add(playerInList);
    		}
    	});
    	return otherPlayers;
    }
    
    
    
	/**
	 * toString will return list of players, with respect to Player number.
	 */
    @Override
    public String toString() {
    	StringBuilder temp = new StringBuilder("");
    	
    	for(int i=0; i<numPlayers; i++)
    		temp.append("Player "+(i+1)+":\n"+listOfPlayers.get(i+1).toString()+"\n\n");
    
    	return temp.toString();    	
    }

}
