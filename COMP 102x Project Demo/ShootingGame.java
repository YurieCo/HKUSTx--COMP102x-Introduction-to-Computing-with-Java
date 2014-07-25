import comp102x.assignment.GameController;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameModel;
import comp102x.assignment.GameView;

public class ShootingGame {
	
	public static void main(String[] args) {
		
		/** Creates the important logic of the game. **/
		GameLogic studentLogic = new StudentLogic();
		
		/** Creates the model of the game. **/
		GameModel gameModel = new GameModel(studentLogic);
		
		/** Creates the view of the game. **/
		GameView gameView = new GameView();
		
		/** Creates the controller of the game. **/
		GameController gameController = new GameController(gameModel, gameView, studentLogic);
		
		/** 
		 * Adds the game controller as a listener to mouse click events from the game view. 
		 **/
		gameView.addMouseListener(gameController);
		
		/**
		 * Adds the game controller as a listener to mouse move events from the game view.
		 **/
		gameView.addMouseMotionListener(gameController);
		
		/** Starts the game **/
		gameController.startGame();
		
		/** 
		 * The main game loop for waiting events. 
		 * UPDATED: Using a while loop for the game loop -> calling a method in gameController for the game loop in order to avoid busy waiting. 
		 **/
		gameController.waitForGameLoopToEnd();
	}
}
