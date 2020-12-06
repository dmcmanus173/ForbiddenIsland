package gameManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import fi.enums.TileEnum;
import fi.enums.TreasureEnum;
import otherComponents.Treasure;

/**
 * Class for a Rucksack to contain Treasures.
 * This class is also used for managing Treasures throughout game.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
public class TreasureManager {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static TreasureManager rucksack;
	private ArrayList<Treasure> treasures = new ArrayList<Treasure>();
	private int numClaimedTreasures;
	private Boolean gottenAllTreasures;
	private ArrayList<Treasure> claimedTreasures = new ArrayList<Treasure>();
	
	//===========================================================
	// Variable for Game Settings
	//===========================================================
	private final int NUM_TREASURES = 4;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of TreasureManager
     * @return TreasureManager. singleton TreasureManager object.
     */
	public static TreasureManager getInstance() {
		if(rucksack == null)
			rucksack = new TreasureManager();
		return rucksack;
	}
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for TreasureManager object.
	 */
	private TreasureManager() {
		treasures.add(new Treasure(TreasureEnum.THE_CRYSTAL_OF_FIRE));
		treasures.add(new Treasure(TreasureEnum.THE_EARTH_STONE    ));
		treasures.add(new Treasure(TreasureEnum.THE_OCEANS_CHALICE ));
		treasures.add(new Treasure(TreasureEnum.THE_STATUE_OF_WIND ));
		numClaimedTreasures = 0;
		gottenAllTreasures = false;
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * isTreasureClaimed method returns whether a treasure of the given type has been claimed.
	 * @return Boolean isClaimed, whether treasure has been claimed before.
	 */
	public Boolean didClaimTreasure(TreasureEnum type) {
		Treasure treasure = getTreasure(type);
		return treasure.isClaimed();
	}
	
	/**
	 * claimTreasure method will claim treasure and check if all treasures claimed
	 * @param a treasure type, TreasureEnum
	 */
	public void claimTreasure(TreasureEnum type) {
		Treasure aTreasure = getTreasure(type);
		if( aTreasure == null ) {
	    	throw new RuntimeException("Can't find Treasure in claimTreasure.");
		}
		aTreasure.claimTreasure();
		numClaimedTreasures += 1;
		claimedTreasures.add(aTreasure);
		if( numClaimedTreasures == NUM_TREASURES ) {
			gottenAllTreasures = true;
			System.out.println("All treasures have been claimed!");
		}
	}
	
	/**
	 * gottenAllTreasures method returns true if all treasures have been claimed.
	 * @return gottenAllTreasures, Boolean value.
	 */
	public Boolean didClaimAllTreasures() {
		return gottenAllTreasures;
	}
	
	/**
	 * findTreasure method returns Treasure for TreasureType.
	 * @param type, TreasureEnum type.
	 * @return treasure, Treasure treasure.
	 */
	private Treasure getTreasure(TreasureEnum type) {
		for(Treasure treasure : treasures) {
			if(treasure.getType() == type)
				return treasure;
		}
	    throw new RuntimeException("Can't find Treasure in findTreasure.");
	}
	
	
	/**
     * getAssociatedTreasureForTile gets the treasureType associated with a given island tile.
     * @param tileName the name of the tile.
     * @return Optional TreasureEnum as not all island tiles have an associated treasure.
     */
	public Optional<TreasureEnum> getAssociatedTreasureForTile(TileEnum tileName) {
		if (getTreasureTilesForTreasure(TreasureEnum.THE_EARTH_STONE).contains(tileName)) 
			return Optional.of(TreasureEnum.THE_EARTH_STONE);
		else if (getTreasureTilesForTreasure(TreasureEnum.THE_STATUE_OF_WIND).contains(tileName))
			return Optional.of(TreasureEnum.THE_STATUE_OF_WIND);
		else if (getTreasureTilesForTreasure(TreasureEnum.THE_CRYSTAL_OF_FIRE).contains(tileName)) 
			return Optional.of(TreasureEnum.THE_CRYSTAL_OF_FIRE);
		else if (getTreasureTilesForTreasure(TreasureEnum.THE_OCEANS_CHALICE).contains(tileName)) 
			return Optional.of(TreasureEnum.THE_OCEANS_CHALICE);
		else 
			return Optional.empty();
		
		
	} 
	
	/**
     * getTreasureTilesForTreasure returns the set of tiles corresponding to a given treasure.
     * @param TreasureEnum the type of treasure.
     * @return Set<TileEnum> set of corresponding treasure tiles.
     */
	public Set<TileEnum> getTreasureTilesForTreasure(TreasureEnum treasureType) {
		switch(treasureType) {
		case THE_EARTH_STONE:
			return setOf(TileEnum.TEMPLE_OF_THE_MOON, TileEnum.TEMPLE_OF_THE_SUN);
		case THE_STATUE_OF_WIND:
			return setOf(TileEnum.WHISPERING_GARDEN, TileEnum.HOWLING_GARDEN);
		case THE_CRYSTAL_OF_FIRE:
			return setOf(TileEnum.CAVE_OF_EMBERS, TileEnum.CAVE_OF_SHADOWS);
		case THE_OCEANS_CHALICE:
			return setOf(TileEnum.CORAL_PALACE, TileEnum.TIDAL_PALACE);
		}
		throw new RuntimeException("Attempting to use a TreasureEnum that does not exist.");
	}
	
	
	/**
     * getSisterTreasureTile gets the sister tile name for a given treasure tile name.
     * @param tileName the name of the tile.
     * @return Optional<TileEnum> the tileEnum corresponding to the sister tile.
     */
	public Optional<TileEnum> getSisterTreasureTileName(TileEnum tileName) {
		Set<TileEnum> treasureTilesForTreasure;
		TileEnum sisterTile;
		for (TreasureEnum treasure : TreasureEnum.values()) { 
			treasureTilesForTreasure =  getTreasureTilesForTreasure(treasure);
			if (treasureTilesForTreasure.contains(tileName)) {
				treasureTilesForTreasure.removeAll(setOf(tileName));
				sisterTile = treasureTilesForTreasure.iterator().next();
				return Optional.of(sisterTile);
			}
		}
		
		return Optional.empty();
		
		
	}
	
	
	/**
     * setOf is a helper function used to neatly create sets of TileEnum.
     * @param TileEnum... tiles comma separated tileEnums
     * @return Set<TileEnum> created set.
     */
	private static Set<TileEnum> setOf(TileEnum... tiles) {
	    return new HashSet<TileEnum>(Arrays.asList(tiles));
	}

	
	/**
	 * printContents method prints what treasures have been claimed!
	 */
	public void printContents() {
		if(numClaimedTreasures == 0)
			System.out.println("There's no treasures in the rucksack.");
		else {
			System.out.println("There following treasures are in the rucksack:");
			for(Treasure treasure : claimedTreasures) {
				if(treasure.isClaimed()) System.out.println( treasure.getType().toString() );
			}
		}
		System.out.println("There are "+(NUM_TREASURES-numClaimedTreasures)+" treasures left on the island.\n");
	}
	 

}
