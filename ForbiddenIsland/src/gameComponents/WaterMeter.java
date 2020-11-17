package gameComponents;

import getInput.GetInput;

/**
 * Class for the WaterMeter in Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    04/11/2020
 * @version 0.1
 */
public class WaterMeter {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static WaterMeter waterMeter;
	private int level;
	
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
		System.out.println("Choose a number to set the WaterMeter Level ("+MIN_LEVEL+"-"+MAX_LEVEL+") to:");
		level = GetInput.getInstance().anInteger(MIN_LEVEL, MAX_LEVEL);
		System.out.println("WaterMeter has been set. Level set to "+level+".\n");
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * increaseWaterMeter method increase the WaterMeter level value,
	 * unless the level is already at MAX_LEVEL.
	 */
	public void increaseWaterMeter() {
		if(level < MAX_LEVEL) {
			level += 1;
			System.out.println("WaterMeter has been increased. Level set to "+level+".");
		}
		else
			System.out.print("WaterMeter can't increase past MAX_LEVEL(=" + MAX_LEVEL + "). WaterMeter remaining at MAX_LEVEL.\n");
	}
	

	/**
	 * toString returns the level at which the WaterMeter is at.
	 * @return String containing variable information for the class.
	 */
	public String toString() {
		
		StringBuilder temp = new StringBuilder("");
		temp.append("Water Meter: " + level);
		return temp.toString();
		
	}
	
	/**
	 * getLevel method which returns the level at which the WaterMeter is at.
	 * @return integer value of level in class WaterMeter
	 */
	public int getLevel() {
		return level;
	}

}
