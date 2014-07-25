import comp102x.IO;
import comp102x.Canvas;

/**
 * This is the main game class for the game Rock-Paper-Scissors
 */
public class RockPaperScissors
{   
    /**
     * This is the entry point of the game - run it to play the game
     */
    public static void main(String args[])
    { 
        //create the canvas which will have player's choice and computer's choice drawn on it
        Canvas canvas = new Canvas(480, 960);
            
        //create the Player object
        Player player = new Player();
            
        //create the Computer object
        Computer computer = new Computer();        
        
        do {
            // clear the canvas
            canvas.removeAll();
            
            //make the choices
            player.makeChoice(); 
            computer.makeChoice();
            
            //show the choices
            player.showChoice(canvas);
            computer.showChoice(canvas);
            
            //get the game result by comparing the player's choice with the computer's choice
            int gameResult = player.getChoice().compareWith(computer.getChoice());
            
            //show the appropriate gameover message based on the game result
            switch(gameResult) 
            {
                case 1: //1 indicates a win
                    IO.outputln("You won!");
                    break;
                case -1: //-1 indicates a lose
                    IO.outputln("You lost!");
                    break;
                case 0: //0 indicates a draw
                    IO.outputln("Draw!");
                    break;
            }
            
        } while (player.playAgain());
    }

}
