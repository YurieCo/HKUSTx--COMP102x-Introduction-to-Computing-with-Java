import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;


/**
 * The StudentLogic class represents the game logic of the shooting game implemented by the student.
 */
public class StudentLogic implements GameLogic{
    
    
    /**
     * Generates a football imagen according to current state 
     * 
     * This method generates a football image by updating both the graphical
     * representation and the internal data of the image by changing its position 
     * and the scale and returns the image to be shown. 
     * 
     * @param depthImages the images that have to be shown according to the step.
     * @param initialStep the initial step of the image.
     * @param curentStep the current step of the image.
     * @param finalStep the final step of the image.
     * @param initialScale the first scale of the image.
     * @param finalScale the last scale of the image.
     * @param initialX the first position in the x axis of the image.
     * @param finalX the last position in the x axis of the image.
     * @param initialY the first position in the y axis of the image.
     * @param finalY the last position in the y axis of the image.
     * @return Current image updated to be correctly displayed at the layout
     */
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        // write your code after this line
        int imageIndex= (depthImages.length -1)* (currentStep-initialStep)/(finalStep-initialStep); //index of the image to be shown in the array depthImages
        
        int xPosition = initialX + (finalX-initialX)* (currentStep-initialStep)/(finalStep-initialStep); // current position in the x axis
        
        int yPosition = initialY + (finalY-initialY)* (currentStep-initialStep)/(finalStep-initialStep); // current position in the y axis
        
        double scale = initialScale + (finalScale-initialScale)* (currentStep-initialStep)/(finalStep-initialStep); //current scale of the image
        
        // Set characteristics of the image
        depthImages[imageIndex].setX(xPosition);
        depthImages[imageIndex].setY(yPosition);
        depthImages[imageIndex].setScale(scale);
        
        return depthImages[imageIndex];
        

    }


    /**
     * Updates the position of the goals during the game 
     * 
     * This method swaps the goals to empty positions by swapping a goal image 
     * to a goal that is being hit in an adyacent position, so is empty.
     * It is neccesary a movable goal to make the swap.
     * 
     * @param goals 2D array with the images to be swapped.
     */
    public void updateGoalPositions(Goal[][] goals) {
        // write your code after this line
        int i,j,m,n,xini,xfin,yini,yfin; //integer for the loops
        int numOfRows = goals.length; //number of rows
        int numOfCols = goals[0].length; //number of columns
        Goal Aux= new Goal(Goal.MOVABLE, false); //auxiliar variable 
        boolean out=false; 
        
        Random randomObject = new Random(); // random changes
        int randomInteger;
        
        for(i=0;i<numOfRows ;i++){
            for(j=0;j<numOfCols ;j++){  
                out=false; //For each image there is only one swap
                
                if((goals[i][j].getType()==2) && !(goals[i][j].isHit())){
                    // Is needed a movable goal that is not being hit
                    if (i==0) xini=0; //set the initial row of the adyacent goals array
                    else xini=i-1;
                    
                    if (j==0) yini=0; //set the initial rcolumn of the adyacent goals array
                    else yini=j-1;
                    
                    if (i==numOfRows-1) xfin=numOfRows-1; //set the final row of the adyacent goals array
                    else xfin=i+1;
                    
                    if (j==numOfCols-1) yfin=numOfCols-1; //set the final column of the adyacent goals array
                    else yfin=j+1;
                    // xinic and xfin define the interval of rows; yinic and yfin define the interval of columns
                    for(m=xini;m<=xfin;m++){
                        for(n=yini;n<=yfin;n++){
                            randomInteger  = randomObject.nextInt(10); //random swaps between goals to avoid a constant behaviour and give randomness(10%)
                            // The loop goes only for the adyacent goals given by xini, yini, xfin, yfin
                            if((goals[m][n].isHit()) && !((m==i) && (n==j)) && (randomInteger==9) ){
                                // A value of 9 will make it slow, lower numbers will made more swapings
                                Aux=goals[m][n];
                                goals[m][n]=goals[i][j];
                                goals[i][j]=Aux;
                                // The swap is being made
                                out=true;
                                
                            }
                            if(out) break;
                        }
                        if(out) break; //For each image there is only one swap
                    }
                    
                }
                
            }
            
        }
        
    
    }

    /**
     * Updates the game record table
     * 
     * This method arranges the game record in descendent order, saving only the 
     * 10 best records sorting them according to the score and the level.
     * In case of certain conditions, an special behaviour is performed
     * 
     * @param highScoreRecords the array containing the previous records.
     * @param name the user name of the current record
     * @param level the level of the current record
     * @param score the score of the current record
     * @return ret high scores to be shown at the display
     */
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // write your code after this line
        int i,j, indexmin=9;
        int min=13;
        String staux="";  //auxiliar variable
        GameRecord aux= new GameRecord("",1,0); //auxiliar variable
        GameRecord[] rec; //returning variable 
        GameRecord[] ret= new GameRecord[10];
        //An if condition is performed for each case
        
        if (highScoreRecords.length==0){
            //If there are no previous game play records, return a GameRecord array of size 1 containing the current record.
            rec= new GameRecord[1];
            rec[0] =new GameRecord(name,level,score);
                 
            return rec;
        }
                
        
        for(i=0;i<highScoreRecords.length;i++){
            staux= staux + highScoreRecords[i].getName(); // staux contains all the names in the previous scores
            if (highScoreRecords[i].getScore()<=min){
                indexmin = i; //search for the index of the worst record
                min=highScoreRecords[i].getScore();//search for the worst score
            }
            
            //search for the same name in highScoreRecords
            if (name.equals(highScoreRecords[i].getName())){
                
                if((highScoreRecords[i].getScore()<score) || ((highScoreRecords[i].getScore()==score) && (highScoreRecords[i].getLevel()<level) ) ){
                    //if it is a better record, update
                    highScoreRecords[i].setScore(score);
                    highScoreRecords[i].setLevel(level);
                    //rearrange the highScoreRecords
                    for(i=1;i<highScoreRecords.length;i++){
                           for(j=0;j<highScoreRecords.length-i;j++){
                               if( (highScoreRecords[j].getScore()<highScoreRecords[j+1].getScore()) || ( (highScoreRecords[j].getScore()==highScoreRecords[j+1].getScore()) && (highScoreRecords[j].getLevel()<highScoreRecords[j+1].getLevel()) ) ){
                                   aux=highScoreRecords[j];
                                   highScoreRecords[j]=highScoreRecords[j+1];
                                   highScoreRecords[j+1]=aux;
                                }
                           }
                    }
                    return highScoreRecords;
                }
                else{ //if it is a worse record, nothing
                    return highScoreRecords;
                }
            }
        }
        
        if ((staux=="") && (highScoreRecords.length<10)){
            //If the player's name doesn't exist in the previous records and there are less than 10 previous records,
            //return a GameRecord array containing all the previous records and the new record.
            rec= new GameRecord[highScoreRecords.length +1];
            for(i=0;i<highScoreRecords.length;i++){
                rec[i]= highScoreRecords[i]; //store highScoreRecords
            }
            rec[highScoreRecords.length] =new GameRecord(name,level,score); //and add the new one
            
            ret=rec; //ret will be the variable returned
             
           
        }
        
        if ((staux=="") && (highScoreRecords.length==10)){
            // If the player's name doesn't exist in the previous records and there are 10 previous records, 
            //return a GameRecord array containing the best 10 records.
            
            rec= new GameRecord[10];
            for(i=0;i<10;i++){
                rec[i]= highScoreRecords[i];//store highScoreRecords
            }
            rec[indexmin]=new GameRecord(name,level,score);//change the worst record for the new one with the name
            
            ret=rec; //ret will be the variable returned
            
            
        }
        
        if((staux!="") && ((score>highScoreRecords[highScoreRecords.length-1].getScore()) || ((score==highScoreRecords[highScoreRecords.length-1].getScore()) &&(level>highScoreRecords[highScoreRecords.length-1].getLevel())))){
            //If the player's name exists in the previous records and the current record is better than the previous record,
            //return a GameRecord array containing all the previous records,
            //but with the score and level of the player updated to those of the current game play.
            
           if (highScoreRecords.length<10) rec= new GameRecord[highScoreRecords.length +1]; //set the size of the array (max: 10)
           else rec= new GameRecord[10];
            
           for(i=highScoreRecords.length;i>0;i--){ //rearrange the records to set the new one as the best
                if(i==10) i=9;
                rec[i]=highScoreRecords[i-1];
           }
           rec[0] =new GameRecord(name,level,score);
           
           ret=rec; //ret will be the variable returned
           
        }
        
        if((staux!="") && ((score<highScoreRecords[highScoreRecords.length-1].getScore()) || ((score==highScoreRecords[highScoreRecords.length-1].getScore()) &&(level<=highScoreRecords[highScoreRecords.length-1].getLevel())))){
            //If the player's name exists in the previous records and the current record is the same or is worse than the previous record,
            //return a GameRecord array containing all the previous records.
            if(highScoreRecords.length>=10){ //the new record is the worst, so no change is performed
                rec= new GameRecord[10];
                for(i=0;i<10;i++){
                    rec[i]= highScoreRecords[i]; //store highScoreRecords
                }
                
            }else { //the new record is set at the last position
                rec= new GameRecord[highScoreRecords.length +1];
                for(i=0;i<highScoreRecords.length;i++){
                    rec[i]= highScoreRecords[i]; //store highScoreRecords
                }
                rec[highScoreRecords.length] =new GameRecord(name,level,score); //add the record at the last position
                
            }
             ret=rec; //ret will be the variable returned
        }
        
        //a bubble ordering algorithm (descendent) is implemented in case that if conditions were avoided, only for ret array
        for(i=1;i<ret.length;i++){
             for(j=0;j<ret.length-i;j++){
                 if( (ret[j].getScore()<ret[j+1].getScore()) || ( (ret[j].getScore()==ret[j+1].getScore()) && (ret[j].getLevel()<ret[j+1].getLevel()) ) ){
                      aux=ret[j];
                      ret[j]=ret[j+1];
                      ret[j+1]=aux;
                 }
             }
        }
        
        
       return ret;
       
       
    }
    
    
}

