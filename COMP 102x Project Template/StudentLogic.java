import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
    
    
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        // write your code after this line
        int imageIndex = (depthImages.size() - 1) * ((currentStep - initialStep) / ( finalStep - initialStep)); //I used parathesis becuase multiplication have higher priority than division !!
        int xPosition = initialX + (finalX - initialX) * ((currentStep - initialStep) / ( finalStep - initialStep) );// !! 
        int yPosition = initialY + (finalY - initialY) * ((currentStep - initialStep) / ( finalStep - initialStep) );
        double scale = initialScale + (finalScale - initialScale) * ((currentStep - initialStep) / ( finalStep  - initialStep) );
        
        depthImages[ imageIndex ].setX( xPosition ); //set x pozition equal to xPozition
        depthImages[ imageIndex ].setY( yPosition ); // set y pozition equal to yPoztition
        depthImages[ imageIndex ].setScale( scale );  // scale image with factor 'scale'

        return depthImages[ imageIndex ];

    }


    public void updateGoalPositions(Goal[][] goals) {
        // write your code after this line
        
        
        
        
        
    }

    
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // write your code after this line
        
        
        
        
        
    }
    
    
}
