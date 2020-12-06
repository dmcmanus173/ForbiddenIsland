package setup;

import fi.board.Board;
import board.FloodDeck;
import gameManager.GetInput;
import fi.watermeter.*;
import fi.cards.TreasureDeck;

/**
 * Class to setup instance of game Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    06/12/2020
 * @version 0.2
 */
public class MainSetup {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for MainSetup. Calls all setup classes.
	 */
	public MainSetup() {
		welcome();
		waterMeterSetup();
		new PlayerSetup();
		
		FloodDeck.getInstance();
		Board.getInstance().printBoard();
		TreasureDeck.getInstance();
	}
	
	/**
	 * Welcome function prints welcome to game screen.
	 */
	private void welcome() {
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
	
	/**
	 * WaterMeter Setup
	 */
	private void waterMeterSetup() {
		WaterMeter waterMeter = WaterMeter.getInstance();
		int minLevel = waterMeter.getMinLevel();
		int maxLevel = waterMeter.getMaxLevel();
		System.out.println("Choose a number to set the WaterMeter Level ("+minLevel+"-"+maxLevel+") to:");
		int chosenLevel = GetInput.getInstance().anInteger(minLevel, maxLevel);
		waterMeter.setCurrentLevel(chosenLevel);
		System.out.println("WaterMeter has been set. Level set to "+waterMeter.getCurrentLevel()+".\n");
	}

}
