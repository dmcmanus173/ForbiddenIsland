package fi.main;

import fi.game.GameManager;
import fi.game.GetInput;
import fi.setup.MainSetup;

public class Main {

	public static void main(String[] args) {
		new MainSetup();
		GameManager.getInstance().playGame();
		GetInput.getInstance().endInput();
	}

}
