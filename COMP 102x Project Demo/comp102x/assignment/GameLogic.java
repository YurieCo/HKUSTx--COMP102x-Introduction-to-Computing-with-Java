package comp102x.assignment;

import comp102x.ColorImage;

public interface GameLogic {
	
	public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY);
	public void updateGoalPositions(Goal[][] goals);
	public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score);

}
