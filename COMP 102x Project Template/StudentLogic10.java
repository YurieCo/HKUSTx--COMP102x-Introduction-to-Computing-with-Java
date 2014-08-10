import java.util.Random;
import comp102x.ColorImage;
import comp102x.assignment.Goal;
import comp102x.assignment.GameRecord;
import comp102x.assignment.GameLogic;

/**
 * Write a description of class StudentLogic here.
 * 
 * @author  
 * @version 2014/07/26
 */
public class StudentLogic implements GameLogic
{
    /**
     * Constructor for objects of class StudentLogic
     */
    public StudentLogic() {}

    /**
     * Generates an intermediate football image for the shooting animation.
     * 
     * @param depthImages  Array of football images of different depths.
     * @param initialStep  Index of initail step.
     * @param currentStep  Index of current step.
     * @param finalStep    Index of final step.
     * @param initialScale Initial size of football.
     * @param finalScale   Final size of football.
     * @param initialX     Initial X coordinate of football. 
     * @param finalX       Final X coordinate of football.
     * @param initialX     Initial Y coordinate of football. 
     * @param finalX       Final Y coordinat of footballe.
     * 
     * @return Football image that has been processed.
     */
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, 
                                                        int initialStep, int currentStep, int finalStep,
                                                        double initialScale, double finalScale,
                                                        int initialX, int finalX, int initialY, int finalY)
    {
        int imageIndex = ((depthImages.length - 1) * (currentStep - initialStep)) / (finalStep - initialStep);
        ColorImage currentImage = depthImages[imageIndex];
        currentImage.setX(initialX + ((finalX - initialX) * (currentStep - initialStep)) / (finalStep - initialStep));
        currentImage.setY(initialY + ((finalY - initialY) * (currentStep - initialStep)) / (finalStep - initialStep));
        currentImage.setScale(initialScale + (finalScale - initialScale) * (currentStep - initialStep) / (finalStep - initialStep));
        return currentImage;
    }   
    
    /**
     * Check whether the goal is movable.
     * @param goal Goal to check.
     * @return true if goal is movable, false otherwise.
     */
    static private boolean isTarget(Goal goal)
    {
        return !goal.isHit() && goal.getType() == Goal.MOVABLE;
    }
   
    /**
     * Offet index of the Moore neighborhood.
     */
    private  static int[][] neibors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    
    /**
     * Count the movable goals in the Moore neighborhood.
     * @param goals 2D array of Goal objects that represents the goals displayed on the main game screen. 
     * @param irow  row index of current center of the Moore neighborhood.
     * @param icol  column index of current center of the Moore neighborhood.
     * @return number of the movable goals in the Moore neighborhood.
     */
    private static int findAdjacent(Goal[][] goals, int irow, int icol)
    {
        int nrow = goals.length;
        int ncol = goals[0].length;       
        int count = 0;
        
        for (int k = 0; k < neibors.length; k++) {
            int krow = irow + neibors[k][0];
            int kcol = icol + neibors[k][1];
            if (0 <= krow && krow < nrow && 0 <= kcol && kcol < ncol) {
                if (isTarget(goals[krow][kcol]))
                    count++;
            }
        }

        return count;
    }
    
    /**
     * Swap the center Goal object with the inumber-th movable goal in the Moore neighborhood.
     * @param goals 2D array of Goal objects that represents the goals displayed on the main game screen. 
     * @param irow  row index of current center of the Moore neighborhood.
     * @param icol  column index of current center of the Moore neighborhood.
     * @param inumber sequence number of the target (movable goal) to swap with current center in the Moore neighborhood.
     */
    private static void swapAdjacent(Goal[][] goals, int irow, int icol, int inumber)
    {
        int nrow = goals.length;
        int ncol = goals[0].length;       
        
        for (int k = 0; k < neibors.length; k++) {
            int krow = irow + neibors[k][0];
            int kcol = icol + neibors[k][1];
            if (0 <= krow && krow < nrow && 0 <= kcol && kcol < ncol) {
                if (isTarget(goals[krow][kcol])) {
                   if (--inumber == 0) {
                       Goal temp = goals[irow][icol];
                       goals[irow][icol] = goals[krow][kcol];
                       goals[krow][kcol] = temp;
                       return;
                   }
                }
            }
        }

    }
    
    /**
     * Move one goal that is movable to an adjacent position randomly.
     * This method is called by the program periodically to update the positions of the goals.
     * Procedure is as follows.
     * 1) Count up pairs of the hitted (empty) goal and the adjacent goal that is movable.
     * 2) Select a target pair by a random number.
     * 3) Swap the hitted (empty) goal with the movable goal of the target pair. 
     * 
     * @param goals 2D array of Goal objects that represents the goals displayed on the main game screen. 
     *              Can be rewritten this content (side effects)
     */
    public void updateGoalPositions(Goal[][] goals)
    {
        int nrow = goals.length;
        int ncol = goals[0].length;
        int count = 0;
        
        /**
         * Find the hitted (empty) goal, count the adjacent movable goals, and sum up them.
         */
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if (goals[i][j].isHit())
                    count += findAdjacent(goals, i, j);
            }
        }
        if (count == 0) return;  // no empty goal.
        
        /**
         * Select a target pair by a random number.
         */
        Random rnd = new Random();
        int target = rnd.nextInt(count);

        /**
         * Find the hitted (emply) goal which is one of the target pair, and swap the goals in the target pair.
         */
        count = 0;
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if (goals[i][j].isHit()) {
                   count += findAdjacent(goals, i, j);
                   if (count > target) {
                       swapAdjacent(goals, i, j, count - target);
                       return;
                   }
                }
            }
        }
    }
    
    /**
     * Compare GameRecord. A record is larger than the other one if it has a higher score,
     * or the two records have the same score, but it has a higher level.
     * @param x GameRecord
     * @param y GameRecord
     * @return  0 if x = y, 1 if x > y, -1 if x < y 
     */
    private static int compareGameRecord(GameRecord x, GameRecord y)
    {
        if (x.getScore() > y.getScore())
            return 1;
        else if (x.getScore() < y.getScore())
            return -1;
        else if (x.getLevel() > y.getLevel())
            return 1;
        else if (x.getLevel() < y.getLevel())
            return -1;
        else
            return 0;
    }
    
    /**
     *  Compares the record of the current game play with those of previous game plays and update the highscore records.
     *  This method is called by the program when the game finishes.
     *  
     *  @param highScoreRecords the 1D array of the GameRecords of previous game plays.
     *  @param name  name of the current game play.
     *  @param level level of the current game play.
     *  @param score score of the current game play.
     *  
     *  @return the 1D array of the GameRecords which is updated with current game play.
     */
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score)
    {
        GameRecord currentRecord = new GameRecord(name, level, score);
        int bucketLength = highScoreRecords.length + 1;
        GameRecord[] bucket = new GameRecord[bucketLength];
        boolean find = false;
        
        /**
         * Copy records to bucket. If a record has the same name as current game play
         * and current score and level are greater than one in the record, update the record with current one.
         */
        for (int i = 0; i < highScoreRecords.length; i++) {
            bucket[i] = highScoreRecords[i];
            if (name.equals(bucket[i].getName())) {
                if (compareGameRecord(currentRecord, bucket[i]) > 0)
                    bucket[i] = currentRecord;
                find = true;
            }
        }
        
        if (!find)
            bucket[bucketLength - 1] = currentRecord;
        else
            bucketLength--;
        
        /**
         * Sort bucket
         */    
        for (int i = 0; i < bucketLength - 1; i++) {
            for (int j = i + 1; j < bucketLength; j++) {
                if (compareGameRecord(bucket[i], bucket[j]) < 0) {
                    GameRecord temp = bucket[i];
                    bucket[i] = bucket[j];
                    bucket[j] = temp;
                }
            }
        }   
        
        /**
         * Copy bucket to new array.
         */
        if (bucketLength > 10)
            bucketLength = 10;
        GameRecord[] newHighScoreRecords = new GameRecord[bucketLength];
        for (int i = 0; i < bucketLength; i++)
            newHighScoreRecords[i] = bucket[i];
            
        return newHighScoreRecords;
    }

}

