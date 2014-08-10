import java.util.Random;
import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
	
	
	public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
	    // write your code after this line
	     int currentFrameNumber = ((depthImages.length - 1) * (currentStep - initialStep)) / (finalStep - initialStep); 
	     ColorImage football = depthImages[currentFrameNumber]; 
	     double scale = initialScale + ((finalScale - initialScale) * (double)(currentStep - initialStep)) / (double)(finalStep - initialStep); 
	     int x = initialX + ((finalX - initialX) * (currentStep - initialStep)) / (finalStep - initialStep); 
	     int y = initialY + ((finalY - initialY) * (currentStep - initialStep)) / (finalStep - initialStep); 
	     football.setScale(scale);
	     football.setX(x); 
	     football.setY(y); 
	     return football;
	    
	    
	    

	}


	public void updateGoalPositions(Goal[][] goals) {
		// write your code after this line
		int rows = goals.length; int cols = goals[0].length; 
		boolean processed[][] = new boolean[rows][cols]; 
		for(int i = 0; i < rows; i++) 
		 { 
		    for(int j = 0; j < cols; j++) 
		    processed[i][j] = false; 
		  } 
		    for(int i = 0; i < rows; i++) 
		    {
		        for(int j = 0; j < cols; j++) 
		         {
		            if(!goals[i][j].isHit() && !processed[i][j] && goals[i][j].getType() == 2) 
		            { 
		                Random random = new Random(); 
		                int dRow = random.nextInt(3) - 1; 
		                int dCol; 
		                 for(dCol = random.nextInt(3) - 1; dRow == 0 && dCol == 0; dCol = random.nextInt(3) - 1);
		                int newRow = i + dRow; int newCol = j + dCol; 
		                if(newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && goals[newRow][newCol].isHit()) 
		                { 
		                    Goal temp = goals[i][j]; 
		                    goals[i][j] = goals[newRow][newCol]; 
		                    goals[newRow][newCol] = temp; 
		                    processed[newRow][newCol] = true; 
		                  }
		                  } 
		                  processed[i][j] = true; 
		              } 
		          } 
		      }

		
		
		
		
	

	
	public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
		// write your code after this line
		
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
