package gameManager;

import board.Board;
import gameComponents.FloodDeck;
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
    	if( gameWon )
    		System.out.println("Congratulations! You have survived Forbidden Island!");
    	else
    		System.out.println("Game over! You lose!");
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
    	System.out.println("It is "+player.getName()+"'s go. "+player.getName()+" is on "+player.getLocation().getTileName()+".");
    	
    	while( !gameOver && (remainingActions > 0) ) {
    		System.out.println("You have "+remainingActions+" actions remaining. Would you like to: ");
    		
    		System.out.println("1. Move.                 (Costs 1 Action)");
    		System.out.println("2. Shore Up.             (Costs 1 Action)");
    		System.out.println("3. Give a Treasure Card. (Costs 1 Action)");
    		System.out.println("4. Capture A Treasure.   (Costs 1 Action)");
    		System.out.println("5. See what Treasure Cards you have.");
    		System.out.println("6. Remove a Treasure Card.");
    		System.out.println("7. See what treasure is inside the Rucksack.");
    		System.out.println("8. Print the map.");
    		System.out.println("9. Print player location.");
    		System.out.println("0. End Go.");
    		System.out.println("Choose a number for what you would like to do.");
    		
    		option = GetInput.getInstance().anInteger(0, 9);
    		
    		if(option == 1) {
    			if( player.move() ) remainingActions -= 1;
    		}
    		else if(option == 2) {
    			if( player.shoreUp() ) remainingActions -= 1;
    		}
    		else if(option == 3) {
    			if( player.giveTreasureCard() ) remainingActions -= 1;
    		}
    		else if(option == 4) { 
    			if( player.claimTreasure() ) remainingActions -= 1;
    		}
    		else if(option == 5) { 
    			player.printCardsHeld();
    		}
    		else if(option == 6) {
    			player.removeTreasureCard();
    		}
    		else if(option == 7) {
    			Rucksack.getInstance().printContents();
    		}
    		else if(option == 8) {
    			Board.getInstance().printBoard();
    		}
    		else if(option == 9) {
    			System.out.println( player.getName()+" is on "+player.getLocation().getTileName()+"." );
    		}
    		else if(option == 0) {
    			remainingActions = 0;
    		}
    	}
    	
    	System.out.println(player.getName()+" is drawing two cards from the TreasureDeck:");
    	player.getTreasureCard();
    	player.getTreasureCard();
    	
    	System.out.println("\n"+player.getName()+" is drawing flood cards.");
    	FloodDeck.getInstance().drawFloodCards();
    	
    	System.out.println("\n"+player.getName()+"'s go is over.\n");
    }
    
    @Override
    public String toString() {
    	StringBuilder temp = new StringBuilder("");
    	
    	temp.append("It is Player"+playersGo+"'s go.");
    	
    	return temp.toString();    	
    }

}
