package comp102x.assignment;

import comp102x.ColorImage;

/**
 * The GameModel class represents the internal data of the shooting game.
 */
public class GameModel {

	/** Parameter for defining the minimum pan angle of the arrow. */
	public static final int ARROW_MIN_PAN_ANGLE = -45;

	/** Parameter for defining the maximum pan angle of the arrow. */
	public static final int ARROW_MAX_PAN_ANGLE = 45;

	/** Parameter for defining the minimum tilt angle of the arrow. */
	public static final int ARROW_MIN_TILT_ANGLE = 0;

	/** Parameter for defining the maximum tilt angle of the arrow. */
	public static final int ARROW_MAX_TILT_ANGLE = 75;

	/**
	 * Parameter for defining the minimum football scale when generating the
	 * shooting football image.
	 */
	public static final double FOOTBALL_MIN_SCALE = 0.2;

	/**
	 * Parameter for defining the maximum football scale when generating the
	 * shooting football image.
	 */
	public static final double FOOTBALL_MAX_SCALE = 1.0;

	/** Parameter for defining how many rows of goals. */
	public static final int GOAL_ROWS = 3;

	/** Parameter for defining how many columns of goals. */
	public static final int GOAL_COLS = 4;

	/** Parameter for defining the time allowed for the game. */
	public static final int TIME_ALLOWED = 60;

	/**
	 * Parameter for defining the total number of steps for the shooting
	 * animation.
	 */
	public static final int ANIMATION_STEPS = 40;

	/**
	 * Parameter for defining the number of different depth images for the
	 * football.
	 */
	public static final int FOOTBALL_FRAMES = 10;

	/**
	 * Parameter for defining how fast will the movable goals try to update
	 * their positions.
	 */
	public static final int GOAL_UPDATE_INTERVAL = 800;

	/** Parameter for defining how fast will the football travels. */
	public static final int FOOTBALL_UPDATE_INTERVAL = 10;

	/**
	 * Parameter for defining how long to show the explosion image when a goal
	 * is hit.
	 */
	public static final int SHOW_EXPLOSION_DURATION = 100;

	/** The time left for the game. */
	private int timeLeft;

	/** The current score obtained. */
	private int score;

	/** The level of the current game. */
	private int level;

	/** The number of remaining goals. */
	private int remainingGoals;

	/** The arrow object of the game. */
	private Arrow arrow;

	/** The football object of the game. */
	private Football football;

	/** The 2D array of goal objects. */
	private Goal[][] goals;

	/** The array of football images of different depths. */
	private ColorImage[] depthImages;

	/**
	 * The logic implemented by the students for processing the goals and the
	 * football.
	 */
	private GameLogic studentLogic;

	/**
	 * Constructs a game model.
	 * @param studentModel the delegated logic implemented by students.
	 */
	public GameModel(GameLogic studentModel) {

		// initialization of various parameters.

		this.score = 0;
		this.timeLeft = TIME_ALLOWED;
		this.level = 1;
		this.remainingGoals = GOAL_ROWS * GOAL_COLS;

		this.arrow = new Arrow();
		this.football = new Football();
		this.goals = new Goal[GOAL_ROWS][GOAL_COLS];
		this.depthImages = new ColorImage[FOOTBALL_FRAMES];

		/*
		 * loading football images of different depths with filename "bX.png". the
		 * larger X is, the larger the depth (i.e. the closer it appears to be).
		 * the array stores the images from larger depth to smaller depth (i.e.
		 * closer to father away).
		 */
		for (int i = 0; i < FOOTBALL_FRAMES; i++) {
			depthImages[i] = new ColorImage("images/footballs/b" + (FOOTBALL_FRAMES - i) + ".png");
			depthImages[i].setMovable(false);
		}

		this.studentLogic = studentModel;
	}

	/**
	 * Returns the current score obtained.
	 * @return the current score obtained.
	 */
	public int getScore() {

		return score;
	}

	/**
	 * Returns the level of the current game play.
	 * @return the level of the current game play.
	 */
	public int getLevel() {

		return level;
	}

	/**
	 * Sets the level of the current game play.
	 * @param level the level of the game play to be updated to.
	 */
	public void setLevel(int level) {

		this.level = level;
	}
	
	/**
	 * Returns the time left for the current game play.
	 * @return the time left for the current game play.
	 */
	public int getTimeLeft() {

		return timeLeft;
	}
	
	/**
	 * Decreases the current time left for the game.
	 */
	public void decreaseTimeLeft() {

		timeLeft--;
	}
	
	/**
	 * Returns the goal objects.
	 * @return the goal objects.
	 */
	public Goal[][] getGoals() {

		return goals;
	}
	
	/**
	 * Returns the arrow object. 
	 * @return the arrow object.
	 */
	public Arrow getArrow() {

		return arrow;
	}
	
	/**
	 * Increments the score of the current game play.
	 */
	public void incrementScore() {

		score++;
	}

	/**
	 * Decrement the number of remaining goals.
	 */
	public void decrementRemaningGoals() {

		remainingGoals--;
	}

	/**
	 * Returns the number of remaining goals.
	 * @return the number of remaining goals.
	 */
	public int getRemaining() {

		return remainingGoals;
	}

	/**
	 * Updates the angles of the arrow according to the mouse position on the
	 * screen.
	 * 
	 * @param x the x position of the mouse on the screen.
	 * @param y the y position of the mouse on the screen.
	 */
	public void updateArrowData(int x, int y) {

		double pan = 0;
		double tilt = 0;

		int xMin = GameView.WIDTH / 4;
		int xMax = GameView.WIDTH * 3 / 4;

		if (x < xMin) x = xMin;
		if (x > xMax) x = xMax;

		pan = ARROW_MIN_PAN_ANGLE + (x - xMin) * (ARROW_MAX_PAN_ANGLE - ARROW_MIN_PAN_ANGLE) / (xMax - xMin);
		arrow.setPan(pan);

		int yMin = GameView.HEIGHT * 3 / 4;
		int yMax = GameView.HEIGHT;

		if (y < yMin) y = yMin;
		if (y > yMax) y = yMax;

		tilt = ARROW_MIN_TILT_ANGLE + (y - yMin) * (ARROW_MAX_TILT_ANGLE - ARROW_MIN_TILT_ANGLE) / (yMax - yMin);
		arrow.setTilt(tilt);
	}

	/**
	 * Updates the hit status of the goals and return the row and column for
	 * the hit goal if any.
	 * 
	 * This method checks if the football image at the end of the shooting
	 * animation is completely within any goal image. If yes, the hit status
	 * of that goal image is updated to be true. The row number and column
	 * number of the goal is then returned. If no goal is hit, null is
	 * returned.
	 * 
	 * @param footballImage the football image at the end of the shooting animation.
	 * @param goalWidth the width of a goal image.
	 * @param goalHeight the height of a goal image.
	 * @param margin the graphical separation between each goal image.
	 * @return a 1D array with the first element as the row and second element
	 *         as the column of the hit goal. null is returned if not goal
	 *         is hit.
	 */
	public int[] updateHit(ColorImage footballImage, int goalWidth, int goalHeight, int margin) {

		if (footballImage == null)
			return null;

		int bX = footballImage.getX();
		int bY = footballImage.getY();
		int bW = (int) (footballImage.getWidth() * footballImage.getScale());
		int bH = (int) (footballImage.getHeight() * footballImage.getScale());

		for (int rowNumber = 0; rowNumber < GOAL_ROWS; rowNumber++)
			for (int colNumber = 0; colNumber < GOAL_COLS; colNumber++) {

				if (goals[rowNumber][colNumber] != null && !goals[rowNumber][colNumber].isHit()) {

					int tX = colNumber * (goalWidth + margin);
					int tY = rowNumber * goalHeight;
					int tW = goalWidth;
					int tH = goalHeight;

					if (bX > tX && bY > tY && bX + bW < tX + tW
							&& bY + bH < tY + tH) {

						goals[rowNumber][colNumber].setHit(true);
						return new int[] { rowNumber, colNumber };
					}
				}
			}

		return null;
	}
	
	/**
	 * Updates the path information for the shooting animation.
	 * 
	 * @param startX the starting x of the shooting path.
	 * @param destinationX the destination x of the shooting path.
	 * @param startY the starting y of the shooting path.
	 * @param destinationY the destination y of the shooting path.
	 */
	public void updateFootballData(int startX, int destinationX, int startY, int destinationY) {

		football.setStartX(startX);
		football.setStartY(startY);
		football.setDestinationX(destinationX);
		football.setDestinationY(destinationY);
	}
	
	/**
	 * Initializes the goal array.
	 */
	public void initializeGoalArray() {
		
		int row = goals.length;
		int col = goals[0].length;
		
		if (level == 3)
			for (int i = 0; i < row; i++)
				for (int j = 0; j < col; j++)
					goals[i][j] = new Goal(Goal.MOVABLE, false);
		
		else if (level == 2)
			for (int i = 0; i < row; i++)
				for (int j = 0; j < col; j++)
					if ((i + j) % 2 == 0)
						goals[i][j] = new Goal(Goal.STATIONARY, false);
					else
						goals[i][j] = new Goal(Goal.MOVABLE, false);
		
		else
			for (int i = 0; i < row; i++)
				for (int j = 0; j < col; j++)
					goals[i][j] = new Goal(Goal.STATIONARY, false);
	}

	/**
	 * Updates the goal positions using the student implemented model.
	 */
	public void updateGoalPositions() {

		studentLogic.updateGoalPositions(goals);
	}

	/**
	 * Generates an intermediate football for the shooting animation using the student implemented model.
	 * @param currentStep the current step of the animation.
	 * @return the generated intermediate football image for the shooting animation.
	 */
	public ColorImage generateIntermediateFootballImage(int currentStep, int rotationAngle) {

		ColorImage image = studentLogic.generateIntermediateFootballImage(depthImages, 0, currentStep, ANIMATION_STEPS, FOOTBALL_MAX_SCALE, FOOTBALL_MIN_SCALE, football.getStartX(), football.getDestinationX(), football.getStartY(), football.getDestinationY()); 
		image.setRotation(rotationAngle);
		return image;
	}

	
}
