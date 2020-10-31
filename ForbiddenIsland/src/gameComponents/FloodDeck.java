package gameComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import board.Board;
import board.Tile;

/**
 * Class for FloodDeck in Forbidden Island game.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    27/10/2020
 * @version 0.1
 */
public class FloodDeck {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static FloodDeck floodDeck;
	private Queue<Tile> islands = new LinkedList<>();
	private ArrayList<Tile> usedCards = new ArrayList<Tile>();

	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of floodDeck
     * @return floodDeck. singleton FloodDeck object.
     */
    public static FloodDeck getInstance(){
        if(floodDeck == null){
            floodDeck = new FloodDeck( Board.getInstance().getIslandTiles() );
        }
        return floodDeck;
    }
    
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for FloodDeck object.
	 */
	public FloodDeck(ArrayList<Tile> islands) {
		this.usedCards = islands;
		shuffleDeck();
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * shuffleDeck method will shuffle the contents of FloodDeck
	 */
	public void shuffleDeck() {
		while( !islands.isEmpty() )
			usedCards.add(islands.remove());
		Collections.shuffle(usedCards);
		while( !usedCards.isEmpty() )
			islands.add(usedCards.remove(0));
	}
	
	/**
	 * pickCard method will take the next card from the FloodDeck,
	 * then it will change the Island state related to the card
	 * from Not Flooded -> Flooded
	 * or Flooded -> Sunk
	 */
	public void pickCard() {
		Tile chosenCard;
		if( islands.isEmpty() )
			shuffleDeck();
		chosenCard = islands.remove();
		chosenCard.flood();
		usedCards.add(chosenCard);
	}
	
}
