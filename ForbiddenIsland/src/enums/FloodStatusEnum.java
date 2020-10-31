package enums;

/**
 * Class holding the tile flood status types in the Forbidden Island Game.
 * @author Demi Oke & Daniel McManus
 * @date    31/10/2020
 * @version 0.1
 *
 */

public enum FloodStatusEnum {
	
	NOT_FLOODED("Not flodded"),
	FLOODED("Flooded"),
	SUNKEN("Sunken");
	
	private String str;
	
	/**
     * constructor for Flood Status taking in the string constant 
     * associated with the status.
     * @param str String which will be the string version of the enum.
     */
	private FloodStatusEnum(String str) {
		this.str = str; 
    }
	
	/**
     * gets type relating to type of adventurer
     * @return the name.
     */
	@Override
	public String toString() {
		return this.str;
	}
	
	
	/*
	// Class-level test
	public static void main(String[] args) {
		
		for (FloodStatusEnum floodStatus : FloodStatusEnum.values()) { 
		    System.out.println(floodStatus); 
		}
		
	}
	*/

}
