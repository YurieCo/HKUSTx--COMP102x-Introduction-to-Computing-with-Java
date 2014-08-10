import comp102x.Canvas;
import comp102x.ColorImage;

/**
 * The Card class represents a card on the game board
 */
public class Card
{
    private int value; //the card value
    private boolean facingUp; //the card's "flipped" state: true indicates that the card is flipped facing up while false indicates the card is flipped facing down
    
    /**
     * The constructor
     * 
     * @param   value    the card value
     */
    public Card(int value)
    {
        //initialize the value instance variable
        this.value = value;
    }
    
    /**
     * Return the card value
     * 
     * @return  the card value
     */
    public int getValue()
    {
        return value;
    }
    
    /**
     * Set the card value
     * 
     * @param   value    the card value
     */
    public void setValue(int value)
    {
        this.value = value;
    }
    
    /**
     * Return the card's "flipped" state
     * 
     * @return  the card's "flipped" state: true indicates that the card is flipped facing up while false indicates otherwise
     */
    public boolean isFacingUp()
    {
        return facingUp;
    }
    
    /**
     * Set the card's "flipped" state
     * 
     * @param   facingUp   the new "flipped" state: true indicates that the card is flipped facing up while false indicates otherwise
     */
    public void setFacingUp(boolean facingUp)
    {
        this.facingUp = facingUp;
    }
    
    /**
     * Draw the card image on the given canvas
     * 
     * @param   canvas      the canvas to draw on
     * @param   x           the x-position of the card image to be drawn
     * @param   y           the y-position of the card image to be drawn
     */
    public void draw(Canvas canvas, int x, int y)
    {
        //set the imageFileName which depends on whether the card's "flipped" state and the card value
        String imageFileName;
        if(!facingUp)
        {
            //if the card is flipped facing down, then show the card back image
            imageFileName = "images/cardback.png";
        }
        else
        {
            //if the card is flipped facing up, then show the card image according to its card value
            imageFileName = "images/card" + value + ".png";
        }
        
        //load the image
        ColorImage image = new ColorImage(imageFileName);
        
        //show the image on the canvas
        canvas.add(image, x, y);
    }
}
