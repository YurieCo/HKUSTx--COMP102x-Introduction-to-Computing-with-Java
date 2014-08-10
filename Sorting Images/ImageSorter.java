import comp102x.Canvas;
import comp102x.ColorImage;
import java.util.Random;

public class ImageSorter
{
    private final int NUMBER = 8;
    private final int WIDTH = 1200;
    private final int HEIGHT = 700;
    private Canvas canvas;
    private ColorImage[] imageArray;
    private Random random;
    
    public ImageSorter() {
    
        ColorImage bg = new ColorImage("background.png");
        bg.setMovable(false);
        bg.setScale(1.5);
        
        imageArray = new ColorImage[NUMBER];
        imageArray[0] = new ColorImage("Alfred.png");
        imageArray[1] = new ColorImage("Jane.png");
        imageArray[2] = new ColorImage("Mary.png");
        imageArray[3] = new ColorImage("Peter.png");
        imageArray[4] = new ColorImage("Sarah.png");
        imageArray[5] = new ColorImage("Susan.png");
        imageArray[6] = new ColorImage("Tom.png");
        imageArray[7] = new ColorImage("Wilson.png");
//        imageArray[8] = new ColorImage(
        
        random = new Random();
        canvas = new Canvas(WIDTH, HEIGHT);
        canvas.add(bg);
        
        // place the images randomly on the canvas
        for (int i = 0; i < NUMBER; i++) {
            
            int x = random.nextInt(WIDTH - imageArray[i].getWidth());
            int y = random.nextInt(HEIGHT - imageArray[i].getHeight());
            imageArray[i].setX(x);
            imageArray[i].setY(y);
            canvas.add(imageArray[i]);
        }
    }
    
    /**
     * Checks if the image array is sorted in ascending order.
     */
    public boolean isAscendinglySorted() {
        
        for (int i = 0; i < imageArray.length - 1; i++) {
            if (imageArray[i].getHeight() > imageArray[i+1].getHeight()) return false;
        }

        return true;
    }
    
    /**
     * Moves the image into position after sorting.
     */
    public void moveInPosition() {
        
        selectSort();
        
        int accumulatedWidth = 0;
        int accumulatedHeight = 0;
        int size = imageArray.length;
    
        for (int i = 0; i < size; i++) {
            
            ColorImage image = imageArray[i];
            image.setX(accumulatedWidth);
            image.setY(accumulatedHeight);
            image.setY(HEIGHT - image.getHeight());
            accumulatedWidth += image.getWidth();
        }    
    }
    
    /**
     * Sorts the image array by image area in ascending order.
     */
    private void selectSort() {
    
        int maxPos;
        
        for (int i = imageArray.length - 1; i > 0; i--) {
            maxPos = maxIndex(i + 1);
            swap(maxPos, i);
        }
    }
    
    /**
     * Finds the index of the color image with the maximum height.
     */
    private int maxIndex(int size) {
        
        int mIndex = 0 ;
        if (size > imageArray.length) size = imageArray.length;
        int maximuHiegit = imageArray[0].getHeight();
        /*
         * Complete the for loop below to find the index of the
         * image with the maximum height
         */
        for(int i = 0; i < size;i++) 
            if( imageArray[i].getHeight() > maximuHiegit)
                maximuHiegit = imageArray[i].getHeight();
        
        for (int i = 0; i < size; i++) {
            if (imageArray[i].getHeight() == maximuHiegit) 
                mIndex = i;
            
            
            
            
        }
        
        return mIndex;
    }
    
    /**
     * Swaps two color images in the images array.
     */
    private void swap(int index1, int index2) {
        
        ColorImage temp = imageArray[index1];
        imageArray[index1] = imageArray[index2];
        imageArray[index2] = temp;
    }
}
