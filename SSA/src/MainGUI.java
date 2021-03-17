import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainGUI {
    public MainGUI(){ }//ctor
    public void State() {
    	
    }
    public static void main(String[] args) {  
    	JFrame frame	=	new	JFrame();
    	Container	cp	=	frame.getContentPane();
    	cp.setLayout(new BorderLayout());
    	
        JButton button	=	new	JButton("CHOOSE ALG");
    	button.setBounds(0,0,120,100);
    	frame.add(button);
    	
    	JButton button1	=	new	JButton("KMP");
    	button1.setBounds(140,0,120,100);
    	DoKMPButton dob1	=	new	DoKMPButton(frame);
    	button1.addActionListener(dob1);
    	frame.add(button1);
    	
    	JButton button2	=	new	JButton("BM");
    	button2.setBounds(280,0,120,100);
    	DoBMButton dob2	=	new	DoBMButton();
    	button2.addActionListener(dob2);
    	frame.add(button2);

    	frame.setSize(500,500);
    	frame.setLayout(null); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true); 
    }
}
