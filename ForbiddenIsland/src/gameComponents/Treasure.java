package gameComponents;

import enums.TreasureEnum;

public class Treasure {
	
	/**
	 * Class which will manage the different treasures in Forbidden Island
	 * @author Daniel McManus
	 * @version 0.1
	 *
	 */
	
	private TreasureEnum treasure;
	private boolean claimed;
	
	public Treasure(TreasureEnum treasure) {
		
		this.treasure = treasure;
		claimed = false;
	
	}
	
	public void claimTreasure() {
	
		claimed = true;
	
	}
	
	public boolean isClaimed() {
		
		return claimed;
		
	}
	
	public String toString() {
		
		StringBuilder temp = new StringBuilder("");
		temp.append("Treasure: " + treasure.toString());
		temp.append("\nClaimed: " + claimed);
		return temp.toString();
		
	}
	
	
	// Class-level test
	public static void main(String[] args) {
		
		Treasure treasure = new Treasure(TreasureEnum.THE_CRYSTAL_OF_FIRE);
		System.out.println(treasure.toString()); // "Name: The Crystal of Fire\nClaimed: false"
		treasure.claimTreasure(); 
		System.out.print(treasure.isClaimed());  // "true"
		
	}
	
}
