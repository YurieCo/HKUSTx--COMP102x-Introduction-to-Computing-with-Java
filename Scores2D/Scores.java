import comp102x.IO;

/**
 * Demostrate the use of array for storing and manipulating a set of scores
 */
public class Scores
{
   //private double[][] scores; // declare a reference variable to an array
   
   private double[][] scores = {
     {99.0, 89.0, 85.0, 92.0}, 
     {90.0, 74.0, 75.0, 82.0},
     {85.0, 75.0, 64.0, 91.0},
     {72.0, 82.0, 81.0, 94.0}
    };

   /**
    * SetScore asks user to enter score for each array element
    */
   public void initializeAllScores() {       
      scores = new double[4][4];  
      scores[0][0] = 99.0; scores[0][1] = 89.0;
      scores[0][2] = 85.0; scores[0][3] = 92.0;
      
      scores[1][0] = 90.0; scores[1][1] = 74.0;
      scores[1][2] = 75.0; scores[1][3] = 82.0;  
      
      scores[2][0] = 85.0; scores[2][1] = 75.0;    
      scores[2][2] = 64.0; scores[2][3] = 91.0;

      scores[3][0] = 72.0; scores[3][1] = 82.0;
      scores[3][2] = 81.0; scores[3][3] = 94.0;

    }
    
   public double getScoreByIndices(int rowIndex, int colIndex) {    
        int numOfRows = scores.length;                        
        int numOfCols = scores[0].length;        

        if ( rowIndex < 0 || rowIndex >= numOfRows )
            return -1.0;        
        if ( colIndex < 0 || colIndex >= numOfCols )
            return -1.0;            

        return scores[rowIndex][colIndex]; 
    }
    
   public void printAllScores() {
       int numOfRows = scores.length;                        
       int numOfCols = scores[0].length; 
       for ( int r=0; r<numOfRows; r++) {            
           IO.output("Row " + r + " : ");
           for (int c=0; c<numOfCols; c++) {
                IO.output(getScoreByIndices(r,c) + " " );
            } // for loop c
            IO.outputln(""); // next line
        } // for loop r
    } // end of the method
   
}
