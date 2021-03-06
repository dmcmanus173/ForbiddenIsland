package fi.gameView;

import fi.board.Board;
import fi.enums.TileEnum;
import fi.game.GameOverObserver;
import fi.game.GetInput;
import fi.treasures.TreasureManager;
import fi.watermeter.WaterMeter;

public class GameView {
	//===========================================================
	// Variable Setup
	//===========================================================
	private static GameView gameView;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of TreasureDeck
     * @return treasureDeck. singleton TreasureDeck object.
     */
    public static GameView getInstance(){
        if(gameView == null)
            gameView = new GameView();
        return gameView;
    }
    
	//===========================================================
    // Constructor
    //===========================================================
    /*
     * Constructor for GetInput
     */
    private GameView() {
    	
    }
    
    public void welcome() {
		StringBuilder temp = new StringBuilder("");
		temp.append("     ▄████████  ▄██████▄     ▄████████ ▀█████████▄   ▄█  ████████▄  ████████▄     ▄████████ ███▄▄▄▄     ");
		temp.append("\n    ███    ███ ███    ███   ███    ███   ███    ███ ███  ███   ▀███ ███   ▀███   ███    ███ ███▀▀▀██▄ ");
		temp.append("\n    ███    █▀  ███    ███   ███    ███   ███    ███ ███▌ ███    ███ ███    ███   ███    █▀  ███   ███ ");
		temp.append("\n   ▄███▄▄▄     ███    ███  ▄███▄▄▄▄██▀  ▄███▄▄▄██▀  ███▌ ███    ███ ███    ███  ▄███▄▄▄     ███   ███ ");
		temp.append("\n  ▀▀███▀▀▀     ███    ███ ▀▀███▀▀▀▀▀   ▀▀███▀▀▀██▄  ███▌ ███    ███ ███    ███ ▀▀███▀▀▀     ███   ███ ");
		temp.append("\n    ███        ███    ███ ▀███████████   ███    ██▄ ███  ███    ███ ███    ███   ███    █▄  ███   ███ ");
		temp.append("\n    ███        ███    ███   ███    ███   ███    ███ ███  ███   ▄███ ███   ▄███   ███    ███ ███   ███ ");
		temp.append("\n    ███         ▀██████▀    ███    ███ ▄█████████▀  █▀   ████████▀  ████████▀    ██████████  ▀█   █▀  ");
		temp.append("\n                            ███    ███                                                                ");
		temp.append("\n                       ▄█     ▄████████  ▄█          ▄████████ ███▄▄▄▄   ████████▄                    ");
		temp.append("\n                      ███    ███    ███ ███         ███    ███ ███▀▀▀██▄ ███   ▀███                   ");
		temp.append("\n                      ███▌   ███    █▀  ███         ███    ███ ███   ███ ███    ███                   ");
		temp.append("\n                      ███▌   ███        ███         ███    ███ ███   ███ ███    ███                   ");
		temp.append("\n                      ███▌ ▀███████████ ███       ▀███████████ ███   ███ ███    ███                   ");
		temp.append("\n                      ███           ███ ███         ███    ███ ███   ███ ███    ███                   ");
		temp.append("\n                      ███     ▄█    ███ ███▌    ▄   ███    ███ ███   ███ ███   ▄███                   ");
		temp.append("\n                      █▀    ▄████████▀  █████▄▄██   ███    █▀   ▀█   █▀  ████████▀                    ");
		temp.append("\n                                        ▀                                                             ");
		temp.append("\n"																									  );
		System.out.println(temp);
    }
    
    
	
	//===========================================================
    // Treasure stuff
    //===========================================================
	
	public void treasureHasSunk() {
		System.out.println("An uncollected Treasure has sunk!");
	}
	
	public void claimedAllTreasures() {
		System.out.println("All treasures have now been collected. Everyone should make their way to Fool's Landing to escape with a Helicopter Lift Card!");
	}
	
	public void getRemainingNumberTreasures() {
		System.out.println("Only "+ TreasureManager.getInstance().getNumOfRemainingTreasuresToCollect() + " more treasures to collect before everyone can escape the island.");
	}
	
	//===========================================================
    // Tile stuff
    //===========================================================
	public void noFloodedTiles() {
		System.out.println("There are no tiles that you can shore up!");
	}
	
	public void changeFloodStatus(TileEnum tileEnum) {
		Board board = Board.getInstance();
		System.out.println(tileEnum.toString()+" is now "+board.getTileWithName(tileEnum).getFloodStatus().toString()+"!");
	}
	
	//===========================================================
    // WaterMeter
    //===========================================================
	public void waterMeterSetup() {
		WaterMeter waterMeter = WaterMeter.getInstance();
		int minLevel = waterMeter.getMinLevel();
		int maxLevel = waterMeter.getMaxLevel();
		System.out.println("Choose a number to set the WaterMeter Level ("+minLevel+"-"+maxLevel+") to:");
		int chosenLevel = GetInput.getInstance().anInteger(minLevel, maxLevel);
		waterMeter.setCurrentLevel(chosenLevel);
		System.out.println("WaterMeter has been set. Level set to "+waterMeter.getCurrentLevel()+".\n");
	}
	
	public void increasedWaterMeter() {
		WaterMeter waterMeter = WaterMeter.getInstance();
		System.out.println("The water meter has been increased to " + waterMeter.getCurrentLevel() + ". All players will now have to take " +  waterMeter.getCurrentLevel() + " FloodCards at the end of their go!");
	}
		
	//===========================================================
    // Other
    //===========================================================
	/**
	 * gameOver prints the gameOver state to the console.
	 */
	public void gameOver() {
		System.out.println(GameOverObserver.getInstance().toString());
	}
	
	/**
	 * showClaimedTreasures method will show what/number of treasures been claimed so far.
	 */
	public void showClaimedTreasures() {
		TreasureManager treasureManager = TreasureManager.getInstance();
		System.out.println(treasureManager.toString());
	}
	
	/**
	 * showMap method will call the Board for a copy of the board map, then print it to the console.
	 */
	public void showMap() {
		Board board = Board.getInstance();
		System.out.println(board.toString());
	}
	
	public void allHandsAreFull() {
		System.out.println("Everyone's hand is full. Cannot give card.");
	}
    
	public void printNewLine() {
		System.out.println();
	}

}
