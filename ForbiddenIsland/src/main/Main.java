package main;

import gameManager.gameManager;
import getInput.GetInput;
import setup.MainSetup;

public class Main {

	public static void main(String[] args) {
		MainSetup setup = new MainSetup();
		gameManager.getInstance().playersGo();
		
		GetInput.getInstance().endInput();
	}

}
