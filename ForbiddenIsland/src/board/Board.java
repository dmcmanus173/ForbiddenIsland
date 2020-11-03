package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import enums.PlayerMovesEnum;
import enums.TileEnum;
import enums.TreasureEnum;


/**
 * Class representing the board object.
 * @author Demi Oke & Daniel McManus
 * @date    31/10/2020
 * @version 0.1
 *
 */


public class Board {
	
	
	//===========================================================
    // Variable Setup
    //===========================================================
	private static Board board;
	private ArrayList<ArrayList<Optional<Tile>>> islandTiles;
	private ArrayList<Tile> orderedTiles;
	private Map<TileEnum, int[]> islandTilesNamePositionMap;
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
    	orderedTiles = instanciateIslandTiles(); // Instantiate all tiles
    	Optional<Tile> islandTile; // dependency
    	// Island tiles are optional as some array positions are empty due to the odd shape of the board
    	this.islandTiles = new ArrayList<ArrayList<Optional<Tile>>>(); 
    	this.islandTilesNamePositionMap = new HashMap<TileEnum, int[]>();
    	int tileCounter = 0;
    	
    	
//    	System.out.println("making board.");
    	for (int y=0; y<NUM_ROWS; y++) {         // For each row in Board
 //   		System.out.println("making row:" + y);
    		islandTiles.add(new ArrayList<Optional<Tile>>());
            
            for (int x=0; x<NUM_COLS; x++) {     // For each column in row
            	if (tilePositionIsWithinIslandShape(x, y)) {
            		islandTile = Optional.of(orderedTiles.get(tileCounter));
            		islandTiles.get(y).add(islandTile);
            		int[] pos = {x, y};
            		islandTilesNamePositionMap.put(islandTile.get().tileName, pos);
            		tileCounter += 1;
            	} else {
            		islandTiles.get(y).add(Optional.empty());
            	}
            }  
        }
    }
    
    
    /**
     * returns a particular board tile.
     * @param pos the tiles position
     * @return the tile for a particular position.
     */
    private Optional<Tile> getTileAtPosition(int[] pos) {
    	if(tilePositionIsWithinIslandShape(pos[0], pos[1])) {
    		return islandTiles.get(pos[1]).get(pos[0]);
    	} else { 
    		return Optional.empty();
    	}
    }
    
    /**
     * returns a particular board tile.
     * Unlike getTileAtPosition, this function should ALWAYS return a tile, 
     * as every island tile should be within the shape of the island.
     * @param tileName the name of the tile.
     * @return the tile for a of a given name.
     * @throws Exception 
     */
    private Tile getTileWithName(TileEnum tileName) throws Exception {
    	int[] pos = islandTilesNamePositionMap.get(tileName);
        Optional<Tile> islandTile = getTileAtPosition(pos);
        if (islandTile.isPresent()) {
        	return islandTile.get();
        } else {
        	// the position of every island tile must exist within the island shape.
        	throw new Exception("The position of the tile with the name: " + tileName + " is not in the feasibile region.");
        }
    }
    
    private Optional<Tile> getTileWithDirectionFrom(TileEnum tileName, PlayerMovesEnum playerDirection) {
    	int[] currentPos = islandTilesNamePositionMap.get(tileName);
    	int yPos = currentPos[1];
    	int xPos = currentPos[0];
    	int[] detstination = {xPos, yPos};
    	switch(playerDirection) {
    	case NORTH:
    		detstination[1] = yPos + 1;
    		return getTileAtPosition(detstination);
    	case EAST:
    		detstination[0] = xPos + 1;
    		return getTileAtPosition(detstination);
    	case SOUTH:
    		detstination[1] = yPos - 1;
    		return getTileAtPosition(detstination);
    	case WEST:
    		detstination[0] = xPos - 1;
    		return getTileAtPosition(detstination);
    	case NORTH_EAST:
    		detstination[0] = xPos + 1;
    		detstination[1] = yPos + 1;
    		return getTileAtPosition(detstination);
    	case SOUTH_EAST:
    		detstination[0] = xPos + 1;
    		detstination[1] = yPos - 1;
    		return getTileAtPosition(detstination);
    	case SOUTH_WEST:
    		detstination[0] = xPos - 1;
    		detstination[1] = yPos - 1;
    		return getTileAtPosition(detstination);
    	case NORTH_WEST:
    		detstination[0] = xPos - 1;
    		detstination[1] = yPos + 1;
    		return getTileAtPosition(detstination);
    	default:
    		System.out.println("Option not plausable.");
    		return Optional.empty();
    	
    	}
    }
    
    
    /**
     * returns a particular board tile.
     * @param pos the tiles position
     * @return the tile for a particular position.
     */
    private Optional<Tile> get(int[] pos) {
        return islandTiles.get(pos[1]).get(pos[0]);
    }
    
    
    
    
    /**
     * tileExistsAtPosition. Defines the feasible region where island tiles can exist. 
     * Checks if a tile can be placed at a given position on the board
     * @param x the x position on the board
     * @param y the y position on the board
     * @return boolean determining if the tile can be placed at the given position.
     */
    private boolean tilePositionIsWithinIslandShape(int x, int y) {
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
    	StringBuilder boardString = new StringBuilder("");
    	
        boardString.append("          -----------           \n          | ");
        for(int i=0; i<2; i++)
        	boardString.append(orderedTiles.get(i).getTileName().getAbbr()+" | ");
        boardString.append("\n     ---------------------      \n     | ");
        for(int i=2; i<6; i++)
        	boardString.append(orderedTiles.get(i).getTileName().getAbbr()+" | ");
        boardString.append("\n------------------------------- \n| ");
        for(int i=6; i<12; i++)
        	boardString.append(orderedTiles.get(i).getTileName().getAbbr()+" | ");
        boardString.append("\n------------------------------- \n| ");
        for(int i=12; i<18; i++)
        	boardString.append(orderedTiles.get(i).getTileName().getAbbr()+" | ");
        boardString.append("\n------------------------------- \n     | ");
        for(int i=18; i<22; i++)
        	boardString.append(orderedTiles.get(i).getTileName().getAbbr()+" | ");
        boardString.append("\n     ---------------------      \n          | ");
        for(int i=22; i<24; i++)
        	boardString.append(orderedTiles.get(i).getTileName().getAbbr()+" | ");
        boardString.append("\n          -----------           \n");

    	System.out.println(boardString.toString());
    }
    
    
//    public String getTileString(String tileAbbr) {
//    	StringBuilder tileString = new StringBuilder("");
//    	String horizLine = "______";
//    	String vertLine = "|";
//    	String sixSpaces = "      ";
//    	String twoSpaces = "  ";
//    	String oneSpace = " ";
//    	String zeroStr = " ______";
//    	System.out.println(tileString.toString());
//    	tileString.append(" ______ ______");
//    	tileString.append("|      |      |");
//    	tileString.append("|  FL  |");
//    	tileString.append("|______|");
//    	
//    	for (int y=0; y<=18; y++) {         // For each row in Board
//    		
//            for (int x=0; x<=4; x++) {     // For each column in row
//            	
//            }
//            System.out.println(tileString.toString());
//            
//        }
//
//    }
    
    
    
    
    /**
     * instanciateIslandTiles instantiates individual island tiles and shuffle them.
     * @return tiles a shuffled list of all the island tiles in the game.
     */
	private ArrayList<Tile> instanciateIslandTiles() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		Optional<TreasureEnum> treasure;
		for (TileEnum tileName : TileEnum.values()) { 
			treasure = getAssociatedTreasureForTile(tileName);
			if ( treasure.isPresent() ) {
				tiles.add(new TreasureTile(tileName, treasure.get()));
			} else {
				tiles.add(new Tile(tileName));
			}
		}
		
		Collections.shuffle(tiles);
		return tiles;
	}
	
	
	/**
     * getAssociatedTreasureForTile gets the treasureType associated with a given island tile.
     *  @param tileName the name of the tile.
     * @return Optional TreasureEnum as not all island tiles have an associated treasure.
     */
	private Optional<TreasureEnum> getAssociatedTreasureForTile(TileEnum tileName) {
		if (tileName == TileEnum.TEMPLE_OF_THE_MOON || tileName == TileEnum.TEMPLE_OF_THE_SUN) 
			return Optional.of(TreasureEnum.THE_EARTH_STONE);
		else if (tileName == TileEnum.WHISPERING_GARDEN || tileName == TileEnum.HOWLING_GARDEN)
			return Optional.of(TreasureEnum.THE_STATUE_OF_WIND);
		else if (tileName == TileEnum.CAVE_OF_EMBERS || tileName == TileEnum.CAVE_OF_SHADOWS) 
			return Optional.of(TreasureEnum.THE_CRYSTAL_OF_FIRE);
		else if (tileName == TileEnum.CORAL_PALACE || tileName == TileEnum.TIDAL_PALACE) 
			return Optional.of(TreasureEnum.THE_OCEANS_CHALICE);
		else 
			return Optional.empty();
		
		
	} 
	
	public void printOrderedTiles() {
		for (int y=0; y<24; y++) {
			System.out.println(orderedTiles.get(y).getTileName().toString());
		}
	}
	
	// TODO Change this so that instanciateIslandTiles() doesn't need to be called more than once.
	/**
     * getIslandTiles gets a list of all the Tiles on the board.
     * @return ArrayList<Tile> containing all Tiles on board.
     */
	public ArrayList<Tile> getIslandTiles() {
		return orderedTiles;
	}
	
	/*
	// Class-level test
	public static void main(String[] args) {
		Board.getInstance().printBoard();
		Board.getInstance().printOrderedTiles();
		
	}
	*/

}

