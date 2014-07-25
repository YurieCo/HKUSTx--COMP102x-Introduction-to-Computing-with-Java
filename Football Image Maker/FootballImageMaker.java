import comp102x.ColorImage;
import comp102x.Canvas;

public class FootballImageMaker
{
    public static final int BOUNDARY_FLAG_WIDTH = 70; // The maximum width for the flag image after rescaling.
    public static final int BOUNDARY_FLAG_HEIGHT = 46; // The maximum height for the flag image after rescaling.
    public static final int TRANSPARENCY = 214; // Transparency ranged from 0 to 255. 0 = full transparency. 255 = no transparency.
    
    public static void main(String[] args) {
        
        // Load the football image.
        ColorImage football = new ColorImage("soccer120.png");
        
        // Load the flag image.
        ColorImage flag = new ColorImage();
        
        // Adjust its size and transparency.
        flag = SimpleImageProcessor.resizeAndSetAlpha(flag, BOUNDARY_FLAG_WIDTH, BOUNDARY_FLAG_HEIGHT, TRANSPARENCY);
        
        // Calculating the x, y positions of where to put the flag image on the football image.
        int footballWidth = football.getWidth();
        int footballHeight = football.getHeight();
        int flagWidth = flag.getWidth();
        int flagHeight = flag.getHeight();
        int x = (footballWidth - flagWidth) / 2;
        int y = (footballHeight - flagHeight) / 2;

        // Draw the flag image on the ball.
        football.drawImage(flag, x, y);
        
        // Saving the image on the file system.
        football.save();
    }

}
