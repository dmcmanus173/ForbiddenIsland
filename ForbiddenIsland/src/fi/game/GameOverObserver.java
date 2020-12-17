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
	private Map<TreasureEnum, Integer> numSunkenTilesForTreasure;
	
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MAX_NUM_OF_TREASURE_TILES_TO_SINK = 2;
	
	
	private final String GAME_OVER = "----- THE GAME IS OVER -----\n";
	
	private final String YOU_LOSE = "You lose.\n";
	private final String SUNKEN_TREASURE_TILE = "The treasure has sunken.\n";
	private final String SUNKEN_FOOLS_LANDING_TILE = "Fool's Landing has sunken. No way to escape.\n";
	private final String SUNKEN_PLAYER = "A player is sunken and there is no tile the player can movve to.\n";
	private final String WATER_METER_MAX = "The water meter has been increased to its maximum level.\n";
	
	private final String YOU_WIN = "You win.\n"; 
	private final String PLAYERS_DID_ESCAPE_WITH_TREASURE = "All players escaped from Fool's Landing Tile with all the treasures.\n";
	
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
    
    public void waterMeterIsFull() {
    	setGameLose();
    	gameOverReason += WATER_METER_MAX;
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
    
	//===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    
    public int getNumSunkTreasures(TreasureEnum treasureEnum) {
    	return numSunkenTilesForTreasure.get(treasureEnum);
    }
    
    public void destroy() {
    	gameOverObserver = null;
    }

}
