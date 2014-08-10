import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
    
    /**
     * Generates an intermediate football image for the shooting animation using current state of progress.
     * @param depthImages   array of football images of different depths
     * @param initialStep   intial step of shooting animation
     * @param currentStep   current step of shooting animation
     * @param finalStep final step of shooting animation
     * @param initialScale  initial scale of selected depth image
     * @param finalScale    final scale of selected depth image
     * @param initialX  initial x position of selected depth image
     * @param finalX    final x position of selected depth image
     * @param initialY  initial y position of selected depth image
     * @param finalY    final y position of selected depth image
     * 
     * @return  the selected depth image with updates x-Position, y-Position, and scale
     */
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {        
        // calculate the ratio to be used in determining intermediate values
        double ratio = ((double)(currentStep - initialStep)) /((double)(finalStep - initialStep));
        // find correct Image to be used by finding index
        int imageIndex = (int)((depthImages.length - 1) * ratio);
        
        // calculate intermediate X and Y coordinates        
        int xPosition = (int)(initialX + (finalX - initialX) * ratio);
        int yPosition = (int)(initialY + (finalY - initialY) * ratio);
        
        // calculate the intermediate scaling
        double scale = initialScale + ((finalScale - initialScale) * ratio);
        
        // update the given image with intermediate values
        ColorImage image = depthImages[imageIndex];
        image.setX(xPosition);
        image.setY(yPosition);
        image.setScale(scale);
        
        return image;
        
    }
        
    /**
     * Returns true in 1 in 8 chance
     * @param None
     * @return Returns true in 1 in 8 chances
     */
    private boolean nextChance(){
        Random random = new Random();
        int chance = random.nextInt(15);
        return chance < 1;
    }        
    
    /**
     * Checks if the given goal is movable
     * @param goal  Goal to be tested
     * @return  boolean whether the goal can be moved or not
     */
    private boolean isMovable(Goal goal){
        return goal.getType() == Goal.MOVABLE;
    }

    /**
     * Checks if the given coordinate is valid in the given range
     * @param curR  current Row
     * @param curC  current Column
     * @param lenR  no. of Rows 
     * @param lenC  no. of Columns
     * @return  boolean whether the current row and col coordinates are within total no. of rows and cols
     */
    private boolean isValidCoordinate(int curR, int curC, int lenR, int lenC){
        return curR >= 0 && curR < lenR && curC >= 0 && curC < lenC;
    }
    
    /**
     * Swaps the goal between two given positions
     */
    private void swap(Goal[][] goals, int initI, int initJ, int finalI, int finalJ){
        Goal temp = goals[initI][initJ];
        goals[initI][initJ] = goals[finalI][finalJ];
        goals[finalI][finalJ] = temp;
    }
    
    /**
     * Updates the positions of the goals.
     * <p>
     * The method changes the position of movable goals by some chance to an adjacent position, given that the goal in the adjacent position is already being hit.
     * </p>
     * @param goals A 2-D array of Goal objects that represents the goals displayed on the main game screen. 
     */    
    public void updateGoalPositions(Goal[][] goals) {
        // write your code after this line
        int i, j, k, adjR, adjC;
        // get no. of rows and cols of goals
        int rows = goals.length;
        int cols = goals[0].length;
        
        // initialize 8 directions to which a goal can be moved relative to its current position
        int[][] dir = {{1, 0}, {1, 1}, {0,1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
        
        // flags to denote if the goals are moved or not in current call
        boolean moved[][] = new boolean[rows][cols];
        // initialize flags with false
        for(i = 0; i < rows; i++){
            for(j = 0; j < cols; j++){
                moved[i][j] = false;
            }
        }
        
        // check every goal in each row and col
        for(i = 0; i < rows; i++){
            for(j = 0; j < cols; j++){               
                if(isMovable(goals[i][j])){
                    // the goal is movable
                    for(k = 0; k < 8; k++){
                        // get possible direction to move
                        adjR = i + dir[k][0];                        
                        adjC = j + dir[k][1];
                        // check if the adjacent coordinates are valid for given configuration of goals
                        if(isValidCoordinate(adjR, adjC, rows, cols)){
                            // check if the adjacent goal is hit
                            if(goals[adjR][adjC].isHit()){
                                // if given chance is true
                                if(nextChance()){
                                    // swap the goals, set their moved flags to true, and break k-loop
                                    swap(goals, i, j, adjR, adjC);
                                    moved[i][j] = true;
                                    moved[adjR][adjC] = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * If the given name is present in any of the records, it returns that record's index, else it return -1
     */
    int indexIfExists(GameRecord[] records, String name){
        for(int i = 0; i < records.length; i++){
            if(records[i].getName().equals(name)){
                return i;
            }            
        }
        return -1;
    }
    
    /**
     * Applies insertion sort to sort the given records
     */
    void sort(GameRecord[] records){
        int i, j;
        GameRecord key;
        for( i = 1; i < records.length; i++){
            key = records[i];
            for(j = i - 1; j >= 0; j--){
                if(records[j].getScore() < key.getScore() || (records[j].getScore() == key.getScore() && records[j].getLevel() < key.getLevel())){
                    records[j+1] = records[j];
                }else{
                    break;
                }
            }
            records[j+1] = key;
        }
    }
    
    /**
     * compares the record of the current game play with those of previous game plays and updates the highscore records
     * @param highScoreRecords  An array of GameRecord holding the highscores of previous games
     * @param name  Name of the current game play
     * @param level Level of the current game play
     * @param score Score of the current game play
     * 
     * @return  the updated highscore considering current game play into the records
     */
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        GameRecord[] newRecords;
        // Store current record in key
        GameRecord key = new GameRecord(name, level, score);
        final int MAX = 10;
        // previous records' length
        int prevLen = highScoreRecords.length;
        int newLen, prevPosName, i;
        
        // if no previous record, return current record in new array
        if(prevLen == 0){
            newRecords = new GameRecord[1];
            newRecords[0] = key;
            return newRecords;
        }
        
        // get index of current Name in previous records, if exists, else get -1
        prevPosName = indexIfExists(highScoreRecords, name);
        if(prevPosName >= 0){
            // name found on previous records, so no change in records' size
            newLen = prevLen;
            newRecords = new GameRecord[newLen];
            
            // if new record is better than older record, update it
            if(highScoreRecords[prevPosName].getScore() < key.getScore() || (highScoreRecords[prevPosName].getScore() == key.getScore() 
            && highScoreRecords[prevPosName].getLevel() < key.getLevel())){
                highScoreRecords[prevPosName] = key;
                // sort the updates records
                sort(highScoreRecords);
            }
            
            // copy older records t onew records
            for(i = 0; i < prevLen; i++){
                newRecords[i] = highScoreRecords[i];
            }
        }else{
            // current name not in previous records, new length depends on previous length
            newLen = prevLen < MAX? prevLen + 1: MAX;
            newRecords = new GameRecord[newLen];
            
            // copy old records to new records
            for(i = 0; i < prevLen; i++){
                newRecords[i] = highScoreRecords[i];
            }
            
            // if previous length is less than MAX, store current record at end of new records, and sort them
            if(prevLen < MAX){                
                newRecords[newLen-1] = key;
                sort(newRecords);
            }else{
                // else if current record is better than any of previous records, update the records
                for(i = 0; i < newLen; i++){
                    if(newRecords[i].getScore() < key.getScore() || (newRecords[i].getScore() == key.getScore() 
                    && newRecords[i].getLevel() < key.getLevel())){
                        // current record is better than all records after and including at position i
                        // shift the records one position to right from postion i+1, remove last record
                        for(int j = newLen - 1; j > i; j--){
                            newRecords[j] = newRecords[j-1];
                        }
                        // creates a space at i, place key at that position
                        newRecords[i] = key;
                        break;
                    }
                }
            }
        }                        
        return newRecords;
    }
}
