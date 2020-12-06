package fi.enums;

/**
 * Class holding the tile flood status types in the Forbidden Island Game.
 * @author Demi Oke & Daniel McManus
 * @date    31/10/2020
 * @version 0.1
 *
 */

public enum FloodStatusEnum {
	
	NOT_FLOODED ("Not flodded", 2),
	FLOODED     ("Flooded",     1),
	SUNKEN      ("Sunken",      0);
	
	private String str;
	private int statusLevel;
	
	/**
     * constructor for Flood Status taking in the string constant 
     * associated with the status.
     * @param str String which will be the string version of the enum.
     */
	private FloodStatusEnum(String str, int statusLevel) {
		this.str = str; 
		this.statusLevel = statusLevel;
    }
	
	/**
     * gets string representation of the 
     * floodStatus
     * @return the name.
     */
	@Override
	public String toString() {
		return this.str;
	}
	
	/**
     * gets type integer representation of the 
     * corresponding floodStatus
     * @return floodStatus as int.
     */
	public int toInt() {
		return this.statusLevel;
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