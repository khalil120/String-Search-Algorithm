import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class MainGUI implements ActionListener{
	
	private JPanel selectAlg;
	private JRadioButton r1;
	private JRadioButton r2;
	private JRadioButton ra1;
	private JRadioButton ra2;
	private JRadioButton rb1;
	private JRadioButton rb2;
	private JTextField inputField;
	private JTextField patField;

    public MainGUI(){ 
    	selectAlg = new JPanel();
    	init();

    }
    private void init() {

    	JPanel runType = new JPanel();
    	JPanel inputPanel = new JPanel();
    	
        JLabel algText	=	new	JLabel("CHOOSE ALGORITHM: ");
        algText.setFont(new Font(algText.getFont().getName(), Font.PLAIN, 18));
    	selectAlg.add(algText);  	
    	ra1 = new JRadioButton("A) BM ");    
    	ra2 = new JRadioButton("B) KMP ");
    	ButtonGroup G1 = new ButtonGroup();
    	G1.add(ra1);
    	G1.add(ra2);
    	selectAlg.add(ra1);
    	selectAlg.add(ra2);
    	selectAlg.setLayout(new BoxLayout(selectAlg, BoxLayout.Y_AXIS));
    	
        JLabel typeText	=	new	JLabel("Run Type: ");
        typeText.setFont(new Font(typeText.getFont().getName(), Font.PLAIN, 18));
        runType.add(typeText); 
    	r1 = new JRadioButton("A) Automatic");    
    	r2 = new JRadioButton("B) Manual ");
    	ButtonGroup G2 = new ButtonGroup();
    	G2.add(r1);
    	G2.add(r2);
    	runType.add(r1);
    	runType.add(r2);
    	runType.setLayout(new BoxLayout(runType, BoxLayout.Y_AXIS));
    	selectAlg.add(runType);
    	
    	JLabel inputlbl	=	new	JLabel("Input: ");
    	inputlbl.setFont(new Font(inputlbl.getFont().getName(), Font.PLAIN, 18));
    	inputPanel.add(inputlbl);
    	rb1 = new JRadioButton("Current File");
    	rb2 = new JRadioButton("Manual Input");
    	inputField = new JTextField(30);
    	JLabel patlbl	=	new	JLabel("Pattern: ");
    	patlbl.setFont(new Font(patlbl.getFont().getName(), Font.PLAIN, 18));
    	patField = new JTextField(30);
    	ButtonGroup G3 = new ButtonGroup();
    	G3.add(rb1);
    	G3.add(rb2);
    	inputPanel.add(rb1);
    	inputPanel.add(rb2);
    	inputPanel.add(inputField);
    	inputPanel.add(patlbl);
    	inputPanel.add(patField);
    	inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    	selectAlg.add(inputPanel);
    	
    	JButton start = new JButton("start");
    	start.addActionListener(this);
    	start.setBackground(new Color(59, 89, 182));
    	start.setForeground(Color.WHITE);
    	start.setFocusPainted(false);
    	start.setFont(new Font("Tahoma", Font.BOLD, 12));
    	selectAlg.add(start);
    	

    }
    
    public void show() {
    	
    	JFrame frame	=	new	JFrame();
    	frame.setTitle("String Search Algorithms");
    	Container	cp	=	frame.getContentPane();
    	cp.setLayout(new BorderLayout());
    	
    	frame.add(selectAlg);    	
    	frame.pack();
    	frame.setSize(500,500);
    	frame.setLayout(null); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true); 
    	
    }
    

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("clicked!!!");
		DoBMButton run;
		BM bm ;
		if(ra1.isSelected()) {
			System.out.println("BM");
			//to be changed - new featuers - BUG HERE will Fix Soon 
			String pat = patField.getName();
			if(rb1.isSelected()) {
				//input from file
				bm = new BM("ABCDABD");
				String input = bm.getInput();
				String patt = bm.getPattern();
				run = new DoBMButton( input, patt);
				run.actionPerformed(null);
				if(r1.isSelected())
					 run.getBm().setSearchtype(true);
				else
					run.getBm().setSearchtype(false);
			}else {
				String text = inputField.getText();
				String txt = patField.getText();
				bm = new BM(text,txt);
				run = new DoBMButton(text,txt);
				run.actionPerformed(null);
				if(r1.isSelected())
					 run.getBm().setSearchtype(true);
				else
					run.getBm().setSearchtype(false);
			}
			
		}else {
			System.out.println("KMP");
		}
	
	}
	
	
    public static void main(String[] args) { 
    	
    	MainGUI GUI = new MainGUI();
    	GUI.show();
    }
}
