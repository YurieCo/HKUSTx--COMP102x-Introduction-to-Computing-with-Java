package comp102x.assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

import comp102x.ColorImage;
import comp102x.IO;

/**
 * The GameController class represents the game logic of the shooting game.
 */
public class GameController implements MouseListener, MouseMotionListener, ActionListener {

	/** Parameter for debug mode. */
	public static final boolean IS_DEBUG = false;

	/** Defining the different types of sounds for playing when an event occurs. */
	private static final int FIRE_SOUND = 0;
	private static final int HIT_SOUND = 1;
	private static final int END_SOUND = 2;

	/**
	 * Defining a thread pool for executing certain parts of the game logic, e.g. shooting.
	 */
	public static final ExecutorService threadPool = Executors.newSingleThreadExecutor();

	/** The timer for updating the time left for the game. */
	private Timer gameTimer;

	/** The timer for updating the goal's position. */
	private Timer goalTimer;

	/** Indicates whether there is a shooting event occurring. */
	private boolean shooting;

	/** Indicated whether the game is playing or not. */
	private boolean playing;

	/** The game model object to be controlled. */
	private GameModel gameModel;

	/** The game view object to be controlled. */
	private GameView gameView;

	/**
	 * The logic implemented by students to handling the saving and outputting
	 * of highscore table.
	 */
	private GameLogic studentLogic;

	/**
	 * Constructs a game controller.
	 * 
	 * @param gameModel the game model to be controlled.
	 * @param gameView the game view to be controlled.
	 * @param studentLogic the delegated logic implemented by students.
	 */
	public GameController(GameModel gameModel, GameView gameView, GameLogic studentLogic) {

		// initialization of various parameters.

		this.gameModel = gameModel;
		this.gameView = gameView;
		this.studentLogic = studentLogic;

		this.gameTimer = new Timer(1000, this); // setup the timer for counting down, adding this controller object as the listener.
		this.gameTimer.setActionCommand("decreaseTime"); // setup the action command for this timer, for distinguishing the suitable action to perform when the event occurs.

		this.goalTimer = new Timer(GameModel.GOAL_UPDATE_INTERVAL, this); // setup the timer for updating the goals, adding this controller object as the listener.
		this.goalTimer.setActionCommand("moveGoals"); // setup the action command for this timer, for distinguishing the suitable action to perform when the event occurs.

		this.shooting = false;
		this.playing = false;
	}

	/**
	 * Starts the game.
	 * 
	 * This method asks the user to choose a suitable level, updates the game
	 * model and game view accordingly and starts the timers.
	 */
	public void startGame() {

		IO.output("Please choose the level of difficulty [1-3]: ");
		int level = IO.inputInteger();

		gameModel.setLevel(level);
		gameModel.initializeGoalArray();

		gameView.showStartScreen();
		gameView.showStartGameScreen();
		gameView.updateFootballImage();
		
		gameView.updateTime(GameModel.TIME_ALLOWED);
		gameView.updateScore(gameModel.getScore());
		gameView.showGameScreen();
		
		playing = true;
		aim(500, 400);

		gameTimer.start();
		goalTimer.start();
	}

	/**
	 * Ends the game.
	 * 
	 * This method rounds up the game by updating the game model and game view
	 * accordingly and stopping the timers. Records of the game will also be
	 * shown.
	 */
	private void endGame() {

		threadPool.execute(new Runnable() {

			@Override
			public void run() {

				pause(1000);

				gameTimer.stop();
				goalTimer.stop();

				gameView.showEndGameScreen();
				playSound(END_SOUND);

				updateHighScoreRecords();
				outputHighScoreTable();

				endGameLoop();
			}
		});
	}

	/**
	 * Aims to a goal by specifying the mouse position.
	 * 
	 * This method aims to the goal by updating both the graphical
	 * representation and the internal data of the arrow. If shooting is not
	 * currently in progress, the graphical representation and the internal data
	 * of the football.
	 * 
	 * @param x the x position of the mouse.
	 * @param y the y position of the mouse.
	 */
	private void aim(int x, int y) {

		if (!playing)
			return;

		gameView.updateArrowImage(x, y);
		gameModel.updateArrowData(x, y);

		if (!shooting)
			updateFootball();
	}

	/**
	 * Updates the graphical representation and the internal date of the football.
	 */
	private void updateFootball() {
		
		gameView.updateFootballImage();

		Arrow arrow = gameModel.getArrow();

		int initialX = gameView.getloadedFootballX();
		int initialY = gameView.getLoadedFootballY();
		int finalX = gameView.calculateFinalFootballX(arrow.getPan(), GameModel.ARROW_MIN_PAN_ANGLE, GameModel.ARROW_MAX_PAN_ANGLE);
		int finalY = gameView.calculateFinalFootballY(arrow.getTilt(), GameModel.ARROW_MIN_TILT_ANGLE, GameModel.ARROW_MAX_TILT_ANGLE);

		gameModel.updateFootballData(initialX, finalX, initialY, finalY);
	}

	/**
	 * Shoots the football and check if there is goal hit.
	 * 
	 * This method plays the shooting animation and checks if any goal is hit
	 * after shooting. It will update both the graphics and data and finally it
	 * will check if the end game condition of all goals being hit is
	 * attained.
	 */
	private void shoot() {
		
		if (!playing || shooting)
			return;

		shooting = true;

		Runnable r = new Runnable() {

			public void run() {

				playSound(FIRE_SOUND);
				int currentStep = 0;
				ColorImage image = null;
				int rotationAngle = 0;

				// playing the shooting animation by looping
				// ----------------------------------------------------
				while (currentStep <= GameModel.ANIMATION_STEPS) {

					image = gameModel.generateIntermediateFootballImage(currentStep, rotationAngle);
					
					// for avoiding null pointer exception in case the implementation of the StudentLogic class has not been completed yet.
					if (image == null)
						image = new ColorImage(1, 1);
					
					gameView.replaceFootballImage(image);
					currentStep++;
					rotationAngle += 10;
					pause(GameModel.FOOTBALL_UPDATE_INTERVAL);
				}
				// ----------------------------------------------------

				gameView.hideFootballImage();

				// checks if there are any goal being hit.
				// ----------------------------------------------------
				int[] hitCoordinates = gameModel.updateHit(image, gameView.getGoalImageWidth(), gameView.getGoalImageHeight(), GameView.MARGIN);
				// ----------------------------------------------------

				// performs update internally and graphically if there is a hit on a goal.
				// ----------------------------------------------------
				if (hitCoordinates != null) {

					gameModel.incrementScore();
					gameModel.decrementRemaningGoals();
					gameView.showExplosionImage(hitCoordinates[0], hitCoordinates[1]);
					gameView.updateGoalImages(gameModel.getGoals());
					gameView.updateScore(gameModel.getScore());
					playSound(HIT_SOUND);
					pause(GameModel.SHOW_EXPLOSION_DURATION);
					gameView.hideExplosionImage();
				}
				// ----------------------------------------------------

				shooting = false;

				// checks if all the goal are already being hit. if yes, ends the game. otherwise, reset the football
				// ----------------------------------------------------
				if (!playing || gameModel.getRemaining() == 0) {

					playing = false;
					endGame();
				}

				else {

					gameView.resetFootballImage();
					updateFootball();
				}
				// ----------------------------------------------------
			}
		};

		// another thread is used to execute the main part of this method,
		// otherwise, the intermediate updates of the graphics will not be
		// visible, making no animation effect.  
		threadPool.execute(r); 
	}

	/**
	 * Pauses the current thread for an approximate amount of time.
	 * @param milliseconds the designated time for the thread to be paused.
	 */
	private void pause(int milliseconds) {

		try {

			Thread.sleep(milliseconds);

		} catch (InterruptedException e) {

			if (IS_DEBUG) {

				System.err.println("Thread interrupted");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Counts down the time of the game.
	 * 
	 * This method decreases the time left for the game and displays the
	 * decreased time on the screen. If the time is zero after decreasing and
	 * not shooting is occurring, ends the game.
	 */
	private void countDown() {

		if (!playing)
			return;

		gameModel.decreaseTimeLeft();
		gameView.updateTime(gameModel.getTimeLeft());

		if (gameModel.getTimeLeft() == 0) {

			playing = false;
			if (!shooting)
				endGame();
		}
	}

	/**
	 * Moves the goals to new positions.
	 * 
	 * This methods updates the internal goal positions and the graphical
	 * representation of the goals.
	 */
	private void moveGoals() {

		if (!playing)
			return;

		gameModel.updateGoalPositions();
		gameView.updateGoalImages(gameModel.getGoals());
	}

	/**
	 * Updates the highscore records.
	 * 
	 * This method reads the previous saved game records and updates it with the
	 * current record by using the student implemented controller.
	 */
	private void updateHighScoreRecords() {

		GameRecord[] preRecords = new GameRecord[10];
		int i = 0;
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader("highScore.txt"))) {

			while ((line = reader.readLine()) != null) {

				Scanner scanner = new Scanner(line);
				scanner.useDelimiter("\t");
				preRecords[i] = new GameRecord(scanner.next(), scanner.nextInt(), scanner.nextInt());
				scanner.close();
				i++;
			}

		} catch (IOException e) {

			if (IS_DEBUG)
				System.err.println("Error when reading highScore.txt");
		}

		GameRecord[] records = new GameRecord[i];
		System.arraycopy(preRecords, 0, records, 0, i); // trim the array if it has smaller than 10 records.

		try (PrintWriter writer = new PrintWriter(new FileWriter("highScore.txt"))) {

			IO.output("Please input your name: ");
			String name = IO.inputString();
			if (name.length() > 20)
				name = name.substring(0, 20);
			
			// update the highscore records by using the student implemented controller
			GameRecord[] updatedRecords = studentLogic.updateHighScoreRecords(records, name, gameModel.getLevel(), gameModel.getScore());
			
			for (GameRecord record : updatedRecords)
				writer.println(record.getName() + "\t" + record.getLevel() + "\t" + record.getScore());
			
			
		} catch (IOException e) {

			if (IS_DEBUG)
				System.err.println("Error when saving highScore.txt");
		}
	}

	/**
	 * Outputs a highscore table.
	 * 
	 * This method outputs a highscore table by reading the most updated records
	 * and then process it with the student implemented controller.
	 */
	private void outputHighScoreTable() {

		try (BufferedReader reader = new BufferedReader(new FileReader(
				"highScore.txt"))) {

			IO.output(String.format("%-20s", "name"));
			IO.output(String.format("%10s", "level"));
			IO.output(String.format("%10s", "score"));
			IO.outputln("\n----------------------------------------");
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				
				Scanner scanner = new Scanner(line);
				scanner.useDelimiter("\t");
				String name = scanner.next();
				int level = scanner.nextInt();
				int score = scanner.nextInt();
				scanner.close();
				
				IO.output(String.format("%-20s", name));
				IO.output(String.format("%10d", level));
				IO.output(String.format("%10d", score));
				IO.outputln("");
			}

		} catch (IOException e) {

			if (IS_DEBUG)
				System.err.println("Error when outputing highscore table");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 * 
	 * Bridging a mouse move into an aiming event.
	 */
	public void mouseMoved(MouseEvent e) {

		aim(e.getX(), e.getY());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * 
	 * Bridging a mouse click to a shooting event.
	 */
	public void mouseClicked(MouseEvent e) {

		shoot();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Bridging a timer event to a time decreased event or a move goal event
	 * by inspecting the action command of the timer event.
	 */
	public void actionPerformed(ActionEvent e) {

		if ("decreaseTime".equals(e.getActionCommand()))
			countDown();

		else if ("moveGoals".equals(e.getActionCommand()))
			moveGoals();
	}

	/**
	 * Plays a sound according to the type of sound input.
	 * @param soundType the type of sound to play.
	 */
	private void playSound(int soundType) {
		
		try {

			AudioInputStream audioIn;

			switch (soundType) {

			case FIRE_SOUND:
				audioIn = AudioSystem.getAudioInputStream(new File("sounds/shoot.wav"));
				break;
			case HIT_SOUND:
				audioIn = AudioSystem.getAudioInputStream(new File("sounds/explode.wav"));
				break;
			case END_SOUND:
				audioIn = AudioSystem.getAudioInputStream(new File("sounds/end.wav"));
				break;
			default:
				return;
			}

			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.addLineListener(new SoundHandler(clip));
			clip.start();

		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {

			e.printStackTrace();
		}
	}
	
	class SoundHandler implements LineListener {
		
		Clip clip;
		
		public SoundHandler(Clip clip) {
			
			this.clip = clip;
		} 

		@Override
		public void update(LineEvent event) {
			
			if (event.getType() == LineEvent.Type.STOP)
		        clip.close();
		}
	}

	// unused event handlers
	// --------------------------------------------
	public void mouseDragged(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	// --------------------------------------------
	
	/**
	 * Asks the calling thread to wait for the game loop to end.
	 */
	public void waitForGameLoopToEnd() {
		
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Signals to the waiting thread that the game loop has ended.
	 */
	public void endGameLoop() {
		
		synchronized (this) {
			notify();
		}
	}
}
