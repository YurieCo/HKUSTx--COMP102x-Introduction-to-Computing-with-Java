import comp102x.IO;
import comp102x.Canvas;
import comp102x.ColorImage;

/**
 * Write a description of class withCanvas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class withCanvas
{

public Canvas canvas = new Canvas();
     public ColorImage image1 = new ColorImage();

     public void setAndOutput() {

          canvas.add(image1, 0, 0);
          image1.setX(50);
          image1.setY(100);
          image1.setRotation(45);

          IO.output("x: "+image1.getX() + " y: "+image1.getY()+"\n");
          
          image1.setY(image1.getX() + 100);
          image1.setRotation(image1.getRotation() - 90);

          IO.output(" X: " + image1.getX());
          IO.output(" Y: " + image1.getY());
          IO.output(" A: " + image1.getRotation());
        }
}
