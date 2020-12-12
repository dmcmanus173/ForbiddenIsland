package fi.game;

import java.util.ArrayList;

import fi.enums.AdventurerEnum;
import fi.enums.FloodStatusEnum;
import fi.enums.TileEnum;
import fi.gameView.GameView;
import fi.playerController.DiverController;
import fi.playerController.EngineerController;
import fi.playerController.ExplorerController;
import fi.playerController.MessengerController;
import fi.playerController.NavigatorController;
import fi.playerController.PilotController;
import fi.playerController.PlayerController;
import fi.board.Board;
import fi.cards.FloodDeck;
import fi.players.Player;
import fi.players.Players;

/**
 * Class for GameManager of Forbidden Island.
 * 
 * @author  Demi Oke and Daniel McManus
 * @date    07/12/2020
 * @version 0.1
 */
public class GameManager {
	//===========================================================
	// Variable Setup
	//===========================================================
	private static GameManager gameManager;
	private ArrayList<PlayerController> players = new ArrayList<PlayerController>();
	private int playersGoPos;
	
	//===========================================================
    // Get Instance of Singleton
    //===========================================================
    /**
     * getInstance method returns single instance of GameManager.
     * @return GameManager. singleton GameManager object.
     */
    public static GameManager getInstance(){
        if(gameManager == null)
            gameManager = new GameManager();
        return gameManager;
    }
    
	//===========================================================
	// Constructor
	//===========================================================
	/**
	 * Constructor for GameManager object.
	 */
    private GameManager() {
    	playersGoPos = 0; // Player 1 to take first go.
    	gameSetup();
    }
    
    /**
     * Sets up playerGo for each player. Puts players on board.
     * Changes drawn tiles to flooded side.
     */
    private void gameSetup() {
    	Board board = Board.getInstance();
    	FloodDeck floodDeck = FloodDeck.getInstance();
    	
    	// Setting up players
    	ArrayList<Player> players = Players.getInstance().getPlayers();
    	for(Player aPlayer : players) {
    		board.setUpPlayerOnBoard(aPlayer);
    		if(aPlayer.getRole() == AdventurerEnum.DIVER) {
    			DiverController diverPlayerController = new DiverController(aPlayer);
    			this.players.add(diverPlayerController);
    		}
    		else if(aPlayer.getRole() == AdventurerEnum.ENGINEER) {
    			EngineerController engineerPlayerController = new EngineerController(aPlayer);
    			this.players.add(engineerPlayerController);
    		}
    		else if(aPlayer.getRole() == AdventurerEnum.EXPLORER) {
    			ExplorerController explorerPlayerController = new ExplorerController(aPlayer);
    			this.players.add(explorerPlayerController);
    		}
    		else if(aPlayer.getRole() == AdventurerEnum.MESSENGER) {
    			MessengerController messengerPlayerController = new MessengerController(aPlayer);
    			this.players.add(messengerPlayerController);
    		}
    		else if(aPlayer.getRole() == AdventurerEnum.NAVIGATOR) {
    			NavigatorController navigatorPlayerController = new NavigatorController(aPlayer);
    			this.players.add(navigatorPlayerController);
    		}
    		else if(aPlayer.getRole() == AdventurerEnum.PILOT) {
    			PilotController pilotPlayerController = new PilotController(aPlayer);
    			this.players.add(pilotPlayerController);
    		}
    		else
    			throw new RuntimeException("Can't find AdventurerEnum respective Controller.");
    	}
    	
    	// Setting up board
    	ArrayList<TileEnum> tilesToFlood = floodDeck.getTilesToFlood(true);
    	tilesToFlood.forEach(tileEnum -> {
    		if(board.getTileWithName(tileEnum).getFloodStatus() == FloodStatusEnum.NOT_FLOODED) {
    			board.floodTile(tileEnum);
    			GameView.getInstance().changeFloodStatus(tileEnum);
    		}
    	});
    	System.out.println();
    }
    
    /**
     * nextPlayersGo method gets the Player number for the next Player to take a turn.
     */
    private void nextPlayersGoPos() {
    	if(playersGoPos == players.size()-1)
    		playersGoPos = 0;
    	else
    		playersGoPos += 1;
    }
    
    /**
     * playersGo will make players keep taking their turns.
     * A loop which will run until there are no more goes for the group of players to take!
     */
    public void playGame() {
    	PlayerController playerToGo = players.get(playersGoPos); 
    	while( !playerToGo.doRound() ) {
    		nextPlayersGoPos();
    		playerToGo = players.get(playersGoPos);
    	}
    }
    
}