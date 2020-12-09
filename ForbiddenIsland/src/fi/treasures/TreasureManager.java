package fi.treasures;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
	private boolean treasuresAreAvailableToCollect;
	Map<TreasureEnum, Integer> numSunkenTilesForTreasure;
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int NUM_TREASURES = TreasureEnum.values().length;
	private final int NUM_TREASURE_CARDS_TO_COLLECT_TREASURE = 4;
	private final int MAX_NUM_OF_TREASURE_TILES_TO_SINK = 2;
	
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
		treasuresAreAvailableToCollect = true;
		numSunkenTilesForTreasure = new HashMap<TreasureEnum, Integer>();
		
		for (TreasureEnum treasureType : TreasureEnum.values()) { 
			numSunkenTilesForTreasure.put(treasureType, 0);
  		}
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
		if(collectedTreasures.contains(treasureType)) 
			throw new RuntimeException("Attempting to claim a treasure that is already claimed.");
		
		else {
			collectedTreasures.add(treasureType);
			discardTreasueCardsFromPlayersHand(playerHand, treasureType);
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
			playerHand.discardCard(treasureCardsToDiscard.get(0));
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
	 * getNumOfRemainingTreasuresToCollect method returns the remaining number of treasures
	 * left to collect.
	 * @return int number of treasures left to collect.
	 */
	public int getNumOfRemainingTreasuresToCollect() {
		return NUM_TREASURES - collectedTreasures.size();
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
	
	/**
	 * treasuresAreAvailableToCollect returns true if there is still treasures left to claim.
	 * @return Boolean true if treasures are still to be collected
	 */
	public boolean treasuresAreAvailableToCollect() {
		return treasuresAreAvailableToCollect;
	}
	
	
	public void treasureTileDidSink(TreasureEnum treasureType) {
		increaseNumberOfTreasureTilesSunkenForTreasure(treasureType);
		int numSunkenTreasureTilesForTreasure = numSunkenTilesForTreasure.get(treasureType);
		if( (numSunkenTreasureTilesForTreasure == MAX_NUM_OF_TREASURE_TILES_TO_SINK) && (!didClaimTreasure(treasureType)) ) {
			treasuresAreAvailableToCollect = false;
		}
	}
	
	private void increaseNumberOfTreasureTilesSunkenForTreasure(TreasureEnum treasureType) {
		int currentNumberOfSunkenTreasureTilesForTreasure = numSunkenTilesForTreasure.get(treasureType);
		numSunkenTilesForTreasure.replace(treasureType, currentNumberOfSunkenTreasureTilesForTreasure + 1);
	}
	
	/**
	 * printContents method prints what treasures have been claimed!
	 */
	@Override
	public String toString() {
		StringBuilder collectedTreasuresString = new StringBuilder("");
		if(collectedTreasures.size() == 0)
			collectedTreasuresString.append("No treasures have been collected.\n");
		else {
			collectedTreasuresString.append("There following treasures have been collected:\n");
			collectedTreasures.forEach((treasureType) -> {
				System.out.println( treasureType );
				collectedTreasuresString.append(treasureType.toString() + "\n");
			});
			
		}
		collectedTreasuresString.append("There are "+(NUM_TREASURES-collectedTreasures.size())+" treasures left on the island.\n");
		return collectedTreasuresString.toString();
	}
	 
}