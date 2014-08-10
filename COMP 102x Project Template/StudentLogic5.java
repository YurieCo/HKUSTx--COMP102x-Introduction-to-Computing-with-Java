import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

/**
 * The StudentLogic class represents the methods to implement necessary to the project.
 */
public class StudentLogic implements GameLogic{
    
    /**
     * Generate an intermediate football image
     * <p>
     * Calculate the proper index of the array of football images,
     * calculate the x and y positions as well the scale of the intermediate image.
     * 
     * @param   depthImages   the array of football images of different depths
     * @param   initialStep   the initial step of shooting animation
     * @param   currentStep   the current step of shooting animation
     * @param   finalStep     the final step of shooting animation
     * @param   initialScale  the initial scale of the football
     * @param   finalScale    the final scale of the football
     * @param   initialX      the initial x position of the football
     * @param   finalX        the final x position of the football
     * @param   initialY      the initial y position of the football
     * @param   finalY        the final y position of the football
     * @return                the intermediate football image of the animation
     */
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        // Variables declaration
        int imageIndex; 
        int xPosition;
        int yPosition;
        double scale;
        
        // Calculate the index of intermediate image with the following formula
        imageIndex = (depthImages.length - 1) * (currentStep - initialStep) / (finalStep - initialStep);
        
        // Calculate the x position of intermediate image with the following formula
        xPosition = initialX + (finalX - initialX) * (currentStep - initialStep) / (finalStep - initialStep);
        
        // Calculate the y position of intermediate image with the following formula
        yPosition = initialY + (finalY - initialY) * (currentStep - initialStep) / (finalStep - initialStep);
        
        // Calculate the scale of intermediate image with the following formula
        scale = initialScale + (finalScale - initialScale) * (currentStep - initialStep) / (finalStep - initialStep);
        
        // Generate the intermediate image from the array and calculated index
        ColorImage intermediateImage = depthImages[imageIndex];
        
        // Apply calculated transformations on the intermediate image
        intermediateImage.setX(xPosition);
        intermediateImage.setY(yPosition);
        intermediateImage.setScale(scale);
        
        return intermediateImage;
    }

    /**
     * Update the positions of the goals
     * <p>
     * The method implements a randomized method to update the position of goals.
     * Each goal has a chance to move or not, and in case of movemenet each of them
     * has a chance to move vertically (up/down) and horizontally (left/right).
     * Goals can move diagonally in case of simultaneous verical and horizontal movement.
     * The chance of movement can be tuned with the variable: chanceToMoveTreshold.
     * chanceHorizontally and chanceVertically variables are used to move the goals.
     * These are random generated numbers: 0,1,2 and then offseted with -1. This can be
     * used directly to move the goals. -1 means up/left, 0 means do nothing and 1 means
     * move down/right. If the goal is on the edge or in the corner it must be handled!
     * Note: type of movable goals is 2, and stationary is 1
     * 
     * @param   goals      the 2-D array of goal objects
     */ 
    public void updateGoalPositions(Goal[][] goals) {
        // Variables declaration
        int chanceToMoveTreshold = 5; // tuneable treshold of chance of movement
        int chanceToMove;             // variable is used to decide to move or not
        int chanceHorizontally = 0;   // horizontal movement <--->
        int chanceVertically = 0;     // vertical movement
        
        int i; // vertical coordinate for the loop
        int j; // horizontal coordinate for the loop
        
        // Temporary variables necessary to the movements
        boolean isHitFrom;    // the hit flag from the origin
        boolean isHitTo;      // the hit flag from the destination
        int [][]handledGoals; // temporary array to handle which goal is movable
        handledGoals = new int[3][4];
        
        Random randomObject = new Random(); // to generate random numbers (decisions)
        
        for (i = 0; i < goals.length; i++) {
            for (j = 0; j < goals[0].length; j++) {
                // if goal is movable and was not handled before
                if (goals[i][j].getType() == 2 && handledGoals[i][j] == 0){
                    chanceToMove  = randomObject.nextInt(10); // random number from 0 to 9
                    // "chanceToMoveTreshold" chance to update goal position
                    if (chanceToMove >= chanceToMoveTreshold){
                        // if current goal is in top row, it can move down or do nothing
                        if (i == 0) {
                            chanceVertically = randomObject.nextInt(2);
                        }
                        // if current goal is in the middle row, it can move up, down or do nothing
                        // offset chanceVertically with -1 is useful to move the goal up/down.
                        // Negative "chanceVertically" means move up, and positive means move down
                        else if (i > 0 && i < 2) {
                            chanceVertically = randomObject.nextInt(3) - 1;
                        }
                        // if current goal is in bottom row, it can move up or do nothing
                        else if (i == 2) {
                            chanceVertically = randomObject.nextInt(2) - 1;
                        }
                        
                        // if current goal is on the left side, it can move right or do nothing
                        if (j == 0) {
                            chanceHorizontally = randomObject.nextInt(2);
                        }
                        // if current goal is in the middle columns, it can move left, right or do nothing
                        else if (j > 0 && j < 3) {
                            chanceHorizontally = randomObject.nextInt(3) - 1;
                        }
                        // if current goal is on the right side, it can move left or do nothing
                        else if (j == 3) {
                            chanceHorizontally = randomObject.nextInt(2) - 1;
                        }
                        
                        // it was checked before, that the goal is movable, now check if destination is movable also and hasn't been handled before!
                        if (handledGoals[i + chanceVertically][j + chanceHorizontally] == 0 && goals[i + chanceVertically][j + chanceHorizontally].getType() == 2){
                            // read the current isHit flags from the origin and the destination
                            isHitFrom = goals[i][j].isHit();
                            isHitTo   = goals[i + chanceVertically][j + chanceHorizontally].isHit();
                            
                            // exchange the isHit flags
                            goals[i][j].setHit(isHitTo);
                            goals[i + chanceVertically][j + chanceHorizontally].setHit(isHitFrom);
                            
                            // handle these goals in temporary array also
                            handledGoals[i][j] = 1;
                            handledGoals[i + chanceVertically][j + chanceHorizontally] = 1;
                        }
                    }
                }
                // if goal is not movable update temporary array also
                else{
                    handledGoals[i][j] = 1;
                }
            }
        }
    }

    /**
     * Update the high score record
     * <p>
     * The method is called by the program when the game finishes.
     * The method compares the record of the current game play with
     * those of previous game plays and update the highscore records.
     * 
     * @param   highScoreRecords   the 1-D array of the GameRecords of PREVIOUS game plays
     * @param   name               the name of the current game play
     * @param   level              the level of the current game play
     * @param   score              the score of the current game play
     * @return                     the 1-D array of GameRecords after processing the record of the current game play
     */
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // Variables declaration
        int nameIndex = -1;           // to check whether the player is on the list or not
        int i;                        // loop index
        boolean noMoreReplace = true; // indicator for the bubble sorting
        
        // Temporary variables
        String tempName;
        int    tempScore;
        int    tempLevel;
        
        // Temporary arrays
        GameRecord []newHighScores   = new GameRecord[highScoreRecords.length+1];
        GameRecord []finalHighScores = new GameRecord[highScoreRecords.length+1];
        
        // check whether the player is on the list or not
        for (i = 0; i < highScoreRecords.length; i++) {
            if (name.equals(highScoreRecords[i].getName())) {
                nameIndex = i;
                break;
            }
        }
        
        // If name is on the list
        if (nameIndex != -1) {
            // temporary arrays are created from original
            newHighScores = highScoreRecords;
            finalHighScores = highScoreRecords;
            // replace previous score only if new is better, or level is better with the same score
            if ((score > highScoreRecords[nameIndex].getScore()) || (score == highScoreRecords[nameIndex].getScore() && level > highScoreRecords[nameIndex].getLevel())) {
                newHighScores[nameIndex].setScore(score);
                newHighScores[nameIndex].setLevel(level);
            }
        }
        // new player
        else {
            // fill temporary array with scores from original array
            for (i = 0; i < highScoreRecords.length; i++) {
                newHighScores[i] = new GameRecord(highScoreRecords[i].getName(), highScoreRecords[i].getLevel(), highScoreRecords[i].getScore());
            }
            
            // add new player's score to the end of new array
            newHighScores[highScoreRecords.length] = new GameRecord(name, level, score);
            
            // if length of original array is 10 the final array shouldn't be longer!
            if (highScoreRecords.length+1 < 11) {
                finalHighScores = new GameRecord[highScoreRecords.length+1];
                finalHighScores = newHighScores;
            }
            else {
                finalHighScores = new GameRecord[highScoreRecords.length];
                finalHighScores = highScoreRecords;
            }
        }   
        
        // bubble sorting
        while (noMoreReplace) {
            noMoreReplace = false; // if there are no more soring possibilities the while loop breaks
            for (i = 0; i < newHighScores.length - 1; i++) { // get trough the elements of temporary array
                // If there is higher score, or same score with higher level the two elements are interchanged
                if (newHighScores[i+1].getScore() > newHighScores[i].getScore() || (newHighScores[i+1].getScore() == newHighScores[i].getScore() && newHighScores[i+1].getLevel() > newHighScores[i].getLevel())) {
                    // temporary variables necessary for the interchange
                    tempName  = newHighScores[i].getName();
                    tempScore = newHighScores[i].getScore();
                    tempLevel = newHighScores[i].getLevel();
                    
                    // interchange the elements
                    newHighScores[i].setName(newHighScores[i+1].getName());
                    newHighScores[i].setScore(newHighScores[i+1].getScore());
                    newHighScores[i].setLevel(newHighScores[i+1].getLevel());
                    
                    newHighScores[i+1].setName(tempName);
                    newHighScores[i+1].setScore(tempScore);
                    newHighScores[i+1].setLevel(tempLevel);
                    
                    // there was a replace, the while loop shouldn't be breaked
                    noMoreReplace = true;
                }
            }
        }
        
        // assignment to the final array which is never longer than 10
        for (i = 0; i < finalHighScores.length; i++) {
            finalHighScores[i].setName(newHighScores[i].getName());
            finalHighScores[i].setScore(newHighScores[i].getScore());
            finalHighScores[i].setLevel(newHighScores[i].getLevel());
        }
        
        return finalHighScores;
    }
    
}

