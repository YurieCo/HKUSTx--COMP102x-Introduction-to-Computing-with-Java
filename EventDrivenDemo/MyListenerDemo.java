import comp102x.Canvas;
import comp102x.ColorImage;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MyListenerDemo implements MouseListener {
    private Canvas canvas;
    
    public MyListenerDemo() {       
        canvas = new Canvas();
        canvas.addMouseListener(this);
    }
    
    public void mouseClicked(MouseEvent e) {  
       ColorImage image = new ColorImage("happyFace.png");
       int x = e.getX() - image.getWidth()/2;
       int y = e.getY() - image.getHeight()/2; 
       canvas.add(image, x, y);    
    }
    
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}
