package main;

import gameManager.GameManager;
import getInput.GetInput;
import setup.MainSetup;

public class Main {

	public static void main(String[] args) {
		new MainSetup();
		GameManager.getInstance().playersGo();
		GetInput.getInstance().endInput();
	}

}
