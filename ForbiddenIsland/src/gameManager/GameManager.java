package gameManager;

import board.Board;
import getInput.GetInput;
import player.Player;
import player.Players;
import player.Rucksack;

public class GameManager {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static GameManager gameManager;
	private int numPlayers;
	private int playersGo;
	private Boolean gameOver;
	private Boolean gameWon;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    public static GameManager getInstance(){
        if(gameManager == null)
            gameManager = new GameManager();
        return gameManager;
    }
    
	//===========================================================
	// Constructor
	//===========================================================
    private GameManager() {
    	gameOver = false;
    	gameWon  = false;
    	playersGo = 1; // Player 1 to take first go.
    	numPlayers = Players.getInstance().getNumPlayers();
    }
    
    private void nextPlayersGo() {
    	if(playersGo == numPlayers)
    		playersGo = 1;
    	else
    		playersGo += 1;
    }
    
    public void playersGo() {
    	while( !gameOver && !gameWon ) {
        	aPlayersTurn();
        	nextPlayersGo();
    	}
    }
    
    public void gameWon() {
    	gameWon = true;
    }
    
    public void gameOver() {
    	gameOver = true;
    }
    
    private void aPlayersTurn () {
    	Player player = Players.getInstance().getPlayer(playersGo);
    	int remainingActions = 3;
    	int option;
    	System.out.println("It is "+player.getName()+"'s go.");
    	
    	while( !gameOver && (remainingActions > 0) ) {
    		System.out.println("You have "+remainingActions+" actions remaining. Would you like to: ");
    		System.out.println("1. Print the map.");
    		System.out.println("2. See what treasure is inside the Rucksack.");
    		System.out.println("3. End Go.");
    		System.out.println("Choose a number for what you would like to do.");
    		
    		option = GetInput.getInstance().anInteger(1, 3);
    		if(option == 1) 
    			Board.getInstance().printBoard();
    		else if(option == 2)
    			Rucksack.getInstance().printContents();
    		else if(option == 3) {
    			remainingActions = 0;
    			
    		}
    	}
    	System.out.println(player.getName()+"'s go is over.\n");
    	
    }
    
    @Override
    public String toString() {
    	StringBuilder temp = new StringBuilder("");
    	
    	temp.append("It is Player"+playersGo+"'s go.");
    	
    	return temp.toString();    	
    }

}
