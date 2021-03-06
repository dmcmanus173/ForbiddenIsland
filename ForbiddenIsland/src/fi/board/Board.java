package fi.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.players.Player;

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
    	Optional<Tile> islandTile; 				 // dependency
    	
    	// Island tiles are optional as some array positions are empty due to the odd shape of the board
    	this.islandTiles = new ArrayList<ArrayList<Optional<Tile>>>(); 
    	this.islandTilesNamePositionMap = new HashMap<TileEnum, int[]>();
    	int tileCounter = 0;
    	
    	for (int y=0; y<NUM_ROWS; y++) {         // For each row in Board
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
    
	// used at the start of a game
    public void setUpPlayerOnBoard(Player player) {
    	Tile playerStartingTile = getTileWithName(player.getLocation());
    	playerStartingTile.addPlayerToTile(player);
    }
    
    /**
     * movePlayer method moves Player player on board to TileEnum destination
     * @param player, the player to move
     * @param destination, the target destination for player.
     */
    public void movePlayer(Player player, TileEnum destination) {
    	Tile currentTile = getTileWithName(player.getLocation());
    	Tile destinationTile = getTileWithName(destination);
    	currentTile.removePlayerFromTile(player);
    	destinationTile.addPlayerToTile(player);
    }
    
    /**
     * shoreUpTile method shores up the tile of TileEnum tileName
     * @param tileName
     * @return FloodStatusEnum
     */
    public FloodStatusEnum shoreUpTile(TileEnum tileName) { //TODO use FloodStatusEnum in PlayerGo
    	Tile tileToShoreUp = getTileWithName(tileName);
    	return tileToShoreUp.shoreUp();
    }
    
    /**
     * floodTile method flood the tile of TileEnum tileName
     * @param tileName
     * @return FloodStatusEnum
     */
    public FloodStatusEnum floodTile(TileEnum tileName) { //TODO use FloodStatusEnum in PlayerGo
    	Tile tileToFlood = getTileWithName(tileName);
    	return tileToFlood.flood();
    }
    
    /**
     * getOtherPlayersOnTile gets all other players on the same tile as player
     * @param player, the player getting other players on same tile
     * @return Set of players on the same tile.
     */
    public Set<Player> getOtherPlayersOnTile(Player player) {
    	Tile currentTile = getTileWithName(player.getLocation());
    	Set<Player> otherPlayers = new HashSet<Player>();
    	Set<Player> playersOnTile = currentTile.getPlayersOnTile();
    	
    	for(Player playerOnTile: playersOnTile) {
    		if(playerOnTile != player) {
    			otherPlayers.add(playerOnTile);
    		}
    	}
    	
    	return otherPlayers;
    }

    
    //========================================================================================================================================================
    /**
     * getTilesToShoreUpAround method gets flooded tiles in the adjacent direction of tileName, as well as tileName if it is also flooded.
     * @param tileName
     * @return list of flooded tiles adjacent to and including if flooded, tileName
     */
    public ArrayList<TileEnum> getTilesToShoreUpAround(TileEnum tileName) {
    	ArrayList<TileEnum>  tilesPlayerCanShoreUp = new ArrayList<TileEnum>();
    	ArrayList<TileEnum>  tilesAroundPlayer = getTilesAroundTile(tileName, true);
    	
    	// Add tile player is on if can be shored up
		if(getTileWithName(tileName).getFloodStatus() == FloodStatusEnum.FLOODED) {
			tilesPlayerCanShoreUp.add(tileName);
		}
		
    	// Adding flooded tiles around player that can be shoredUp
    	tilesAroundPlayer.forEach((aTile) -> {
    		if(getTileWithName(aTile).getFloodStatus() == FloodStatusEnum.FLOODED) {
    			tilesPlayerCanShoreUp.add(aTile);
    		}
    	});

    	return tilesPlayerCanShoreUp;
    }
    
    
    /**
     * getTilesAroundTile method gets tiles immediately around a specified tile.
     * @param tileName the name of the tile.
     * @param returnAdjacent boolean determining if chosen tiles should only be adjacent. true returns adjacent tiles, false returns diagonal tiles.
     * @return ArrayList<Tile> containing the player movement direction and the corresponding tile in that direction.
     */
    public ArrayList<TileEnum>  getTilesAroundTile(TileEnum tileName, boolean returnAdjacentOnly) {
    	ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
    	ArrayList<Optional<Tile>> tilesAroundPlayer= new ArrayList<Optional<Tile>>();
    	    	
    	int[] currentPos = islandTilesNamePositionMap.get(tileName);
    	int yPos = currentPos[1];
    	int xPos = currentPos[0];
    	int[] northDetstination = {xPos, yPos-1};
    	int[] eastDetstination = {xPos+1, yPos};
    	int[] southDetstination = {xPos, yPos+1};
    	int[] westDetstination = {xPos-1, yPos};
    	
    	tilesAroundPlayer.add(getTileAtPosition(northDetstination));
		tilesAroundPlayer.add(getTileAtPosition(eastDetstination));
		tilesAroundPlayer.add(getTileAtPosition(southDetstination));
		tilesAroundPlayer.add(getTileAtPosition(westDetstination));
    	
    	if(!returnAdjacentOnly) {
    		int[] northEastDetstination = {xPos+1, yPos-1};
        	int[] southEastDetstination = {xPos+1, yPos+1};
        	int[] southWestDetstination = {xPos-1, yPos+1};
        	int[] northWestDetstination = {xPos-1, yPos-1};
        	
    		tilesAroundPlayer.add(getTileAtPosition(northEastDetstination));
    		tilesAroundPlayer.add(getTileAtPosition(southEastDetstination));
    		tilesAroundPlayer.add(getTileAtPosition(southWestDetstination));
    		tilesAroundPlayer.add(getTileAtPosition(northWestDetstination));
    	}
    	
    	tilesAroundPlayer.forEach((tileAtDirection) -> {
    		if(tileAtDirection.isPresent() && tileAtDirection.get().isSunken() == false) {
        		tilesPlayerCanMoveTo.add(tileAtDirection.get().getTileName());
        	}
    	});
   
    	return tilesPlayerCanMoveTo;
    }
    
    
    
    /**
     * getNearestTilesToTile sequential searches for the nearest unsunk tile on to
     * a particular tile on the board.
     * @param Tile the specified tile.
     * @return ArrayList<Tile> containing the player movement direction and the corresponding tile in that direction.
     */
    public ArrayList<TileEnum> getNearestTilesToTile(TileEnum tileName) {
    	ArrayList<TileEnum> tilesPlayerCanMoveTo = new ArrayList<TileEnum>();
    	Optional<Tile> tileAtDirection;
    	int xPos, yPos, xMin, yMin, xMax, yMax;
    	for(int radius = 1; radius< NUM_COLS; radius++) {
    		System.out.println("radius is now " + radius);
    		int[] currentPos = islandTilesNamePositionMap.get(tileName);
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
        		    		tilesPlayerCanMoveTo.add(tileAtDirection.get().getTileName());
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
    		if(x>1 && x <4) 
    			return true;
    	}
    	if (y== 1 || y== 4) {
    		if(x>0 && x <5) 
    			return true;
    	}
    	if (y== 2 || y== 3) {
    		if(x>=0 && x <=5) 
    			return true;
    	}
    	return false;
    }
     
    /**
     * toString method returns the map of the game.
     */
    @Override
    public String toString() {
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

    	return boardString.toString();
    }
    
    /**
     * instanciateIslandTiles instantiates individual island tiles and shuffle them.
     * @return tiles a shuffled list of all the island tiles in the game.
     */
	private ArrayList<Tile> instanciateIslandTiles() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		for (TileEnum tileName : TileEnum.values()) { 
			if(tileName == TileEnum.FOOLS_LANDING) {
				tiles.add(new FoolsLandingTile(tileName));
			} else if(tileName.hasTreasure()) {
				tiles.add(new TreasureTile(tileName));
			} else {
				tiles.add(new Tile(tileName));
			}
		}
		Collections.shuffle(tiles);
		return tiles;
	}
	
	/**
     * getIslandTiles gets a list of all the Tiles on the board.
     * @return ArrayList<Tile> containing all Tiles on board.
     */
	public ArrayList<Tile> getIslandTiles() {
		return orderedTiles;
	}
	
	/**
	 * getPlayersFromTile gets a list of all of the players on the tile.
	 * @return playersFromTile
	 * @param TileEnum tile
	 */
	public ArrayList<Player> getPlayersFromTile(TileEnum tileEnum) {
		ArrayList<Player> playersOnTile = new ArrayList<Player>();
		Tile tile = getTileWithName(tileEnum);
		playersOnTile.addAll(tile.getPlayersOnTile());
		return playersOnTile;
	}
	
	/**
	 * getAllFloodedTiles returns all tiles that are not SUNK
	 * @return ArrayList of Not Sunk tiles.
	 */
	public ArrayList<TileEnum> getUnsunkenTiles() {
		ArrayList<TileEnum> potentialTiles = new ArrayList<TileEnum>();
		for(Tile aTile : getIslandTiles()) {
			if( !aTile.isSunken() )
				potentialTiles.add(aTile.getTileName());
		}
		return potentialTiles;
	}
	
	/**
	 * getAllFloodedTiles returns all tiles that are FLOODED
	 * @return ArrayList of flooded tiles.
	 */
	public ArrayList<TileEnum> getAllFloodedTiles() {
		ArrayList<TileEnum> potentialTiles = new ArrayList<TileEnum>();
		for(Tile aTile : getIslandTiles()) {
			if(aTile.getFloodStatus() == FloodStatusEnum.FLOODED)
				potentialTiles.add(aTile.getTileName());
		}
		return potentialTiles;
	}
	
	
	//===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    
    public void destroy() {
    	board = null;
    }

	
	

}
