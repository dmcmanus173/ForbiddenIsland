package roles;

import java.util.ArrayList;

import board.Board;
import board.Tile;
import enums.AdventurerEnum;
import enums.TileEnum;
import player.Player;
import player.Players;

/**
 * Class for a Player Role Messenger in Forbidden Island
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    13/11/2020
 * @version 0.1
 */
public class Messenger extends Role {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for Messenger
	 */
	public Messenger() {
		super(AdventurerEnum.MESSENGER);
	}
	
	//===========================================================
	// Other Functions
	//===========================================================
	/**
	 * startPosition method will return the tile that a player is to start on.
	 * @return Tile startPosition, where the player for a given role should start the game.
	 */
	public Tile startPosition() {
		// Messenger - Silver Gate
		TileEnum tileName = TileEnum.SILVER_GATE;
		Tile location = null;
		
		if ( !tileName.equals(null) ) {
			ArrayList<Tile> tiles = Board.getInstance().getIslandTiles();
			for(Tile tile : tiles) {
				if( tile.getTileName().equals(tileName) )
					location = tile;
			}
		}
		return location;
	}
	
	/**
	 * getPlayersForGiveTreasureCard will get a list of players suitable of giving a card to.
	 * @param thisPlayer, reference to the player calling this function
	 * @param location, the Tile location the player is  on.
	 * @param int MAX_TREASURE_CARDS, the Maximum number of treasure cards a player may have.
	 */
	@Override
	public ArrayList<Player> getPlayersForGiveTreasureCard(Player thisPlayer, Tile location, int MAX_TREASURE_CARDS){
		ArrayList<Player> potentialPlayers = new ArrayList<Player>();
		System.out.println("This player is the Messenger. They can give a card to anyone.");
		ArrayList<Player> allPlayers = Players.getInstance().getPlayers();
		for(Player aPlayer : allPlayers) {
			if( (aPlayer.getNumTreasureCards() < MAX_TREASURE_CARDS) && (thisPlayer != aPlayer))
				potentialPlayers.add(aPlayer);
		}
		return potentialPlayers;
	}

}
