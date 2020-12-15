package fi.board;

import java.util.HashSet;
import java.util.Set;

import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.players.Player;



/**
 * Abstract class for tiles on the board in the Forbidden Island Game.
 * @author Demi Oke and Daniel McManus
 * @date 04/11/2020
 * @version 0.1
 *
 */

public class Tile {
	//===========================================================
    // Variable Setup
    //===========================================================
	protected TileEnum tileName;
	protected FloodStatusEnum floodStatus;
	protected Set<Player> playersOnTile;
	
	//===========================================================
    // Constructor
    //===========================================================
    /**
     * Constructor for a tile object.
     * @param tileName     Name of Tile
     * @param floodStatus  The status of Tile i.e. flooded, not flooded or sunken
     */
	public Tile(TileEnum tileName) {
		this.tileName = tileName;
		this.floodStatus = FloodStatusEnum.NOT_FLOODED;
		this.playersOnTile = new HashSet<Player>();
	}
	
	//===========================================================
    // Public Getters
    //===========================================================
    /**
     * getter to return the name the Tile
     * @return the name of the Tile.
     */
	public TileEnum getTileName() {
		return this.tileName;
	}
	
	/**
    * getter to return the flood status Tile
    * @return the flood status of the Tile.
    */
	public FloodStatusEnum getFloodStatus() {
		return this.floodStatus;
	}
	
	/**
    * getter to return the players on a tile
    * @return the flood status of the Tile.
    */
	public Set<Player> getPlayersOnTile() {
		return playersOnTile;
	}
	
	/**
    * addPlayerToTile method adds a player to the set of players on tile.
    * @param Player player that is to be added to tile.
    */
	public void addPlayerToTile(Player player) {
		if(!playersOnTile.contains(player)) playersOnTile.add(player);
	    else throw new RuntimeException("Cannot add player to a tile they already exist on.");
		
	}
	
	
	/**
    * addPlayerToTile method removes a player to the set of players on tile.
    * @param Player player that is to be removed to tile.
    */
	public void removePlayerFromTile(Player player) {
		if(playersOnTile.contains(player)) playersOnTile.remove(player);
		else throw new RuntimeException("Cannot remove player from tile. Player does not exist on tile");
		
	}
	
	/**
    * checks to see if tile is sunken
    * @return true if tile is sunken or false if not.
    */
	public boolean isSunken() {
		if(floodStatus == FloodStatusEnum.SUNKEN)
			return true;
		return false;
	}
	
	/**
    * shoreUp method shores up a tile only if it has been flooded.
    * @return true if tile is has been shored up.
    */
	public FloodStatusEnum shoreUp() {
		if(floodStatus == FloodStatusEnum.FLOODED) {
			floodStatus = FloodStatusEnum.NOT_FLOODED;
			return FloodStatusEnum.NOT_FLOODED;
		}
		else if(floodStatus == FloodStatusEnum.SUNKEN)
			return FloodStatusEnum.SUNKEN;
		else
			return FloodStatusEnum.FLOODED;
		
	}
	
	/**
    * flood method floods a tile if it is shored up and sinks a tile if it is flooded.
    */
	public FloodStatusEnum flood() {
		if(floodStatus == FloodStatusEnum.NOT_FLOODED) {
			floodStatus = FloodStatusEnum.FLOODED;
			return FloodStatusEnum.FLOODED;
		}
		else if(floodStatus == FloodStatusEnum.FLOODED) {
			floodStatus = FloodStatusEnum.SUNKEN;
			return FloodStatusEnum.SUNKEN;
		}
		//TODO: TALK ABOUT THIS WITH DANIEL.
		else throw new RuntimeException("Attempting to flood sunken tile.");
	}
	
	/**
    * toString method returns relevant information about tile
    * that will be displayed to players during the game.
    * @return Strings tile info.
    */
	@Override
	public String toString() {
		StringBuilder tileString = new StringBuilder("");
		tileString.append(tileName);
		if(floodStatus == FloodStatusEnum.FLOODED) {
			tileString.append("(" + floodStatus + "). ");
		} else {
			tileString.append(". ");
		}
		if(playersOnTile.isEmpty()) return tileString.toString();
		
		tileString.append("\n\t");
		tileString.append("Other players here: ");
		for (Player player : playersOnTile) { 
			tileString.append("The "+ player.getRole() + ", ");
		}
		return tileString.toString();
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
