package fi.watermeter;

/**
 * Class for the WaterMeter in Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    17/11/2020
 * @version 0.1
 */

public class WaterMeter {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static WaterMeter waterMeter;
	private int currentLevel;
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MIN_LEVEL = 1;
	private final int MAX_LEVEL = 4;
	private final int GAME_OVER_LEVEL = MAX_LEVEL+1;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of Players
     * @return Players. singleton Players object.
     */
    public static WaterMeter getInstance(){
        if(waterMeter == null)
            waterMeter = new WaterMeter();
        return waterMeter;
    }
    
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for TreasureDeck object.
	 * Sets the Water level to 1.
	 */
	private WaterMeter(){
		currentLevel = MIN_LEVEL;
	}
	
	//===========================================================
	// Main Functions
	//===========================================================
	/**
	 * increaseWaterMeter method increase the WaterMeter level value,
	 * unless the level is already at MAX_LEVEL.
	 */
	public void increaseWaterMeter() {
		currentLevel += 1; 
	}
	
	/**
	 * setCurrentLevel method allows setting the waterMeter to be between the 
	 * MIN_LEVEL and MAX_LEVEL.
	 */
	public void setCurrentLevel(int startingLevel) {
		if( (MIN_LEVEL <= startingLevel) && (startingLevel <= MAX_LEVEL) )
			currentLevel = startingLevel;
	}

	/**
	 * toString returns the level at which the WaterMeter is at.
	 * @return String containing variable information for the class.
	 */
	public String toString() {
		
		StringBuilder temp = new StringBuilder("");
		temp.append("Water Meter: " + currentLevel);
		return temp.toString();
		
	}
	
	//===========================================================
	// Getter Functions
	//===========================================================
	
	/**
	 * getLevel method which returns the level at which the WaterMeter is at.
	 * @return integer value of level in class WaterMeter
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	
	/**
	 * getMinLevel method returns minimum level allowed to set waterMeter to.
	 * @return
	 */
	public int getMinLevel() {
		return MIN_LEVEL;
	}
	
	
	/**
	 * getMaxLevel method returns maximum level allowed to set waterMeter to.
	 * @return
	 */
	public int getMaxLevel() {
		return MAX_LEVEL;
	}
	
	/**
	 * getGameOverLevel method returns level which results in gameOver.
	 * @return
	 */
	public int getGameOverLevel() {
		return GAME_OVER_LEVEL;
	}	
}
