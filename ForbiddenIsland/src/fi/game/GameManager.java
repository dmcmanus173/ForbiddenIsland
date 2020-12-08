package fi.game;

import java.util.ArrayList;

import fi.enums.TileEnum;
import fi.board.Board;
import fi.cards.FloodDeck;
import fi.players.Player;
import fi.players.Players;

/**
 * Class for GameManager of Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    07/12/2020
 * @version 0.1
 */
public class GameManager {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static GameManager gameManager;
	private ArrayList<PlayerGo> players = new ArrayList<PlayerGo>();
	private int playersGoPos;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of GameManager.
     * @return GameManager. singleton GameManager object.
     */
    public static GameManager getInstance(){
        if(gameManager == null)
            gameManager = new GameManager();
        return gameManager;
    }
    
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for GameManager object.
	 */
    private GameManager() {
    	playersGoPos = 0; // Player 1 to take first go.
    	gameSetup();
    }
    
    private void gameSetup() {
    	Board board = Board.getInstance();
    	FloodDeck floodDeck = FloodDeck.getInstance();
    	
    	// Setting up players
    	ArrayList<Player> players = Players.getInstance().getPlayers();
    	for(Player aPlayer : players) {
    		PlayerGo newPlayerGo = new PlayerGo(aPlayer);
    		newPlayerGo.drawCardsFromTreasureDeckToStart();
    		this.players.add(newPlayerGo);
    
    		board.setUpPlayerOnBoard(aPlayer);
    	}
    	
    	// Setting up board
    	ArrayList<TileEnum> tilesToFlood = floodDeck.getTilesToFlood(true);
    	tilesToFlood.forEach(tileEnum -> board.floodTile(tileEnum));
    	
    }
    
    /**
     * nextPlayersGo method gets the Player number for the next Player to take a turn.
     */
    private void nextPlayersGoPos() {
    	if(playersGoPos == players.size()-1)
    		playersGoPos = 1;
    	else
    		playersGoPos += 1;
    }
    
    /**
     * playersGo will make players keep taking their turns.
     * A loop which will run until there are no more goes for the group of players to take!
     */
    public void playGame() {
    	PlayerGo playerToGo = players.get(playersGoPos); 
    	while( !playerToGo.doRound() ) {
    		nextPlayersGoPos();
    		playerToGo = players.get(playersGoPos);
    	}
    	System.out.println("Game over!"); //TODO is it okay to print here? Currently this, PlayerGo, setup have print/input
    }
    
}