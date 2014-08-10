import java.awt.*;
import javax.swing.*;  
import java.awt.event.*;                                       

class MyWindowDemo3 extends JFrame implements MouseListener {
    public MyWindowDemo3() {    
        setTitle("Window created using JFrame");      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(100,100);
        setSize(400,200);
        setVisible(true); 
        
        JPanel content = new JPanel();                           
        content.setLayout(new FlowLayout());
        
        JLabel label = new JLabel("Panel label");
        content.add(label);
        JButton button = new JButton("Click Me");
        content.add(button);
        button.addMouseListener(this);
        
        setContentPane(content);                          
    } 
    public void mouseClicked(MouseEvent e) {
        Toolkit.getDefaultToolkit().beep();  
    }
    
    public void mousePressed(MouseEvent e) {
        //IO.outputln("Button #" + e.getButton() + " is pressed.");
    }
    
    public void mouseReleased(MouseEvent e) {
        //IO.outputln("Button #" + e.getButton() + " is released.");
    }
    
    public void mouseEntered(MouseEvent e) {
        //IO.outputln("Mouse entered.");
    }
    
    public void mouseExited(MouseEvent e) {
        //IO.outputln("Mouse exited.");
    }
}
