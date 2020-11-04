package enums;


/**
 * Class holding the types of movement directions a player can make.
 * @author Demi Oke & Daniel McManus
 * @date    04/11/2020
 * @version 0.1
 *
 */

public enum PlayerMovesEnum {
	
	NORTH        ("Press 'w' to move up to", 'w', true),
	EAST         ("Press 'd' to move right to", 'd', true),
	SOUTH        ("Press 's' to move down to", 's', true),
	WEST         ("Press 'a' to move left to", 'a', true),
	NORTH_EAST   ("Press 'e' to move diagonally to", 'e', false),
	SOUTH_EAST   ("Press 'c' to move diagonally to", 'c', false),
	SOUTH_WEST   ("Press 'z' to move diagonally to", 'z', false),
	NORTH_WEST   ("Press 'q' to move diagonally to", 'q', false);
	
	
	
	private final String str;
	private final char keyboardChar;
	private final boolean isAdjacent;
	
	
	/**
     * constructor for Tiles taking in the string representation 
     * of the direction the player can move.
     * @param str String representation of direction
     * @param keyboardChar char representing keyboard character
     * @param isAdjacent boolean representing if move is adjacent.
     */
	private PlayerMovesEnum(String str, char keyboardChar, boolean isAdjacent) {
		this.str = str;
		this.keyboardChar = keyboardChar;
		this.isAdjacent = isAdjacent;
		
    }
	
	
	
	/**
    * gets direction as string
    * @return the name.
    */
	@Override
	public String toString() {
		return this.str;
	}
	
	/**
    * gets keyboard character 
    * associated with the movement.
    * @return keyboardChar.
    */
	public char getKeyboardChar() {
		return this.keyboardChar;
	}
	
	/**
    * is the move an adjacent one i.e. up, down, left, right.
    * @return boolean describing if the move is adjacent.
    */
	public boolean isAdjacentMove() {
		return this.isAdjacent;
	}
	
	
	

}
