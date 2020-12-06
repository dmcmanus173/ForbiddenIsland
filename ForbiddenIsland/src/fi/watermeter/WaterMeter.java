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
	private final int MAX_LEVEL = 5;
	
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
	// Other Functions
	//===========================================================
	/**
	 * increaseWaterMeter method increase the WaterMeter level value,
	 * unless the level is already at MAX_LEVEL.
	 */
	public void increaseWaterMeter() {
		currentLevel += 1; 
		if(currentLevel == MAX_LEVEL) {
			// hanle game over.
			System.out.println("WaterMeter has been increased. Level set to "+currentLevel+".");
		}
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
	
	/**
	 * getLevel method which returns the level at which the WaterMeter is at.
	 * @return integer value of level in class WaterMeter
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	
	
	/**
	 * getLevel method which returns the level at which the WaterMeter is at.
	 * @return integer value of level in class WaterMeter
	 */
	public void setCurrentLevel(int startingLevel) {
		currentLevel = startingLevel;
	}
	
	
	
}
