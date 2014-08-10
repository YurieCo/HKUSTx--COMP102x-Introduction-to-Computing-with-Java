import comp102x.IO;
import comp102x.Canvas;

/**
 * This is the main game class for the matching game
 */
public class MatchingGame
{   
    //constant declarations
    public static final int BOARD_MARGIN = 50;
    public static final int CARD_WIDTH = 200;
    public static final int CARD_HEIGHT = 200;
    public static final int NUMBER_OF_CARDS_PER_ROW = 4;
    public static final int NUMBER_OF_ROWS = 2;
    
    /**
     * This is the entry point of the program
     */
    public static void main(String[] args)
    {
        MatchingGame game = new MatchingGame();
        game.runGameLoop();
    }
    
    /**
     * This is the main game loop
     */
    public void runGameLoop()
    {
        //create the canvas for cards display
        Canvas canvas = new Canvas(BOARD_MARGIN*2+CARD_WIDTH*NUMBER_OF_CARDS_PER_ROW, BOARD_MARGIN*2+CARD_HEIGHT*NUMBER_OF_ROWS);
        
        //create the GameBoard object
        GameBoard gameBoard = new GameBoard();
        
        //draw the game board
        gameBoard.draw(canvas);
        
        boolean isGameOver = false; //it indicates whether the game is over or not
            
        //continue the game until the game is over
        while(!isGameOver)
        {
            int firstCardRowNumber;
            int firstCardColumnNumber;
            int secondCardRowNumber;
            int secondCardColumnNumber;
            
            //ask the user for picking the first card by giving a row and a column number
            do
            {
                
                IO.outputln("Pick the row number of the first card. [1-" + NUMBER_OF_ROWS + "]");
                firstCardRowNumber = IO.inputInteger();
                
            } while(firstCardRowNumber<=0||firstCardRowNumber>NUMBER_OF_ROWS);
            
            do
            {
                
                IO.outputln("Pick the column number of the first card. [1-" + NUMBER_OF_CARDS_PER_ROW + "]");
                firstCardColumnNumber = IO.inputInteger();
                
            } while(firstCardColumnNumber<=0||firstCardColumnNumber>NUMBER_OF_CARDS_PER_ROW);
            
            // flip the card to be facing up
            gameBoard.flipCardUp(firstCardRowNumber - 1, firstCardColumnNumber - 1);
            gameBoard.draw(canvas);

            //ask the user for picking the second card by giving a row and a column number
            do
            {
                
                IO.outputln("Pick the row number of the second card. [1-" + NUMBER_OF_ROWS + "]");
                secondCardRowNumber = IO.inputInteger();
                
            } while(secondCardRowNumber<=0||secondCardRowNumber>NUMBER_OF_ROWS);
                
            do
            {
                
                IO.outputln("Pick the column number of the second card. [1-" + NUMBER_OF_CARDS_PER_ROW + "]");
                secondCardColumnNumber = IO.inputInteger();
                
            } while(secondCardColumnNumber<=0||secondCardColumnNumber>NUMBER_OF_CARDS_PER_ROW);
                   
            // flip the card to be facing up
            gameBoard.flipCardUp(secondCardRowNumber - 1, secondCardColumnNumber - 1);
            gameBoard.draw(canvas);
            
            //check if the first card matches the second card
            boolean isMatch = gameBoard.checkCardMatch(firstCardRowNumber - 1, firstCardColumnNumber - 1, secondCardRowNumber - 1, secondCardColumnNumber - 1);
            if(isMatch)
            {
                //if the cards match, show the corresponding message
                IO.outputln("Nice, they match!");
            }
            else
            {
                //if the cards don't match, show a message, flip the cards to be facing down, and redraw the game board
                IO.outputln("Oops, they don't match ...");
                
                //pause the execution for a while so that the cards flipped facing up can be viewed
                pause(1500);
                
                //flip the two cards to be facing down
                gameBoard.flipCardDown(firstCardRowNumber - 1, firstCardColumnNumber - 1);
                gameBoard.flipCardDown(secondCardRowNumber - 1, secondCardColumnNumber - 1);
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
