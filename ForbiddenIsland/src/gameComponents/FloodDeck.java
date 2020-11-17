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
 * @date    17/11/2020
 * @version 0.2
 */
public class FloodDeck {
	//===========================================================
	// Variable Setup
	//===========================================================
	private static FloodDeck floodDeck;
	private Queue<Tile> islands = new LinkedList<>();
	private ArrayList<Tile> usedCards = new ArrayList<Tile>();

	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int NUM_TILE_FLOOD_SETUP = 6;
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of floodDeck
     * @return floodDeck. singleton FloodDeck object.
     */
    public static FloodDeck getInstance(){
        if(floodDeck == null){
        	ArrayList<Tile> islands = Board.getInstance().getIslandTiles();
            floodDeck = new FloodDeck( islands );
        }
        return floodDeck;
    }
    
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for FloodDeck object.
	 */
	private FloodDeck(ArrayList<Tile> islands) {
		this.usedCards.addAll(islands);
		this.usedCards.addAll(islands);
		shuffleDeck();
		for(int i=0; i<NUM_TILE_FLOOD_SETUP; i++)
			pickCard();
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * drawFloodCards method draws a number of FloodCards equal to the WaterMeter
	 */
	public void drawFloodCards() {
		int numCards = WaterMeter.getInstance().getLevel();
		for(int i=0; i<numCards; i++)
			pickCard();
	}
	
	/**
	 * pickCard method will take the next card from the FloodDeck,
	 * then it will change the Island state related to the card
	 * from Not Flooded -> Flooded
	 * or Flooded -> Sunk
	 */
	private void pickCard() {
		Tile chosenCard;
		if( islands.isEmpty() )
			shuffleDeck();
		chosenCard = islands.remove();
		chosenCard.flood();
		usedCards.add(chosenCard);
	}
	
	/**
	 * shuffleDeck method will shuffle the contents of FloodDeck
	 */
	private void shuffleDeck() {
		while( !islands.isEmpty() )
			usedCards.add(islands.remove());
		Collections.shuffle(usedCards);
		while( !usedCards.isEmpty() )
			islands.add(usedCards.remove(0));
	}
}
