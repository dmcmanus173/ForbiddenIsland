package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import enums.TileEnum;
import enums.TreasureEnum;


public class Board {
	
	
	//===========================================================
    // Variable Setup
    //===========================================================
	private static Board board;
	private ArrayList<ArrayList<Optional<Tile>>> islandTiles;
	private final int NUM_ROWS = 6;
	private final int NUM_COLS = 6;
	
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of board.
     * @return board. singleton Board object.
     */
    public static Board getInstance(){
        if(board == null){
            board = new Board();
        }
        return board;
    }
    
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Board constructor. 
	 * Set up all the tiles, shuffle them and place them on the board.
	 */
    private Board() {
    	ArrayList<Tile> instanciatedTiles = instanciateIslandTiles(); // Instantiate all tiles
    	
    	// Island tiles are optional as some array positions are empty due to the odd shape of the board
    	this.islandTiles = new ArrayList<ArrayList<Optional<Tile>>>(); 
    	
    	System.out.println("making board.");
    	for (int y=0; y<NUM_ROWS; y++) {         // For each row in Board
    		System.out.println("making row:" + y);
    		islandTiles.add(new ArrayList<Optional<Tile>>());
            
            for (int x=0; x<NUM_COLS; x++) {     // For each column in row
            	if (canPlaceTileAtPosition(x, y)) {
            		Optional<Tile> islandTile = Optional.of(instanciatedTiles.remove(0));
            		islandTiles.get(y).add(islandTile);
            	} else {
            		islandTiles.get(y).add(Optional.empty());
            	}
            }  
        }
    }
    
    
    /**
     * canPlaceTileAtPosition. Checks if a tile can be placed at a given position on the board
     * @param x the x position on the board
     * @param y the y position on the board
     * @return boolean determining if the tile can be placed at the given position.
     */
    private boolean canPlaceTileAtPosition(int x, int y) {
    	if (y== 0 || y== 5) {
    		if(x>1 && x <4) return true;
    	}
    	if (y== 1 || y== 4) {
    		if(x>0 && x <5) return true;
    	}
    	if (y== 2 || y== 3) {
    		return true;
    	}
    	return false;
    }
    
    
    
    /**
     * void function to print the game board.
     * TODO: spice it up a bit ;)
     */
    public void printBoard() {
    	System.out.println("printing board.");
    	for (int y=0; y<NUM_ROWS; y++) {         // For each row in Board
    		StringBuilder tileString = new StringBuilder("");
            for (int x=0; x<NUM_COLS; x++) {     // For each column in row
            	Optional<Tile> islandTile = islandTiles.get(y).get(x);
            	if(islandTile.isPresent()) {
            		Tile presentIstlandTile = islandTile.get();
            		tileString.append(presentIstlandTile.getTileName());
            		if (presentIstlandTile instanceof TreasureTile) {
            			TreasureTile treasureIslandTile = (TreasureTile) presentIstlandTile;
            			tileString.append("[T=" + treasureIslandTile.getTreasureType() + "], ");
            		} else {
            			tileString.append(", ");
            		}
            	} else {
            		tileString.append("empty, ");
            	}
            }
            System.out.println(tileString.toString());
            
        }
    }
    
    
    /**
     * instanciateIslandTiles instantiates individual island tiles and shuffle them.
     * @return tiles a shuffled list of all the island tiles in the game.
     */
	private ArrayList<Tile> instanciateIslandTiles() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		for (TileEnum tileName : TileEnum.values()) { 
			Optional<TreasureEnum> treasure = getAssociatedTreasureForTile(tileName);
			if (treasure.isPresent()) {
				TreasureEnum presentTreasure = treasure.get();
				tiles.add(new TreasureTile(tileName, presentTreasure));
			} else {
				tiles.add(new Tile(tileName));
			}
		}
		
		Collections.shuffle(tiles);
		return tiles;
	}
	
	
	/**
     * getAssociatedTreasureForTile gets the treasureType associated with a given island tile.
     * @return Optional TreasureEnum as not all island tiles have an associated treasure.
     */
	private Optional<TreasureEnum> getAssociatedTreasureForTile(TileEnum tileName) {
		if (tileName == TileEnum.TEMPLE_OF_THE_MOON || tileName == TileEnum.TEMPLE_OF_THE_SUN) {
			return Optional.of(TreasureEnum.THE_EARTH_STONE);
		} else if (tileName == TileEnum.WHISPERING_GARDEN || tileName == TileEnum.HOWLING_GARDEN) {
			return Optional.of(TreasureEnum.THE_STATUE_OF_WIND);
		} else if (tileName == TileEnum.CAVE_OF_EMBERS || tileName == TileEnum.CAVE_OF_SHADOWS) {
			return Optional.of(TreasureEnum.THE_CRYSTAL_OF_FIRE);
		} else if (tileName == TileEnum.CORAL_PALACE || tileName == TileEnum.TIDAL_PALACE) {
			return Optional.of(TreasureEnum.THE_OCEANS_CHALICE);
		} else {
			return Optional.empty();
		}
		
	} 
	
	// TODO Change this so that instanciateIslandTiles() doesn't need to be called more than once.
	/**
     * getIslandTiles gets a list of all the Tiles on the board.
     * @return ArrayList<Tile> containing all Tiles on board.
     */
	public ArrayList<Tile> getIslandTiles() {
		return instanciateIslandTiles();
	}
	
	/*
	// Class-level test
	public static void main(String[] args) {
		Board.getInstance().printBoard();
		
	}
	*/

}

