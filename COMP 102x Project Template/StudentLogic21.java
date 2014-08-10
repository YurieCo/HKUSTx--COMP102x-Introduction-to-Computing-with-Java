import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
    
    
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        // write your code after this line
        int imageIndex=0;
        double scale=0.0, xPosition, yPosition;
        imageIndex = (depthImages.length-1) * ((currentStep-initialStep)/(finalStep-initialStep));
        xPosition = (double)(initialX + (finalX - initialX) * (currentStep-initialStep)/(finalStep-initialStep));
        yPosition = (double)(initialY + (finalY - initialY) * (currentStep-initialStep)/(finalStep-initialStep));
        scale = (double)initialScale + ((double)finalScale-(double)initialScale) * (((double)currentStep-(double)initialStep)/((double)finalStep-(double)initialStep));
        depthImages[imageIndex].setX((int)(xPosition));
        depthImages[imageIndex].setY((int)(yPosition));
        depthImages[imageIndex].setScale(scale);
        return depthImages[imageIndex];
    }


    public void updateGoalPositions(Goal[][] goals) {
        // write your code after this line
        Random random = new Random();
        for (int row=0; row<goals.length; row++) {
            for (int col=0; col<goals[row].length; col++) {
                // check if goal is moveable
                if (goals[row][col].getType() == 2) {
                    int randomX = random.nextInt(2-(-1)) + -1;
                    int randomY = random.nextInt(2-(-1)) + -1;
                    // check if moving to a spot outside of array
                    if (((row == 0) && (randomX == -1)) || ((col == 0) && (randomY == -1)))
                        continue;
                    // check to see if moving outside of array    
                    else if (((row == goals.length-1) && (randomX == 1)) || ((col == goals[row].length-1) && (randomY == 1)))
                        continue;
                    // check to see if new spot is empty and move the goal there.    
                    else {
                        if ((goals[row+randomX][col+randomY].isHit() == true) && (goals[row][col].isHit() == false)) {
                            goals[row+randomX][col+randomY].setHit(false);
                            goals[row][col].setHit(true);
                        }
                            
                    }
                        
                }
            }
        }
    }

    
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // write your code after this line
        GameRecord record = new GameRecord (name, level, score);
        boolean exists = false;
        // if list is empty, insert name in to array of length 1
        if (highScoreRecords.length == 0) {
            highScoreRecords = new GameRecord[1]; 
            highScoreRecords[0] = record;
        }
        
        else
        {
            // if there a less than 10 records, add new record or update score if better.
            if (highScoreRecords.length < 10)
            {
                //search to see if name already exists
                for (int i=0; i<highScoreRecords.length; i++)
                {
                    if (highScoreRecords[i].getName().equals(record.getName()))
                    {
                        exists = true;
                        if (record.getScore() > highScoreRecords[i].getScore()) {
                            highScoreRecords[i] = record;
                        }
                        break;
                    }
                }
                //name doesn't exist...need to add it to the scores
                if (!exists) {
                    GameRecord[] newList = new GameRecord[highScoreRecords.length+1];
                    System.arraycopy(highScoreRecords, 0, newList, 0, highScoreRecords.length);
                    highScoreRecords = new GameRecord[newList.length];
                    System.arraycopy(newList, 0, highScoreRecords, 0, newList.length);
                    highScoreRecords[highScoreRecords.length-1] = record;
                }
            }
            
            // check to see if new score is better than any of the 10 records stored.
            else 
            {
                //search to see if name already exists
                for (int i=0; i<highScoreRecords.length; i++)
                {
                    if (highScoreRecords[i].getName().equals(record.getName()))
                    {
                        exists = true;
                        if (record.getScore() >= highScoreRecords[i].getScore()) {
                            highScoreRecords[i] = record;
                        }
                        break;
                    }
                }
                
                //if name is new, check to see if score and level are better than any in record list.
                if (!exists) {
                    for (int i=0; i<highScoreRecords.length; i++) {
                        if (record.getScore() > highScoreRecords[i].getScore()) {
                            GameRecord[] temp = new GameRecord[1];
                            //shift array by 1
                            for (int j=highScoreRecords.length-1; j>=i; j--) {
                                if ((j-1)>=0)
                                    highScoreRecords[j] = highScoreRecords[j-1];
                            }
                            highScoreRecords[i] = record;
                            break;
                        }
                        else if ((record.getScore() == highScoreRecords[i].getScore()) && (record.getLevel() > highScoreRecords[i].getLevel())) {
                            GameRecord[] temp = new GameRecord[1];
                            //shift array by 1
                            for (int j=highScoreRecords.length-1; j>=i; j--) {
                                if (j==0)
                                    break;
                                else 
                                    highScoreRecords[j] = highScoreRecords[j-1];
                            }
                            highScoreRecords[i] = record;
                            break;
                        }
                    }
                }
            }
            
            //sort scores
            for (int i=0; i<highScoreRecords.length; i++) {
                for (int j=i+1; j<highScoreRecords.length; j++) {
                    if (highScoreRecords[j].getScore() > highScoreRecords[i].getScore()) {
                        GameRecord[] temp = new GameRecord[1];
                        temp[0] = highScoreRecords[j];
                        highScoreRecords[j] = highScoreRecords[i];
                        highScoreRecords[i] = temp[0];
                    }
                    if ((highScoreRecords[j].getScore() >= highScoreRecords[i].getScore()) && (highScoreRecords[j].getLevel() > highScoreRecords[i].getLevel()))
                    {
                        GameRecord[] temp = new GameRecord[1];
                        temp[0] = highScoreRecords[j];
                        highScoreRecords[j] = highScoreRecords[i];
                        highScoreRecords[i] = temp[0];
                        
                    }
                }
            }
        }
        
        return highScoreRecords;
    }
}
