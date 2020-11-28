package setup;

import board.Board;
import board.FloodDeck;
import otherComponents.WaterMeter;
import treasureCards.TreasureDeck;

/**
 * Class to setup instance of game Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    17/11/2020
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
		Welcome();
		WaterMeter.getInstance();
		new PlayerSetup();
		FloodDeck.getInstance();
		Board.getInstance().printBoard();
		TreasureDeck.getInstance();
	}
	
	/**
	 * Welcome function prints welcome to game screen.
	 */
	private void Welcome() {
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

}
