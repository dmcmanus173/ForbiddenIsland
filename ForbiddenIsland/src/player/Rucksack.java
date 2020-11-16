package player;

import java.util.ArrayList;

import enums.TreasureEnum;
import gameComponents.Treasure;

/**
 * Class for a Rucksack to contain Treasures.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
public class Rucksack {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private static Rucksack rucksack;
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
     * getInstance method returns single instance of Rucksack
     * @return rucksack. singleton Rucksack object.
     */
	public static Rucksack getInstance() {
		if(rucksack == null)
			rucksack = new Rucksack();
		return rucksack;
	}
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Rucksack object.
	 */
	private Rucksack() {
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
	public Boolean isTreasureClaimed(TreasureEnum type) {
		Treasure foundTreasure = findTreasure(type);
		if( foundTreasure == null ) {
			System.out.println("Error found in isTreasureClaimed in Rucksack class. foundTreasure == null");
			return false;
		}
		return foundTreasure.isClaimed();
	}
	
	/**
	 * claimTreasure method will claim treasure and check if all treasures claimed
	 * @param a treasure type, TreasureEnum
	 */
	public void claimTreasure(TreasureEnum type) {
		Treasure aTreasure = findTreasure(type);
		aTreasure.claimTreasure();
		numClaimedTreasures += 1;
		claimedTreasures.add(aTreasure);
		gotAllTreasures();
	}
	
	/**
	 * gottenAllTreasures method returns true if all treasures have been claimed.
	 * @return gottenAllTreasures, Boolean value.
	 */
	public Boolean gottenAllTreasures() {
		return gottenAllTreasures;
	}
	
	/**
	 * findTreasure method returns Treasure for TreasureType.
	 * @param type, TreasureEnum type.
	 * @return treasure, Treasure treasure.
	 */
	private Treasure findTreasure(TreasureEnum type) {
		for(Treasure treasure : treasures) {
			if(treasure.getType() == type)
				return treasure;
		}
		System.out.println("ERROR: findTreasure in Rucksack returning a null.");
		return null;
	}
	
	/**
	 * gotAllTreasures method, if got all treasures, prints message.
	 * Changes value of gottenAllTreasures to true if all treasures got.
	 */
	private void gotAllTreasures() {
		if( numClaimedTreasures == NUM_TREASURES ) {
			gottenAllTreasures = true;
			System.out.println("All treasures have been claimed!");
		}
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
