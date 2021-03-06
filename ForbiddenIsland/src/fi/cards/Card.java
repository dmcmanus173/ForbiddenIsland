package fi.cards;

/**
 * Abstract Class for a Card in the Forbidden Island Game.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */

// Remove Enum rawtype warnings.
@SuppressWarnings("rawtypes")
public abstract class Card {

	//===========================================================
	// Variable Setup
	//===========================================================
    protected Enum name;   // Name of object referred to by card
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Abstract constructor for Card object.
	 * @param name Enum name for card.
	 */
	Card(Enum name){
		this.name   = name;
	}

	//===========================================================
	// Getters
	//===========================================================
	// getter for card's name
    public Enum getName() {
		return this.name;
	}

	/**
	 * toString method to return details of the class.
	 * @return String type object containing cardType as String.
	 */
	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder("");
		temp.append(name.toString());
		return temp.toString();
	}
	
}
