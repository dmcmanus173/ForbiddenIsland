package player;

import java.util.ArrayList;

import enums.AdventurerEnum;
import treasureCards.TreasureDeck;

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
	private ArrayList<PlayerView> listPlayers = new ArrayList<PlayerView>();
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
    	listPlayers.add(new PlayerView(name, role));
    	numPlayers++;
    }
    
	/**
	 * addPlayers will add a new player to the listPlayers.
	 * @param Player player. Player to be added to listPlayers.
	 */
    public void addPlayer(PlayerView player) {
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
    public PlayerView getPlayer(int i) {
    	return listPlayers.get(i-1);
    }
    
	/**
	 * getPlayers will retrieve Player i.
	 * @param int i, i is the player number. Not the position of the player in listPlayer. i = pos+1.
	 */  
    public ArrayList<PlayerView> getPlayers() {
    	return listPlayers;
    }
    
    /**
     * useHelicopterLift method will use the first Helicopter Lift Card it can find through the list of players
     * and move the selected players that have been chosen to move.
     * @return Boolean true if the players have been moved. Otherwise false.
     */
    public Boolean useHelicopterLift() {
    	if( TreasureDeck.getInstance().checkIfPlayersHaveHelicopterLift() ) {
    		for(PlayerView player : getPlayers()) {
    			if(player.hasHelicopterLiftCard()) {
    				player.useHelicopterLift();
    				return true;
    			}
    		}
    	}
    	return false;    		
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
