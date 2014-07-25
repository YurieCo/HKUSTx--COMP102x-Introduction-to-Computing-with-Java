package comp102x.assignment;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import comp102x.Canvas;
import comp102x.ColorImage;
import comp102x.Text;

/**
 * The GameView class represents the graphical components of the shooting game.
 */
public class GameView {
	
	/** The width of the canvas window. */
	public static final int WIDTH = 1000;
	
	/** The height of the canvas window. */
	public static final int HEIGHT = 680;
	
	/** Parameters for controller the graphical representation of the arrow. */
	private static final int ARROW_MIN_Y_ADJUSTMENT = 0;
	private static final int ARROW_MAX_Y_ADJUSTMENT = 100;
	private static final double ARROW_MIN_SCALE = 0.5;
	private static final double ARROW_MAX_SCALE = 1.0;
	
	/**  Parameter for adjusting the time for showing each complementary screens.*/
	private static final int SHOW_SCREEN_TIME = 2000;
	
	/** Parameter for adjusting the space between each goal image. */
	public static final int MARGIN = 70;
	
	/** The canvas for showing the game. */
	private Canvas canvas = null;
	
	/** The graphical score component for showing the current score. */
	private Text score = null;
	
	/** The graphical time component for showing the current time left. */
	private Text time = null;
	
	/** The start up screen of the game. */
	private ColorImage startScreen  = null;
	
	/** The screen for signaling the start of the game. */
	private ColorImage startGameScreen  = null;
	
	/** The screen for signaling the end of the game. */
	private ColorImage endScreen = null;
	
	/** The background image for the game. */
	private ColorImage backgroundImage = null;
	
	/** The arrow image for the game. */
	private ColorImage arrowImage = null;
	
	/** The initial football image that is loaded on the arrow. */
	private ColorImage originalFootballImage = null;
	
	/** The currently showing football image. */
	private ColorImage currentFootballImage = null;
	
	/** The explosion image when a goal is hit. */
	private ColorImage explosionImage = null;
	
	/** The grid of goal images. */
	private ColorImage[][] goalImages = null;
	
	/**
	 * Constructs a view of the game.
	 */
	public GameView() {
		
		// initialization of various parameters.
		
		this.canvas = new Canvas(WIDTH, HEIGHT);
		
		this.score = new Text("Score:");
		this.score.setMovable(false);
		this.time = new Text("Time:");
		this.time.setMovable(false);
		
		this.startScreen = createResizedColorImage(new ColorImage("images/startScreen.png"));
		this.startScreen.setMovable(false);
		
		this.startGameScreen = createResizedColorImage(new ColorImage("images/startGameScreen.png"));
		this.startGameScreen.setMovable(false);
		
		this.endScreen = createResizedColorImage(new ColorImage("images/endScreen.png"));
		this.endScreen.setMovable(false);
		
		this.backgroundImage = new ColorImage(WIDTH, HEIGHT);
		this.backgroundImage.setMovable(false);
		
		this.arrowImage = new ColorImage("images/arrow.png");
		this.arrowImage.setX((WIDTH - arrowImage.getWidth()) / 2); // positioning the arrow image to the horizontal center.
		this.arrowImage.setY(HEIGHT - arrowImage.getHeight() / 4); // position the arrow vertically so that only 1/4 of it is showing on the screen.
		this.arrowImage.setMovable(false);
		
		this.originalFootballImage = new ColorImage("images/footballs/b10.png"); // load the closest football image as the initial football image.
		this.originalFootballImage.setMovable(false);
		
		this.currentFootballImage = this.originalFootballImage;
		
		this.explosionImage = new ColorImage("images/effect.png");
		this.explosionImage.setMovable(false);
		
		int row = GameModel.GOAL_ROWS;
		int col = GameModel.GOAL_COLS;
		
		this.goalImages = new ColorImage[row][col];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				goalImages[i][j] = new ColorImage("images/goal.png");
				goalImages[i][j].setX(j * (goalImages[i][j].getWidth() + MARGIN));
				goalImages[i][j].setY(i * goalImages[i][j].getHeight());
				goalImages[i][j].setMovable(false);
			}
	}
	
	/**
	 * Shows the start up screen of the game for a predefined time.
	 */
	public void showStartScreen() {
		
		canvas.removeAll();
		canvas.add(startScreen);
		pause(SHOW_SCREEN_TIME);
	}
	
	/**
	 * Shows the start game screen for a predefined time. 
	 */
	public void showStartGameScreen() {
		
		canvas.removeAll();
		canvas.add(startGameScreen);
		pause(SHOW_SCREEN_TIME);
	}
	
	/**
	 * Shows the gaming screen, where playing can see the goals, arrow,
	 * football, ... etc.
	 */
	public void showGameScreen() {
		
		canvas.removeAll();
		canvas.add(backgroundImage);
		
		ColorImage[] walls = new ColorImage[5];
		for (int i = 0; i < 5; i++) {
			walls[i] = new ColorImage("images/wall.png");
			walls[i].setX(i * walls[i].getWidth() * 3 / 2);
			walls[i].setY(HEIGHT / 2);
			walls[i].setMovable(false);
			canvas.add(walls[i]);
		}
		
		canvas.add(arrowImage);
		
		for (int i = 0; i < goalImages.length; i++)
			for (int j = 0; j < goalImages[0].length; j++)
				canvas.add(goalImages[i][j]);
		
		canvas.add(originalFootballImage);
		canvas.add(score, GameView.WIDTH * 5 / 6, GameView.HEIGHT * 9 / 10 - 20);
		canvas.add(time, GameView.WIDTH * 5 / 6, GameView.HEIGHT * 9 / 10 + 10);
	}
	
	/**
	 * Shows the end game screen.
	 */
	public void showEndGameScreen() {
		
		canvas.removeAll();
		canvas.add(endScreen);
	}
	
	/**
	 * Updates the graphical representation of the arrow image, after the mouse
	 * has moved.
	 * 
	 * This method updates the rotation, scaling and y position of the arrow
	 * image. It makes use of the canvas screen size and some parameters for
	 * interpolation and then update the image accordingly.
	 * 
	 * @param x the x position of the mouse.
	 * @param y the y position of the mouse.
	 */
	public void updateArrowImage(int x, int y) {
		
		int xMin = WIDTH / 4;
		int xMax = WIDTH * 3 / 4;		
			
		if (x < xMin) x = xMin;
		if (x > xMax) x = xMax;

		int rotation = GameModel.ARROW_MIN_PAN_ANGLE + (x - xMin) * (GameModel.ARROW_MAX_PAN_ANGLE - GameModel.ARROW_MIN_PAN_ANGLE) / (xMax - xMin);
		arrowImage.setRotation(rotation);
		
		int yMin = HEIGHT * 3 / 4;
		int yMax = HEIGHT;
		
		if (y < yMin) y = yMin;
		if (y > yMax) y = yMax;
			
		double scale = ARROW_MIN_SCALE + (y - yMin) * (ARROW_MAX_SCALE - ARROW_MIN_SCALE) / (yMax - yMin);
		double yAdjustment = ARROW_MIN_Y_ADJUSTMENT + (y - yMin) * (ARROW_MAX_Y_ADJUSTMENT - ARROW_MIN_Y_ADJUSTMENT) / (yMax - yMin);
		
		scaleImageAtCenter(arrowImage, scale);
		arrowImage.setY((int) (yMax - arrowImage.getHeight() / 2 / 4 - yAdjustment));
	}
	
	/**
	 * Updates the position of the football image on the arrow after the arrow
	 * has been moved.
	 * 
	 * This method make use of the current arrow position, scaling and rotation
	 * to update the loaded football image accordingly.
	 */
	public void updateFootballImage() {
		
		int x = arrowImage.getX();
		int y = arrowImage.getY();
		
		x += (arrowImage.getWidth() * arrowImage.getScale() - originalFootballImage.getWidth()) / 2;
		y -= originalFootballImage.getHeight();
		
		x += Math.tan(Math.toRadians(arrowImage.getRotation())) * arrowImage.getHeight() * arrowImage.getScale() / 2;
		
		scaleImageAtCenter(originalFootballImage, 1.0);
		originalFootballImage.setX(x);
		originalFootballImage.setY(y);
	}
	
	/**
	 * Returns the x position of the football image loaded on the arrow. 
	 * @return the x position of the football image loaded on the arrow.
	 */
	public int getloadedFootballX() {
		
		return originalFootballImage.getX();
	}
	
	/**
	 * Returns the y position of the football image loaded on the arrow.
	 * @return the y position of the football image loaded on the arrow.
	 */
	public int getLoadedFootballY() {
		
		return originalFootballImage.getY();
	}
	
	/**
	 * Calculates the final x position of the football image by interpolation.
	 * @param pan the current pan angle of the arrow.
	 * @param minPan the minimum pan angle allowed for the arrow.
	 * @param maxPan the maximum pan angle allowed for the arrow.
	 * @return the final x position of the football calculated.
	 */
	public int calculateFinalFootballX(double pan, int minPan, int maxPan) {
		
		return originalFootballImage.getX() + (int) (pan * GameView.WIDTH  * 0.75 / ((maxPan - minPan) / 2));
	}
	
	/**
	 * Calculates the final y position of the football image by interpolation.
	 * @param tilt the current tilt angle of the arrow.
	 * @param minTilt the minimum tilt angle allowed for the arrow.
	 * @param maxTilt the maximum tilt angle allowed for the arrow.
	 * @return the final y position of the football calculated.
	 */
	public int calculateFinalFootballY(double tilt, int minTilt, int maxTilt) {
		
		return originalFootballImage.getY() - (int) (tilt * GameView.HEIGHT * 0.5 / (maxTilt - minTilt) + GameView.HEIGHT * 0.25);
	}
	
	/**
	 * Replaces the currently showing football image by a new image.
	 * @param newFootballImage the new shooting image to be updated to.
	 */
	public void replaceFootballImage(ColorImage newFootballImage) {
		
		if (newFootballImage == null)
			return;
			
		canvas.add(newFootballImage);
		canvas.remove(currentFootballImage);
		currentFootballImage = newFootballImage;
	}
	
	/**
	 * Shows the explosion image at a specific goal location.
	 * @param row the row of the goal. 
	 * @param col the column of the goal.
	 */
	public void showExplosionImage(int row, int col) {
		
		ColorImage goalImage = goalImages[row][col];
		
		int x = goalImage.getX() + goalImage.getWidth() / 2 - explosionImage.getWidth() / 2;
		int y = goalImage.getY() + goalImage.getHeight() / 2 - explosionImage.getHeight() / 2;
		
		explosionImage.setX(x);
		explosionImage.setY(y);
		canvas.add(explosionImage);
	}
	
	/**
	 * Hides the explosion image.
	 */
	public void hideExplosionImage() {
		
		canvas.remove(explosionImage);
	}
	
	/**
	 * Hides the current football image.
	 */
	public void hideFootballImage() {
		
		canvas.remove(currentFootballImage);
	}
	
	/**
	 * Resets the current football image to be the football image loaded on the
	 * arrow.
	 */
	public void resetFootballImage() {
		
		updateFootballImage();
		canvas.add(originalFootballImage);
		currentFootballImage = originalFootballImage;
	}
	
	/**
	 * Updates the goal images according to the current status of the goals.
	 * @param goals a 2D array storing the goals and their status.
	 */
	public void updateGoalImages(Goal[][] goals) {
		
		int rows = goals.length;
		int cols = goals[0].length;
		
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++) {
			
				if (goals[row][col] == null)
					continue;
				
				if (goals[row][col].isHit())
					goalImages[row][col].setScale(0.0);
				else
					goalImages[row][col].setScale(1.0);
			}
	}
	
	/**
	 * Returns the width of a goal image.
	 * 
	 * Assumption: all the goals have the same width.
	 * @return the width of a goal image.
	 */
	public int getGoalImageWidth() {
		
		return goalImages[0][0].getWidth();
	}
	
	/**
	 * Returns the height of a goal image.
	 * 
	 * Assumption: all the goals have the same height.
	 * @return the height of a goal image.
	 */
	public int getGoalImageHeight() {
		
		return goalImages[0][0].getHeight();
	}
	
	/**
	 * Updates the graphical score display.
	 * @param score the score to be displayed.
	 */
	public void updateScore(int score) {
		
		this.score.setText("Score: " + score);
	}
	
	/**
	 * Updates the graphical time left display.
	 * @param time the time left to be displayed.
	 */
	public void updateTime(int time) {
		
		this.time.setText("Time: " + time);
	}
	
	/**
	 * Adds a MouseListener to the canvas.
	 * @param listener the MouseListener to be added to the canvas.
	 */
	public void addMouseListener(MouseListener listener) {
		
		canvas.addMouseListener(listener);
	}
	
	/**
	 * Adds a MouseMotionListener to the canvas.
	 * @param listener the MouseMotionListener to be added to the canvas.
	 */
	public void addMouseMotionListener(MouseMotionListener listener) {
		
		canvas.addMouseMotionListener(listener);
	}
	
	/**
	 * Pauses the current thread for an approximate amount of time.
	 * @param milliseconds the designated time for the thread to be paused.
	 */
	private void pause(int milliseconds) {
		
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Scales an image at the center.
	 * @param image the image to be scaled.
	 * @param scale the desire scale to be updated to.
	 */
	private void scaleImageAtCenter(ColorImage image, double scale) {
		
		int originalWidth = image.getWidth();
		int originalHeight = image.getHeight();
		int currentWidth = (int) (image.getWidth() * image.getScale());
		int currentHeight = (int) (image.getHeight() * image.getScale());
		int xAdjustment = (currentWidth - originalWidth) / 2;
		int yAdjustment = (currentHeight - originalHeight) / 2;
		image.setX(image.getX() + xAdjustment);
		image.setY(image.getY() + yAdjustment);
		
		int finalWidth = (int) (originalWidth * scale);
		int finalHeight = (int) (originalHeight * scale);
		
		xAdjustment = (originalWidth - finalWidth) / 2;
		yAdjustment = (originalHeight - finalHeight) / 2;
		
		image.setScale(scale);
		image.setX(image.getX() + xAdjustment);
		image.setY(image.getY() + yAdjustment);
	}
	
	private static ColorImage createResizedColorImage(ColorImage image) {
		
		BufferedImage originalImage = createBufferedImage(image);
		BufferedImage scaledImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
		Graphics2D g2d = (Graphics2D) scaledImage.getGraphics();
		g2d.drawImage(originalImage, 0, 0, WIDTH, HEIGHT, null);
        
        ColorImage ret = BufferedImage2ColorImage(scaledImage);
        ret.setX(image.getX());
        ret.setY(image.getY());
        
        return ret;
	}
	
	private static ColorImage BufferedImage2ColorImage(BufferedImage bufferedImage) {
		
		ColorImage temp = new ColorImage(bufferedImage.getWidth(), bufferedImage.getHeight());
		
        for (int x = 0; x < bufferedImage.getWidth(); x++)
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                 int argb = bufferedImage.getRGB(x, y);
                 int a = (argb >> 24) & 0xff;
                 int r = (argb >> 16) & 0xff;
                 int g = (argb >> 8) & 0xff;
                 int b = (argb >> 0) & 0xff;
                 
                 temp.setRGB(x, y, r, g, b);
                 temp.setAlpha(x, y, a);
            }
        
        return temp;
	}

    private static BufferedImage createBufferedImage(ColorImage image) {
        
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                ret.setRGB(x, y, toRGB(image.getAlpha(x, y), image.getRed(x, y), image.getGreen(x, y), image.getBlue(x, y)));
        
        return ret;
    }
    
    private static int toRGB(int alpha, int red, int green, int blue) {
    
        return alpha << 24 | red << 16 | green << 8 | blue;
    }
}
