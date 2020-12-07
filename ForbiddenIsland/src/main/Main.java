package main;

import fi.game.GameManager;
import gameManager.GetInput;
import setup.MainSetup;

public class Main {

	public static void main(String[] args) {
		new MainSetup();
		GameManager.getInstance().playGame();
		GetInput.getInstance().endInput();
	}

}
