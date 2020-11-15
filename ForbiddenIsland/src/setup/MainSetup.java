package setup;

import board.Board;
import gameComponents.FloodDeck;
import gameComponents.TreasureDeck;
import gameComponents.WaterMeter;

/**
 * Class to setup instance of game Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    01/11/2020
 * @version 0.1
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
		//Scanner inputScanner = new Scanner(System.in);
		
		new PlayerSetup();
		
		// Create singletons
		Board.getInstance().printBoard();
		WaterMeter.getInstance();
		FloodDeck.getInstance();
		TreasureDeck.getInstance();
	}
	
	/**
	 * Welcome function prints welcome to game screen.
	 */
	private void Welcome() {
		StringBuilder temp = new StringBuilder("");
		temp.append("     ▄████████  ▄██████▄     ▄████████ ▀█████████▄   ▄█  ████████▄  ████████▄     ▄████████ ███▄▄▄▄   "
				  + "\n    ███    ███ ███    ███   ███    ███   ███    ███ ███  ███   ▀███ ███   ▀███   ███    ███ ███▀▀▀██▄ "
				  + "\n    ███    █▀  ███    ███   ███    ███   ███    ███ ███▌ ███    ███ ███    ███   ███    █▀  ███   ███ "
				  + "\n   ▄███▄▄▄     ███    ███  ▄███▄▄▄▄██▀  ▄███▄▄▄██▀  ███▌ ███    ███ ███    ███  ▄███▄▄▄     ███   ███ "
				  + "\n  ▀▀███▀▀▀     ███    ███ ▀▀███▀▀▀▀▀   ▀▀███▀▀▀██▄  ███▌ ███    ███ ███    ███ ▀▀███▀▀▀     ███   ███ "
				  + "\n    ███        ███    ███ ▀███████████   ███    ██▄ ███  ███    ███ ███    ███   ███    █▄  ███   ███ "
				  + "\n    ███        ███    ███   ███    ███   ███    ███ ███  ███   ▄███ ███   ▄███   ███    ███ ███   ███ "
				  + "\n    ███         ▀██████▀    ███    ███ ▄█████████▀  █▀   ████████▀  ████████▀    ██████████  ▀█   █▀  "
				  + "\n                            ███    ███                                                                "
				  + "\n                       ▄█     ▄████████  ▄█          ▄████████ ███▄▄▄▄   ████████▄                    "
				  + "\n                      ███    ███    ███ ███         ███    ███ ███▀▀▀██▄ ███   ▀███                   "
				  + "\n                      ███▌   ███    █▀  ███         ███    ███ ███   ███ ███    ███                   "
				  + "\n                      ███▌   ███        ███         ███    ███ ███   ███ ███    ███                   "
				  + "\n                      ███▌ ▀███████████ ███       ▀███████████ ███   ███ ███    ███                   "
				  + "\n                      ███           ███ ███         ███    ███ ███   ███ ███    ███                   "
				  + "\n                      ███     ▄█    ███ ███▌    ▄   ███    ███ ███   ███ ███   ▄███                   "
				  + "\n                      █▀    ▄████████▀  █████▄▄██   ███    █▀   ▀█   █▀  ████████▀                    "
				  + "\n                                        ▀                                                             "
				  + "\n");
		System.out.println(temp);
	}

}
