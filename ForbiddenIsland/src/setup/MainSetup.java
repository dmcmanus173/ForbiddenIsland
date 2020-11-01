package setup;

import java.util.Scanner;

public class MainSetup {
	
	public MainSetup(Scanner user) {
		Welcome();
		PlayerSetup players = new PlayerSetup(user);
	}
	
	
	public void Welcome() {
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
