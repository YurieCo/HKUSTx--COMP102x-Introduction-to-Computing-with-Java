import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
    /**
     * 
     * return depth Image
     * 
     * The depthImages array contains an array of football images of different depths. Select the correct depth image according to the current step of the shooting animation
     * @param:depthImages-an array of ColorImage
     * The parameters initialStep, currentStep and finalStep represent the initial step, the current step and the final step of the shooting animation respectively:
     * @param:initialStep
     * @param:currentStep
     * @param:finalStep
     * Set the scale of the selected depth image. The correct scale of the depth image can be calculated by the following formula, where initialScale and finalScale represent the initial and final scales of the football respectively.
     * @param:initialScale
     * @param:finalScale
     * Set the x position of the selected depth image. The correct x position of the depth image can be calculated by the following formula, where initialX and finalX represent the initial and final x positions of the football respectively:
     * @param:initialX
     * @param:final
     * Set the y position of the selected depth image. The correct y position of the depth image can be calculated by the following formula, where initialY and finalY represent the initial and final y positions of the football respectively.
     * @param:initialY
     * @param:finalY
     */
    
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        // write your code after this line
        int imageIndex=(depthImages.length-1)*(currentStep-initialStep)/finalStep-initialStep;
        int xPosition=initialX+(finalX-initialX)*(currentStep-initialStep)/finalStep-initialStep;
        int yPosition=initialY+(finalY-initialY)*(currentStep-initialStep)/finalStep-initialStep;
        double scale=initialScale+(finalScale-initialScale)*(currentStep-initialStep)/finalStep-initialStep;
        ColorImage selectedDepthImage=depthImages[imageIndex];
        selectedDepthImage.setX(xPosition);
        selectedDepthImage.setY(yPosition);
        selectedDepthImage.setScale(scale);
        return selectedDepthImage;

    }

    /**
     * Goals that are stationary should not be changed
     * Goals that are movable should be moved to an adjacent position (horizontally, vertically and diagonally) by some chance, 
     * GIVEN THAT the goal in the adjacent position is already being hit.
     * If there are multiple adjacent positions available, moving to either one is acceptable.
     * Each goal should be moved ONCE only for each call of this method. For example, if a goal has moved from position 1 to position 2, it should not be moved back to position 1 in the same call of this method.
     */
    public void updateGoalPositions(Goal[][] goals) {
        // write your code after this line
        int i,j;
        Goal[][] temp = goals.clone();
        for (i=0;i<goals.length;i++)
        {
            for (j=0;j<goals[i].length;j++)
            {
                if (goals[i][j].getType()==2 && goals[i][j]==temp[i][j] )
                {
                    
                    if (i<goals.length && j<goals[i].length&& i>0 && j>0)
                    {
                        if (goals[i+1][j].isHit()==true ) goals[i][j]=temp[i+1][j];
                        else if (goals[i][j+1].isHit()==true ) goals[i][j]=temp[i][j+1];
                        else if (goals[i-1][j].isHit()==true ) goals[i][j]=temp[i-1][j];
                        else if (goals[i][j-1].isHit()==true ) goals[i][j]=temp[i][j-1];
                        else if (goals[i-1][j-1].isHit()==true ) goals[i][j]=temp[i-1][j-1];
                        else if (goals[i+1][j+1].isHit()==true ) goals[i][j]=temp[i+1][j+1];
                        else if (goals[i-1][j+1].isHit()==true ) goals[i][j]=temp[i-1][j+1];
                        else if (goals[i+1][j-1].isHit()==true ) goals[i][j]=temp[i+1][j-1];
                    }
                    else if(i==0 && j==goals[i].length-1)
                    {
                        if (goals[i+1][j].isHit()==true ) goals[i][j]=temp[i+1][j];
                        else if (goals[i][j-1].isHit()==true ) goals[i][j]=temp[i][j-1];
                        else if (goals[i+1][j-1].isHit()==true ) goals[i][j]=temp[i+1][j-1];
                    }
                    else if(i==goals.length-1 && j==0)
                    {
                        if (goals[i-1][j].isHit()==true ) goals[i][j]=temp[i-1][j];
                        else if (goals[i][j+11].isHit()==true ) goals[i][j]=temp[i][j+1];
                        else if (goals[i-1][j+1].isHit()==true ) goals[i][j]=temp[i-1][j+1];
                    }
                    else if(i==goals.length-1 && j==goals[i].length-1)
                    {
                        if (goals[i-1][j].isHit()==true ) goals[i][j]=temp[i-1][j];
                        else if (goals[i][j-1].isHit()==true ) goals[i][j]=temp[i][j-1];
                        else if (goals[i-1][j-1].isHit()==true ) goals[i][j]=temp[i-1][j-1];
                    }
                    else if(i==0 && j==0)
                    {
                        if (goals[i+1][j].isHit()==true ) goals[i][j]=temp[i+1][j];
                        else if (goals[i][j+1].isHit()==true ) goals[i][j]=temp[i][j+1];
                        else if (goals[i+1][j+1].isHit()==true ) goals[i][j]=temp[i+1][j+1];
                    }
                }   
            }
        }
        
        
        
    }

    /**
     * Updates the highscore records.
     * 
     * This method reads the previous saved game records and updates it with the current record:
     * If there are no previous game play records, return a GameRecord array of size 1 containing the current record.
     * If the player's name doesn't exist in the previous records and there are less than 10 previous records, return a GameRecord array containing all the previous records and the new record.
     * If the player's name doesn't exist in the previous records and there are 10 previous records, return a GameRecord array containing the best 10 records. 
     * A record is better than the other one if it has a higher score, or the two records have the same score, but it has a higher level. 
     * If the player's name exists in the previous records and the current record is better than the previous record, return a GameRecord array containing all the previous records, but with the score and level of the player updated to those of the current game play.
     * If the player's name exists in the previous records and the current record is the same or is worse than the previous record, return a GameRecord array containing all the previous records.
     * In all of the cases listed above, the records in the returned GameRecord array is sorted first by score, and then by level in descending order.
     * 
     */
    
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // write your code after this line
       int length=highScoreRecords.length;
       boolean nameExisted=false; 
       
       if (length==0)
       {
           GameRecord[] newRecord1=new GameRecord[1];
           newRecord1[0]=new GameRecord(name, level, score);
           return newRecord1;
       }
       
       for (int i=0;i<length;i++)
       {
           if (highScoreRecords[i].getName().equals(name))
           {
               nameExisted=true;
               if (highScoreRecords[i].getScore()<score || (highScoreRecords[i].getScore()==score && highScoreRecords[i].getLevel()<level))
               {
                   highScoreRecords[i].setScore(score);
                   highScoreRecords[i].setLevel(level);
               }
               for (int m=0;m<length;m++)
                   {
                       for (int n=m+1;n<length;n++)
                       {
                           if (highScoreRecords[m].getScore()<highScoreRecords[n].getScore() || (highScoreRecords[m].getScore()==highScoreRecords[n].getScore()&&highScoreRecords[m].getLevel()<highScoreRecords[n].getLevel()))
                           {
                               GameRecord temp = highScoreRecords[m];
                               highScoreRecords[m]=highScoreRecords[n];
                               highScoreRecords[n]=temp;
                           }
                       }
                   }
               return highScoreRecords;
           }
       }
       if (nameExisted==false)
       {
           if(length<10)
           {
                GameRecord[] newRecord2=new GameRecord[length+1];
                for (int i=0;i<length;i++)
                {
                    newRecord2[i]=highScoreRecords[i];
                }
                newRecord2[length]=new GameRecord(name,level,score);
                for (int m=0;m< newRecord2.length;m++)
                   {
                       for (int n=m+1;n<newRecord2.length;n++)
                       {
                           if (newRecord2[m].getScore()<newRecord2[n].getScore() || (newRecord2[m].getScore()==newRecord2[n].getScore()&&newRecord2[m].getLevel()<newRecord2[n].getLevel()))
                           {
                               GameRecord temp = newRecord2[m];
                               newRecord2[m]=newRecord2[n];
                               newRecord2[n]=temp;
                           }
                       }
                   }
                return newRecord2;
           }
           else
           {
               int minScore=highScoreRecords[0].getScore();
               int smallerLevel=0;
               for (int i=0;i<length;i++)
               {
                   if (highScoreRecords[i].getScore()<minScore)
                   {
                       minScore=highScoreRecords[i].getScore();
                   }
               }
               for (int i=0;i<length;i++)
               {
                   if (highScoreRecords[i].getScore()==minScore)
                   {
                       smallerLevel=highScoreRecords[i].getLevel();
                       break;
                   }
               }
               for (int i=0;i<length;i++)
               {
                   if (highScoreRecords[i].getScore()==minScore && highScoreRecords[i].getLevel()<smallerLevel)
                   {
                       smallerLevel=highScoreRecords[i].getLevel();
                       break;
                   }
               }
               for (int i=0;i<highScoreRecords.length;i++)
               {
                   if (highScoreRecords[i].getScore()==minScore && highScoreRecords[i].getLevel()==smallerLevel)
                   {
                       highScoreRecords[i].setLevel(level);
                       highScoreRecords[i].setName(name);
                       highScoreRecords[i].setScore(score);
                       break;
                   }
               }
               for (int m=0;m<length;m++)
                   {
                       for (int n=m+1;n<length;n++)
                       {
                           if (highScoreRecords[m].getScore()<highScoreRecords[n].getScore() || (highScoreRecords[m].getScore()==highScoreRecords[n].getScore()&&highScoreRecords[m].getLevel()<highScoreRecords[n].getLevel()))
                           {
                               GameRecord temp = highScoreRecords[m];
                               highScoreRecords[m]=highScoreRecords[n];
                               highScoreRecords[n]=temp;
                           }
                       }
                   }
               return highScoreRecords;
           }
           
       }
       return null;
    }
    
}
