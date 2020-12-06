package fi.treasures;

import java.util.ArrayList;

import fi.board.Board;
import fi.board.TreasureTile;
import fi.cards.TreasureCard;
import fi.cards.Hand;
import fi.enums.TileEnum;
import fi.enums.TreasureEnum;


/**
 * Class for a TreasureManager to manage treasure related events
 * in the Forbidden Island Game.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */

public class TreasureManager {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static TreasureManager treasuereManager;
	private ArrayList<TreasureEnum> collectedTreasures;
	
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int NUM_TREASURES = TreasureEnum.values().length;
	private final int NUM_TREASURE_CARDS_TO_COLLECT_TREASURE = 5;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of TreasureManager
     * @return TreasureManager. singleton TreasureManager object.
     */
	public static TreasureManager getInstance() {
		if(treasuereManager == null)
			treasuereManager = new TreasureManager();
		return treasuereManager;
	}
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for TreasureManager object.
	 */
	private TreasureManager() {
		collectedTreasures = new ArrayList<TreasureEnum>();
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * isTreasureClaimed method returns whether a treasure of the given type has been claimed.
	 * @return Boolean isClaimed, whether treasure has been claimed before.
	 */
	public Boolean didClaimTreasure(TreasureEnum treasureType) {
		return collectedTreasures.contains(treasureType);
	}
	
	/**
	 * claimTreasure method to claim a treasure of a given type in exchange
	 * for the specified number of treasure cards.
	 * @param Hand detailing the cards in the players hand
	 * @param TreasureEnum detailing the type of treasure that was collected
	 */
	public void claimTreasure(Hand playerHand, TreasureEnum treasureType) {
		if(collectedTreasures.contains(treasureType)) {
			throw new RuntimeException("Attempting to claim a treasure that is already claimed.");
		} else {
			collectedTreasures.add(treasureType);
			discardTreasueCardsFromPlayersHand(playerHand, treasureType);
			
			if(didClaimAllTreasures()) {
				// TODO: Notify Gamemanager
			}
		}
	}
	
	
	/**
	 * private method to discard the treasure cards used to collect a 
	 * treasure, from the players hand. This function is only called 
	 * once the treasure has been collected.
	 * @param Hand detailing the cards in the players hand
	 * @param TreasureEnum detailing the type of treasure that was collected
	 */
	private void discardTreasueCardsFromPlayersHand(Hand playerHand, TreasureEnum treasureType) {
		ArrayList<TreasureCard> treasureCardsToDiscard = playerHand.getCardsForTreasure(treasureType);
		for(int i=0; i < NUM_TREASURE_CARDS_TO_COLLECT_TREASURE; i++) {
			playerHand.discardCard(treasureCardsToDiscard.get(i));
		}
	}
	
	/**
	 * didClaimAllTreasures method returns true if all treasures have been claimed.
	 * @return Boolean signifying if all the treasures have been claimed.
	 */
	public Boolean didClaimAllTreasures() {
		return collectedTreasures.size() == NUM_TREASURES;
	}
	
	
	/**
	 * canCollectTreasure method checks the cards in the players hand 
	 * and location of the player to see if they meet the criteria to 
	 * collect a treasure.
	 * @param Hand detailing the cards in the players hand
	 * @param TileEnum detailing the current location of the player
	 * @return Boolean signifying if criteria is met.
	 */
	public boolean canCollectTreasure(Hand playerHand, TileEnum playerLocation) {
		if(playerLocation.hasTreasure()) {
			 TreasureTile treasureTile = (TreasureTile) Board.getInstance().getTileWithName(playerLocation);
			 TreasureEnum treasureType = treasureTile.getTreasureType();
			 if(!collectedTreasures.contains(treasureType)) {
				 int numOfTreasureCardsOfGivenTreasure = playerHand.getCardsForTreasure(treasureType).size();
				 return numOfTreasureCardsOfGivenTreasure >= NUM_TREASURE_CARDS_TO_COLLECT_TREASURE;
			 }
		 } 
		
		return false;
	}
	
//	public int getNumOfCardsToCollectTreasure() {
//		return NUM_TREASURE_CARDS_TO_COLLECT_TREASURE;
//	}
	
	
	

	
	/**
	 * printContents method prints what treasures have been claimed!
	 */
	public void printContents() {
		if(collectedTreasures.size() == 0)
			System.out.println("No treasures have been collected.");
		else {
			System.out.println("There following treasures have been collected:");
			collectedTreasures.forEach((treasureType) -> {
				System.out.println( treasureType );
			});
		}
		System.out.println("There are "+(NUM_TREASURES-collectedTreasures.size())+" treasures left on the island.\n");
	}
	 
}