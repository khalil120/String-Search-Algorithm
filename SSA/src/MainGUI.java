import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;


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

    	JRadioButton r1=new JRadioButton("A) Automatic");    
    	JRadioButton r2=new JRadioButton("B) Manual ");    
    	r1.setBounds(250,0,100,30);    
    	r2.setBounds(250,130,100,30); 
    	frame.add(r1);frame.add(r2); 
    	
    	frame.setSize(500,500);
    	frame.setLayout(null); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true); 
    }
}
