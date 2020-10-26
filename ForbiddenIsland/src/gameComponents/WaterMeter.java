package gameComponents;

public class WaterMeter {

	/**
	 * Class which will manage the water meter for Forbidden Island
	 * @author Daniel McManus
	 * @version 0.1
	 *
	 */
	
	private int level;
	
	private int maxLevel = 5;
	
	public WaterMeter(){
		
		level = 1;
		
	}
	
	public WaterMeter(int level) {
		
		if(level <= maxLevel)
			this.level = level;
		else {
			this.level = maxLevel;
			System.out.println("Level requested is greater than maxLevel(=" + maxLevel + "). Level set to maxLevel.");
		}
	}
	
	public void IncreaseWaterMeter() {
		
		if(level < maxLevel) {
			level += 1;
		}
		else
			System.out.print("WaterMeter can't increase past maxLevel(=" + maxLevel + "). WaterMeter remaining at maxLevel.\n");
		
	}
	
	public String toString() {
		
		StringBuilder temp = new StringBuilder("");
		temp.append("Water Meter: " + level);
		return temp.toString();
		
	}
	
	public int toInt() {
		
		return level;
		
	}

	/*
	// Class-level test
	public static void main(String[] args) {
		
		WaterMeter wm1 = new WaterMeter();
		System.out.println( wm1.toString() ); // "Water Meter: 1"
		wm1.IncreaseWaterMeter();
		System.out.println( wm1.toString() ); // "Water Meter: 2"
		
		WaterMeter wm2 = new WaterMeter(4);
		System.out.println( wm2.toString() ); // "Water Meter: 4"
		wm2.IncreaseWaterMeter();
		System.out.println( wm2.toString() ); // "Water Meter: 5"
		wm2.IncreaseWaterMeter();			  // "WaterMeter can't increase past maxLevel(=5). WaterMeter remaining at maxLevel."
		System.out.println( wm2.toString() ); // "Water Meter: 5"		
		
		WaterMeter wm3 = new WaterMeter(5);
		System.out.println( wm3.toString() ); // "Water Meter: 5"
		
		WaterMeter wm4 = new WaterMeter(6);   // "Level requested is greater than maxLevel(=5). Level set to maxLevel."
		System.out.println( wm4.toString() ); // "Water Meter: 5"	
	
	}
	*/
}
