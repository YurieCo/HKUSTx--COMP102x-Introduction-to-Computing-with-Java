import comp102x.IO;
import comp102x.Canvas;
import comp102x.ColorImage;

public class SimpleStereoCreator
{
    private final int CANVAS_WIDTH = 800;
    private final int CANVAS_HEIGHT = 600;
    private final int MARGIN = 10;
    
    private Canvas canvas;
    private ColorImage leftImage;
    private ColorImage rightImage;
    private ColorImage stereoImage;
    
    public SimpleStereoCreator() {
    
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void createStereo() {
        
        leftImage = new ColorImage(); // loading the left image
        rightImage = new ColorImage(); // loading the right image
        
        ColorImage redImage = leftImage.createRedImage(); // creating a red image from the left image
        ColorImage cyanImage = rightImage.createCyanImage(); // creating a cyan image from the right image
        stereoImage = redImage.add(cyanImage); // creating the stereo image by adding the red and cyan images
        
        canvas.add(stereoImage, 0, 0); // putting the stereo image at the right of the right image
    }

}
