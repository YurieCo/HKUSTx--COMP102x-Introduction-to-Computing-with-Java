import java.awt.*;
import javax.swing.*;  
import java.awt.event.*;                                       

class MyWindowDemo4 extends JFrame implements MouseListener {
    JLabel label;
    JButton button;
    
    public MyWindowDemo4() {    
        setTitle("Window created using JFrame");      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(600,200);
        setSize(400,200);
        setVisible(true); 
        
        JPanel content = new JPanel();                           
        content.setLayout(new FlowLayout());
        content.addMouseListener(this);
        
        label = new JLabel("Panel label");
        content.add(label);

        button = new JButton("Click Me");
        content.add(button);
        button.addMouseListener(this);
        
        setContentPane(content);                          
    } 
    public void mouseClicked(MouseEvent e) {
        Toolkit.getDefaultToolkit().beep();  
    }
    
    public void mouseEntered(MouseEvent e) {
       //Invoked when the mouse enters a component.
       label.setText("Entered" );
    }
	
    public void mouseExited(MouseEvent e) {
       //Invoked when the mouse exits a component.
       label.setText("Exited");
    }
	
    public void mousePressed(MouseEvent e) {
       //Invoked when a mouse button has been pressed on a component.
       label.setText("Pressed at " + e.getX() + " " + e.getY());
    }
 	
    public void mouseReleased(MouseEvent e) {
        //Invoked when a mouse button has been released on a component.
        label.setText("Released at " + e.getX() + " " + e.getY());
    }
   
}
