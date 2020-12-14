package fi.setup;

import fi.board.Board;
import fi.cards.FloodDeck;
import fi.game.GameManager;
import fi.game.GameOverObserver;
import fi.game.GetInput;
import fi.watermeter.*;
import fi.cards.TreasureDeck;

/**
 * Class to setup instance of game Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    07/12/2020
 * @version 0.2
 */
@SuppressWarnings("unused")
public class MainSetup {
	
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for MainSetup. Calls all setup classes.
	 */
	public MainSetup() {
		// Welcome message
		welcome();
		
		// Get Input setup 
		getInputSetup();
		
		// GameOverObserver implemented in WaterMeter, TreasureTile (Board), FoolsLandingTile (Board), GameManager
		gameOverObserverSetup();
		
		// Water Meter Setup needs getInput
		waterMeterSetup();
		
		// Flood Deck needs WaterMeter setup to work
		floodDeckSetup();
		
		// Board Setup
		boardSetup();
		
		// Treasure Deck Setup
		treasureDeckSetup();
		
		// Players Need Board,GetInput,TreasureDeck to work
		new PlayerSetup();
		
		// GameManager needs players, FloodDeck to work
		gameManagerSetup();
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
	 * GetInput setup
	 */
	private void getInputSetup() {
		
		GetInput getInput = GetInput.getInstance();
	}
	
	private void gameOverObserverSetup() {
		GameOverObserver gameOverObserver = GameOverObserver.getInstance();
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
	
	/**
	 * FloodDeck setup
	 */
	private void floodDeckSetup() {
		FloodDeck floodDeck = FloodDeck.getInstance();
	}
	
	/**
	 * Board setup
	 */
	private void boardSetup() {
		Board board = Board.getInstance();
	}
	
	/**
	 * TreasureDeck setup
	 */
	private void treasureDeckSetup() {
		TreasureDeck treasureDeck = TreasureDeck.getInstance();
	}
	
	/**
	 * GameManager setup
	 */
	private void gameManagerSetup() {
		GameManager gameManager = GameManager.getInstance();
	}
	
}
