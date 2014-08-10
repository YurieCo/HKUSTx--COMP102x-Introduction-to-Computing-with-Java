import java.util.Random;
import comp102x.Canvas;
import comp102x.ColorImage;

/**
 * The GameBoard class represents the game board which contains the cards
 */
public class GameBoard
{   
    private Card[][] cards = new Card[MatchingGame.NUMBER_OF_ROWS][MatchingGame.NUMBER_OF_CARDS_PER_ROW]; //The array of Card objects which represent the cards on the game board
    
    /**
     * The constructor which initlizes the Card objects and randomizes the cards
     */
    public GameBoard()
    {
        //initialize the Card objects
        for(int i=0; i<MatchingGame.NUMBER_OF_ROWS; i++)
        {
            for(int j=0; j<MatchingGame.NUMBER_OF_CARDS_PER_ROW; j++)
            {
                cards[i][j] = new Card((i*MatchingGame.NUMBER_OF_CARDS_PER_ROW+j) % (MatchingGame.NUMBER_OF_CARDS_PER_ROW*MatchingGame.NUMBER_OF_ROWS/2) + 1);
            }
        }
        
        //shuffle the cards 100 times
        Random randomNumberGenerator = new Random();
        for(int i=0; i<100; i++)
        {
            //swap two randomly-chosen cards
            int randomRowA = randomNumberGenerator.nextInt(MatchingGame.NUMBER_OF_ROWS);
            int randomColumnA = randomNumberGenerator.nextInt(MatchingGame.NUMBER_OF_CARDS_PER_ROW);
            int randomRowB = randomNumberGenerator.nextInt(MatchingGame.NUMBER_OF_ROWS);
            int randomColumnB = randomNumberGenerator.nextInt(MatchingGame.NUMBER_OF_CARDS_PER_ROW);
            swapCards(randomRowA, randomColumnA, randomRowB, randomColumnB);
        }
    }
    
    /**
     * Swap two cards, card A and card B
     * 
     * @param   cardARowIndex      the row index of card A
     * @param   cardAColumnIndex   the column index of card A
     * @param   cardBRowIndex      the row index of card B
     * @param   cardBColumnIndex   the column index of card B
     */
    private void swapCards(int cardARowIndex, int cardAColumnIndex, int cardBRowIndex, int cardBColumnIndex)
    {
        // Please write your code after this line
        Card a = cards[cardARowIndex] [ cardAColumnIndex ];
        cards[cardARowIndex] [ cardAColumnIndex ] = cards [cardBRowIndex ] [ cardBColumnIndex ];
        cards [cardBRowIndex ] [ cardBColumnIndex ] = a;
        
    }
    
    /**
     * Flip the card to be facing up
     * 
     * @param   cardRowIndex      the row index of the card to be flipped facing up
     * @param   cardColumnIndex   the column index of the card to be flipped facing up
     */ 
    public void flipCardUp(int cardRowIndex, int cardColumnIndex)
    {
        cards[cardRowIndex][cardColumnIndex].setFacingUp(true);
    }
    
    /**
     * Flip the card to be facing down
     * 
     * @param   cardRowIndex      the row index of the card to be flipped facing down
     * @param   cardColumnIndex   the column index of the card to be flipped facing down
     */ 
    public void flipCardDown(int cardRowIndex, int cardColumnIndex)
    {
        cards[cardRowIndex][cardColumnIndex].setFacingUp(false);
    }
    
    /**
     * Check if all the first card matches the second card
     * 
     * @param   firstCardIndex      the card index of the first card
     * @param   secondCardIndex     the card index of the second card
     * @return  true if first card matches the second card; false otherwise
     */ 
    public boolean checkCardMatch(int firstCardRowIndex, int firstCardColumnIndex, int secondCardRowIndex, int secondCardColumnIndex)
    {
        return (cards[firstCardRowIndex][firstCardColumnIndex].getValue() == cards[secondCardRowIndex][secondCardColumnIndex].getValue());
    }
    
     /**
     * Check if all the cards are flipped facing up, i.e., all matches are found
     * 
     * @return  true if all cards are flipped facing up; false otherwise
     */ 
    public boolean checkAllMatchesFound()
    {
        // Please write your code after this line
        
        boolean b = true;
      
        for(Card[] c:cards)
         for(Card c1:c)
        if (!c1.isFacingUp()) b = false; 
        
        
        return b;
    }
    
    /**
     * Draw the card images on the given canvas
     * 
     * @param   canvas      the canvas to draw on
     */
    public void draw(Canvas canvas)
    {
        //clear the canvas
        canvas.removeAll();
        
        //draw all the card images on the canvas
        for(int i=0; i<MatchingGame.NUMBER_OF_ROWS; i++)
        {
            for(int j=0; j<MatchingGame.NUMBER_OF_CARDS_PER_ROW; j++)
            {
                cards[i][j].draw(canvas, MatchingGame.BOARD_MARGIN+j*(MatchingGame.CARD_WIDTH), MatchingGame.BOARD_MARGIN+i*MatchingGame.CARD_HEIGHT);
            }
        }
    }
}
