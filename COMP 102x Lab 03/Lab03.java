import comp102x.ColorImage;
import comp102x.Canvas;

public class Lab03
{
    
    
    public void loadAnImage() 
    {
        // Please write your code after this line
    ColorImage Image = new ColorImage("A.jpg");
    Canvas a = new Canvas(Image.getWidth(), Image.getHeight());
        
    a.add(Image);    
        
    }
    
    public void loadTwoImagesOnTheSameCanvas()
    {
        // Please write your code after this line
        ColorImage Image1 = new ColorImage("A.jpg");
        Canvas a = new Canvas(2*Image1.getWidth(), Image1.getHeight());
        ColorImage Image2 = new ColorImage("B.jpg");
        
       

        a.add(Image1);
        a.add(Image2, Image2.getWidth()  % a.getCanvasWidth(),Image2.getWidth() % a.getCanvasHeight());
       
     //  a.add(Image2, 2*(Image2.getWidth() /Image2.getX()), (Image2.getY() / Image2.getY())*2);
  

        
        
    }
    
    public void imageAddition() 
    {    
        // Please write your code after this line
        ColorImage Image1 = new ColorImage("A.jpg");
        Canvas a = new Canvas(3*Image1.getWidth() + 20, Image1.getHeight());
        ColorImage Image2 = new ColorImage("B.jpg");
        
        ColorImage Image3 = new ColorImage("A.jpg");
             
       a.add(Image1);
       a.add(Image2, Image2.getWidth() % a.getCanvasWidth() + 10, Image2.getHeight() % a.getCanvasHeight()); // this works      
      
    //a.add(Image2, Image2.getWidth()+10,Image2.getHeight());
            
       a.add(Image3.add(Image2,Image1), 2*Image3.getWidth() %  a.getCanvasWidth() + 20, 2*Image3.getHeight() % a.getCanvasHeight()); // this is said to perform by condition
     
        
    }
   
    public void imageMultiplication() 
    {
        // Please write your code after this line
        
       ColorImage Image1 = new ColorImage("A.jpg");
        Canvas a = new Canvas(3*Image1.getWidth() + 20, Image1.getHeight());
        ColorImage Image2 = new ColorImage("B.jpg");
        
        ColorImage Image3 = new ColorImage("A.jpg");
             
       a.add(Image1);
       a.add(Image2, Image2.getWidth() % a.getCanvasWidth() + 10, Image2.getHeight() % a.getCanvasHeight()); // this works      
      
            
       a.add(Image3.multiply(Image2,Image1), 2*Image3.getWidth() %  a.getCanvasWidth() + 20, 2*Image3.getHeight() % a.getCanvasHeight()); // this is said to perform by condition
        
        
    }
    
    public void changeColor()
    {
        ColorImage image = new ColorImage();
        ColorImage img = new ColorImage("A.jpg");
        image.increaseRed(40);
        img.invert();
        img.createCyanImage();
        Canvas canvas = new Canvas(4*image.getWidth()+20, image.getHeight()*4+20);
        canvas.add(image);
 
        canvas.add(img, img.getWidth() % canvas.getCanvasHeight(),img.getHeight() % canvas.getCanvasWidth());
        
        //image.save();
    }
}
