package roles;

import java.util.ArrayList;

import board.Board;
import board.Tile;
import enums.AdventurerEnum;
import player.Player;

/**
 * Class for a Player Role in Forbidden Island
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
abstract public class Role {
	
	//===========================================================
	// Variable Setup
	//===========================================================
	private AdventurerEnum role;
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Role object.
	 * @param AdventurerEnum role, role of a player.
	 */
	public Role(AdventurerEnum role) {
		this.role = role;
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * getRole method will return role type.
	 * @return AdventurerEnum role.
	 */
	public AdventurerEnum getRole() {
		return role;
	}
	
	/**
	 * startPosition will return the Tile that a player of type role is to start the game on.
	 */
	abstract public Tile startPosition();
	
	/**
	 * getPlayersForGiveTreasureCard will get a list of players suitable of giving a card to.
	 * @param thisPlayer, reference to the player calling this function
	 * @param location, the Tile location the player is  on.
	 * @param int MAX_TREASURE_CARDS, the Maximum number of treasure cards a player may have.
	 */
	public ArrayList<Player> getPlayersForGiveTreasureCard(Player thisPlayer, Tile location, int MAX_TREASURE_CARDS){
		ArrayList<Player> potentialPlayers = new ArrayList<Player>();
		System.out.println("This player can only give cards to who is on their tile.");
		ArrayList<Player> tilePlayers = new ArrayList<>(location.getPlayersOnTile());
		for(Player aPlayer : tilePlayers) {
			if( (aPlayer.getNumTreasureCards() < MAX_TREASURE_CARDS) && (thisPlayer != aPlayer))
				potentialPlayers.add(aPlayer);
		}
		return potentialPlayers;
	}
	
	/**
	 * getTilesForIfOnSunk method gets the tiles that a player can move to if they're on a sunken tile.
	 * @param location, the Tile location the player is  on.
	 */
	public ArrayList<Tile> getTilesForIfOnSunk(Tile location) {
		ArrayList<Tile> potentialTiles = new ArrayList<>();
		potentialTiles.addAll( Board.getInstance().getTilesAroundTile(location, true) );
		return potentialTiles;
	}
	
	/**
	 * shoreUp method will call the shoreUpContent function with the correct options dependent on the role calling it.
	 * @return Boolean true if a tile has been shoredUp.
	 */
	public Boolean shoreUp(Player aPlayer) {
		return aPlayer.shoreUpContent();
	}
	
	/**
	 * toString method returns a String describing the Role class.
	 * @return String containing information regarding Role class.
	 */
	public String toString() {
		StringBuilder string = new StringBuilder("");
		string.append(role.toString());
		return string.toString();
	}
}
