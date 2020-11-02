package player;

import java.util.ArrayList;

import enums.AdventurerEnum;
import gameComponents.TreasureDeck;

public class Players {
	private ArrayList<Player> listPlayers = new ArrayList<Player>();
	private int numPlayers;
	
	private static Players players;
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of TreasureDeck
     * @return treasureDeck. singleton TreasureDeck object.
     */
    public static Players getInstance(){
        if(players == null)
            players = new Players();
        return players;
    }
    
    private Players() {
    	numPlayers = 0;
    }
    
    public void addPlayer(String name, AdventurerEnum role) {
    	listPlayers.add(new Player(name, role));
    	numPlayers++;
    }
    
    public void removeAllPlayers() {
    	listPlayers.removeAll(listPlayers);
    }
    
    public Player getPlayer(int i) {
    	return listPlayers.get(i-1);
    }
    
    @Override
    public String toString() {
    	StringBuilder temp = new StringBuilder("");
    	
    	for(int i=0; i<numPlayers; i++)
    		temp.append("Player "+(i+1)+":\n"+getPlayer(i+1).toString());
    	
    	return temp.toString();    	
    }

}
