import comp102x.Canvas;
import comp102x.IO;
import java.util.Random;

/**
 * The Computer class represents the computer in the game
 * It uses a random number generator to make its choice randomly
 */
public class Computer
{
    private Choice choice; //stores the computer's choice
    
    /**
     * Use a random number generator to generate a random choice and then create the corresponding Choice object
     */
    public void makeChoice()
    {
        // write your code after this line
        Random randomObject = new Random();
        choice = new Choice ( randomObject.nextInt(3) );
        
    }
    
    /**
     * Draw an image that represents the computer's choice on the given canvas
     * 
     * @param   canvas      the canvas to draw on
     */
    public void showChoice(Canvas canvas)
    {
        choice.draw(canvas, 0, 0, 180);
    }
    
    /**
     * Get the choice object that represents the computer's choice
     * 
     * @return  the choice object that represents the computer's choice
     */
    public Choice getChoice()
    {
        return choice;
    }
}
