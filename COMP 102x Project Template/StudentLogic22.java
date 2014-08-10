import java.util.Random;
import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
    
    /**
     * Generates a step-by-step image of moving football. The coordinates and size of the object are computed to change linearly with the step number. 
     * 
     * @param depthImages   array of football images of different depths
     * @param initialStep   number of first step of moving football
     * @param currentStep   number of current step of moving football
     * @param finalStep     number of last step of moving football
     * @param initialScale  size of football at the beginning
     * @param finalScale    size of football at the end
     * @param initialX      initial X-coordinate of football
     * @param finalX        final X-coordinate of football
     * @param initialY      initial Y-coordinate of football
     * @param finalY        final Y-coordinate of football
     * 
     * @return selected depth image of demanded position and size
     * 
     */
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY){
        // write your code after this line
        double fraction = ((double )currentStep-initialStep)/(finalStep-initialStep);
        double imageIndex = ((double )depthImages.length - 1)*fraction;
        double xPosition = initialX + ((double )finalX - initialX)*fraction;
        double yPosition = initialY + ((double )finalY - initialY)*fraction;
        double scale = initialScale + ((double )finalScale - initialScale)*fraction;
        ColorImage returnedImage = depthImages[(int ) imageIndex];
        returnedImage.setX((int ) Math.floor(xPosition));
        returnedImage.setY((int ) Math.ceil(yPosition));
        returnedImage.setScale(scale);
        return returnedImage;
        
    }

    /**
     * Updates the positions of the goals, sometimes swapping the movable objects with their neighbors. There is a guarantee that each goal will be moved at most once during a method call. The probability that the object moves is 40%.
     * 
     * @param goals 2D array of Goal objects that represents the goals displayed on the main game screen
     * 
     * @return no value
     */
    
    
    public void updateGoalPositions(Goal[][] goals) {
        // write your code after this line
        // get dimensions of array
        int height = goals.length;
        int width = goals[0].length;
        
        //additional variables & arrays
        int target1=0, target2=0;    //coordinates of 2nd swapped goal
        boolean found;           //is there a hit neighbor?   
        boolean[][] once = new boolean[height][width];      // "Each goal should be moved ONCE", true if we can move the goal, false otherwise
        for(int i=0; i<height; i++)
            for(int j=0; j<width; j++)
                once[i][j]=true;
        
        //a bit of randomness
        Random randomObject = new Random();
        int randomInteger;   
        
        // main loop
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                if(goals[i][j].getType() == 1 || goals[i][j].isHit()) continue;       //we don't want to move goals which are stationary or hit
                if(once[i][j] == false) continue;       //that goal has already been moved
                
                randomInteger = randomObject.nextInt(100); 
                if(randomInteger < 60) continue;              //60% chances for goal not to move
                
                found=false;
                //search the neighborhood
                for(target1 = i-1; target1<i+2; target1++)
                 for(target2 = j-1; target2<j+2; target2++)
                     if(goals[target1][target2].isHit() && once[target1][target2] == true) {
                         found=true;
                         break;             
                        }
                //now if we found a hit goal in the neighborhood, it is in (target1, target2) and we have found=true
                if(found == false) continue;
                
                //swap (i,j) with (target1, target2)
                Goal temp = goals[i][j];
                goals[i][j]=goals[target1][target2];
                goals[target1][target2]=temp;
                
                once[i][j] = false;
                once[target1][target2] = false;         //to prevent moving it second time
            }
        }
        
        
        
    }

    
    /**
     * Compares the record of the current game play with those of previous game plays and update the highscore records. The method is called by the program when the game finishes. The highscore table is no bigger than 10 records. Every player appears in the table at most once.
     * 
     * @param highScoreRecords  old highscore table
     * @param name              name of the player
     * @param level             difficulty level of the current game
     * @param score             score of the current game
     * 
     * @return updated highscore table
     */
    
    
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // write your code after this line
        if(highScoreRecords.length == 0){
            GameRecord[] returnArray = new GameRecord[1];
            returnArray[0]= new GameRecord(name, level, score);
            return returnArray;
        }       //1. satisfied
        
        int sameName;   //index of entry with the same name, if exists, highScoreRecords.length otherwise
        
        for(sameName=0; sameName<highScoreRecords.length; sameName++)
            if(name.equals(highScoreRecords[sameName].getName()))
              break;
        
        if(sameName < highScoreRecords.length && (highScoreRecords[sameName].getScore() > score || (highScoreRecords[sameName].getScore() == score && highScoreRecords[sameName].getLevel() >= level)))
            return highScoreRecords;    // 5. satisfied
       
        int firstLess;    //first entry with less score than just achieved
        for(firstLess=0; firstLess<highScoreRecords.length; firstLess++)
                if(highScoreRecords[firstLess].getScore() < score || (highScoreRecords[firstLess].getScore() == score && highScoreRecords[firstLess].getLevel() < level)) 
                  break;
        
        if(firstLess == 10) 
            return highScoreRecords; 
                
        if(sameName < highScoreRecords.length){
            
            GameRecord[] returnArray = new GameRecord[highScoreRecords.length];
            int i=0, j=0; //indices of highScoreRecords, returnArray
            
            //first part of high score (scores better than achieved) is unchanged
            while(j<firstLess){
                returnArray[i] = highScoreRecords[j];
                j++;
                i++;
            }   
            
            //then our score
            returnArray[i] = new GameRecord(name, level, score);
            i++;
            
            //continue copying
            while(j<sameName){
                returnArray[i] = highScoreRecords[j];
                j++;
                i++;
            }
            
            j++;        //skip old score
            
            //last part 
            while(j<highScoreRecords.length){
                returnArray[i] = highScoreRecords[j];
                j++;
                i++;
            }
            
            return returnArray;
        }       //that was 4.
        
        //now we are sure that our name didn't exist in high scores
        
        if(highScoreRecords.length < 10){
                        
            GameRecord[] returnArray = new GameRecord[highScoreRecords.length+1];
            int i=0, j=0; //indices of highScoreRecords, returnArray
            
            //first part of high score (scores better than achieved) is unchanged
            while(j<firstLess){
                returnArray[i] = highScoreRecords[j];
                j++;
                i++;
            }   
            
            //then our score
            returnArray[i] = new GameRecord(name, level, score);
            i++;
            
            //last part 
            while(j<highScoreRecords.length){
                returnArray[i] = highScoreRecords[j];
                j++;
                i++;
            }
            
            return returnArray;
        }       //2. done
        
        //highScoreRecords.length = 10
        GameRecord[] returnArray = new GameRecord[10];
        
        int i=0, j=0; //indices of highScoreRecords, returnArray
        
        //first part of high score (scores better than achieved) is unchanged
        while(j<firstLess){
            returnArray[i] = highScoreRecords[j];
            j++;
            i++;
        }   
        
        //then our score
        returnArray[i] = new GameRecord(name, level, score);
        i++;
        
        //last part 
        while(j<highScoreRecords.length-1){
            returnArray[i] = highScoreRecords[j];
            j++;
            i++;
        }
        
        return returnArray;
    }
    
    
}
