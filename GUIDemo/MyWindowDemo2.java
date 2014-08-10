import java.awt.*;
import javax.swing.*;                                          

class MyWindowDemo2 extends JFrame{
    public MyWindowDemo2() {    
        setTitle("Window created using JFrame");      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(100,100);
        setSize(400,200);
        setVisible(true); 
        
        JPanel content = new JPanel();   
        content.setLayout(new FlowLayout());
        
        JLabel label = new JLabel("My Panel");
        content.add(label);
        JButton JButton = new JButton("Click Me");
        content.add(JButton);
        
        setContentPane(content);                              
    } 
}

