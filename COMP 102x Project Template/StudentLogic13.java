import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
    
    
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        // write your code after this line
        int imageindex = ((depthImages.length - 1) * (currentStep - initialStep)) / (finalStep - initialStep);
        ColorImage football = depthImages[imageindex];
        double scale = initialScale + ((finalScale - initialScale) * (double)(currentStep - initialStep)) / (double)(finalStep - initialStep);
        int x = initialX + ((finalX - initialX) * (currentStep - initialStep)) / (finalStep - initialStep);
        int y = initialY + ((finalY - initialY) * (currentStep - initialStep)) / (finalStep - initialStep);
        football.setScale(scale);
        football.setX(x);
        football.setY(y);
        return football;
        
        
        

    }

   /**
     * updateGoalPositions() updates the positions of the goals.
     * @param goals[][]is a 2D array of Goal objects that represents the goals displayed on the main game screen.
     */
    public void updateGoalPositions(Goal goals[][])
    {
        int rows = goals.length;    //no of rows of goals
        int cols = goals[0].length; //no of goal in a row

     //array processed[][] used to keep track of updated goals
        boolean processed[][] = new boolean[rows][cols];

      //initially set all goals to not processed i.e. false
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
                processed[i][j] = false; 

        }

       /** for all goals check if goal is not hit and goal is not processed already and goal 
          is of type 2  i.e. movable then update goal position.
        */
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if(!goals[i][j].isHit() && !processed[i][j] && goals[i][j].getType() == 2)
                {
                    Random random = new Random();
                    /**select a random row and                          
                      assign it to dRow variable
                       */
                    int dRow = random.nextInt(3) - 1;
                    int dCol;  //for storing random column
              for(dCol = random.nextInt(3) - 1; dRow == 0 && dCol == 0; dCol = random.nextInt(3) - 1);

/*add random row number to i and store in Variable newRow to    get new Row*/
                    int newRow = i + dRow;  
/*add random col number to j and store in Variable newCol to    get new Col*/

                    int newCol = j + dCol;

    /** if newRow and newCol are from 0 to less than rowlength and column lenth respectively AND that goal is already 
     hitted then exchange the goal positions and set 
     processed of newrow,newcolumn true.
    */
                    if(newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && goals[newRow][newCol].isHit())
                    {
                        Goal temp = goals[i][j];
                        goals[i][j] = goals[newRow][newCol];
                        goals[newRow][newCol] = temp;
                        processed[newRow][newCol] = true;
                    }
                }
              //set goal at i,j to processed.
                processed[i][j] = true;    
            }

        }

    }



    
     public GameRecord[] updateHighScoreRecords(GameRecord highScoreRecords[], String name, int level, int score)
    {
        boolean userExist = isUserExist(highScoreRecords, name);
        if(userExist)
        {
            int size = highScoreRecords.length;
            int i = 0;
            do
            {
                if(i >= size)
                    break;
                if(highScoreRecords[i].getName().equals(name))
                {
                    if(highScoreRecords[i].getScore() < score)
                    {
                        highScoreRecords[i].setScore(score);
                        highScoreRecords[i].setLevel(level);
                    } else
                    if(highScoreRecords[i].getScore() == score && highScoreRecords[i].getLevel() < level)
                        highScoreRecords[i].setLevel(level);
                    break;
                }
                i++;
            } while(true);
        } else
        if(highScoreRecords.length == 10)
        {
            int minIndex = minPos(highScoreRecords, highScoreRecords.length);
            boolean condition2 = highScoreRecords[minIndex].getScore() < score;
            boolean condition3 = highScoreRecords[minIndex].getScore() == score && highScoreRecords[minIndex].getLevel() < level;
            if(condition2 || condition3)
                highScoreRecords[minIndex] = new GameRecord(name, level, score);
        } else
        {
            GameRecord newRecords[] = new GameRecord[highScoreRecords.length + 1];
            System.arraycopy(highScoreRecords, 0, newRecords, 0, highScoreRecords.length);
            newRecords[newRecords.length - 1] = new GameRecord(name, level, score);
            highScoreRecords = newRecords;
        }
        sortArray(highScoreRecords, highScoreRecords.length);
        return highScoreRecords;
    }

    private boolean isUserExist(GameRecord records[], String user)
    {
        int size = records.length;
        for(int i = 0; i < size; i++)
            if(records[i].getName().equals(user))
                return true;

        return false;
    }

    private void sortArray(GameRecord array[], int size)
    {
        for(int remaining = size; remaining > 1;)
        {
            int minIndex = minPos(array, remaining);
            swapArrayRows(array, minIndex, --remaining);
        }

    }

    private int minPos(GameRecord array[], int size)
    {
        int minIndex = 0;
        for(int i = 1; i < size; i++)
        {
            if(array[i].getScore() < array[minIndex].getScore())
            {
                minIndex = i;
                continue;
            }
            if(array[i].getScore() == array[minIndex].getScore() && array[i].getLevel() <= array[minIndex].getLevel())
                minIndex = i;
        }

        return minIndex;
    }

    private void swapArrayRows(GameRecord array[], int index1, int index2)
    {
        GameRecord temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}

