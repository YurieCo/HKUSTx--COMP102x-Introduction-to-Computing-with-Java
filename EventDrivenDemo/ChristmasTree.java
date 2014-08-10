import comp102x.Canvas;
import comp102x.ColorImage;
import java.util.Random;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class ChristmasTree implements MouseListener {
    private ColorImage background;
    private Canvas canvas;
    
    private int nImage = 10;
    private ColorImage[] ornaments = new ColorImage[nImage];
    
    public ChristmasTree() {       
        canvas = new Canvas(600, 600);
        background = new ColorImage("xmastree.png");
        background.setMovable(false);
        
        for (int i = 0; i < nImage; i++)
             ornaments[i] = new ColorImage("image/Picture" + (i+1) + ".png");    
    }
    
    public void demo() {   
        canvas.add(background);
        canvas.addMouseListener(this);
    }
    
    public void mouseClicked(MouseEvent e) {
       int x = e.getX();
       int y = e.getY();
            
       Random random = new Random();
       
       // Modify scale to an appropriate value using the size of image
       double scale = 1;

       ColorImage image = new ColorImage(ornaments[random.nextInt(10)]);

       canvas.add(image, x - (int)(image.getWidth() * scale / 2), y - (int)(image.getHeight() * scale / 2));    
    }
    
    public void mousePressed(MouseEvent e) { }
    
    public void mouseReleased(MouseEvent e) { }
    
    public void mouseEntered(MouseEvent e) { }
    
    public void mouseExited(MouseEvent e) { }
}