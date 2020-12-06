package fi.cards;

import java.util.ArrayList;
import java.util.Stack;


import fi.enums.TileEnum;
import fi.watermeter.WaterMeter;

/**
 * Class for a FloodDeck in the Forbidden Island Game.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */


public class FloodDeck {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static FloodDeck floodDeck;
	private DeckSorter floodDeckSorter;
	
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	
	private final int CARDS_PER_ISLAND_TILE = 2;
	private final int CARDS_FOR_START_OF_GAME = 6;
	
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of TreasureDeck
     * @return treasureDeck. singleton TreasureDeck object.
     */
    public static FloodDeck getInstance(){
        if(floodDeck == null){
        	floodDeck = new FloodDeck();
        }
        return floodDeck;
    }
    
    
    //===========================================================
  	// Constructor
  	//===========================================================
  	/**
  	 * Constructor for FloodDeck object.
  	 */
  	public FloodDeck() {		
  		Stack<Card> cards = new Stack<Card>();
  		for (TileEnum tileName : TileEnum.values()) { 
  			for (int i = 0; i<CARDS_PER_ISLAND_TILE; i++) {
  				cards.add(  new FloodCard(tileName) );
  			} 
  		}
  		floodDeckSorter = new DeckSorter("Flood Deck Sorter", cards);
  	}
  	
 
  	/**
     * getTilesToFlood method returns the names of the tiles
     * on each of the flood cards that are drawn. The cards 
     * themselves are returned.
     * @param boolean determining if the tiles are for the start of game or during the game
     * @return ArrayList<TileEnum> containing the names of the tiles to flood.
     */
  	public ArrayList<TileEnum> getTilesToFlood(boolean forStartOfGame) {
  		int numOfCardsToDrawDuringGame = WaterMeter.getInstance().getCurrentLevel();
  		ArrayList<TileEnum> tilesToFlood = new ArrayList<TileEnum>();
  		int numCardsToDraw = forStartOfGame ? CARDS_FOR_START_OF_GAME : numOfCardsToDrawDuringGame;
  		Card drawnCard;
  		for (int i=0; i<numCardsToDraw; i++) {
			drawnCard = floodDeckSorter.drawCard();
			tilesToFlood.add((TileEnum)drawnCard.getName());
			floodDeckSorter.discardCard(drawnCard);
		} 
  		return tilesToFlood;
  	}
  	
  	
  	
  	// class level test
  	public static void main(String[] args) {
		
  		FloodDeck floodDeck = FloodDeck.getInstance();
  		ArrayList<TileEnum> tilesToFlood;
  		
  		tilesToFlood = floodDeck.getTilesToFlood(true);
  		System.out.println("Tiles for beginning of game:");
  		System.out.println(tilesToFlood);
  		
  		for (int i=0; i<20; i++) {
  			System.out.println("Flood Round: " + i);
  			tilesToFlood = floodDeck.getTilesToFlood(false);
  			System.out.println(tilesToFlood);
		} 
  		
		
	}

}
