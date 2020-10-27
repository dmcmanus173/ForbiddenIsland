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
	private int maxLevel = 5;
	
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
		
		if(level <= maxLevel)
			this.level = level;
		else {
			this.level = maxLevel;
			System.out.println("Level requested is greater than maxLevel(=" + maxLevel + "). Level set to maxLevel.");
		}
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * increaseWaterMeter method increase the WaterMeter level value,
	 * unless the level is already at maxLevel.
	 */
	public void increaseWaterMeter() {
		if(level < maxLevel)
			level += 1;
		else
			System.out.print("WaterMeter can't increase past maxLevel(=" + maxLevel + "). WaterMeter remaining at maxLevel.\n");
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
