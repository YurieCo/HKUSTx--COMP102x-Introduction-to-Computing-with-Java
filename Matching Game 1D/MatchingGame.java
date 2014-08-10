import comp102x.IO;
import comp102x.Canvas;

/**
 * This is the main game class for the matching game
 */
public class MatchingGame
{   
    //constant declarations
    public static final int BOARD_MARGIN = 50;
    public static final int CARD_WIDTH = 150;
    public static final int CARD_HEIGHT = 200;
    public static final int NUMBER_OF_CARDS = 6;
    
    public static void main(String[] args)
    {
        MatchingGame game = new MatchingGame();
        game.runGameLoop();
    }
    
    /**
     * This is the entry point of the game - run it to play the game
     */
    public void runGameLoop()
    {
        //create the canvas for cards display
        Canvas canvas = new Canvas(BOARD_MARGIN*2+CARD_WIDTH*NUMBER_OF_CARDS, BOARD_MARGIN*2+CARD_HEIGHT);
        
        //create the GameBoard object
        GameBoard gameBoard = new GameBoard();
        
        //draw the game board
        gameBoard.draw(canvas);
        
        boolean isGameOver = false; //it indicates whether the game is over or not
            
        //continue the game until the game is over
        while(!isGameOver)
        {
            int firstCard;
            int secondCard;
            
            //ask user for picking the first card, flip it to be facing up, and redraw the game board
            do {
                
                IO.outputln("Pick the first card. [1-" + NUMBER_OF_CARDS + "]");
                firstCard = IO.inputInteger();
                
            } while (firstCard <= 0 || firstCard > NUMBER_OF_CARDS);
            
            // flip the card to be facing up
            gameBoard.flipCardUp(firstCard - 1);
            gameBoard.draw(canvas);

            //ask user for picking the second card, flip it to be facing up, and redraw the game board
            do {
                
                IO.outputln("Pick the second card. [1-" + NUMBER_OF_CARDS + "]");
                secondCard = IO.inputInteger();
                
            } while (secondCard < 1 || secondCard > NUMBER_OF_CARDS);
            
            // flip the card to be facing up
            gameBoard.flipCardUp(secondCard - 1);
            gameBoard.draw(canvas);
            
            //check if the first picked card matches the second card
            boolean isMatch = gameBoard.checkCardMatch(firstCard - 1, secondCard -1);
            if(isMatch)
            {
                //if the cards match, show the corresponding message
                IO.outputln("Nice, they match!");
            }
            else
            {
                //if the cards don't match, show a message, flip the cards to be facing down, and redraw the game board
                IO.outputln("Oops, they don't match ... ");
                
                //pause the execution for a while so that the cards flipped facing up can be viewed
                pause(1500);
                
                //flip the two cards to be facing down
                gameBoard.flipCardDown(firstCard - 1);
                gameBoard.flipCardDown(secondCard - 1);
                gameBoard.draw(canvas);
            }
            
            //check if the game is over
            isGameOver = gameBoard.checkAllMatchesFound();
        }
        
        //game is over at this point, show the corresponding message
        IO.outputln("You found all the matches! Congratulations.");
    }
    
    /**
     * Method for pause the execution of the program.
     */
    private void pause(int milliseconds)
    {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.err.println("Interrupted during pause.");
            System.exit(-1);
        }
    }
}
