package player;

import java.util.ArrayList;

import enums.AdventurerEnum;

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
	private ArrayList<Player> listPlayers = new ArrayList<Player>();
	private int numPlayers;
	private int playersGo; //TODO store player whos go it is, could make this type player. Could have function that gets nextPlayerToGo
	
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
    }
    
	/**
	 * addPlayers will create and add a new player to the listPlayers.
	 * @param String name, the name of the player.
	 * @param AdventurerEnum role, role of the player.
	 */
    public void addPlayer(String name, AdventurerEnum role) {
    	listPlayers.add(new Player(name, role));
    	numPlayers++;
    }
	/**
	 * addPlayers will add a new player to the listPlayers.
	 * @param Player player. Player to be added to listPlayers.
	 */
    public void addPlayer(Player player) {
    	listPlayers.add(player);
    	numPlayers++;
    }
    
	/**
	 * removeAllPlayers will remove all players from the listPlayers
	 */
    public void removeAllPlayers() {
    	listPlayers.removeAll(listPlayers);
    	numPlayers = 0;
    }
    
	/**
	 * getPlayers will retrieve Player i.
	 * @param int i, i is the player number. Not the position of the player in listPlayer. i = pos+1.
	 */  
    public Player getPlayer(int i) {
    	return listPlayers.get(i-1);
    }
    
	/**
	 * getPlayers will retrieve Player i.
	 * @param int i, i is the player number. Not the position of the player in listPlayer. i = pos+1.
	 */  
    public ArrayList<Player> getPlayers() {
    	return listPlayers;
    }
    
    //TODO Function to call all players so that they take their turns
    public void PlayerTurns() {
    	
    }
    
    //TODO A particular players turn
    public void aPlayersTurn (Player aPlayer) {
    	
    }
    
	/**
	 * toString will return list of players, with respect to Player number.
	 */
    @Override
    public String toString() {
    	StringBuilder temp = new StringBuilder("");
    	
    	for(int i=0; i<numPlayers; i++)
    		temp.append("Player "+(i+1)+":\n"+getPlayer(i+1).toString()+"\n\n");
    	
    	return temp.toString();    	
    }

}
