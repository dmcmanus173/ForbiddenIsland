package fi.game;

import java.util.HashMap;
import java.util.Map;

import fi.enums.TreasureEnum;
import fi.treasures.TreasureManager;

public class GameOverObserver {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static GameOverObserver gameOverObserver;
	
	private boolean gameOver;
	private String gameOverReason;
	Map<TreasureEnum, Integer> numSunkenTilesForTreasure;
	
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MAX_NUM_OF_TREASURE_TILES_TO_SINK = 2;
	
	
	private final String GAME_OVER = "----- THE GAME IS OVER -----\"";
	
	private final String YOU_LOSE = "\nYou lose.";
	private final String SUNKEN_TREASURE_TILE = "\nThe treasure has sunken";
	private final String SUNKEN_FOOLS_LANDING_TILE = "\nFool's Landing has sunken. No way to escape.";
	private final String SUNKEN_PLAYER = "\nA player is sunken and there is no tile the player can movve to.";
	
	private final String YOU_WIN = "\nYou win.";
	private final String PLAYERS_DID_ESCAPE_WITH_TREASURE = "\nAll players escaped from Fool's Landing Tile with all the treasures.";
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of TreasureDeck
     * @return treasureDeck. singleton TreasureDeck object.
     */
    public static GameOverObserver getInstance(){
        if(gameOverObserver == null)
        	gameOverObserver = new GameOverObserver();
        return gameOverObserver;
    }
    
	//===========================================================
    // Constructor
    //===========================================================
    /*
     * Constructor for GetInput
     */
    private GameOverObserver() {
    	gameOver = false;
    	gameOverReason = GAME_OVER;
    	numSunkenTilesForTreasure = new HashMap<TreasureEnum, Integer>();
		for (TreasureEnum treasureType : TreasureEnum.values()) { 
			numSunkenTilesForTreasure.put(treasureType, 0);
  		}
    }
    
    public boolean isGameOver() {
    	return gameOver;
    }
    
    public void treasureTileDidSink(TreasureEnum treasureType) {
		increaseNumberOfTreasureTilesSunkenForTreasure(treasureType);
		int numSunkenTreasureTilesForTreasure = numSunkenTilesForTreasure.get(treasureType);
		if( (numSunkenTreasureTilesForTreasure == MAX_NUM_OF_TREASURE_TILES_TO_SINK) && (!TreasureManager.getInstance().didClaimTreasure(treasureType)) ) {
			setGameLose();
			gameOverReason += SUNKEN_TREASURE_TILE;
		}
	}
    
    public void FoolsLandingTileDidSink() {
    	setGameLose();
    	gameOverReason += SUNKEN_FOOLS_LANDING_TILE;
	}
    
    public void sunkenPlayerCanNotMove() {
    	setGameLose();
    	gameOverReason += SUNKEN_PLAYER;
    }
    
    public void playersWonGame() {
    	setGameWin();
	}
    
    private void setGameLose() {
    	if (!gameOver)
    		gameOverReason += YOU_LOSE;
    	gameOver = true;
    }
    
    private void setGameWin() {
    	gameOverReason += YOU_WIN;
    	gameOverReason += PLAYERS_DID_ESCAPE_WITH_TREASURE;
    	gameOver = true;
    }
    
    private void increaseNumberOfTreasureTilesSunkenForTreasure(TreasureEnum treasureType) {
		int currentNumberOfSunkenTreasureTilesForTreasure = numSunkenTilesForTreasure.get(treasureType);
		numSunkenTilesForTreasure.replace(treasureType, currentNumberOfSunkenTreasureTilesForTreasure + 1);
	}
    
    @Override
    public String toString() {
    	return gameOverReason;
    } 

}
