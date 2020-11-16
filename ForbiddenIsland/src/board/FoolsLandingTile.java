package board;

import enums.FloodStatusEnum;
import enums.TileEnum;
import gameManager.GameManager;
import player.Player;
import player.Players;
import player.Rucksack;

public class FoolsLandingTile extends Tile {

	public FoolsLandingTile(TileEnum tileName) {
		super(tileName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
    * addPlayerToTile method adds a player to the set of players on tile.
    * Also checks to see if a winning condition is met.
    * @param Player player that is to be added to tile.
    */
	@Override
	public void addPlayerToTile(Player player) {
		int totalNumOfPlayers = Players.getInstance().getNumPlayers();
		super.addPlayerToTile(player);
		if(playersOnTile.size() ==  totalNumOfPlayers && Rucksack.getInstance().gottenAllTreasures()) {
//			for(Player playerOnTile: playersOnTile) {
//				if(playerOnTile.move()) {
//					GameManager.getInstance().gameWon();
//				}
//			}
		}
		
	}
	
	
	/**
    * flood method floods a tile if it is shored up and sinks a tile if it is flooded.
    */
	@Override
	public void flood() {
		if(floodStatus == FloodStatusEnum.FLOODED) {
			GameManager.getInstance().gameOver();
		} else {
			super.flood();
		}
	}

}
