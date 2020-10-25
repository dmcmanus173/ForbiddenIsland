package gameComponents;

public class Treasure {

	private String  name;
	private boolean claimed;
	
	public Treasure(String name) {
		
		this.name = name;
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
		temp.append("Name: " + name);
		temp.append("\nClaimed: " + claimed);
		return temp.toString();
		
	}
	
	/*
	// Class-level test
	public static void main(String[] args) {
		
		Treasure treasure = new Treasure("Gold Medal");
		System.out.println(treasure.toString()); // "Name: Gold Medal\nClaimed: false"
		treasure.claimTreasure(); 
		System.out.print(treasure.isClaimed());  // "true"
		
	}
	*/
}
