import java.util.Random;
import comp102x.Canvas;

/**
 * The GameBoard class represents the game board which contains the cards
 */
public class GameBoard
{   
    private Card cards[] = new Card[MatchingGame.NUMBER_OF_CARDS]; //The array of Card objects which represent the cards on the game board
    
    /**
     * The constructor which initlizes the Card objects and randomizes the cards
     */
    public GameBoard()
    {
        //initialize the Card objects
        for(int i=0; i<MatchingGame.NUMBER_OF_CARDS; i++)
        {
            cards[i] = new Card(i % (MatchingGame.NUMBER_OF_CARDS/2) + 1);
        }
        
        //shuffle the cards 100 times
        Random randomNumberGenerator = new Random();
        for(int i=0; i<100; i++)
        {
            //swap two randomly-chosen cards
            int randomNumberA = randomNumberGenerator.nextInt(MatchingGame.NUMBER_OF_CARDS);
            int randomNumberB = randomNumberGenerator.nextInt(MatchingGame.NUMBER_OF_CARDS);
            swapCards(randomNumberA, randomNumberB);
        }
    }
    
    /**
     * Swap two cards, card A and card B
     * 
     * @param   cardAIndex      the card index of card A
     * @param   cardBIndex      the card index of card B
     */
    private void swapCards(int cardAIndex, int cardBIndex)
    {
        // Please write your code after this line
        Card temp = cards[ cardAIndex ]; //save current card in temp location
        cards [ cardAIndex ] = cards [ cardBIndex ];
        cards [ cardBIndex ] = temp;
        
        
        
        
        
    }
    
    /**
     * Flip the card to be facing up
     * 
     * @param   cardIndex      the card index of the card to be flipped facing up
     */ 
    public void flipCardUp(int cardIndex)
    {
        cards[cardIndex].setFacingUp(true);
    }
    
    /**
     * Flip the card to be facing down
     * 
     * @param   cardIndex      the card index of the card to be flipped facing down
     */ 
    public void flipCardDown(int cardIndex)
    {
        cards[cardIndex].setFacingUp(false);
    }
    
    /**
     * Check if all the first card matches the second card
     * 
     * @param   firstCardIndex      the card index of the first card
     * @param   secondCardIndex     the card index of the second card
     * @return  true if first card matches the second card; false otherwise
     */ 
    public boolean checkCardMatch(int firstCardIndex, int secondCardIndex)
    {
        return (cards[firstCardIndex].getValue() == cards[secondCardIndex].getValue());
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
        for(Card c:cards)
        if (!c.isFacingUp()) b = false; 
        
        
        
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
        for(int i=0; i<MatchingGame.NUMBER_OF_CARDS; i++)
        {
            cards[i].draw(canvas, MatchingGame.BOARD_MARGIN+i*MatchingGame.CARD_WIDTH, MatchingGame.BOARD_MARGIN);
        }
    }
}
