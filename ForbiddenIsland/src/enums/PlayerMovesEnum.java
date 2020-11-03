package enums;


/**
 * Class holding the types of movement directions a player can make.
 * @author Demi Oke & Daniel McManus
 * @date    31/10/2020
 * @version 0.1
 *
 */

public enum PlayerMovesEnum {
	
	NORTH        ("Up"),
	EAST         ("Right"),
	SOUTH        ("Down"),
	WEST         ("Left"),
	NORTH_EAST   ("Top right corner"),
	SOUTH_EAST   ("Bottom right corner"),
	SOUTH_WEST   ("Top left corner"),
	NORTH_WEST   ("Bottom left corner");
	
	
	
	private final String str;
	
	
	/**
     * constructor for Tiles taking in the string representation 
     * of the direction the player can move.
     * @param str String representation of direction
     */
	private PlayerMovesEnum(String str) {
		this.str = str;
		
    }
	
	
	
	/**
    * gets direction as string
    * @return the name.
    */
	@Override
	public String toString() {
		return this.str;
	}
	

}
