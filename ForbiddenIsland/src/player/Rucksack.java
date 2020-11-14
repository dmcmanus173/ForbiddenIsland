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
	private int numTreasures;
	private Boolean gottenAllTreasures;
	
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
		numTreasures = 0;
		gottenAllTreasures = false;
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * isTreasureClaimed method returns whether a treasure of the given type has been claimed.
	 * @return Boolean isClaimed, whether treasure has been claimed before.
	 * TODO might not be necessary to have this as Boolean. Can't pose this again as the cards will 
	 * have disappeared at a given point. Maybe tiles should tell each other whether treasure has been claimed on first claim.
	 */
	public Boolean isTreasureClaimed(TreasureEnum type) {
		Treasure foundTreasure = findTreasure(type);
		if( foundTreasure == null ) {
			System.out.println("Error found in isTreasureClaimed in Rucksack class. foundTreasure == null");
			return false;
		}
		if( foundTreasure.isClaimed() )
			return true;
		claimTreasure(foundTreasure);
		return false; // Return false for first time treasureClaimed
	}
	
	/**
	 * gottenAllTreasures method returns true if all treasures have been claimed.
	 * TODO @Demi should this call observer to know this has been updated?
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
	 * claimTreasure method will claim treasure and check if all treasures claimed
	 * @param aTreasure
	 */
	private void claimTreasure(Treasure aTreasure) {
		aTreasure.claimTreasure();
		numTreasures += 1;
		gotAllTreasures();
	}
	
	/**
	 * gotAllTreasures method, if got all treasures, prints message.
	 * Changes value of gottenAllTreasures to true if all treasures got.
	 */
	private void gotAllTreasures() {
		if( numTreasures == NUM_TREASURES ) {
			gottenAllTreasures = true;
			System.out.println("All treasures have been claimed!");
		}
	}
	
	/**
	 * printContents method prints what treasures have been claimed!
	 */
	public void printContents() {
	 	System.out.println("The treasures in the rucksack are: ");
	 	for(Treasure treasure : treasures) {
	 		if(treasure.isClaimed()) System.out.println( treasure.toString() );
	 	}
	 	System.out.println("There are "+(NUM_TREASURES-numTreasures)+" treasures left on the island.");
	}
	 

}
