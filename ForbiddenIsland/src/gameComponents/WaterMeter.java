package gameComponents;

/**
 * Class for the WaterMeter in Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */
public class WaterMeter {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private int level;
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int MAX_LEVEL = 5;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for TreasureDeck object.
	 * Sets the Water level to 1.
	 */
	public WaterMeter(){
		level = 1;
	}
	
	/**
	 * Constructor for TreasureDeck object.
	 * @param int level, the level to set waterlevel to.
	 */
	public WaterMeter(int level) {
		
		if(level <= MAX_LEVEL)
			this.level = level;
		else {
			this.level = MAX_LEVEL;
			System.out.println("Level requested is greater than MAX_LEVEL(=" + MAX_LEVEL + "). Level set to MAX_LEVEL.");
		}
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * increaseWaterMeter method increase the WaterMeter level value,
	 * unless the level is already at MAX_LEVEL.
	 */
	public void increaseWaterMeter() {
		if(level < MAX_LEVEL)
			level += 1;
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
	 * @return int value of level in class WaterMeter
	 */
	public int getLevel() {
		return level;
	}

}
