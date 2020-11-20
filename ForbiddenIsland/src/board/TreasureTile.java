package board;

import java.util.ArrayList;

import enums.TileEnum;
import enums.TreasureEnum;
import gameComponents.AbstractTreasureCard;
import gameComponents.TreasureCard;
import gameManager.GameManager;
import player.TreasureManager;


/**
 * Class for TreasureTile in a Forbidden Island Game.
 * This class is extended from Tile.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    04/11/2020
 * @version 0.1
 */
public class TreasureTile extends Tile {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private TreasureEnum treasureType;
	private TileEnum sisterTileName;
	
	
	//===========================================================
    // Constructor
    //===========================================================
    /**
     * Constructor for a tile object.
     * @param tileName      Name of Tile
     * @param floodStatus   The status of Tile i.e. flooded, not flooded or sunken
     * @param treasureType  TreasureEnum treasureType for TreasureTile
     */
	public TreasureTile(TileEnum tileName, TileEnum sisterTileName, TreasureEnum treasureType) {
		super(tileName);
		this.sisterTileName = sisterTileName;
		this.treasureType = treasureType;
	}
	
	
	//===========================================================
	// Public Getters
	//===========================================================
	/**
	 * getTreasureType function, to return the treasureType
	 * @return the TreasureEnum type object treasureType.
	 */
	public TreasureEnum getTreasureType() {
		return treasureType;
	}
	
	
	
	
	/**
     * collectTreasure method checks the players hand to see if they have at least 
     * 4 treasure cards corresponding to the treasure of the island tile.
     * @param ArrayList<AbstractTreasureCard> treasureCards in players hand.
     * @param floodStatus   The status of Tile i.e. flooded, not flooded or sunken
     * @return Boolean determining if the player has collected the treasure or not.
     */
	public Boolean collectTreasure(ArrayList<AbstractTreasureCard> treasureCards) {
		int numOfMatchingTreasureCards = 0;
		AbstractTreasureCard card;
		TreasureCard treasureCard;
		int maxNumOfMatchingTreasureCards = 4;
		
		for(int i=0; i<treasureCards.size(); i++) {
			card = treasureCards.get(i);
			if(card instanceof TreasureCard) {
				treasureCard = (TreasureCard)card;
				if (treasureCard.getTreasureType() == treasureType) {
					numOfMatchingTreasureCards += 1;
					if(numOfMatchingTreasureCards >= maxNumOfMatchingTreasureCards) {
						// TODO: Trigger treasure in rucksack Notify WinLose class that a treasure has been collected.
						TreasureManager.getInstance().claimTreasure(treasureType);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	/**
    * flood method floods a tile if it is shored up and sinks a tile if it is flooded.
    */
	public void flood() {
		super.flood();
		Tile sisterTile = Board.getInstance().getTileWithName(sisterTileName);
		if(isSunken()) {
			if(!TreasureManager.getInstance().didClaimTreasure(treasureType) && sisterTile.isSunken()) {
				GameManager.getInstance().gameOver();
				System.out.println("The Treasure "+treasureType.name()+" has sunk!");
			}
		}
	}
	
	public TileEnum getSisterTile() {
		return sisterTileName;
	}
	
	
	
	
	/**
    * toString method returns relevant information about treasure tiletile
    * that will be displayed to players during the game.
    * @return String treasure tile info.
    */
	@Override
	public String toString() {
		StringBuilder treasureTileString = new StringBuilder("");
		treasureTileString.append(super.toString());
		treasureTileString.append("\n\t");
		treasureTileString.append(treasureType + " can be collected here.");
		return treasureTileString.toString();
		
	}
	
	/*
	// Class-level test
	public static void main(String[] args) {
		TreasureTile tile = new TreasureTile(TileEnum.BREAKERS_BRIDGE, TreasureEnum.THE_CRYSTAL_OF_FIRE);
		Player player1 = new Player("Tom", AdventurerEnum.DIVER);
		Player player2 = new Player("Wendy", AdventurerEnum.EXPLORER);
		
		tile.addPlayerToTile(player1);
		tile.addPlayerToTile(player2);
		tile.flood();
		System.out.println(tile.toString());
		
		tile.removePlayerFromTile(player1);
		tile.shoreUp();
		System.out.println(tile.toString());
		
		tile.removePlayerFromTile(player2);
		tile.flood();
		tile.flood();
		System.out.println(tile.toString());
		
	}
	*/

}
