import java.awt.*;
import javax.swing.*;                                           

class MyWindowDemo extends JFrame{
    public MyWindowDemo() {    
        setTitle("Window created using JFrame");      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocation(100,100);
        setSize(400,200);
        setVisible(true); 
    }                                                           
}
