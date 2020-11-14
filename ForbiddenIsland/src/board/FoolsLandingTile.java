package board;

import enums.TileEnum;
import player.Player;
import player.Players;
import player.Rucksack;

public class FoolsLandingTile extends Tile {

	public FoolsLandingTile(TileEnum tileName) {
		super(tileName);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public void addPlayerToTile(Player player) {
		int totalNumOfPlayers = Players.getInstance().getNumPlayers();
		super.addPlayerToTile(player);
		if(playersOnTile.size() ==  totalNumOfPlayers && Rucksack.getInstance().gottenAllTreasures()) {
			//TODO: loop through players to find if players has card.
		}
		
	}

}
