import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;
import java.util.Arrays;
import java.util.Comparator;

public class StudentLogic implements GameLogic{
    
    
    /**
     * Generates an intermediate football image for the shooting animation
     * @param depthImages array of football images of different depths
     * @param initialStep the initial step of the shooting animation
     * @param currentStep the current step of the shooting animation
     * @param finalStep the final step of the shooting animation
     * @param initialScale the initial scale of the football
     * @param finalScale the final scale of the football
     * @param initialX the initial x position of the football
     * @param finalX the final x position of the football
     * @param initialY the initial y position of the football
     * @param finalY the final y position of the football
     * @return intermediate football image for the shooting animation
     */
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        //calculate the index of the image and get the image from the array
        ColorImage footballImage = depthImages[(depthImages.length - 1) * (currentStep - initialStep) / (finalStep - initialStep)];
        
        //calculate and set the x-position of the image
        footballImage.setX(initialX + (finalX - initialX) * (currentStep - initialStep) / (finalStep - initialStep));
        
        //calculate and set the y-position of the image
        footballImage.setY(initialY + (finalY - initialY) * (currentStep - initialStep) / (finalStep - initialStep));
        
        //calculate and set the scale of the image
        footballImage.setScale(initialScale + (finalScale - initialScale) * (currentStep - initialStep) / (finalStep - initialStep));
        
        //return the generated im   age
        return footballImage;

    }
    
    
    /**
     * Self-created class for representing a position
     */
    class Position{
        private int x, y;
        
        /**
         * Construct a position with x, y-coordinates
         * @param x x-coordinate of the position
         * @param y y-coordinate of the position
         */
        Position(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        /**
         * Gets the x-coordinate of the position
         * @return x-coordinate of the position
         */
        int getX(){ return x;}
        
        /**
         * Gets the y-coordinate of the position
         * @return y-coordinate of the position
         */
        int getY(){ return y;}
    }
    
    
    /**
     * Update the positions of the goals.
     * All goals are first checked if they are movable,
     * Moveable goals then swap position with an random adjacent goal (horizontally, vertically and diagonally),
     * if that goal is already being hit.
     * @param goals 2D array of Goal objects that represents the goals displayed on the main game screen
     */
    public void updateGoalPositions(Goal[][] goals) {
        
        //get the number of rows and columns 
        final int maxRow = goals[0].length;
        final int maxCol = goals.length;
        //get the maxmium position possible
        final Position maxPosition = new Position(maxCol-1, maxRow-1);
        
        //create an new boolean array(initalized with false by java by default) to store if the goals have been moved
        boolean[][] moved = new boolean[maxCol][maxRow];
        
        //consider each goal from each row and each column
        for (int col = 0; col < maxCol; col++){
            for (int row = 0; row < maxRow; row++){
                //variables storing the current goal and position
                Goal currentGoal = goals[col][row];
                Position currentPostion = new Position(col, row);
                
                //consider the next goal if the goal is not movable
                if (currentGoal.getType() != Goal.MOVABLE) continue;
                
                //consider the next goal if the goal has been moved already
                if (moved[col][row]) continue;
                
                //get an adjacent position of the current position and get the adjacent goal
                Position adjPosition = getAdjacentPosition(currentPostion, maxPosition);
                Goal adjGoal = goals[adjPosition.getX()][adjPosition.getY()];
                
                //consider the next goal if the adjacent goal has not been hit
                if (!adjGoal.isHit()) continue;
               
                //swap the goals if the requirement is met
                switchPosition(goals, currentPostion, adjPosition);
                
                //mark the goals as moved
                moved[col][row] = true; moved[adjPosition.getX()][adjPosition.getY()] = true;
        
            }
        }
    }
    
    
    /**
     * Get random adjacent position of a position
     * @param position position to be evaluated
     * @param maxPosition maximum position possible in the grid
     * @param Random adjacent position of the position
     */
    private Position getAdjacentPosition(Position position, Position maxPosition){
        
        Random random = new Random(); //Create an random object
        
        Position newPosition;
        do{
            int changeInX = random.nextInt(3) - 1; //generate an integer from -1 to 1
            
            int changeInY; 
            if (changeInX == 0)
                changeInY = random.nextInt(2) * 2 - 1;  //generate 1 and -1 only, prevents generating the same position as original
            else
                changeInY = random.nextInt(3) - 1;
                
            newPosition = new Position(position.getX() + changeInX, position.getY() + changeInY); //calculate the new postion
            
        }while(!checkValid(newPosition, maxPosition)); //generate a new position if it is out of bounds
        
        return newPosition;
    }
    
    
    /**
     * Check if the position is outside the border
     * @param position position to be checked
     * @param maxPosition maximum position possible in the grid
     * @return boolean representing if the position is outside the border
     */
    private boolean checkValid(Position position, Position maxPosition){
        if((position.getX()) < 0 || (position.getY() < 0)) return false; //check if the coords are negative
        if((position.getX() > maxPosition.getX()) || (position.getY() > maxPosition.getY())) return false; //check if the coords are larger than the max coords possible
        return true; //return true if valid
    }
    
    
    /**
     * Swap position of two goals
     * @param goals 2D array of Goal objects that represents the goals displayed on the main game screen
     * @param goal1 the position of the first goal
     * @param goal2 the position of the second goal
     */
    private void switchPosition(Goal[][] goals, Position goal1, Position goal2){
        Goal temp = goals[goal2.getX()][goal2.getY()];
        goals[goal2.getX()][goal2.getY()] = goals[goal1.getX()][goal1.getY()];
        goals[goal1.getX()][goal1.getY()] = temp;
    }
   
    
    /**
     * Compares the record of the current game play with those of previous game plays and update the highscore records.
     * @param highScoreRecords GameRecords of previous game plays
     * @param name name of the current game play
     * @param level level of the current game play
     * @param score score of the current game play
     */
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        //comparator for two high scores
        Comparator<GameRecord> sortComparator = new Comparator<GameRecord>(){
            public int compare(GameRecord first, GameRecord second){
                if(first.getScore() == second.getScore())
                    return second.getLevel() - first.getLevel();
                else
                    return second.getScore() - first.getScore();
            }
        };
        
        final int recordSize = highScoreRecords.length; //get the size of the record
        int index = searchByName(highScoreRecords, name); //get the index of the player in the old rercord
        GameRecord newRecord = new GameRecord(name, level, score); //construct a new record
        GameRecord[] newRecordArray; //construct a new record array
        if(index != -1){ //check if the player is already in the high score
            newRecordArray = highScoreRecords.clone(); //get a copy of the current high score
            if (sortComparator.compare(newRecord, newRecordArray[index]) < 0)
                newRecordArray[index] = newRecord; //update the record if the player get a higher score then last time
        }else{
            newRecordArray = Arrays.copyOf(highScoreRecords, recordSize + 1); //increase the record size by 1
            newRecordArray[recordSize] = newRecord; //import the new record to the array
        }
        Arrays.sort(newRecordArray, sortComparator); //sort the arry in decending order using the comparator
        return Arrays.copyOf(newRecordArray, Math.min(10, newRecordArray.length)); //make the list shorter if necessary
    }
    
    
    /**
     * Search an GameRecord array by name
     * @param record the array to be searched
     * @param name the name to be searched for
     * @return index of the search key, if it is contained in the array; otherwise, -1 is returned
     */
    private int searchByName(GameRecord[] record, String name){
        //simple linear search
        final int recordSize = record.length;
        for (int i = 0; i < recordSize; i++){
            if (record[i].getName().equals(name)) 
                return i;
        }
        return -1;
    }
    
    
}

