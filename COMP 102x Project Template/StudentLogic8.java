import java.util.Random;
import java.util.Arrays;
import java.util.Comparator;

import comp102x.IO;
import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
    
    Random random = new Random();
    
    private final int MAX_REC = 10; 
    
    /**
     *  Comparator for comparing game records
     */
    Comparator<GameRecord> recordComparator = new Comparator<GameRecord>(){
        public int compare(GameRecord gr1, GameRecord gr2) {
            if (gr1.getScore() < gr2.getScore()) return 1;
            if (gr1.getScore() > gr2.getScore()) return -1;
            if (gr1.getLevel() < gr2.getLevel()) return 1;
            if (gr1.getLevel() > gr2.getLevel()) return -1;
            return 0;
        }
    };
    
    /**
     * Generates an intermediate football image for the shooting animation
     *
     * @param depthImages an array of football images of different depths
     * @param initialStep initial step of the shooting animation
     * @param currentStep current step of the shooting animation
     * @param finalStep final step of the shooting animation
     * @param initialScale initial scale of the football 
     * @param finalScale final scale of the football 
     * @param initialX initial x position of the football 
     * @param finalX final x position of the football 
     * @param initialY initial y position of the football 
     * @param finalY final y position of the football 
     * @return The selected depth image
     */
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        // write your code after this line
        double step = 1.0 * (currentStep - initialStep) / (finalStep - initialStep);
        int imageIndex = (int) Math.round((depthImages.length - 1) * step);
        ColorImage image = depthImages[imageIndex];
        int xPosition =  (int) Math.floor(initialX + (finalX - initialX) * step);
        int yPosition =  (int) Math.floor(initialY + (finalY - initialY) * step);
        double scale = initialScale + (finalScale - initialScale) * step;
        image.setX(xPosition);
        image.setY(yPosition);
        image.setScale(scale);
        return image;
    }
    
    /**
     * Helper class for storing [i,j] goal position
     */
    private class Position {
        int I,J;
        Position (int i, int j) {
            I = i;
            J = j;
        }
    };
    
    /**
     * Generates an array of adjacent positions already being hit
     * for goal given by position [i,j]
     *
     * @param goals 2D array of Goal objects that represents the goals displayed on the main game screen
     * @param i goal row
     * @param j goal column
     * @return The array of Position objects
     */
    private Position[] adjacentPositions (Goal[][] goals, int i , int j) {
        Position[] adjacents = new Position[8];
        int pos = 0;
        int iMin, iMax, jMin, jMax;
        if (i > 0) iMin = i - 1; else iMin = i;
        if (i < goals.length - 1) iMax = i + 1; else iMax = i;
        if (j > 0) jMin = j - 1; else jMin = j;
        if (j < goals[0].length - 1) jMax = j + 1; else jMax = j;
        for (int I = iMin; I <= iMax; I++) {
            for (int J = jMin; J <= jMax; J++) {
                if (I != i && J != j && goals[I][J].isHit()) {
                    adjacents[pos] = new Position(I, J);
                    pos++;
                }
            }
        }
        return Arrays.copyOfRange(adjacents, 0, pos);
    }
    
    /**
     * Swaps goals at positions [i,j] and [I,J]
     *
     * @param goals 2D array of Goal objects that represents the goals displayed on the main game screen
     * @param i first goal row
     * @param j first goal column
     * @param I second goal row
     * @param J second goal column
     */
    public void swapGoals(Goal[][] goals, int i, int j, int I, int J) {
        Goal temp = goals[i][j];
        goals[i][j] = goals[I][J];
        goals[I][J] = temp;
    }

    /**
     * Periodically updates the positions of the goals
     * 
     * creates moved - 2D array for tracking moved goals along the update process
     * loops through goals and for any that is not STATIONARY or already moved 
     * gets array with possible adjacent positions,
     * if not empty picks a random element, swap places and mark new place in moved array
     *
     * @param goals 2D array of Goal objects that represents the goals displayed on the main game screen
     */
    public void updateGoalPositions(Goal[][] goals) {
        // write your code after this line
        int nRows = goals.length;
        int nCols = goals[0].length;
        
        int[][] moved = new int[nRows][nCols]; // 2D array for tracking moved goals along the update process
        
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (goals[i][j].getType() == Goal.STATIONARY || moved[i][j] == 1) continue;
                Position[] adjacents = adjacentPositions (goals, i , j);
                if (adjacents.length == 0) continue;
                int randomIndex = random.nextInt(adjacents.length);
                Position swap = adjacents[randomIndex];
                swapGoals(goals, i, j, swap.I, swap.J);
                moved[swap.I][swap.J] = 1;
            }
        }
    }

    
    /**
     * Compares the record of the current game play with those of previous game plays and update the highscore records
     *
     * @param highScoreRecords Array of the GameRecords of PREVIOUS game plays
     * @param name name of the current game play
     * @param level level of the current game play
     * @param score score of the current game play
     * @return Array of GameRecords
     */
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // write your code after this line
        GameRecord gameRecord = new GameRecord(name, level, score);
        
        boolean newName = true;
        int length = highScoreRecords.length;
        
        
        // check for existing name
        for (int i = 0; i < length; i++) {
            if (highScoreRecords[i].getName().equals(name)) { // found existing name
                if (recordComparator.compare(gameRecord, highScoreRecords[i]) == -1) {
                    highScoreRecords[i] = gameRecord; // update 
                } else {
                    return highScoreRecords; // no change 
                 }
                newName = false;
                break;
            }
        }
        
        if (newName) { 
            if (length < MAX_REC) { // add
                highScoreRecords  = Arrays.copyOf(highScoreRecords, length + 1);
                highScoreRecords[length] = gameRecord;
            } else { // compare with the lowest score at pos 9 and replace it eventually
                if (recordComparator.compare(gameRecord, highScoreRecords[MAX_REC - 1]) == -1) {
                    highScoreRecords[MAX_REC - 1] = gameRecord;
                } else {
                    return highScoreRecords; // no change 
                }
            }
        }
        
        Arrays.sort(highScoreRecords, recordComparator);
        
        return highScoreRecords;
        
    }
    
    
}

