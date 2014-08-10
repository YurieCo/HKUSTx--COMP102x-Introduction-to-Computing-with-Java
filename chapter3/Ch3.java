import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ch3 extends JFrame implements MouseListener{
		
	public Ch3() {
		// this is constructor 
	}	
	public void showMEssage(String from, String mess){
		JOptionPane.showMessageDialog(null,"Message from "+ from +"\n Message:"+mess);
		
	}
	
	public void showPacateTOName(String name){
		Random n = new Random();
		JOptionPane.showConfirmDialog(null, "Iurie "+n.nextFloat()+ " sins" );
	}
	public void showAnother(){
		Component  store = null ;
		JOptionPane.showConfirmDialog(store, "Are you rich ? ");
	}
	
	public void windJavaEdx(int height, int width){
		setTitle("this Is my Window ");
		setSize(height,width);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		JPanel content = new JPanel();
		content.setLayout(new FlowLayout());
		JLabel label =  new JLabel("this is my label");
		content.add(label);
		JButton button  = new JButton("Click here ");
		content.add(button);
		
		setContentPane(content);
	}

	
	public void readFromFile(){
		File f = new File("README.TXT");
		try {
			Scanner input = new Scanner(f);
			String data;
			for(int i = 0; input.hasNextLine();i++){
				data = input.nextLine();
				System.out.println(data + " ");
			}
			
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void windJavaEdxP3(int height, int width){
		setTitle("this Is my Window ");
		setSize(height,width);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		JPanel content = new JPanel();
		
		content.setLayout(new FlowLayout());
		
		JLabel label =  new JLabel("this is my label");
		JLabel label2 = new JLabel("Read from the file");
		content.add(label);
		content.add(label2);
		JButton button  = new JButton("Click here ");
		JButton but2 = new JButton("read");
		content.add(button);
		content.add(but2);
		button.addMouseListener(this);
		setContentPane(content);
		
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Toolkit.getDefaultToolkit().beep();
		showPacateTOName("You ");
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
