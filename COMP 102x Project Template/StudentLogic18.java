import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

//This class implements Comparator to let us compare GameRecord instances
 

public class StudentLogic implements GameLogic, java.util.Comparator<GameRecord>{
    
    /**
     * picks the right image to be drawn to simulate moving ball
     * @param depthImages array of ColorImage
     * @param int initialStep  
     * @param int currentStep
     * @param int finalStep
     * @param double initialScale
     * @param double finalScale
     * @param int initialX
     * @param int finalX
     * @param int initialY
     * @param int finalY
     */
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        // write your code after this line
        
        System.out.println("initial step: "+ initialStep);
        System.out.println("current step: "+ currentStep);
        System.out.println("final step: "+ finalStep);
       /**
        * if we divide directly, java will return an int
        * 
        * took me forever to figure this one out!!!!!
        */
        double ratio = ((currentStep-initialStep));
        ratio = ratio / (finalStep-initialStep);
        System.out.println("ratio:" + ratio);
        double imageIndex =((depthImages.length-1)*ratio);
        System.out.println("image index: "+ imageIndex);
        double xPosition = initialX +((finalX-initialX)*ratio);
        System.out.println("xPosition: "+ xPosition);
        double yPosition = initialY +((finalY-initialY)*ratio);
        System.out.println("yPosition: "+ yPosition);
        double scale  = (finalScale-initialScale)*ratio + initialScale ;
        System.out.println("scale: "+ scale);
        ColorImage pickedImage = depthImages[(int)imageIndex];
        pickedImage.setX((int)xPosition);
        pickedImage.setY((int)yPosition);
        pickedImage.setScale(scale);
        
        
        
        return pickedImage;
    }
    
    /**
     * update goals position
     * @param goals the 2D-array of goals to be updated 
     */


    public void updateGoalPositions(Goal[][] goals) {
        // write your code after this line
        
        //An array list that will contain moved goals
        java.util.ArrayList<Goal> movedGoals = new java.util.ArrayList();
        /**
         * rowIndex and columnIndex will be needed to recall current position
         */
        int rowIndex=0;
        int columnIndex=0;
        for(Goal[] rowOfGoals: goals){
            /**
             * we are about to start checking a new column, initialize index to 0
             */
            columnIndex = 0; 
            for(Goal goal : rowOfGoals){
                 /**
                  * check that current goal hasn't been moved yet
                  */
                 if(!movedGoals.contains(goal)){
                     /**
                      * check that this goal is movable and that it's not already hit
                      */
                     if(goal.getType()==Goal.MOVABLE && !goal.isHit()){
                                                  
                         /**
                          * depending on the value of rowIndex and columnIndex, we check what are the possible moves 
                          * 
                          */
                         
                         /**
                          * iterate through adjacent goals
                          */
                         int k=0;
                         int s=0;
                         /**
                          * An array list that will contain all possible moves (positions)
                          */
                         java.util.ArrayList<int[][]> possibleMoves = new java.util.ArrayList();
                         for(k=rowIndex-1;k<=rowIndex+1;k++){
                             for(s=columnIndex-1;s<=columnIndex+1;s++){
 
                                /**
                                * we might get negative indices or indices that go beyond the matrix goals limits 
                                * check if current goal is in the matrix bounderies, check, check. 
                                */
                                    
                                if(k >= 0 && s >= 0 && k < goals.length && s < rowOfGoals.length ){
                                    
                                   /**
                                   * current goal is not an adjacent one ;)
                                   */
                                   if(k==rowIndex && s==columnIndex){
                                       continue;
                                   } 
                                   /**
                                   * To allow swaping between two goals, the second goal has to be hit
                                   */
                                   if(goals[k][s].isHit()){
                                      
                                       possibleMoves.add(new int[][]{{k,s}});
                                   }
                                    
                                }    
                             }
                         }
                         
                         /**
                         * Once here, we figured out all possible moves
                         * we choose one move randomly
                         * 
                         * We only do this if we actually found some goals that we can swap!
                         */
                                 
                         if(possibleMoves.size()>0){
                              Random r = new Random();
                                 
                              int choosenMoveIndex = r.nextInt(possibleMoves.size());
                              /**
                              * We swap now
                              */
                            
                              
                              Goal tempGoal = goals[rowIndex][columnIndex];
                              goals[rowIndex][columnIndex] = goals[possibleMoves.get(choosenMoveIndex)[0][0]][possibleMoves.get(choosenMoveIndex)[0][1]];
                              goals[possibleMoves.get(choosenMoveIndex)[0][0]][possibleMoves.get(choosenMoveIndex)[0][1]]=tempGoal;
                               
                              /**
                              * Only one thing that needs to be done
                              * mark goal as being moved
                              * 
                              * NB: there is not need to move goals that are already hit
                              */
                              
                              movedGoals.add(goals[possibleMoves.get(choosenMoveIndex)[0][0]][possibleMoves.get(choosenMoveIndex)[0][1]]);
                                    
                         }
                                    
                     }
                 }
                 
                  
               columnIndex++;
             }
             rowIndex++;
             
              
        }
    }

    /**
     * update highScores array and sorts it in ascending order
     * @param highScoreRecords previous highScoreRecords 
     * @param name current player name
     * @param level current game level
     * @param score score abtained in the current game session
     * 
     * @return updated highScoreRecords as an array
     */
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // write your code after this line
    
       /**
        * 1st case
        */
       if (highScoreRecords==null || highScoreRecords.length==0){
           highScoreRecords = new GameRecord[1];
           highScoreRecords[0] = new GameRecord(name,level,score);
           return highScoreRecords;
       }
       
       
       
       
       /**
        * check if name already present
        */
       boolean playerNameAlreadyPresent =false;
       
       //an integer to hold the position of the current player's name in the array if it exists
       int indeOfCurrentPlayer = 0;
       for(GameRecord record: highScoreRecords){
           if(record.getName().equals(name)){
               playerNameAlreadyPresent=true;
              
               break;
           }
           indeOfCurrentPlayer++;
       }
       /**
        * second case
        */
       if(!playerNameAlreadyPresent && highScoreRecords.length < 10){
           GameRecord[] newHighScoreRecords = new GameRecord[highScoreRecords.length+1];
           //copy previous records into new array
           int index = 0;
           for(GameRecord record: highScoreRecords){
              newHighScoreRecords[index]=record;
              
              index++;
           }
           
           newHighScoreRecords[newHighScoreRecords.length-1]=new GameRecord(name,level,score);
           //we just need to sort the array and return it
           java.util.Arrays.sort(newHighScoreRecords, this);
           
           return newHighScoreRecords;
       }
       
       /**
        * 3rd case
        */
       
       if(!playerNameAlreadyPresent && highScoreRecords.length == 10){
                  
           
           GameRecord[] newHighScoreRecords = new GameRecord[11];
           int index = 0;
           
           //copy previous records into new array
           for(GameRecord record: highScoreRecords){
              newHighScoreRecords[index]=record;
              
              index++;
           }
           
          newHighScoreRecords[10]=new GameRecord(name,level,score);
          //we just need to sort the array  go throught the best ten records, construct a new array and return it
          java.util.Arrays.sort(newHighScoreRecords, this);
          //return only an array of ten records, use 
          return java.util.Arrays.copyOf(newHighScoreRecords,10,GameRecord[].class);
           
           
       }
       
       /**
        * 4th case
        */
       if(playerNameAlreadyPresent){
           
           GameRecord tempRecord = new GameRecord(name,level,score);
           if(tempRecord.getScore()>highScoreRecords[indeOfCurrentPlayer].getScore() ||(tempRecord.getScore()==highScoreRecords[indeOfCurrentPlayer].getScore() && tempRecord.getLevel()>highScoreRecords[indeOfCurrentPlayer].getLevel())){
               highScoreRecords[indeOfCurrentPlayer] = tempRecord;
               
               //sort here and return the array
               java.util.Arrays.sort(highScoreRecords, this);
               return highScoreRecords;
           }
           
           //return the array as it was from the beginning
           return highScoreRecords;
       }
       
       
       
       //return the array as it was from the beginning-- Well, program will never reach this point normally
       return highScoreRecords;
       
       
    }
    
    /**
     * Implements compare from the Comparator interface, 
     * implemented such that Arrays.sort will sort in ascending order
     * 
     * @param GameRecord a
     * @param GameRecord b
     * @return -1 if a is greater than b, 0 if a==b, +1 otherwise
     */
    public int compare(GameRecord a, GameRecord b){
       if(a.getScore()>b.getScore()){
           return -1;
       }
                    
       if(a.getScore()== b.getScore() && a.getLevel()>b.getLevel()){
           return -1;
       }
                    
       if(a.getScore()== b.getScore() && a.getLevel()== b.getLevel()){
            return 0;
       }
       return 1;
    }
    
}


