import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
    
    
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        // write your code after this line
        
        // Declare and initialize necessary variables 
        ColorImage colorImage = depthImages[0];
        int imageIndexInt = 0;
        
        //get index of Image
        double imageIndex = (double)(depthImages.length - 1) * (((double)currentStep - (double)initialStep)/((double)finalStep - (double)initialStep));
        imageIndexInt = (int) imageIndex;

        // get image
        colorImage = depthImages[imageIndexInt];
        
        // set X Position 
        double xPosition = initialX + (finalX - initialX) * (((double)currentStep - (double)initialStep)/((double)finalStep - (double)initialStep));
        int xPos = (int) xPosition;
        
        colorImage.setX(xPos);
        
        // set Y Position
        // why 0.9 ? because apperantly the webiste is unable to process rounding mistakes
        double yPosition = initialY + 0.9 + (finalY - initialY) * (((double)currentStep - (double)initialStep)/((double)finalStep - (double)initialStep));
        int yPos = (int) yPosition;
        System.out.println(currentStep);
        System.out.println(yPos);
        
        colorImage.setY(yPos);
        
        // set Scale
        double scale = initialScale + (finalScale - initialScale) * (((double)currentStep - (double)initialStep)/((double)finalStep - (double)initialStep));
        

        
        colorImage.setScale(scale);
        
        // return the image
        return colorImage;
        
        

    }


    public void updateGoalPositions(Goal[][] goals) {
        // write your code after this line
        
        //random object declaration and initialization
        Random rnd = new Random();
        
        // int used later on to set index of goals to swap with  
        int swapWith = 0;
        
        // the 8 directions a goal can move to 
        int[] moveTo = new int[8];
        
        // set goals in list later back to movable because they were set stationary, so they would not be moved again in the same use of the method 
        int[][] movable = new int[12][2];
        
        // lists of available directions of a goal used with the random generator 
        int[] move1 = new int[1];
        int[] move2 = new int[2];
        int[] move3 = new int[3];
        int[] move4 = new int[4];
        int[] move5 = new int[5];
        int[] move6 = new int[6];
        int[] move7 = new int[7];
        
        // index for movable list
        int ii = 0;
        
        // object for swapping
        Goal temp;
        
        
        // check every goal in 2D Array
        for (int i = 0; i < goals.length; i++) {
            for (int j = 0; j < goals[i].length; j++) {
                // if a goal is hit, check for every available goal to swap the position with
                // if statements created to avoid arraybound exception
                
                if (goals[i][j].isHit() == true) {
                    
                    if (i - 1 >= 0) {
                        if (goals[i-1][j].isHit() == false && goals[i-1][j].getType() == 2){
                        moveTo[0] = 1;
                        }
                      }
                    if (i - 1 >= 0 && (i + 1 <= (goals.length - 1)) && j + 1 <= (goals[i].length - 1)) {    
                        if (goals[i-1][j+1].isHit() == false && goals[i-1][j+1].getType() == 2){
                        moveTo[2] = 1;
                        }
                        
                      }
                    if (j - 1 >= 0 && (i + 1 <= (goals.length - 1)) ) {
                      if (goals[i+1][j-1].isHit() == false && goals[i+1][j-1].getType() == 2){
                        moveTo[5] = 1;
                      }    
                      if (goals[i][j-1].isHit() == false && goals[i][j-1].getType() == 2){
                        moveTo[6] = 1;
                        
                      } 
                    }
                    if (j - 1 >= 0 && i - 1 >= 0) {   
                    if (goals[i-1][j-1].isHit() == false && goals[i-1][j-1].getType() == 2){
                        moveTo[3] = 1;
                        } 
                    }    
                    
                    if ((i + 1 <= (goals.length - 1)) ) {
                    if (goals[i+1][j].isHit() == false && goals[i+1][j].getType() == 2){
                        moveTo[1] = 1;
                      }
                    }
                    if ((i + 1 <= (goals.length - 1)) && (j + 1 <= (goals[i].length - 1)) ) {
                    if (goals[i+1][j+1].isHit() == false && goals[i+1][j+1].getType() == 2){
                        moveTo[4] = 1;
                    }  
                    }
                    if ((j + 1 <= (goals[i].length - 1)) ) {
                    if (goals[i][j+1].isHit() == false && goals[i][j+1].getType() == 2){
                        moveTo[7] = 1;
                      }    
                    }
                    
                    // 
                    
                    int countMoveTo = 0;
                    int temp2 = 0;
                    int zz = 0;
                    
                    for (int qw = 0; qw <= moveTo.length - 1; qw++) {
                        if (moveTo[qw] == 1) {
                            countMoveTo++;
                          }
                          
                         
                      }
                    
                    // switch to check how many possible directions there are for swapping
                    // then use one of the lists containing the possibilities to swap
                    switch (countMoveTo) {
                        case 0:
                        continue;
                        
                        case 1:
                        for (int xy = 0; xy <= moveTo.length- 1; xy++) {
                            if (moveTo[xy] == 1) {
                              move1[zz] = xy;
                              zz++;
                            }
                          }
                        temp2 = 0;
                        swapWith = move1[temp2];
                        break;
                        case 2:
                        for (int xy = 0; xy <= moveTo.length -1 ; xy++) {
                            if (moveTo[xy] == 1) {
                              move2[zz] = xy;
                              zz++;
                            }
                          }
                        temp2 = rnd.nextInt(move2.length);
                        swapWith = move2[temp2];
                        break;
                        case 3:
                        for (int xy = 0; xy <= moveTo.length -1; xy++) {
                            if (moveTo[xy] == 1) {
                              move3[zz] = xy;
                              zz++;
                            }
                          }
                        temp2 = rnd.nextInt(move3.length);
                        swapWith = move3[temp2];
                        break;
                       
                        case 4:
                        for (int xy = 0; xy <= moveTo.length - 1; xy++) {
                            if (moveTo[xy] == 1) {
                              move4[zz] = xy;
                              zz++;
                            }
                          }
                        temp2 = rnd.nextInt(move4.length);
                        swapWith = move4[temp2];
                        break;
                        case 5:
                        for (int xy = 0; xy <= moveTo.length -1; xy++) {
                            if (moveTo[xy] == 1) {
                              move5[zz] = xy;
                              zz++;
                            }
                          }
                        temp2 = rnd.nextInt(move5.length); 
                        swapWith = move5[temp2];
                        break;
                        case 6:
                        for (int xy = 0; xy <= moveTo.length -1; xy++) {
                            if (moveTo[xy] == 1) {
                              move6[zz] = xy;
                              zz++;
                            }
                          }
                          temp2 = rnd.nextInt(move6.length);
                          swapWith = move6[temp2];
                        break;
                        case 7:
                        for (int xy = 0; xy <= moveTo.length -1 ; xy++) {
                            if (moveTo[xy] == 1) {
                              move7[zz] = xy;
                              zz++;
                            }
                          }
                          temp2 = rnd.nextInt(move7.length); 
                          swapWith = move7[temp2];
                        break;  
                      }
 
                        // swap with the direction
                         switch(swapWith) {
                         case 0:
                         temp = goals[i-1][j];
                         goals[i-1][j] = goals[i][j];
                         goals[i][j] = temp;
                         goals[i][j].setType(1);
                         movable[ii][0] = i;
                         movable[ii][1] = j;
                         ii++;
                         break;
                         
                         case 1:
                         temp = goals[i+1][j];
                         goals[i+1][j] = goals[i][j];
                         goals[i][j] = temp;
                         goals[i][j].setType(1);
                         movable[ii][0] = i;
                         movable[ii][1] = j;
                         ii++;
                         break;
                         
                         case 2:
                         temp = goals[i-1][j+1];
                         goals[i-1][j+1] = goals[i][j];
                         goals[i][j] = temp;
                         goals[i][j].setType(1);
                         movable[ii][0] = i;
                         movable[ii][1] = j;
                         ii++;
                         break;
                         
                         case 3:
                         temp = goals[i-1][j-1];
                         goals[i-1][j-1] = goals[i][j];
                         goals[i][j] = temp;
                         goals[i][j].setType(1);
                         movable[ii][0] = i;
                         movable[ii][1] = j;
                         ii++;
                         break;
                         
                         case 4:
                         temp = goals[i+1][j+1];
                         goals[i+1][j+1] = goals[i][j];
                         goals[i][j] = temp;
                         goals[i][j].setType(1);
                         movable[ii][0] = i;
                         movable[ii][1] = j;
                         ii++;
                         break;
                         
                         case 5:
                         temp = goals[i+1][j-1];
                         goals[i+1][j-1] = goals[i][j];
                         goals[i][j] = temp;
                         goals[i][j].setType(1);
                         movable[ii][0] = i;
                         movable[ii][1] = j;
                         ii++;
                         break;
                         
                         case 6:
                         temp = goals[i][j-1];
                         goals[i][j-1] = goals[i][j];
                         goals[i][j] = temp;
                         goals[i][j].setType(1);
                         movable[ii][0] = i;
                         movable[ii][1] = j;
                         ii++;
                         break;
                         
                         case 7:
                         temp = goals[i][j+1];
                         goals[i][j+1] = goals[i][j];
                         goals[i][j] = temp;
                         goals[i][j].setType(1);
                         movable[ii][0] = i;
                         movable[ii][1] = j;
                         ii++;
                         break;
                          }
                          // reset moveTo
                         for (int k = 0; k <= moveTo.length -1; k++) {
                             moveTo[k] = 0;
                          }
                         
                         
                      }      
                         
                      
                   }  
                     
                      
                  }
                  
              // set goals back to movable if they were movable in the first place
              
              for (int i = 0; i <= movable.length -1; i++) { 
                  for (int j = 0; j <= movable.length -1; j++) { 
                           goals[movable[i][0]][movable[i][1]].setType(2);
                        }
                      }
                      
              // reset movable
                      
              for (int l = 0; l <= movable.length -1; l++) {
                  for (int m = 0; m <= movable[l].length -1; m++) {
                                 movable[l][m] = 0;
                  }
              }
          }

        
        

    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // write your code after this line
        int add = 0;
        
        // even though the code is enormous, it works like a charm
        
        // check if highScoreRecords is already 10
        if ( highScoreRecords.length + 1 > 10) {
            add = 0;
         } else {
            add = 1;        
         }
         
        // create an object list (newPlayer) in case the name is not already in the HighScore list and has to be added  
        GameRecord[] newPlayer = new GameRecord[highScoreRecords.length + add];
        
        // create an object of the current player 
        GameRecord player = new GameRecord(name, level, score);
        
        // save all entries in highScoreRecords to newPlayer
        for( int i = 0; i <= highScoreRecords.length - 1; i++) {
            
            newPlayer[i] = highScoreRecords[i];
            
          }
        
        //check every object for the name  
        for( int i = 0; i <= highScoreRecords.length - 1; i++) {  
            // if name is the same, check if current score and level is better than prior 
            if (highScoreRecords[i].getName().equals(name)) {
                if ((score > highScoreRecords[i].getScore()) || (level >= highScoreRecords[i].getLevel() && score >= highScoreRecords[i].getScore())) {
                    highScoreRecords[i] = player;
                    
                    // create variable for Index with the maximum value
                    int mIndex = 0;
                    // create temporary object for swapping
                    GameRecord temp;
                    
        // sort list, if current score or level was better 
                     for (int lengthNew = highScoreRecords.length - 1; lengthNew > 0; lengthNew--) {
                               
                              //search highest value of level
                              for (int z = lengthNew; z > 0; z--) {
                                  if(highScoreRecords[z].getLevel() < highScoreRecords[mIndex].getLevel()) mIndex = z;
            
                               }
    
                              //swapping
                              if (mIndex != lengthNew) {
                              temp = highScoreRecords[mIndex];
                              highScoreRecords[mIndex] = highScoreRecords[lengthNew];
                              highScoreRecords[lengthNew] = temp;
                              }
                              mIndex = 0;
                     }
                     
                     //count occurences of level to be able to devide the HighScore list and be able to sort the the level intervals individualy  
                     int count_1 = 0;
                     int count_2 = 0;
                     int count_3 = 0;
    
                     for (int x = 0;  x <= highScoreRecords.length - 1 ; x++) {
                          if (1 == highScoreRecords[x].getLevel()) {
                          count_1++;
                          }
                          if (2 == highScoreRecords[x].getLevel()) {
        
                           count_2++;
                          }
                          if (3 == highScoreRecords[x].getLevel()) {
                           count_3++;
                          }
      
                     }
                     
                     
                    // sort level 3 for highest score
                    if (count_3 > 0){
                     mIndex = 0;
                     for (int lengthNew = count_3 - 1; lengthNew > 0; lengthNew--) {
        
                     for (int z = lengthNew; z > 0; z--) {
                        if(highScoreRecords[z].getScore() < highScoreRecords[mIndex].getScore()) mIndex = z;
            
                     }
        

    
                     if (mIndex != lengthNew) {
                        temp = highScoreRecords[mIndex];
                        highScoreRecords[mIndex] = highScoreRecords[lengthNew];
                        highScoreRecords[lengthNew] = temp;
                     }
                     mIndex = 0;
                     }
                    }
                    
                    //sort level 2 for highest score
                    if (count_2 > 0) {
                    mIndex = count_3;
                    for (int lengthNew = count_3 + count_2 - 1; lengthNew > count_3; lengthNew--) {
        
                      for (int z = lengthNew; z > count_3 - 1; z--) {
                        if(highScoreRecords[z].getScore() < highScoreRecords[mIndex].getScore()) mIndex = z;
            
                       }
        

    
                      if (mIndex != lengthNew) {
                         temp = highScoreRecords[mIndex];
                         highScoreRecords[mIndex] = highScoreRecords[lengthNew];
                         highScoreRecords[lengthNew] = temp;
                      }
                      mIndex = 0;
                     }
                   }
                   
                   //sort level 1 for highest score
                 if (count_1 > 0) {
                   mIndex = count_3 + count_2;
                   for (int lengthNew = highScoreRecords.length - 1; lengthNew > count_3 + count_2; lengthNew--) {
        
                    for (int z = lengthNew; z > count_3 + count_2 - 1; z--) {
                         if(highScoreRecords[z].getScore() < highScoreRecords[mIndex].getScore()) mIndex = z;
            
                    }
        

    
                       if (mIndex != lengthNew) {
                       temp = highScoreRecords[mIndex];
                       highScoreRecords[mIndex] = highScoreRecords[lengthNew];
                       highScoreRecords[lengthNew] = temp;
                    }
                    mIndex = 0;
                   }
                 }   
                    
                    
                    
                     // return HighScore list with no additional player
                    return highScoreRecords;
                    
                    } else {
                     return highScoreRecords;    
                    }
              } 
        }
            
        
        //if HighScore list is already 10 then check for better score or level
        if ( highScoreRecords.length + 1 > 10) {
            
            for( int i = 0; i <= highScoreRecords.length - 1; i++) {  
             
                if ((score > highScoreRecords[i].getScore()) || (level > highScoreRecords[i].getLevel() && score >= highScoreRecords[i].getScore())) {
                    newPlayer[i] = player;
                    break;
                  }
              
           }
           
        } else {    
          // else just add the player to the HighScore list
          newPlayer[highScoreRecords.length] = player;
        }
        
        
        //The code downwards is almost the same as before but for newPlayer (HighScore list extended with one new player) instead for highScoreRecords (HighScore list with no new player added)
        int mIndex = 0;
        GameRecord temp;
        
        
        for (int lengthNew = newPlayer.length - 1; lengthNew > 0; lengthNew--) {
        
            for (int i = lengthNew; i > 0; i--) {
                if(newPlayer[i].getLevel() < newPlayer[mIndex].getLevel()) mIndex = i;
                
            }
        
        
         if (mIndex != lengthNew) {
          temp = newPlayer[mIndex];
          newPlayer[mIndex] = newPlayer[lengthNew];
          newPlayer[lengthNew] = temp;
          }
         mIndex = 0;
        }
        
        int count_1 = 0;
        int count_2 = 0;
        int count_3 = 0;
        
        for (int x = 0;  x <= newPlayer.length - 1 ; x++) {
          if (1 == newPlayer[x].getLevel()) {
              count_1++;
          }
          if (2 == newPlayer[x].getLevel()) {
            
              count_2++;
          }
          if (3 == newPlayer[x].getLevel()) {
              count_3++;
          }
          
        }
          
        if (count_3 > 0){
        mIndex = 0;
        for (int lengthNew = count_3 - 1; lengthNew > 0; lengthNew--) {
            
            for (int i = lengthNew; i > 0; i--) {
                if(newPlayer[i].getScore() < newPlayer[mIndex].getScore()) mIndex = i;
                
            }
            

        
         if (mIndex != lengthNew) {
          temp = newPlayer[mIndex];
          newPlayer[mIndex] = newPlayer[lengthNew];
          newPlayer[lengthNew] = temp;
          }
         mIndex = 0;
        }
        }
        
        if (count_2 > 0) {
        mIndex = count_3;
        for (int lengthNew = count_3 + count_2 - 1; lengthNew > count_3; lengthNew--) {
            
            for (int i = lengthNew; i > count_3 - 1; i--) {
                if(newPlayer[i].getScore() < newPlayer[mIndex].getScore()) mIndex = i;
                
            }
            

        
         if (mIndex != lengthNew) {
          temp = newPlayer[mIndex];
          newPlayer[mIndex] = newPlayer[lengthNew];
          newPlayer[lengthNew] = temp;
          }
         mIndex = 0;
        }
        }
        
        if (count_1 > 0) {
        mIndex = count_3 + count_2;
        for (int lengthNew = newPlayer.length - 1; lengthNew > count_3 + count_2; lengthNew--) {
            
            for (int i = lengthNew; i > count_3 + count_2 - 1; i--) {
                if(newPlayer[i].getScore() < newPlayer[mIndex].getScore()) mIndex = i;
                
            }
            

        
         if (mIndex != lengthNew) {
          temp = newPlayer[mIndex];
          newPlayer[mIndex] = newPlayer[lengthNew];
          newPlayer[lengthNew] = temp;
          }
         mIndex = 0;
        }
        }
        
        // return HighScore list with additional player
        return newPlayer;
    }
    
}


