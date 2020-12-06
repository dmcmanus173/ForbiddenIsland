package fi.board;

import fi.enums.TileEnum;
import fi.enums.TreasureEnum;

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
	
	
	//===========================================================
    // Constructor
    //===========================================================
    /**
     * Constructor for a tile object.
     * @param tileName      Name of Tile
     * @param floodStatus   The status of Tile i.e. flooded, not flooded or sunken
     * @param treasureType  TreasureEnum treasureType for TreasureTile
     */
	public TreasureTile(TileEnum tileName) {
		super(tileName);
		setTreasure();
	}
	
	private void setTreasure() {
		if(tileName == TileEnum.TEMPLE_OF_THE_MOON || tileName == TileEnum.TEMPLE_OF_THE_SUN) {
			treasureType = TreasureEnum.THE_EARTH_STONE;
		} else if(tileName == TileEnum.WHISPERING_GARDEN || tileName == TileEnum.HOWLING_GARDEN) {
			treasureType = TreasureEnum.THE_STATUE_OF_WIND;
		} else if(tileName == TileEnum.CAVE_OF_EMBERS || tileName == TileEnum.CAVE_OF_SHADOWS) {
			treasureType = TreasureEnum.THE_CRYSTAL_OF_FIRE;
		} else if(tileName == TileEnum.CORAL_PALACE || tileName == TileEnum.TIDAL_PALACE) {
			treasureType = TreasureEnum.THE_OCEANS_CHALICE;
		} else {
			throw new RuntimeException("The given tile name is not a Treasure Tile");
		}
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
