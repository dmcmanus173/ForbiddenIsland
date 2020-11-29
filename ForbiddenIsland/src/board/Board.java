package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import enums.PlayerMovesEnum;
import enums.TileEnum;
import enums.TreasureEnum;
import gameManager.TreasureManager;


/**
 * Class representing the board object.
 * @author Demi Oke & Daniel McManus
 * @date    04/11/2020
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
	public Tile getTileWithName(TileEnum tileName) {
    	int[] pos = islandTilesNamePositionMap.get(tileName);
        Optional<Tile> islandTile = getTileAtPosition(pos);
        
        if (islandTile.isPresent()) return islandTile.get();
        else throw new RuntimeException("The position of the tile with the name: " + tileName + " is not in the feasibile region.");
        // the position of every island tile must exist within the island shape.
    }
    
//    private void setUpPlayerOnBoard(ArrayList<Player> players) {
//    	players.forEach((player) -> {
//    		//TODO: Find out why i have to declare the tile inside loop.
//    		Tile playerStartingTile = getTileWithName(player.getLocation().getTileName());
//    		playerStartingTile.addPlayerToTile(player);
//    	});
//    }
    
//    public void movePlayer(Player player, TileEnum destination) {
//    	Tile currentTile = getTileWithName(player.getLocation().getTileName());
//    	Tile destinationTile = getTileWithName(destination);
//    	// TODO: change playersOnTile to arrayAist instead of set?? so we can do this..
//    	// destinationTile.addPlayerToTile(currentTile.removePlayerFromTile(player));
//    	currentTile.removePlayerFromTile(player);
//    	destinationTile.addPlayerToTile(player);
//    	//TODO: change tilePosition of player from current Tile to destination tile.
//    }
    
    
//    public boolean shoreUpTile(TileEnum tileName) {
//    	Tile tileToShoreUp = getTileWithName(tileName);
//    	return tileToShoreUp.shoreUp();
//    }
//    
//    public Optional<Tile> floodTile(TileEnum tileName) {
//    	Tile tileToFlood = getTileWithName(tileName);
//    	tileToFlood.flood();
//    	if (tileToFlood.isSunken()) return Optional.of(tileToFlood);
//    	else return Optional.empty();
//    }
    
    /**
     * getTileWithDirectionFrom returns the tile at the direction from the given tile. .
     * Unlike getTileAtPosition, this function should ALWAYS return a tile, 
     * as every island tile should be within the shape of the island.
     * @param tileName the name of the tile.
     * @param playerDirection the direction at which to get the tile, i.e. up, down, left, right etc.
     * @param dist the search distance from the tile.
     * @return Optional<Tile> at the direction from the given tile. 
     */
    private Optional<Tile> getTileWithDirectionFrom(Tile tile, PlayerMovesEnum playerDirection) {
    	int[] currentPos = islandTilesNamePositionMap.get(tile.getTileName());
    	int yPos = currentPos[1];
    	int xPos = currentPos[0];
    	int[] detstination = {xPos, yPos};
    	switch(playerDirection) {
    	case NORTH:
    		detstination[1] = yPos - 1;
    		return getTileAtPosition(detstination);
    	case EAST:
    		detstination[0] = xPos + 1;
    		return getTileAtPosition(detstination);
    	case SOUTH:
    		detstination[1] = yPos + 1;
    		return getTileAtPosition(detstination);
    	case WEST:
    		detstination[0] = xPos - 1;
    		return getTileAtPosition(detstination);
    	case NORTH_EAST:
    		detstination[0] = xPos + 1;
    		detstination[1] = yPos - 1;
    		return getTileAtPosition(detstination);
    	case SOUTH_EAST:
    		detstination[0] = xPos + 1;
    		detstination[1] = yPos + 1;
    		return getTileAtPosition(detstination);
    	case SOUTH_WEST:
    		detstination[0] = xPos - 1;
    		detstination[1] = yPos + 1;
    		return getTileAtPosition(detstination);
    	case NORTH_WEST:
    		detstination[0] = xPos - 1;
    		detstination[1] = yPos - 1;
    		return getTileAtPosition(detstination);
    	default:
    		System.out.println("Option not plausable.");
    		return Optional.empty();
    	
    	}
    }
    
    
    /**
     * getTilesAroundTile method gets tiles immediately around a specified tile.
     * @param tileName the name of the tile.
     * @param returnAdjacent boolean determining if chosen tiles should only be adjacent. true returns adjacent tiles, false returns diagonal tiles.
     * @return ArrayList<Tile> containing the player movement direction and the corresponding tile in that direction.
     */
    public ArrayList<Tile>  getTilesAroundTile(Tile tile, boolean returnAdjacentOnly) {
    	ArrayList<Tile> tilesPlayerCanMoveTo = new ArrayList<Tile>();
    	Optional<Tile> tileAtDirection;
    	if (returnAdjacentOnly) {
    		for (PlayerMovesEnum playerMove : PlayerMovesEnum.values()) { 
    		    if(playerMove.isAdjacentMove()) {
    		    	tileAtDirection = getTileWithDirectionFrom(tile, playerMove);
    		    	if(tileAtDirection.isPresent() && tileAtDirection.get().isSunken() == false) {
    		    		tilesPlayerCanMoveTo.add(tileAtDirection.get());
    		    	}
    		    }
    		}
    	} else {
    		for (PlayerMovesEnum playerMove : PlayerMovesEnum.values()) { 
    			tileAtDirection = getTileWithDirectionFrom(tile, playerMove);
		    	if(tileAtDirection.isPresent() && tileAtDirection.get().isSunken() == false) {
		    		tilesPlayerCanMoveTo.add(tileAtDirection.get());
		    	}
    		}
    	}
    	return tilesPlayerCanMoveTo;
    }
    
    /**
     * getNearestTilesToTile sequential searches for the nearest unsunk tile on to
     * a particular tile on the board.
     * @param Tile the specified tile.
     * @return ArrayList<Tile> containing the player movement direction and the corresponding tile in that direction.
     */
    public ArrayList<Tile> getNearestTilesToTile(Tile tile) {
    	ArrayList<Tile> tilesPlayerCanMoveTo = new ArrayList<Tile>();
    	Optional<Tile> tileAtDirection;
    	int xPos, yPos, xMin, yMin, xMax, yMax;
    	for(int radius = 1; radius< NUM_COLS; radius++) {
    		System.out.println("radius is now " + radius);
    		int[] currentPos = islandTilesNamePositionMap.get(tile.getTileName());
    		xPos = currentPos[0];
        	yPos = currentPos[1];
        	
        	xMin = xPos - radius;
        	yMin = yPos - radius;
        	
        	xMax = xPos + radius;
        	yMax = yPos + radius;
        	int[] posWithinRadius = {0, 0};
        	
        	for(int j = yMin; j<= yMax; j++) {
        		for(int i = xMin; i<= xMax; i++) {
        			if(j == yMin | i == xMin | j == yMax | i == xMax) {
        				posWithinRadius[0] = i;
            			posWithinRadius[1] = j;
        				tileAtDirection = getTileAtPosition(posWithinRadius);
            			if(tileAtDirection.isPresent() && tileAtDirection.get().isSunken() == false) {
        		    		tilesPlayerCanMoveTo.add(tileAtDirection.get());
        		    	}
        			}
            	}
        	}
        	
        	if(!tilesPlayerCanMoveTo.isEmpty()) return tilesPlayerCanMoveTo;
    	}
    	
    	// return empty map.
    	return tilesPlayerCanMoveTo;
    	
    }
    
    
    
    /**
     * getNearestTilesToTile sequential searches for the nearest unsunk tile on to
     * a particular tile on the board.
     * @param Tile the specified tile.
     * @return ArrayList<Tile> containing the player movement direction and the corresponding tile in that direction.
     */
    public ArrayList<Tile> getTiles(Tile tile, int maxRadius, boolean returnadjacebnt) {
    	ArrayList<Tile> tilesPlayerCanMoveTo = new ArrayList<Tile>();
    	Optional<Tile> tileAtDirection;
    	int xPos, yPos, xMin, yMin, xMax, yMax;
    	for(int radius = 1; radius< maxRadius; radius++) {
    		System.out.println("radius is now " + radius);
    		int[] currentPos = islandTilesNamePositionMap.get(tile.getTileName());
    		xPos = currentPos[0];
        	yPos = currentPos[1];
        	
        	xMin = xPos - radius;
        	yMin = yPos - radius;
        	
        	xMax = xPos + radius;
        	yMax = yPos + radius;
        	int[] posWithinRadius = {0, 0};
        	
        	for(int j = yMin; j<= yMax; j++) {
        		for(int i = xMin; i<= xMax; i++) {
        			if(j == yMin | i == xMin | j == yMax | i == xMax) {
        				posWithinRadius[0] = i;
            			posWithinRadius[1] = j;
        				tileAtDirection = getTileAtPosition(posWithinRadius);
            			if(tileAtDirection.isPresent() && tileAtDirection.get().isSunken() == false) {
        		    		tilesPlayerCanMoveTo.add(tileAtDirection.get());
        		    	}
        			}
            	}
        	}
        	
        	if(!tilesPlayerCanMoveTo.isEmpty()) return tilesPlayerCanMoveTo;
    	}
    	
    	// return empty map.
    	return tilesPlayerCanMoveTo;
    	
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
    		if(x>=0 && x <=5) return true;
    	}
    	return false;
    }
    
    
    
    /**
     * void function to print the game board.
     */
    public void printBoard() {
    	StringBuilder boardString = new StringBuilder("");
    	Tile aTile;
        boardString.append("              ---------------          \n              | ");
        for(int i=0; i<2; i++) {
        	aTile = orderedTiles.get(i);
        	boardString.append(aTile.getTileName().getAbbr()+" "+aTile.getFloodStatus().toInt()+" | ");
        }
        boardString.append("\n       -----------------------------      \n       | ");
        for(int i=2; i<6; i++) {
        	aTile = orderedTiles.get(i);
        	boardString.append(aTile.getTileName().getAbbr()+" "+aTile.getFloodStatus().toInt()+" | ");
        }
        boardString.append("\n------------------------------------------- \n| ");
        for(int i=6; i<12; i++) {
        	aTile = orderedTiles.get(i);
        	boardString.append(aTile.getTileName().getAbbr()+" "+aTile.getFloodStatus().toInt()+" | ");
        }
        boardString.append("\n------------------------------------------- \n| ");
        for(int i=12; i<18; i++) {
        	aTile = orderedTiles.get(i);
        	boardString.append(aTile.getTileName().getAbbr()+" "+aTile.getFloodStatus().toInt()+" | ");
        }
        boardString.append("\n------------------------------------------- \n       | ");
        for(int i=18; i<22; i++) {
        	aTile = orderedTiles.get(i);
        	boardString.append(aTile.getTileName().getAbbr()+" "+aTile.getFloodStatus().toInt()+" | ");
        }
        boardString.append("\n       -----------------------------      \n              | ");
        for(int i=22; i<24; i++) {
        	aTile = orderedTiles.get(i);
        	boardString.append(aTile.getTileName().getAbbr()+" "+aTile.getFloodStatus().toInt()+" | ");
        }
        boardString.append("\n              ---------------           \n");

    	System.out.println(boardString.toString());
    }
    
    
    /**
     * instanciateIslandTiles instantiates individual island tiles and shuffle them.
     * @return tiles a shuffled list of all the island tiles in the game.
     */
	private ArrayList<Tile> instanciateIslandTiles() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		Optional<TreasureEnum> treasure;
		Optional<TileEnum> sisterTreasureTile;
		for (TileEnum tileName : TileEnum.values()) { 
			treasure = TreasureManager.getInstance().getAssociatedTreasureForTile(tileName);
			sisterTreasureTile = TreasureManager.getInstance().getSisterTreasureTileName(tileName);
			if ( treasure.isPresent() && sisterTreasureTile.isPresent()) {
				tiles.add(new TreasureTile(tileName, sisterTreasureTile.get(), treasure.get()));
			} else if (tileName == TileEnum.FOOLS_LANDING) {
				tiles.add(new FoolsLandingTile(tileName));
			} else {
				tiles.add(new Tile(tileName));
			}
		}
		
		Collections.shuffle(tiles);
		return tiles;
	}
	
	
	public void printOrderedTiles() {
		for (int y=0; y<24; y++) {
			System.out.println(orderedTiles.get(y).getTileName().toString());
		}
	}
	
	/**
     * getIslandTiles gets a list of all the Tiles on the board.
     * @return ArrayList<Tile> containing all Tiles on board.
     */
	public ArrayList<Tile> getIslandTiles() {
		return orderedTiles;
	}
	
	/**
     * getUnsunkenTiles gets a list of all the Tiles on the board that have not sunk.
     * @return ArrayList<Tile> containing all tiles that are not sunk Tiles on board.
     */
	public ArrayList<Tile> getUnsunkenTiles() {
		ArrayList<Tile> potentialTiles = new ArrayList<Tile>();
		for(Tile aTile : getIslandTiles()) {
			if( !aTile.isSunken() )
				potentialTiles.add(aTile);
		}
		return potentialTiles;
	}	
	
	
	// Class-level test
	public static void main(String[] args) {
		Board.getInstance().printBoard();
//		Board.getInstance().printOrderedTiles();
		
		// testing sisterTile
		ArrayList<Tile> tiles = Board.getInstance().getIslandTiles();
		Tile tile;
		for(int i=0; i<tiles.size(); i++) {
			tile = tiles.get(i);
			if(tile instanceof TreasureTile) {
				System.out.println(tile.tileName + " : " + ((TreasureTile) tile).getSisterTile());
			}
		}
		
//		Tile firstTile = tiles.get(0);
//		Tile nearestTile = tiles.get(6);
//		Tile nearestTile2 = tiles.get(7);
//		Tile lastTile = tiles.get(23);
//		
//		System.out.println("First tile is " + firstTile.tileName);
//		System.out.println("Last tile is " + lastTile.tileName);
//		
//		tiles.forEach((tile) -> {
//			if(tile != firstTile & tile != lastTile & tile != nearestTile & tile != nearestTile2) {
//				tile.flood();
//				tile.flood();
//			}
//		});
//		
//		ArrayList<Tile> nearestTiles = Board.getInstance().getNearestTilesToTile(firstTile);
//		
//		
//		System.out.println("Nearest Tiles to " + firstTile.tileName + "are ===========================");
//		nearestTiles.forEach((tile) -> {
//			System.out.println(tile.tileName);
//		});
		
		
	
		
		
		
	}
	

}

